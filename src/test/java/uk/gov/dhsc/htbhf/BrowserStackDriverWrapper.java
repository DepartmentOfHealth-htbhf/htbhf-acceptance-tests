package uk.gov.dhsc.htbhf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static uk.gov.dhsc.htbhf.BrowserStackCapabilities.buildDesiredCapabilities;
import static uk.gov.dhsc.htbhf.BrowserStackCapabilities.getBrowserStackCapabilities;

/**
 * Controls building of the WebDriver and WebDriverWait needed for BrowserStack testing so that we can rebuild them when required.
 */
@Slf4j
public class BrowserStackDriverWrapper implements WebDriverWrapper {

    private static final String BROWSER_STACK_URL = "https://hub-cloud.browserstack.com/wd/hub";

    private final int waitTimeoutInSeconds;

    private final DesiredCapabilities desiredCapabilities;

    private final URL browserStackUrl;

    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

    public BrowserStackDriverWrapper(String browserStackUser, String browserStackKey, int waitTimeoutInSeconds) {
        Validate.notBlank(browserStackUser, "BrowserStack user must be provided, set the BROWSER_STACK_USER environment variable");
        Validate.notBlank(browserStackKey, "BrowserStack key must be provided, set the BROWSER_STACK_KEY environment variable");
        log.info("Using login: {} {}", browserStackUser, browserStackKey);
        this.waitTimeoutInSeconds = waitTimeoutInSeconds;
        Map<String, String> browserStackCapabilities = getBrowserStackCapabilities(System.getProperties());
        log.info("Using capabilities: {}", browserStackCapabilities);
        this.desiredCapabilities = buildDesiredCapabilities(browserStackCapabilities, browserStackUser, browserStackKey);
        this.browserStackUrl = buildBrowserStackUrl();
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    @Override
    public void initDriver() {
        this.webDriver = buildWebDriver();
        this.webDriverWait = buildWebDriverWait();
    }

    @Override
    public void quitDriver() {
        webDriver.quit();
    }

    @Override
    public void closeDriver() {
        webDriver.close();
    }

    //Simply to deal with the checked Exception
    private URL buildBrowserStackUrl() {
        try {
            return new URL(BROWSER_STACK_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to build BrowserStack URL", e);
        }
    }

    private WebDriverWait buildWebDriverWait() {
        return new WebDriverWait(webDriver, waitTimeoutInSeconds);
    }

    private RemoteWebDriver buildWebDriver() {
        return new RemoteWebDriver(browserStackUrl, desiredCapabilities);
    }
}
