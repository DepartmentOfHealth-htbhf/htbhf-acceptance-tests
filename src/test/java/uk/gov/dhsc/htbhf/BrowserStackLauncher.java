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
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import uk.gov.dhsc.htbhf.browserstack.RetryTriggerException;

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
    private static final int MAX_RETRY_ATTEMPTS = 3;
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

    public static String getTestName() {
        return testNameLocal.get();
    }

    public static void setTestName(String testName) {
        testNameLocal.set(testName);
    }

    public static void main(String[] args) {
        setRootLoggerLevel();
        try {
            executorService = Executors.newFixedThreadPool(MAX_THREADS);

            CompletableFuture[] completableFuturesArray = TEST_NAMES.stream()
                    .map(testName -> CompletableFuture.supplyAsync(() -> runTest(testName), executorService))
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
        if (!CollectionUtils.isEmpty(summary.getFailures())) {
            int attemptNumber = context.getRetryCount() + 1;
            if (attemptNumber < MAX_RETRY_ATTEMPTS) {
                log.error("Test [{}] had failures on attempt number [{}] so triggering a retry.", testName, attemptNumber);
            }
            throw new RetryTriggerException();
        }
    }

    //TODO MRS 2019-08-24: Seems to be ignoring the root logger level in application.properties so setting here for now.
    private static void setRootLoggerLevel() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);
    }

}
