package uk.gov.dhsc.htbhf.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.dhsc.htbhf.TestResultHandler;
import uk.gov.dhsc.htbhf.WebDriverWrapper;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

/**
 * Base setup for all the step classes which autowires in the web driver and anything
 * else need to setup pages and perform steps.
 */

public abstract class BaseSteps {

    @Autowired
    protected WebDriverWrapper webDriverWrapper;

    @Autowired
    protected WireMockManager wireMockManager;

    @Autowired
    protected TestResultHandler testResultHandler;

    @Value("${base.url}")
    protected String baseUrl;

    @Value("${session.details.url}")
    protected String sessionDetailsUrl;

    @Value("${spring.profiles.active}")
    protected String activeProfile;

    protected WebDriver getWebDriver() {
        return webDriverWrapper.getWebDriver();
    }

    protected WebDriverWait getWebDriverWait() {
        return webDriverWrapper.getWebDriverWait();
    }

}
