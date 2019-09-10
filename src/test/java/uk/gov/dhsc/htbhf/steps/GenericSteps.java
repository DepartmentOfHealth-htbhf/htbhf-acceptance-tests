package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.GenericPage;

/**
 * Contains steps which can be run from a number of different pages. We need to have this because we are not
 * allowed to have one class which defines steps extend another which defines steps, so these step definitions
 * need to be in their own step definition class.
 */
public class GenericSteps extends CommonSteps {

    @When("^I do not select an option$")
    public void whenIDoNotSelectAnOption() {
        // Specifically does nothing
    }

    @When("^I click continue$")
    public void whenIClickContinue() {
        GenericPage genericPage = getPages().getGenericPage();
        genericPage.clickContinue();
    }

}
