package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

import static uk.gov.dhsc.htbhf.page.PageName.CHECK_ANSWERS;
import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildClaimValuesForPregnantWoman;
import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildClaimValuesWithMaliciousFirstName;
import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildClaimValuesWithNoAddressLine2;
import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildClaimValuesWithNoCounty;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends CommonSteps {

    @When("^I complete the application with valid details for a pregnant woman|" +
            "I am on the check answers page having entered valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesForPregnantWoman());
    }

    @When("^I complete the application with valid details for a woman who is not pregnant")
    public void completeTheApplicationAsAWomanWhoIsNotPregnant() {
        enterDetailsUpToPage(CHECK_ANSWERS);
    }

    @When("^I complete the application with valid details for an applicant with no second line of address")
    public void completeApplicationWithAddressWithNoSecondLineOfAddress() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesWithNoAddressLine2());
    }

    @When("^I complete the application with valid details for an applicant with no county")
    public void completeApplicationWithAddressWithNoCounty() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesWithNoCounty());
    }

    @When("^I complete the application with valid details that contains malicious input")
    public void completeApplicationWithMaliciousContent() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesWithMaliciousFirstName());
    }

    @When("^I submit an application with valid details")
    public void submitApplicationWithValidDetails() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesForPregnantWoman());
        acceptTermsAndConditionsAndSubmitApplication();
    }

    @When("^I submit an application to update an existing claim")
    public void submitApplicationToUpdateAnExistingClaim() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildClaimValuesForPregnantWoman());
        acceptTermsAndConditionsAndUpdateApplication();
    }

}
