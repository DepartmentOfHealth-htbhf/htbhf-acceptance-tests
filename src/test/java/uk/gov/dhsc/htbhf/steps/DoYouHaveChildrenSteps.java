package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.DoYouHaveChildrenPage;

/**
 * Contains steps definitions for the Do You Have Children page.
 */
public class DoYouHaveChildrenSteps extends CommonSteps {

    @When("^I say No to the do you have children under four years old question")
    public void selectNoToDoYouHaveChildren() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectNoRadioButton();
    }

    @When("^I have said No to the do you have children under four years old question")
    public void selectNoToDoYouHaveChildrenAndContinue() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectNoRadioButton();
        doYouHaveChildrenPage.clickContinue();
    }

    @When("^I say Yes to the do you have children under four years old question")
    public void selectYesToDoYouHaveChildren() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectYesRadioButton();
    }

    @Then("^Yes and No options are displayed on the do you have children under four years old page")
    public void yesAndNoOptionsAreDisplayed() {
        assertYesNoOptionsAreDisplayed(getPages().getDoYouHaveChildrenPage());
    }

    @Then("^I am informed that I need to select an option for do you have children under four years old")
    public void verifyNoOptionSelectedOnPage() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        assertErrorHeaderTextPresent(doYouHaveChildrenPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(
                doYouHaveChildrenPage,
                doYouHaveChildrenPage.getFieldErrorId(),
                doYouHaveChildrenPage.getErrorLinkCss(),
                "Select yes if you have children who are under 4 years old");
    }

}
