package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import uk.gov.dhsc.htbhf.page.GuidancePage;

/**
 * Steps for the overview page of the application
 */
public class GuidanceSteps extends BaseSteps {

    private GuidancePage guidancePage;

    @Given("^I am on the first page of the application")
    public void givenIAmOnTheFirstPageOfTheApplication() {
        guidancePage = new GuidancePage(webDriver, baseUrl, webDriverWait);
        guidancePage.openApplyPage();
    }
}
