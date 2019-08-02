package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base page all page objects which contains all common utility and navigation methods.
 */
public abstract class BasePage {

    WebDriver webDriver;

    private final String baseUrl;
    private final WebDriverWait wait;

    public BasePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
        this.wait = wait;
    }

    abstract String getPath();

    abstract String getPageName();

    abstract String getPageTitle();

    public void open() {
        String fullUrl = baseUrl + getPath();
        webDriver.get(fullUrl);
    }

    public WebElement findByClassName(String className) {
        return webDriver.findElement(By.className(className));
    }

    public WebElement findByCss(String cssSelector) {
        return webDriver.findElement(By.cssSelector(cssSelector));
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.titleIs(getPageTitle()));
    }

}
