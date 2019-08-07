package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object that retrieves the confirmation code via loading the page with WebDriver.
 */
public class ConfirmationCodePage {

    private WebDriver webDriver;
    private String sessionDetailsUrl;

    public ConfirmationCodePage(WebDriver webDriver, String sessionDetailsUrl) {
        this.webDriver = webDriver;
        this.sessionDetailsUrl = sessionDetailsUrl;
    }

    public String getConfirmationCodeForSession() {
        webDriver.get(sessionDetailsUrl);
        WebElement body = webDriver.findElement(By.xpath("html/body"));
        return body.getText();
    }

}
