package uk.gov.dhsc.htbhf.browserstack;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.WebDriverWrapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static uk.gov.dhsc.htbhf.browserstack.BrowserStackCapabilities.buildDesiredCapabilities;
import static uk.gov.dhsc.htbhf.browserstack.BrowserStackCapabilities.getBrowserStackCapabilities;

/**
 * Controls building of the WebDriver and WebDriverWait needed for BrowserStack testing so that we can rebuild them when required.
 */
@Slf4j
public class BrowserStackDriverWrapper implements WebDriverWrapper {

    private static final String BROWSER_STACK_URL = "https://hub-cloud.browserstack.com/wd/hub";

    private final int waitTimeoutInSeconds;

    private final String browserStackUser;

    private final String browserStackKey;

    private final URL browserStackUrl;

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private ThreadLocal<WebDriverWait> webDriverWait = new ThreadLocal<>();

    public BrowserStackDriverWrapper(String browserStackUser, String browserStackKey, int waitTimeoutInSeconds) {
        Validate.notBlank(browserStackUser, "BrowserStack user must be provided, set the BROWSER_STACK_USER environment variable");
        Validate.notBlank(browserStackKey, "BrowserStack key must be provided, set the BROWSER_STACK_KEY environment variable");
        this.browserStackUser = browserStackUser;
        this.browserStackKey = browserStackKey;
        this.waitTimeoutInSeconds = waitTimeoutInSeconds;
        this.browserStackUrl = buildBrowserStackUrl();
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    @Override
    public WebDriverWait getWebDriverWait() {
        return webDriverWait.get();
    }

    @Override
    public void initDriver() {
        Map<String, String> browserStackCapabilities = getBrowserStackCapabilities();
        log.info("Using capabilities: {}", browserStackCapabilities);
        DesiredCapabilities desiredCapabilities = buildDesiredCapabilities(browserStackCapabilities, browserStackUser, browserStackKey);
        this.webDriver.set(buildWebDriver(desiredCapabilities));
        this.webDriverWait.set(buildWebDriverWait());
    }

    @Override
    public void quitDriver() {
        getWebDriver().quit();
    }

    @Override
    public void closeDriver() {
        getWebDriver().close();
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
        return new WebDriverWait(getWebDriver(), waitTimeoutInSeconds);
    }

    private RemoteWebDriver buildWebDriver(DesiredCapabilities desiredCapabilities) {
        return new RemoteWebDriver(browserStackUrl, desiredCapabilities);
    }
}
