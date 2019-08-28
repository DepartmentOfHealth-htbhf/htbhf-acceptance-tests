package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter code page.
 */
public class EnterCodePage extends SubmittablePage {

    private static final String CONFIRMATION_CODE_INPUT_ID = "confirmation-code";

    private InputField enterCodeInputField;

    public EnterCodePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.enterCodeInputField = new InputField(webDriver, wait, CONFIRMATION_CODE_INPUT_ID);
    }

    @Override
    String getPath() {
        return "/enter-code";
    }

    @Override
    String getPageName() {
        return "enter code";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Enter your code";
    }

    public void enterCode(String code) {
        enterCodeInputField.enterValue(code);
    }
}
