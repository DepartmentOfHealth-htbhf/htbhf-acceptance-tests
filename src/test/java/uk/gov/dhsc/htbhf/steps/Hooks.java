package uk.gov.dhsc.htbhf.steps;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import uk.gov.dhsc.htbhf.TestResult;

@Slf4j
public class Hooks extends BaseSteps {

    private static ThreadLocal<String> sessionIdThreadLocal = new ThreadLocal<>();

    public static ThreadLocal<String> getSessionIdThreadLocal() {
        return sessionIdThreadLocal;
    }

    /**
     * As we have to always quit the driver for BrowserStack tests, we need to initialise a new one for each new test.
     */
    @Before
    public void initDriver() {
        webDriverWrapper.initDriver();
    }

    /**
     * We need to always quit the WebDriver for BrowserStack tests.
     */
    @After
    public void finaliseTest(Scenario scenario) {
        try {
            if (isBrowserStackProfile()) {
                String sessionId = getSessionId();
                sessionIdThreadLocal.set(sessionId);
                TestResult sessionDetails = new TestResult(scenario);
                testResultHandler.handleResults(sessionDetails, sessionId);
            }
            wireMockManager.resetWireMockStubs();
        } catch (Exception e) {
            log.error("Unexpected Exception caught finalising scenario: " + scenario.getName(), e);
        } finally {
            webDriverWrapper.quitDriver();
        }
    }

    private boolean isBrowserStackProfile() {
        return "browserstack".equals(activeProfile);
    }

    private String getSessionId() {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getWebDriver();
        SessionId sessionId = remoteWebDriver.getSessionId();
        return (sessionId != null) ? sessionId.toString() : "Not available";
    }
}
