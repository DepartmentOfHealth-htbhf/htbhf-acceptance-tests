package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.DoYouLiveInScotlandPage;
import uk.gov.dhsc.htbhf.page.OverviewPage;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends BaseSteps {

    private OverviewPage overviewPage;
    private DoYouLiveInScotlandPage doYouLiveInScotlandPage;


    //TODO MRS 2019-08-01: Keep building this up...
    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        overviewPage = new OverviewPage(webDriver, baseUrl, webDriverWait);
        overviewPage.clickStartButton();
        doYouLiveInScotlandPage = new DoYouLiveInScotlandPage(webDriver, baseUrl, webDriverWait);
        doYouLiveInScotlandPage.waitForPageToLoad();
    }

}
