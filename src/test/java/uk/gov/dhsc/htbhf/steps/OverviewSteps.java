package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import uk.gov.dhsc.htbhf.page.OverviewPage;

/**
 * Steps for the overview page of the application
 */
public class OverviewSteps extends BaseSteps {

    private OverviewPage overviewPage;

    @Given("^I am on the first page of the application")
    public void givenIAmOnTheFirstPageOfTheApplication() {
        overviewPage = new OverviewPage(webDriver, baseUrl, webDriverWait);
        overviewPage.open();
    }
}
