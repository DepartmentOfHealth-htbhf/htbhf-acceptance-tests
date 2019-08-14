package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.After;

public class Hooks extends BaseSteps {
    @After
    public void quitDriver() {
        //TODO MRS 2019-08-14: Potentially need a property so that we quit the webdriver only after BrowserStack tests.
        // Will also need to create a new WebDriver before each BrowserStack test.
        if ("browserstack".equals(activeProfile)) {
            System.out.println("Calling quit on WebDriver, active profile is: " + activeProfile);
            //We need to always quit the WebDriver for BrowserStack tests.
            webDriver.quit();
        }
    }
}
