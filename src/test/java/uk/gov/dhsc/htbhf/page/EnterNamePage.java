package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter your name page.
 */
public class EnterNamePage extends SubmittablePage {

    private static final String FIRST_NAME_INPUT_ID = "first-name";
    private static final String LAST_NAME_IUNPUT_ID = "last-name";

    private InputField firstNameInputField;
    private InputField lastNameInputField;

    public EnterNamePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.firstNameInputField = new InputField(webDriver, FIRST_NAME_INPUT_ID);
        this.lastNameInputField = new InputField(webDriver, LAST_NAME_IUNPUT_ID);
    }

    @Override
    String getPath() {
        return "/enter-name";
    }

    @Override
    String getPageName() {
        return "enter name";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your name?";
    }

    public void enterName(String firstName, String lastName) {
        firstNameInputField.enterValue(firstName);
        lastNameInputField.enterValue(lastName);
    }
}
