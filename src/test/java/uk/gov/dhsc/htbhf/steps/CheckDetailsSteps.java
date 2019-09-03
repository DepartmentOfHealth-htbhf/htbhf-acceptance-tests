package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import uk.gov.dhsc.htbhf.page.CheckAnswersPage;
import uk.gov.dhsc.htbhf.page.CheckDetailsRowData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contains step definitions for the Check Your Details page.
 */
public class CheckDetailsSteps extends BaseSteps {

    private CheckAnswersPage checkAnswersPage;

    @Then("^I am shown the check answers page with correct page content$")
    public void verifyCheckDetailsPageCorrect() {
        checkAnswersPage = getPages().getCheckAnswersPage();
        assertThat(checkAnswersPage.getH1Text())
                .as("expected check page H1 text to not be empty")
                .isNotBlank();
        assertThat(checkAnswersPage.getH2Text())
                .as("expected check page H2 text to not be empty")
                .isNotBlank();
        assertThat(checkAnswersPage.getSubmitButtonText())
                .as("expected submit button text to not be empty")
                .isNotBlank();
        List<CheckDetailsRowData> claimContents = checkAnswersPage.getClaimSummaryListContents();
        assertHeaderAndChangeLinkShownForEachRow(claimContents);
        List<CheckDetailsRowData> childrenContents = checkAnswersPage.getChildrenSummaryListContents();
        assertHeaderTextShownForEachRow(childrenContents);
    }

    private void assertHeaderAndChangeLinkShownForEachRow(List<CheckDetailsRowData> claimContents) {
        List<CheckDetailsRowData> matchingRows = claimContents.stream()
                .filter(CheckDetailsRowData::hasChangeLinkAndHeaderText)
                .collect(Collectors.toList());
        assertThat(matchingRows)
                .as("Change link and header must be shown for each row in the claim summary")
                .hasSize(claimContents.size());
    }

    private void assertHeaderTextShownForEachRow(List<CheckDetailsRowData> childrenContents) {
        List<CheckDetailsRowData> matchingRows = childrenContents.stream()
                .filter(CheckDetailsRowData::hasHeaderText)
                .collect(Collectors.toList());
        assertThat(matchingRows)
                .as("Header text must be shown for each row in the children's date of birth summary")
                .hasSize(childrenContents.size());
    }

}
