package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The submit button on a page.
 */
public class SubmitButton extends BaseComponent {

    private static final String ID = "submit-button";

    public SubmitButton(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public void click() {
        click(By.id(ID));
    }

    public void doubleClick() {
        doubleClick(By.id(ID));
    }

    public String getSubmitButtonText() {
        WebElement submitButton = findById(ID);
        return submitButton.getText();
    }
}
