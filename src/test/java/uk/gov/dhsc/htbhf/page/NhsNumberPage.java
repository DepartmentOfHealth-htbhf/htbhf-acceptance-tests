package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter your nhs number page.
 */
public class NhsNumberPage extends SubmittablePage {

    private static final String NHSNO_INPUT_ID = "nhs-number";
    private InputField nhsnoInputField;

    public NhsNumberPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.nhsnoInputField = new InputField(webDriver, wait, NHSNO_INPUT_ID);
    }

    @Override
    String getPath() {
        return "/test/nhs-number";
    }

    @Override
    PageName getPageName() {
        return PageName.NHS_NUMBER;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Enter your NHS Number";
    }

    public void enterNhsno(String nhsno) {
        nhsnoInputField.enterValue(nhsno);
    }

    public String getNhsnoInputErrorId() {
        return nhsnoInputField.getInputErrorId();
    }

    public String getNhsnoInputErrorLinkCss() {
        return nhsnoInputField.getInputErrorLinkCss();
    }

    public String getNhsnoInputValue() {
        return nhsnoInputField.getValue();
    }
}
