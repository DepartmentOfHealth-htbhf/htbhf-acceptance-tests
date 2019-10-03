package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import uk.gov.dhsc.htbhf.page.PageName;

/**
 * Steps for the privacy notice page.
 */
public class PrivacyNoticeSteps extends CommonSteps {

    @Then("the back link on the page links to the do you live in Scotland page")
    public void assertBackLinkCorrect() {
        assertBackLinkPointsToPage(PageName.SCOTLAND);
    }
}
