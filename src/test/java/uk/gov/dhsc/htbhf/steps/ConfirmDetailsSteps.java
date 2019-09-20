package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dhsc.htbhf.page.ConfirmUpdatedPage;
import uk.gov.dhsc.htbhf.page.ConfirmationPage;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.utils.ClaimantRequestTestDataFactory.buildClaimantRequestJson;

/**
 * Steps for the Confirmation page which is the shown once their application has been submitted.
 */
public class ConfirmDetailsSteps extends CommonSteps {

    @Then("^I am shown a successful confirmation page|" +
            "all page content is present on the confirm details page")
    public void iAmShownASuccessfulConfirmationPage() {
        ConfirmationPage confirmationPage = getPages().getConfirmationPage();
        assertThat(confirmationPage.getH2Text())
                .as("expected confirm page H2 text to be correct")
                .isEqualTo("What happens next");
        assertThat(confirmationPage.getPanelTitleText().trim())
                .as("expected confirmation header to be correct")
                .isEqualTo("Application complete");
    }

    @Then("^my entitlement is 12.40 per week with a first payment of 49.60")
    public void myEntitlementIsShownCorrectly() {
        //We need to use the no wait method so that we can use this method for both confirmation and confirm updated page (otherwise it waits for the title).
        String totalVoucherValue = getPages().getConfirmationPageNoWait().getPanelBodyText();
        assertThat(StringUtils.normalizeSpace(totalVoucherValue))
                .as("Entitlement amount should be shown correctly on the confirmation page")
                .isEqualTo("You’re entitled to £12.40 a week. Your first payment will be £49.60.");
    }

    @Then("^my claim is sent to the back end")
    public void claimsSuccessfullySentToBackEnd() {
        String expectedBody = buildClaimantRequestJson(claimValuesThreadLocal.get());
        wireMockManager.verifyClaimantServiceRequestMatching(expectedBody);
    }

    @Then("^I am informed that my claim has been updated")
    public void checkClaimHasBeenUpdated() {
        ConfirmUpdatedPage confirmUpdatedPage = getPages().getConfirmUpdatedPage();
        assertThat(confirmUpdatedPage.getH2Text())
                .as("expected confirm page H2 text to be correct")
                .isEqualTo("What happens next");
        assertThat(confirmUpdatedPage.getPanelTitleText().trim())
                .as("expected confirmation header to be correct")
                .isEqualTo("Application updated");
    }
}
