package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

import java.time.LocalDate;

/**
 * Page object for the Are You Pregnant? page.
 */
public class AreYouPregnantPage extends SubmittablePage {

    private static final String DAY_INPUT_FIELD_ID = "expectedDeliveryDate-day";
    private static final String MONTH_INPUT_FIELD_ID = "expectedDeliveryDate-month";
    private static final String YEAR_INPUT_FIELD_ID = "expectedDeliveryDate-year";

    private InputField dayInputField;
    private InputField monthInputField;
    private InputField yearInputField;
    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;

    public AreYouPregnantPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.dayInputField = new InputField(webDriver, wait, DAY_INPUT_FIELD_ID);
        this.monthInputField = new InputField(webDriver, wait, MONTH_INPUT_FIELD_ID);
        this.yearInputField = new InputField(webDriver, wait, YEAR_INPUT_FIELD_ID);
        this.yesRadioButton = new RadioButton(webDriver, wait, RadioButton.YES);
        this.noRadioButton = new RadioButton(webDriver, wait, RadioButton.NO);
    }

    @Override
    String getPath() {
        return "/are-you-pregnant";
    }

    @Override
    String getPageName() {
        return "are you pregnant";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Are you pregnant?";
    }

    public void selectYes() {
        yesRadioButton.select();
    }

    public void selectNo() {
        noRadioButton.select();
    }

    public void enterExpectedDeliveryDate(int incrementMonth) {
        LocalDate date = LocalDate.now().plusMonths(incrementMonth);
        setExpectedDeliveryDate(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    private void setExpectedDeliveryDate(int day, int month, int year) {
        dayInputField.enterValue(String.valueOf(day));
        monthInputField.enterValue(String.valueOf(month));
        yearInputField.enterValue(String.valueOf(year));
    }
}
