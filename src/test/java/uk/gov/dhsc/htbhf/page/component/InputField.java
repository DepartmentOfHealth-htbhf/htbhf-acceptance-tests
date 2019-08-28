package uk.gov.dhsc.htbhf.page.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Component representing an input field on a page.
 */
public class InputField extends BaseComponent {

    private String inputFieldId;

    public InputField(WebDriver webDriver, WebDriverWait webDriverWait, String inputFieldId) {
        super(webDriver, webDriverWait);
        this.inputFieldId = inputFieldId;
    }

    public String getValue() {
        return getElement().getAttribute("value");
    }

    public void enterValue(String value) {
        getElement().sendKeys(value);
    }

    public void clearValue() {
        getElement().clear();
    }

    public WebElement getElement() {
        return findById(inputFieldId);
    }

    public String getInputErrorId() {
        return inputFieldId + "-error";
    }

    public String getInputErrorLinkCss() {
        return String.format("a[href=\"#%s-error\"]", inputFieldId);
    }

}
