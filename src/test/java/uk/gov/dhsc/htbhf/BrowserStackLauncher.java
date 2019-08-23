package uk.gov.dhsc.htbhf;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * Test launcher for starting the compatibility tests without using the Gradle test task so we can control
 * how the tests are run and with what properties (a.k.a. driver capabilities)
 */
@Slf4j
public class BrowserStackLauncher {

    public static final String BROWSER_STACK_TEST_NAME = "BROWSERSTACK.TEST.NAME";

    public static void main(String[] args) {
        //TODO MRS 2019-08-23: Add in all the test permutations and run them in parallel with a max of 5 threads.
        runTest("windows-win10-chrome");
        runTest("windows-win10-firefox");
    }

    private static void runTest(String testName) {
        log.info("Running test for compatibility name: {}", testName);
        System.setProperty(BROWSER_STACK_TEST_NAME, testName);
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(RunCompatibilityTests.class))
                .build();

        Launcher launcher = LauncherFactory.create();

        // Register a listener of your choice
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        //TODO MRS 2019-08-23: Do something with the TestExecutionSummary, re-run if there anhy failures
        PrintWriter outFile = new PrintWriter(System.out, true);
        summary.printTo(outFile);
        summary.getFailures().forEach(failure -> failure.getException().printStackTrace());
    }

}
