package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter your national insurance number page.
 */
public class EnterNinoPage extends SubmittablePage {

    private static final String NINO_INPUT_ID = "nino";
    private InputField ninoInputField;

    public EnterNinoPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.ninoInputField = new InputField(webDriver, wait, NINO_INPUT_ID);
    }

    @Override
    String getPath() {
        return "/enter-nino";
    }

    @Override
    String getPageName() {
        return "enter national insurance";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your National Insurance number?";
    }

    public void enterNino(String nino) {
        ninoInputField.enterValue(nino);
    }
}
