package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter your name page.
 */
public class TestNamesPage extends SubmittablePage {

    private static final String FIRST_NAME_INPUT_ID = "first-name";
    private static final String LAST_NAME_INPUT_ID = "last-name";

    private InputField firstNameInputField;
    private InputField lastNameInputField;

    public TestNamesPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.firstNameInputField = new InputField(webDriver, wait, FIRST_NAME_INPUT_ID);
        this.lastNameInputField = new InputField(webDriver, wait, LAST_NAME_INPUT_ID);
    }

    @Override
    String getPath() {
        return "/test/name";
    }

    @Override
    PageName getPageName() {
        return PageName.TEST_NAMES;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your name?";
    }

    public void enterName(String firstName, String lastName) {
        firstNameInputField.enterValue(firstName);
        lastNameInputField.enterValue(lastName);
    }

    public String getFirstName() {
        return firstNameInputField.getValue();
    }

    public String getLastName() {
        return lastNameInputField.getValue();
    }

    public String getFirstNameInputErrorId() {
        return firstNameInputField.getInputErrorId();
    }

    public String getFirstNameInputErrorLinkCss() {
        return firstNameInputField.getInputErrorLinkCss();
    }

    public String getLastNameInputErrorId() {
        return lastNameInputField.getInputErrorId();
    }

    public String getLastNameInputErrorLinkCss() {
        return lastNameInputField.getInputErrorLinkCss();
    }
}
