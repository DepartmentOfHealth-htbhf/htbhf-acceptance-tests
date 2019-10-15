package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import uk.gov.dhsc.htbhf.page.UnsuccessfulApplicationPage;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the unsuccessful application page.
 */
public class UnsuccessfulApplicationSteps extends CommonSteps {

    // *during private beta* INELIGIBLE messaging refers to being unable to find someone in our records, as all invited applicants are eligible
    private static final Map<String, String> STATUS_TO_BODY_TEXT_MAP = Map.of(
            "INELIGIBLE", "We cannot find you in our records. You may have entered something wrong.",
            "PENDING", "Your benefit status is pending",
            "NO_MATCH", "We cannot find you in our records. You may have entered something wrong.",
            "ERROR", "An error occurred whilst checking your eligibility",
            "DUPLICATE", "A claim already exists with your details"
    );

    @Then("^the page content displays that I am not eligible because my eligibility status is (.*)")
    public void unsuccessfulApplicationShown(String status) {
        UnsuccessfulApplicationPage unsuccessfulApplicationPage = getPages().getUnsuccessfulApplicationPage();
        String bodyText = unsuccessfulApplicationPage.getBodyText();
        assertThat(bodyText).isEqualTo(STATUS_TO_BODY_TEXT_MAP.get(status));
    }
}
