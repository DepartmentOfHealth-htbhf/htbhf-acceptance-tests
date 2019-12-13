package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.eligibility.model.testhelper.ChildDobGenerator;
import uk.gov.dhsc.htbhf.page.component.InputField;

import java.time.LocalDate;
import java.util.List;

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

    public LocalDate enterChild3OrUnderDetails() {
        return enterChild3OrUnderDetails("Child" + this.childIndex);
    }

    public LocalDate enterChild3OrUnderDetails(String childName) {
        enterChildName(childName);
        LocalDate dateOfBirth = ChildDobGenerator.getDateOfBirthOfThreeYearOld();
        enterChildsDateOfBirth(dateOfBirth);
        this.childIndex++;
        return dateOfBirth;
    }

    public void enterChildUnder1Details(String childName) {
        enterChildName(childName);
        LocalDate dateOfBirthLastYear = ChildDobGenerator.getDateOfBirthOfUnderOneYearOld();
        enterChildsDateOfBirth(dateOfBirthLastYear);
        this.childIndex++;
    }

    public List<WebElement> findAllRemoveChildButtons() {
        return findAllByCss(".govuk-button.govuk-button--secondary");
    }

    public void clickRemoveButtonForChild(int childIndex) {
        WebElement removeButton = findById(getRemoveButtonIdForIndex(childIndex));
        removeButton.click();
    }

    private String getRemoveButtonIdForIndex(int childIndex) {
        return "remove-child-" + childIndex;
    }

    public String getChildDateOfBirthFieldErrorId(int childIndex) {
        return "child-dob-" + childIndex + "-error";
    }

    public String getChildDateOfBirthErrorLinkCss(int childIndex) {
        return "a[href=\"#child-dob-" + childIndex + "-error\"]";
    }

    public String getChildNameFieldErrorId(int childIndex) {
        return getNameInputField(childIndex).getInputErrorId();
    }

    public String getChildNameErrorLinkCss(int childIndex) {
        // TODO Should the inconsistency in camel case vs kebab case in htbhf-date-input.njk be fixed in web-ui, this will fail and
        //  should be corrected to use getNameInputFieldForIndex(index).getInputErrorLinkCss()
        return "a[href=\"#child-dob-name-" + childIndex + "-error\"]";
    }

    public void enterChildsDateOfBirth(LocalDate dateOfBirth) {
        enterDay(dateOfBirth.getDayOfMonth());
        enterMonth(dateOfBirth.getMonthValue());
        enterYear(dateOfBirth.getYear());

    }

    private void enterDay(int day) {
        getDayInputField(childIndex).enterValue(String.valueOf(day));
    }

    private InputField getDayInputField(int childIndex) {
        return new InputField(webDriver, wait, getDayInputIdForIndex(childIndex));
    }

    private void enterMonth(int month) {
        getMonthInputField(childIndex).enterValue(String.valueOf(month));
    }

    private InputField getMonthInputField(int childIndex) {
        return new InputField(webDriver, wait, getMonthInputIdForIndex(childIndex));
    }

    private void enterYear(int year) {
        getYearInputField(childIndex).enterValue(String.valueOf(year));
    }

    private InputField getYearInputField(int childIndex) {
        return new InputField(webDriver, wait, getYearInputIdForIndex(childIndex));
    }

    private void enterChildName(String name) {
        getNameInputField(childIndex).enterValue(name);
    }

    private InputField getNameInputField(int childIndex) {
        return new InputField(webDriver, wait, getNameInputIdForIndex(childIndex));
    }

    private String getNameInputIdForIndex(int index) {
        return "childDob-name-" + index;
    }

    public String getChildDateOfBirthDay(int childIndex) {
        return getDayInputField(childIndex).getValue();
    }

    private String getDayInputIdForIndex(int index) {
        return "childDob-" + index + "-day";
    }

    public String getChildDateOfBirthMonth(int childIndex) {
        return getMonthInputField(childIndex).getValue();
    }

    private String getMonthInputIdForIndex(int index) {
        return "childDob-" + index + "-month";
    }

    public String getChildDateOfBirthYear(int childIndex) {
        return getYearInputField(childIndex).getValue();
    }

    private String getYearInputIdForIndex(int index) {
        return "childDob-" + index + "-year";
    }
}
