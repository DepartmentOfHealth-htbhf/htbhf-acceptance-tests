package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;

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
    public void quitDriver() {
        if (isBrowserStackProfile()) {
            log.info("Calling quit on WebDriver, active profile is: {}", activeProfile);
            webDriverWrapper.quitDriver();
        }
    }

    private boolean isBrowserStackProfile() {
        return "browserstack".equals(activeProfile);
    }
}
