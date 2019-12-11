package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dhsc.htbhf.page.DecisionPage;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.utils.ClaimantRequestTestDataFactory.buildClaimantRequestJson;

/**
 * Steps for the Decision page which is the shown once their application has been submitted.
 */
public class DecisionSteps extends CommonSteps {

    @Then("^I am shown an instant failure")
    public void verifyOnCorrectPage() {
        getPages().getUnsuccessfulDecisionPage();
    }

    @Then("^I am shown a successful decision page|" +
            "all page content is present on the decision details page")
    public void iAmShownASuccessfulDecisionPage() {
        DecisionPage decisionPage = getPages().getSuccessfulDecisionPage();
        assertThat(decisionPage.getH2Text())
                .as("expected decision page H2 text to be correct")
                .isEqualTo("What happens next");
        assertThat(decisionPage.getPanelTitleText().trim())
                .as("expected decision header to be correct")
                .isEqualTo("Application successful");
    }

    @Then("^my entitlement is 12.40 per week with a first payment of 49.60")
    public void myEntitlementIsShownCorrectly() {
        //We need to use the no wait method so that we can use this method for both decision and decision updated page (otherwise it waits for the title).
        String totalVoucherValue = getPages().getDecisionPageNoWait().getPanelBodyText();
        assertThat(StringUtils.normalizeSpace(totalVoucherValue))
                .as("Entitlement amount should be shown correctly on the decision page")
                .isEqualTo("You’re entitled to £12.40 a week. Your first payment will be £49.60.");
    }

    @Then("^my claim is sent to the back end")
    public void claimsSuccessfullySentToBackEnd() {
        String expectedBody = buildClaimantRequestJson(claimValuesThreadLocal.get());
        wireMockManager.verifyClaimantServiceRequestMatching(expectedBody);
    }

    @Then("^the page content displays that my application was not successful")
    public void unsuccessfulApplicationShown() {
        DecisionPage unsuccessfulApplicationPage = getPages().getUnsuccessfulDecisionPage();
        assertThat(unsuccessfulApplicationPage.getPanelTitleText().trim())
                .as("expected decision header to be correct")
                .isEqualTo("Application not successful");
        assertThat(unsuccessfulApplicationPage.getPanelBodyText().trim())
                .as("expected decision body to be correct")
                .isEqualTo("You will not be sent a prepaid money card");
    }
}
