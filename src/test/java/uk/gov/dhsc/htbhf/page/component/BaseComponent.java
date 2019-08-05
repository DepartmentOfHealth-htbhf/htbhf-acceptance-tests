package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Contains functions for all components on a page.
 */
public abstract class BaseComponent {

    private final WebDriver webDriver;

    public BaseComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement findByClassName(String className) {
        return webDriver.findElement(By.className(className));
    }

    public WebElement findByCss(String cssSelector) {
        return webDriver.findElement(By.cssSelector(cssSelector));
    }

    public WebElement findById(String id) {
        return webDriver.findElement(By.id(id));
    }
}
