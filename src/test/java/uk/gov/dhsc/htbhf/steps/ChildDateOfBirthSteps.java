package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.eligibility.model.testhelper.ChildDobGenerator;
import uk.gov.dhsc.htbhf.page.ChildDateOfBirthPage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.steps.Constants.LONG_STRING;

/**
 * Steps for the page where the claimant enters the date of birth of their children
 */
public class ChildDateOfBirthSteps extends CommonSteps {

    @Given("^I submit the details of my child who is under four years old")
    public void submitChild3OrUnderDetails() {
        enterOneChildsDateOfBirth();
    }

    @Given("^I enter the details of my child who is under four years old")
    public void enterChild3OrUnderDetails() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        LocalDate dob = childDateOfBirthPage.enterChild3OrUnderDetails();
        claimValuesThreadLocal.get().addChildDob(dob);
    }

    @Given("^I submit the details of my child who is under four years old without a name")
    public void enterChild3OrUnderDetailsWithNoName() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChild3OrUnderDetails("");
        childDateOfBirthPage.clickContinue();
    }

    @Given("I enter the details of my two children who are under four years old")
    public void enterTwoSetsOfChildren3OrUnderDetails() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChildUnder1Details("Joe");
        childDateOfBirthPage.clickAddAnotherChild();
        childDateOfBirthPage.enterChild3OrUnderDetails("Joanne");
    }

    @Given("^there is a Remove Button for both children's date of birth")
    public void removeButtonsForBothChildrensDatesOfBirth() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        List<WebElement> allRemoveButtons = childDateOfBirthPage.findAllRemoveChildButtons();
        assertThat(allRemoveButtons).hasSize(2);

        List<String> allRemoveButtonIds = allRemoveButtons.stream()
                .map(button -> button.getAttribute("id"))
                .collect(Collectors.toList());
        assertThat(allRemoveButtonIds).containsExactly("remove-child-1", "remove-child-2");
    }

    @When("^I do not enter my child's date of birth")
    public void doNotEnterChildsDateOfBirth() {
        //Intentionally does nothing
    }

    @When("^I click remove for the first child's date of birth")
    public void removeFirstChildsDateOfBirth() {
        getPages().getChildDateOfBirthPage().clickRemoveButtonForChild(1);
    }

    @When("I submit the details of my ten children who are under four years old")
    public void enterTenSetsOfChildren3OrUnderDetails() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        for (int childIndex = 1; childIndex <= 10; childIndex++) {
            boolean shouldClickAddAnother = childIndex < 10;
            enterChildDetailsAndClickAddAnother(childDateOfBirthPage, childIndex, shouldClickAddAnother);
        }
        childDateOfBirthPage.clickContinue();
    }

    private void enterChildDetailsAndClickAddAnother(ChildDateOfBirthPage childDateOfBirthPage, int childIndex, boolean shouldClickAddAnother) {
        childDateOfBirthPage.enterChild3OrUnderDetails("Child" + childIndex);
        if (shouldClickAddAnother) {
            childDateOfBirthPage.clickAddAnotherChild();
        }
    }

    @When("^I enter a future date as my child's date of birth")
    public void enterChildDateOfBirthInFuture() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        LocalDate dateInFuture = LocalDate.now().plusYears(5);
        childDateOfBirthPage.enterChildsDateOfBirth(dateInFuture);
        childDateOfBirthPage.clickContinue();
    }

    @When("^I select to add another child")
    public void addAnotherChild() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.clickAddAnotherChild();
    }

    @When("^I submit the details of my child who is under four years old with a very long name")
    public void enterChildsDobWithLongName() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChild3OrUnderDetails(LONG_STRING);
        childDateOfBirthPage.clickContinue();
    }

    @When("^I submit the details of my two children who are under four years both with very long names")
    public void enterTwoChildrensDobsWithLongName() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChildUnder1Details(LONG_STRING);
        childDateOfBirthPage.clickAddAnotherChild();
        childDateOfBirthPage.enterChild3OrUnderDetails(LONG_STRING);
        childDateOfBirthPage.clickContinue();
    }

    @Then("^there are no Remove Child buttons visible")
    public void noRemoveChildButtonsVisible() {
        List<WebElement> allRemoveButtons = getPages().getChildDateOfBirthPage().findAllRemoveChildButtons();
        assertThat(allRemoveButtons).isEmpty();
    }

    @Then("^I only see the second child's date of birth")
    public void secondChildDateOfBirthOnlyShown() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        String childDobDay = childDateOfBirthPage.getChildDateOfBirthDay(1);
        String childDobMonth = childDateOfBirthPage.getChildDateOfBirthMonth(1);
        String childDobYear = childDateOfBirthPage.getChildDateOfBirthYear(1);

        // The second child will be 3 years old
        LocalDate dateOfBirthForSecondChild = ChildDobGenerator.getDateOfBirthOfThreeYearOld();

        assertThat(childDobDay).isEqualTo(String.valueOf(dateOfBirthForSecondChild.getDayOfMonth()));
        assertThat(childDobMonth).isEqualTo(String.valueOf(dateOfBirthForSecondChild.getMonthValue()));
        assertThat(childDobYear).isEqualTo(String.valueOf(dateOfBirthForSecondChild.getYear()));
    }

    @Then("^I am informed that I need to enter the date of birth for the first child")
    public void childDateOfBirthMandatoryErrorShown() {
        assertChildDateOfBirthErrorShown("Enter your child’s date of birth", 1);
    }

    @Then("^I am informed that I need to enter the date of birth for both children")
    public void childDateOfBirthMandatoryErrorShownForBothChildren() {
        assertChildDateOfBirthErrorShown("Enter your child’s date of birth", 1);
        assertChildDateOfBirthErrorShown("Enter your child’s date of birth", 2);
    }

    @Then("^I am informed that the first child must be under four")
    public void childDateOfBirthMustBeUnderFourErrorShown() {
        assertChildDateOfBirthErrorShown("You can only apply for children who are under 4 years old", 1);
    }

    @Then("^I am informed that I need to enter a shorter name")
    public void childNameIsTooLongErrorShown() {
        assertChildNameErrorShown(1);
    }

    @Then("^I am informed that I need to enter a shorter name for both children")
    public void childNameIsTooLongErrorShownForBothChildren() {
        assertChildNameErrorShown(1);
        assertChildNameErrorShown(2);
    }

    private void assertChildDateOfBirthErrorShown(String expectedErrorMessage, int childIndex) {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        assertErrorHeaderTextPresent(childDateOfBirthPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(childDateOfBirthPage,
                childDateOfBirthPage.getChildDateOfBirthFieldErrorId(childIndex),
                childDateOfBirthPage.getChildDateOfBirthErrorLinkCss(childIndex),
                expectedErrorMessage);
    }

    private void assertChildNameErrorShown(int childIndex) {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        assertErrorHeaderTextPresent(childDateOfBirthPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(childDateOfBirthPage,
                childDateOfBirthPage.getChildNameFieldErrorId(childIndex),
                childDateOfBirthPage.getChildNameErrorLinkCss(childIndex),
                "Enter a shorter name");
    }
}
