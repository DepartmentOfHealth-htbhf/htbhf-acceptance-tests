package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.*;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends CommonSteps {

    @When("^I complete the application with valid details for a pregnant woman|" +
            "I am on the check answers page having entered valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForPregnantWoman());
    }

    @When("^I complete the application with valid details for a woman who is not pregnant")
    public void completeTheApplicationAsAWomanWhoIsNotPregnant() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForANonPregnantWoman());
    }

    @When("^I complete the application with valid details for an applicant with no second line of address")
    public void completeApplicationWithAddressWithNoSecondLineOfAddress() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesWithNoAddressLine2());
    }

    @When("^I complete the application with valid details for an applicant with no county")
    public void completeApplicationWithAddressWithNoCounty() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesWithNoCounty());
    }

    @When("^I complete the application with valid details that contains malicious input")
    public void completeApplicationWithMaliciousContent() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesWithMaliciousFirstName());
    }

    @When("^I submit an application with valid details")
    public void submitApplicationWithValidDetails() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForPregnantWoman());
        acceptTermsAndConditionsAndSubmitSuccessfulApplication();
    }

    @When("^I submit an application with details which fail due to (.*)")
    public void submitApplicationWithDetailsWhichFails(String failureScenarioString) {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForPregnantWoman());
        acceptTermsAndConditionsSetupFailureScenarioAndSubmitApplication(ClaimFailureScenario.fromString(failureScenarioString));
    }
}
