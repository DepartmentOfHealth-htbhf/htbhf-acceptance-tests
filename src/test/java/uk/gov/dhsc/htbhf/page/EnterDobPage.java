package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;
import uk.gov.dhsc.htbhf.page.component.SubmitButton;

/**
 * Page object for the Enter Date of Birth page.
 */
public class EnterDobPage extends BasePage {

    private static final String DOB_DAY_INPUT_FIELD_ID = "dateOfBirth-day";
    private static final String DOB_MONTH_INPUT_FIELD_ID = "dateOfBirth-month";
    private static final String DOB_YEAR_INPUT_FIELD_ID = "dateOfBirth-year";

    private InputField dayInputField;
    private InputField monthInputField;
    private InputField yearInputField;
    private SubmitButton submitButton;

    public EnterDobPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.dayInputField = new InputField(webDriver, DOB_DAY_INPUT_FIELD_ID);
        this.monthInputField = new InputField(webDriver, DOB_MONTH_INPUT_FIELD_ID);
        this.yearInputField = new InputField(webDriver, DOB_YEAR_INPUT_FIELD_ID);
        this.submitButton = new SubmitButton(webDriver);
    }

    @Override
    String getPath() {
        return "/enter-dob";
    }

    @Override
    String getPageName() {
        return "enter date of birth";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your date of birth?";
    }

    public InputField getDayInputField() {
        return dayInputField;
    }

    public InputField getMonthInputField() {
        return monthInputField;
    }

    public InputField getYearInputField() {
        return yearInputField;
    }

    public void clickContinue() {
        submitButton.click();
    }
}
