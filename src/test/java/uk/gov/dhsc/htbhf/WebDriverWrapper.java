package uk.gov.dhsc.htbhf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.Pages;

/**
 * Wraps the lifecycle of a WebDriver and WebDriverWait.
 */
public interface WebDriverWrapper {

    WebDriver getWebDriver();

    WebDriverWait getWebDriverWait();

    Pages getPages();

    void initDriver();

    void quitDriver();

}
