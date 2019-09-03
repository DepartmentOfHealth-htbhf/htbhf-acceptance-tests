package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

import java.time.LocalDate;

/**
 * Page object for the Enter Children's Dates of Birth page.
 */
public class ChildDateOfBirthPage extends SubmittablePage {

    private int childIndex = 1;

    public ChildDateOfBirthPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/child-date-of-birth";
    }

    @Override
    PageName getPageName() {
        return PageName.CHILD_DATE_OF_BIRTH;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Enter your children’s dates of birth";
    }

    public void clickAddAnotherChild() {
        WebElement addAnotherChildLink = findById("add-another-child");
        addAnotherChildLink.click();
    }

    public void enterChild3OrUnderDetails(int dayIncrement) {
        String name = "Child" + this.childIndex;
        enterChildName(name);
        LocalDate dateOfBirthLastYear = LocalDate.now().plusDays(dayIncrement).minusYears(1);
        enterChild3OrUnderDateOfBirth(dateOfBirthLastYear);
        this.childIndex++;
    }

    private void enterChild3OrUnderDateOfBirth(LocalDate dateOfBirth) {
        enterDay(dateOfBirth.getDayOfMonth());
        enterMonth(dateOfBirth.getMonthValue());
        enterYear(dateOfBirth.getYear());
    }

    private void enterDay(int day) {
        InputField inputField = new InputField(webDriver, wait, getDayInputIdForIndex(childIndex));
        inputField.enterValue(String.valueOf(day));
    }

    private void enterMonth(int month) {
        InputField inputField = new InputField(webDriver, wait, getMonthInputIdForIndex(childIndex));
        inputField.enterValue(String.valueOf(month));
    }

    private void enterYear(int year) {
        InputField inputField = new InputField(webDriver, wait, getYearInputIdForIndex(childIndex));
        inputField.enterValue(String.valueOf(year));
    }

    private void enterChildName(String name) {
        InputField inputField = new InputField(webDriver, wait, getNameInputIdForIndex(childIndex));
        inputField.enterValue(name);
    }

    private String getNameInputIdForIndex(int index) {
        return "childDob-name-" + index;
    }

    private String getDayInputIdForIndex(int index) {
        return "childDob-" + index + "-day";
    }

    private String getMonthInputIdForIndex(int index) {
        return "childDob-" + index + "-month";
    }

    private String getYearInputIdForIndex(int index) {
        return "childDob-" + index + "-year";
    }
}
