package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

public class PostcodePage extends SubmittablePage {

    private static final String POSTCODE_INPUT_ID = "postcode";
    private final InputField postcodeInputField;

    public PostcodePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        postcodeInputField = new InputField(webDriver, wait, POSTCODE_INPUT_ID);
    }

    @Override
    String getPath() {
        return "/postcode";
    }

    @Override
    PageName getPageName() {
        return PageName.POSTCODE;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your address?";
    }

    public void enterPostcode(String postcode) {
        postcodeInputField.enterValue(postcode);
    }

    public String getPostcodeInputErrorId() {
        return postcodeInputField.getInputErrorId();
    }

    public String getPostcodeInputErrorLinkCss() {
        return postcodeInputField.getInputErrorLinkCss();
    }
}
