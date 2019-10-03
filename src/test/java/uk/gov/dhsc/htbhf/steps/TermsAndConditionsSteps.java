package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

/**
 * Steps for the Terms and Conditions page after the claimant has checked their details.
 */
public class TermsAndConditionsSteps extends CommonSteps {

    @When("^I accept the terms and conditions and submit my application")
    public void iAcceptTheTermsAndConditionsAndSubmitMyApplication() {
        acceptTermsAndConditionsAndSubmitApplication();
    }

    @When("^I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {
        checkAnswersAndAcceptTsAndCs();
    }

}
