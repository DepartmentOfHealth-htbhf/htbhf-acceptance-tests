package uk.gov.dhsc.htbhf.steps;

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

    @When("^I say Yes to the do you have children under four years old question")
    public void selectYesToDoYouHaveChildren() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectYesRadioButton();
    }

}
