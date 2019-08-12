package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import uk.gov.dhsc.htbhf.page.ConfirmationPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the Confirmation page which is the shown once their application has been submitted.
 */
public class ConfirmDetailsSteps extends BaseSteps {

    private ConfirmationPage confirmationPage;

    @Then("/^I am shown a successful confirmation page$/")
    public void iAmShownASuccessfulConfirmationPage() {
        confirmationPage = new ConfirmationPage(webDriver, baseUrl, webDriverWait);
        confirmationPage.waitForPageToLoad();
        checkAllPageContentIsPresentAndCorrect();
        wireMockManager.resetWireMockStubs();
    }

    @Then("/^my entitlement is 12.40 per week with a first payment of 49.60$/")
    public void myEntitlementIsShownCorrectly() {
        String totalVoucherValue = confirmationPage.getPanelBodyText();
        assertThat(totalVoucherValue).as("Entitlement amount should be shown correctly on the confirmation page")
                .isEqualTo("You’re entitled to\n£12.40 a week. Your first payment\nwill be £49.60.");
    }

    private void checkAllPageContentIsPresentAndCorrect() {
        String h2Text = confirmationPage.getH2Text();
        assertThat(h2Text).as("expected confirm page H2 text to be correct").isEqualTo("What happens next");
        String panelTitle = confirmationPage.getPanelTitleText();
        assertThat(panelTitle).as("expected confirmation header to be correct").isEqualTo("Application complete");
    }
}
