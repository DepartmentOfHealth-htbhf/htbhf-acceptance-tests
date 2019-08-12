package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.CheckDetailsPage;
import uk.gov.dhsc.htbhf.page.TermsAndConditionsPage;

/**
 * Steps for the Terms and Conditions page after the claimant has checked their details.
 */
public class TermsAndConditionsSteps extends BaseSteps {

    private CheckDetailsPage checkDetailsPage;
    private TermsAndConditionsPage termsAndConditionsPage;

    @When("/^I accept the terms and conditions and submit my application$/")
    public void acceptTermsAndConditionsAndSubmitApplication() {
        wireMockManager.setupWireMockMappingsWithStatus("ELIGIBLE");
        checkDetailsPage = new CheckDetailsPage(webDriver, baseUrl, webDriverWait);
        checkDetailsPage.clickContinue();
        acceptTsAndCsAndSubmitApplication();
    }

    private void acceptTsAndCsAndSubmitApplication() {
        termsAndConditionsPage = new TermsAndConditionsPage(webDriver, baseUrl, webDriverWait);
        termsAndConditionsPage.waitForPageToLoad();
        termsAndConditionsPage.clickAcceptTermsAndConditionsCheckBox();
        termsAndConditionsPage.clickContinue();
    }

}
