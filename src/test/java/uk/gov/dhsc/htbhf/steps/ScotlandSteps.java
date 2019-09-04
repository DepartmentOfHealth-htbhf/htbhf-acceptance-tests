package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;

/**
 * Steps for the Do You Live In Scotland page
 */
public class ScotlandSteps extends BaseSteps {

    @Then("^I am shown the do you live in Scotland page")
    public void doYouLiveInScotlandPageIsShown() {
        getPages().getScotlandPage();
    }

}
