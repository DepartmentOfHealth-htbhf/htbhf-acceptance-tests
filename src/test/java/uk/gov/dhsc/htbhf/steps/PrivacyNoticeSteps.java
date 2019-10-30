package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.PrivacyNoticePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the privacy notice page.
 */
public class PrivacyNoticeSteps extends CommonSteps {

    @Then("^all page content is present on the privacy notice page")
    public void verifyPrivacyNoticeContentPresent() {
        checkPrivacyNoticePageContentIsPresentAndCorrect();
    }

    private void checkPrivacyNoticePageContentIsPresentAndCorrect() {
        PrivacyNoticePage privacyNoticePage = getPages().getPrivacyNoticePage();
        String h1Text = privacyNoticePage.getH1Text();
        assertThat(h1Text).as("expected privacy notice page H1 text to be correct")
                .isEqualTo("Privacy notice");
        String h2Text = privacyNoticePage.getH2Text();
        assertThat(h2Text).as("expected privacy notice page H2 text to be correct")
                .isEqualTo("What data we collect and why we need it");

        // Make sure that there is a table on the page (this is the specific cookie details)
        List<WebElement> allTables = privacyNoticePage.findAllTablesOnPage();
        assertThat(allTables).as("expect to find at least one table on privacy notice page")
                .hasSizeGreaterThanOrEqualTo(1);
    }

}
