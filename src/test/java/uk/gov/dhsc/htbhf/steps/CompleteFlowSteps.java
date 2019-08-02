package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.DoYouHaveChildren;
import uk.gov.dhsc.htbhf.page.DoYouLiveInScotlandPage;
import uk.gov.dhsc.htbhf.page.EnterDobPage;
import uk.gov.dhsc.htbhf.page.OverviewPage;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends BaseSteps {

    private OverviewPage overviewPage;
    private DoYouLiveInScotlandPage doYouLiveInScotlandPage;
    private EnterDobPage enterDobPage;
    private DoYouHaveChildren doYouHaveChildren;

    //TODO MRS 2019-08-01: Keep building this up...
    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        overviewPage = new OverviewPage(webDriver, baseUrl, webDriverWait);
        overviewPage.clickStartButton();
        enterDoYouLiveInScotlandNoAndSubmit();
        enterDateOfBirthAndSubmit();
        doYouHaveChildren = new DoYouHaveChildren(webDriver, baseUrl, webDriverWait);
        doYouHaveChildren.waitForPageToLoad();
    }

    private void enterDateOfBirthAndSubmit() {
        enterDobPage = new EnterDobPage(webDriver, baseUrl, webDriverWait);
        enterDobPage.waitForPageToLoad();
        enterDobPage.getDayInputField().enterValue("28");
        enterDobPage.getMonthInputField().enterValue("11");
        enterDobPage.getYearInputField().enterValue("1970");
        enterDobPage.clickContinue();
    }

    private void enterDoYouLiveInScotlandNoAndSubmit() {
        doYouLiveInScotlandPage = new DoYouLiveInScotlandPage(webDriver, baseUrl, webDriverWait);
        doYouLiveInScotlandPage.waitForPageToLoad();
        doYouLiveInScotlandPage.selectNoRadioButton();
        doYouLiveInScotlandPage.clickContinue();
    }

}
