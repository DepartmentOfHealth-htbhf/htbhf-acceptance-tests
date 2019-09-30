package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private static final String ARE_YOU_PREGNANT_ERROR_LINK_CSS = "a[href=\"#are-you-pregnant-error\"]";
    private static final String ARE_YOU_PREGNANT_FIELD_ERROR_ID = "are-you-pregnant-error";
    private static final String EXPECTED_DELIVERY_DATE_ERROR_LINK_CSS = "a[href=\"#expected-delivery-date-error\"]";
    private static final String EXPECTED_DELIVERY_DATE_FIELD_ERROR_ID = "expected-delivery-date-error";

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
    PageName getPageName() {
        return PageName.ARE_YOU_PREGNANT;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Are you pregnant?";
    }

    public static String getAreYouPregnantFieldErrorId() {
        return ARE_YOU_PREGNANT_FIELD_ERROR_ID;
    }

    public static String getAreYouPregnantErrorLinkCss() {
        return ARE_YOU_PREGNANT_ERROR_LINK_CSS;
    }

    public static String getExpectedDeliveryDateFieldErrorId() {
        return EXPECTED_DELIVERY_DATE_FIELD_ERROR_ID;
    }

    public static String getExpectedDeliveryDateErrorLinkCss() {
        return EXPECTED_DELIVERY_DATE_ERROR_LINK_CSS;
    }

    public String getExpectedDeliveryDateInstructionalText() {
        WebElement instructionalText = findById("expected-delivery-date-hint");
        return instructionalText.getText();
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

    public String getDayValue() {
        return dayInputField.getValue();
    }

    public String getMonthValue() {
        return monthInputField.getValue();
    }

    public String getYearValue() {
        return yearInputField.getValue();
    }

    public void enterTextIntoDateFields() {
        dayInputField.enterValue("foo");
        monthInputField.enterValue("bar");
        yearInputField.enterValue("baz!");
    }

    private void setExpectedDeliveryDate(int day, int month, int year) {
        dayInputField.enterValue(String.valueOf(day));
        monthInputField.enterValue(String.valueOf(month));
        yearInputField.enterValue(String.valueOf(year));
    }
}
