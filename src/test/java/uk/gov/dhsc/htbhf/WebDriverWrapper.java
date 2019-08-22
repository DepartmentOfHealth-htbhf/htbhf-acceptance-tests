package uk.gov.dhsc.htbhf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Wraps the lifecycle of a WebDriver and WebDriverWait.
 */
public interface WebDriverWrapper {

    WebDriver getWebDriver();

    WebDriverWait getWebDriverWait();

    void initDriver();

    void quitDriver();

    void closeDriver();

}
