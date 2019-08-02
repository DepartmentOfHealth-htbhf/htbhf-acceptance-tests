package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.DoYouLiveInScotlandPage;
import uk.gov.dhsc.htbhf.page.OverviewPage;

/**
 * Steps for the overview page of the application
 */
public class OverviewSteps extends BaseSteps {

    private OverviewPage overviewPage;
    private DoYouLiveInScotlandPage doYouLiveInScotlandPage;

    @Given("^I am on the first page of the application")
    public void givenIAmOnTheFirstPageOfTheApplication() {
        overviewPage = new OverviewPage(getWebDriver(), getBaseUrl(), getWebDriverWait());
        overviewPage.open();
    }

    //TODO MRS 2019-08-01: Keep building this up...
    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        overviewPage.clickStartButton();
        doYouLiveInScotlandPage = new DoYouLiveInScotlandPage(getWebDriver(), getBaseUrl(), getWebDriverWait());
        doYouLiveInScotlandPage.waitForPageToLoad();
    }
}
