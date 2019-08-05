package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * The submit button on a page.
 */
public class SubmitButton extends BaseComponent {

    private static final String ID = "submit-button";

    public SubmitButton(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement get() {
        return findById(ID);
    }

    public void click() {
        WebElement submitButton = get();
        submitButton.click();
    }

    public String getSubmitButtonText() {
        WebElement submitButton = get();
        return submitButton.getText();
    }
}
