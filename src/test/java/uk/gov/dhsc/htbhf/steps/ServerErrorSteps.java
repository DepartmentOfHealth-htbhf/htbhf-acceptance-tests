package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

/**
 * Steps for setting up error scenarios
 */
public class ServerErrorSteps extends CommonSteps {

    @When("^An error occurs after clicking the submit button")
    public void setupErrorAfterSubmit() {
        wireMockManager.setupErrorWiremockClaimMapping();
        getPages().getTermsAndConditionsPage().clickContinue();
    }

}
