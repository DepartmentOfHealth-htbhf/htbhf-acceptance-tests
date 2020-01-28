package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.SubmitButton;

/**
 * Base class for any page with a submit/continue button.
 */
public abstract class SubmittablePage extends BasePage {

    private SubmitButton submitButton;

    protected SubmittablePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.submitButton = new SubmitButton(webDriver, wait);
    }

    public void clickContinue() {
        submitButton.click();
    }

    public void doubleClickContinue() {
        submitButton.doubleClick();
    }

    public String getSubmitButtonText() {
        return submitButton.getSubmitButtonText();
    }

}
