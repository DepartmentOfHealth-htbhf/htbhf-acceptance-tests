package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Contains functions for all components on a page.
 */
public abstract class BaseComponent {

    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;

    public BaseComponent(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    protected WebElement findByClassName(String className) {
        return webDriver.findElement(By.className(className));
    }

    protected WebElement findByCss(String cssSelector) {
        return webDriver.findElement(By.cssSelector(cssSelector));
    }

    protected WebElement findById(String id) {
        return webDriver.findElement(By.id(id));
    }

    protected WebElement findByXpath(String xpathExpression) {
        return webDriver.findElement(By.xpath(xpathExpression));
    }

    protected List<WebElement> findAllByCss(String cssSelector) {
        return webDriver.findElements(By.cssSelector(cssSelector));
    }

    protected List<WebElement> findAllById(String id) {
        return webDriver.findElements(By.id(id));
    }

    protected List<WebElement> findAllByXpath(String xpathExpression) {
        return webDriver.findElements(By.xpath(xpathExpression));
    }

    protected void click(By by) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(by));
        WebElement element = webDriver.findElement(by);
        element.click();
    }

    protected String getHrefForElement(WebElement webElement) {
        return webElement.getAttribute("href");
    }
}
