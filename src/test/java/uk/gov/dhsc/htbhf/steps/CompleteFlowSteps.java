package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;

import static uk.gov.dhsc.htbhf.page.PageName.CHECK_ANSWERS;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends CommonSteps {

    @When("^I complete the application with valid details for a pregnant woman|" +
            "I am on the check answers page having entered valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        enterDetailsUpToPage(CHECK_ANSWERS);
    }
}
