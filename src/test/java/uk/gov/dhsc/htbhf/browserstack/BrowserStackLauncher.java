package uk.gov.dhsc.htbhf.browserstack;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import uk.gov.dhsc.htbhf.RunCompatibilityTests;
import uk.gov.dhsc.htbhf.steps.Hooks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * Test launcher for starting the compatibility tests without using the Gradle test task so we can control
 * how the tests are run and with what properties (a.k.a. driver capabilities)
 */
@Slf4j
public class BrowserStackLauncher {

    private static final int MAX_THREADS = 5;
    private static final int MAX_RETRY_ATTEMPTS = 4;
    private static final int TOTAL_TIMEOUT_MINS = 15;
    private static final String COMPATIBILITY_REPORT_DIR = "build/reports/compatibility-report";
    private static final String COMPATIBILITY_REPORT_FILE = COMPATIBILITY_REPORT_DIR + "/index.html";
    private static ThreadLocal<String> testNameLocal = new ThreadLocal<>();
    private static ExecutorService executorService;
    private static final List<TestResultSummary> results = new CopyOnWriteArrayList<>();

    private static final List<String> TEST_NAMES = List.of(
            "mobile-android-galaxys8",
            "mobile-android-pixel",
            "mobile-android-pixel3",
            "mobile-ios-iphone8",
            "mobile-ios-iphonexs",
            "tablet-ios-ipad6",
            "tablet-ios-ipad-mini4",
            "tablet-ios-ipad-pro-11",
            "windows-win10-chrome",
            "windows-win10-firefox",
            "windows-win10-edge",
            "windows-win10-ie",
            "mac-mojave-safari",
            "mac-mojave-chrome",
            "mac-mojave-firefox"
    );

    public static String getTestName() {
        return testNameLocal.get();
    }

    public static void setTestName(String testName) {
        testNameLocal.set(testName);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        try {
            executorService = Executors.newFixedThreadPool(MAX_THREADS);

            CompletableFuture[] completableFuturesArray = TEST_NAMES.stream()
                    .map(testName -> CompletableFuture.supplyAsync(() -> runTest(testName), executorService))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(completableFuturesArray);

            combinedFuture.get(TOTAL_TIMEOUT_MINS, TimeUnit.MINUTES);

            outputReport();

            if (anyTestsFailed()) {
                log.error("Some tests failed - please see the test report for details");
                System.exit(1);
            }
        } finally {
            executorService.shutdown();
        }
    }

    private static TestExecutionSummary runTest(String testName) {
        log.info("Running compatibility test: [{}]", testName);
        setTestName(testName);

        try {
            RetryTemplate retryTemplate = buildRetryTemplate();
            return retryTemplate.execute(context -> runTest(testName, context));
        } catch (RetryTriggerException r) {
            log.error("Max retries of [{}] exceeded for test [{}], compatibility test failed.", MAX_RETRY_ATTEMPTS, testName);
        } catch (Throwable t) {
            log.error("Unexpected exception caught trying to run the test: {}" + testName, t);
        }
        return null;
    }

    private static RetryTemplate buildRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(MAX_RETRY_ATTEMPTS);
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        return retryTemplate;
    }

    private static TestExecutionSummary runTest(String testName, RetryContext context) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(RunCompatibilityTests.class))
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        checkForFailures(testName, context, summary);
        return summary;
    }

    private static void checkForFailures(String testName, RetryContext context, TestExecutionSummary summary) {
        int attemptNumber = context.getRetryCount() + 1;
        storeTestRunSummary(testName, summary, attemptNumber);
        if (!CollectionUtils.isEmpty(summary.getFailures())) {
            if (attemptNumber < MAX_RETRY_ATTEMPTS) {
                log.error("Test [{}] had failures on attempt number [{}] so triggering a retry.", testName, attemptNumber);
            }
            throw new RetryTriggerException();
        }
    }

    private static void storeTestRunSummary(String testName, TestExecutionSummary summary, int attempt) {
        String sessionId = Hooks.getSessionIdThreadLocal().get();
        TestResultSummary result = new TestResultSummary(summary, testName, attempt, sessionId);
        results.add(result);
    }

    private static void outputReport() throws IOException {
        File reportDir = new File(COMPATIBILITY_REPORT_DIR);
        reportDir.mkdirs();
        TestOutputHtmlGenerator.generateHtmlReport(results, COMPATIBILITY_REPORT_FILE);
        log.info("Compatibility test report output to: {}", COMPATIBILITY_REPORT_FILE);
    }

    private static boolean anyTestsFailed() {
        return results.stream().anyMatch(summary -> !summary.isPassed() && summary.getAttempts() == MAX_RETRY_ATTEMPTS);
    }

}
