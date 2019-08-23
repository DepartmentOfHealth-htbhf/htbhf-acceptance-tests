package uk.gov.dhsc.htbhf;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * Test launcher for starting the compatibility tests without using the Gradle test task so we can control
 * how the tests are run and with what properties (a.k.a. driver capabilities)
 */
@Slf4j
public class BrowserStackLauncher {

    private static final int MAX_THREADS = 5;
    private static ThreadLocal<String> testNameLocal = new ThreadLocal<>();
    private static ExecutorService executorService;

    private static final List<String> TEST_NAMES = List.of(
            "mobile-android-galaxys9",
            "windows-win10-chrome",
            "windows-win10-firefox",
            "windows-win10-edge",
            "mac-mojave-chrome",
            "mac-mojave-firefox"
    );

    public static void main(String[] args) {
        setRootLoggerLevel();
        try {
            executorService = Executors.newFixedThreadPool(MAX_THREADS);

            CompletableFuture[] completableFuturesArray = TEST_NAMES.stream()
                    .map(BrowserStackLauncher::runAndOutput)
                    .toArray(CompletableFuture[]::new);
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(completableFuturesArray);

            combinedFuture.get();
        } catch (InterruptedException e) {
            log.error("InterruptedException caught trying to run task", e);
        } catch (ExecutionException e) {
            log.error("ExecutionException caught trying to run task", e);
        } finally {
            executorService.shutdown();
        }
    }

    //TODO MRS 2019-08-24: Seems to be ignoring the root logger level in application.properties so setting here for now.
    private static void setRootLoggerLevel() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }

    public static String getTestName() {
        return testNameLocal.get();
    }

    public static void setTestName(String testName) {
        testNameLocal.set(testName);
    }

    private static CompletableFuture<Void> runAndOutput(String testName) {
        CompletableFuture<TestExecutionSummary> completableFuture = CompletableFuture.supplyAsync(() -> runTest(testName), executorService);
        return completableFuture.thenAccept(testExecutionSummary -> outputSummary(testExecutionSummary, testName));
    }

    private static TestExecutionSummary runTest(String testName) {
        log.info(">>>>>>>>>>>>Running test for compatibility name: {}", testName);
        setTestName(testName);
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(RunCompatibilityTests.class))
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        return listener.getSummary();
    }

    private static void outputSummary(TestExecutionSummary summary, String testName) {
        //TODO MRS 2019-08-23: Do something with the TestExecutionSummary, re-run if there any failures
        log.info(">>>>>>>>>>>>>>>Test summary for test with name: {}", testName);
        PrintWriter outFile = new PrintWriter(System.out, true);
        summary.printTo(outFile);
        summary.getFailures().forEach(failure -> failure.getException().printStackTrace());
    }

}
