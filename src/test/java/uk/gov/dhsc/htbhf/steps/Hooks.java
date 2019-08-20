package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseSteps {

    @Before
    public void initDriver() {
        if ("browserstack".equals(activeProfile)) {
            webDriverWrapper.initDriver();
        }
    }

    @After
    public void quitDriver() {
        //TODO MRS 2019-08-14: Potentially need a property so that we quit the webdriver only after BrowserStack tests.
        // Will also need to create a new WebDriver before each BrowserStack test.
        if ("browserstack".equals(activeProfile)) {
            System.out.println("Calling quit on WebDriver, active profile is: " + activeProfile);
            //We need to always quit the WebDriver for BrowserStack tests so we need to rebuild one for the next test.
            webDriverWrapper.quitDriver();
        }
    }
}
