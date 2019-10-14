package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.ScotlandPage;

/**
 * Steps for the do you live in Scotland page
 */
public class ScotlandSteps extends CommonSteps {

    @When("^I say No to the do you live in Scotland question")
    public void selectNoToDoYouLiveInScotland() {
        ScotlandPage scotlandPage = getPages().getScotlandPage();
        scotlandPage.selectNoRadioButton();
    }

    @When("^I select the Yes option on the do you live in Scotland page")
    public void selectYesToDoLiveInScotland() {
        ScotlandPage scotlandPage = getPages().getScotlandPage();
        scotlandPage.selectYesRadioButton();
    }

    @Then("^I am informed that I need to select an option for do you live in Scotland")
    public void assertSelectOptionErrorShown() {
        ScotlandPage scotlandPage = getPages().getScotlandPage();
        assertErrorHeaderTextPresent(scotlandPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(
                scotlandPage,
                scotlandPage.getFieldErrorId(),
                scotlandPage.getFieldErrorLinkCss(),
                "Select yes if you live in Scotland");
    }
}
