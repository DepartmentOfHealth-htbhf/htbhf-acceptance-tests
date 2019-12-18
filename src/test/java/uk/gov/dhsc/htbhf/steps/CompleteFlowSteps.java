package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

import static uk.gov.dhsc.htbhf.steps.ClaimScenario.FULL_ADDRESS_MISMATCH_NOT_PREGNANT;
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

    @When("^I submit an application with details which fail due to (.*)$")
    public void submitApplicationForFailureScenario(String scenarioString) {
        setupAndSubmitScenario(scenarioString);
    }

    @When("^I submit an application that doesn't get an instant answer due to: (.*)$")
    public void submitApplicationForPendingScenario(String scenarioString) {
        setupAndSubmitScenario(scenarioString);
    }

    @When("^I submit an application for a non-pregnant claimant that doesn't get an instant answer due to an address mismatch$")
    public void submitPendingApplicationForNonPregnantClaimant() {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForANonPregnantWoman());
        acceptTermsAndConditionsSetupScenarioResponseAndSubmitApplication(FULL_ADDRESS_MISMATCH_NOT_PREGNANT);
    }

    private void setupAndSubmitScenario(String scenarioString) {
        enterDetailsUpToCheckAnswersPage(buildClaimValuesForPregnantWoman());
        acceptTermsAndConditionsSetupScenarioResponseAndSubmitApplication(ClaimScenario.fromString(scenarioString));
    }
}
