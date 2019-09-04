package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.GuidancePage;
import uk.gov.dhsc.htbhf.page.PageName;

/**
 * Contains common steps used by more than one step
 */
public class CommonSteps extends BaseSteps {

    private GuidancePage guidancePage;

    @Given("^I am starting a new application")
    public void givenIAmStartingANewApplication() {
        guidancePage = getPages().getGuidancePageNoWait(PageName.APPLY);
        guidancePage.open();
    }

    @When("^I navigate to the (.*) page")
    public void navigateToPage(String pageName) {
        BasePage page = getPages().getPageByName(PageName.getPageName(pageName));
        page.openDirect();
    }
}
