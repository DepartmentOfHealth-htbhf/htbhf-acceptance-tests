package uk.gov.dhsc.htbhf.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import uk.gov.dhsc.htbhf.CucumberConfiguration;

/**
 * Base setup for all the step classes which autowires in the web driver and anything
 * else need to setup pages and perform steps.
 */
@ContextConfiguration(classes = {CucumberConfiguration.class})
public abstract class BaseSteps {

    @Autowired
    private WebDriver webDriver;
    @Autowired
    private WebDriverWait webDriverWait;

    @Value("${base.url}")
    String baseUrl;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }
}
