package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

import static uk.gov.dhsc.htbhf.page.PageName.CHECK_ANSWERS;
import static uk.gov.dhsc.htbhf.steps.ActionOptionsTestDataFactory.buildActionOptionsForPregnantWoman;
import static uk.gov.dhsc.htbhf.steps.ActionOptionsTestDataFactory.buildActionOptionsWithMaliciousFirstName;
import static uk.gov.dhsc.htbhf.steps.ActionOptionsTestDataFactory.buildActionOptionsWithNoAddressLine2;
import static uk.gov.dhsc.htbhf.steps.ActionOptionsTestDataFactory.buildActionOptionsWithNoCounty;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends CommonSteps {

    @When("^I complete the application with valid details for a pregnant woman|" +
            "I am on the check answers page having entered valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildActionOptionsForPregnantWoman());
    }

    @When("^I complete the application with valid details for a woman who is not pregnant")
    public void completeTheApplicationAsAWomanWhoIsNotPregnant() {
        enterDetailsUpToPage(CHECK_ANSWERS);
    }

    @When("^I complete the application with valid details for an applicant with no second line of address")
    public void completeApplicationWithAddressWithNoSecondLineOfAddress() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildActionOptionsWithNoAddressLine2());
    }

    @When("^I complete the application with valid details for an applicant with no county")
    public void completeApplicationWithAddressWithNoCounty() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildActionOptionsWithNoCounty());
    }

    @When("I complete the application with valid details that contains malicious input")
    public void completeApplicationWithMaliciousContent() {
        enterDetailsUpToPage(CHECK_ANSWERS, buildActionOptionsWithMaliciousFirstName());
    }

}
