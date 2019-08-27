package uk.gov.dhsc.htbhf.steps;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.gov.dhsc.htbhf.TestResult;

@Slf4j
public class Hooks extends BaseSteps {

    /**
     * As we have to always quit the driver for BrowserStack tests, we need to initialise a new one for each new test.
     */
    @Before
    public void initDriver() {
        if (isBrowserStackProfile()) {
            webDriverWrapper.initDriver();
        }
    }

    /**
     * We need to always quit the WebDriver for BrowserStack tests.
     */
    @After
    public void finaliseTest(Scenario scenario) {
        if (isBrowserStackProfile()) {
            try {
                String sessionId = getSessionId();
                log.info("Uploading results for test with sessionId: {}", sessionId);
                TestResult sessionDetails = new TestResult(scenario);
                testResultHandler.handleResults(sessionDetails, sessionId);
            } finally {
                webDriverWrapper.quitDriver();
            }
        }
    }

    private boolean isBrowserStackProfile() {
        return "browserstack".equals(activeProfile);
    }

    private String getSessionId() {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getWebDriver();
        return remoteWebDriver.getSessionId().toString();
    }
}
