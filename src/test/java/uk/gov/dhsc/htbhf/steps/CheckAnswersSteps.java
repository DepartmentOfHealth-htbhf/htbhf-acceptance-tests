package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.CheckAnswersPage;
import uk.gov.dhsc.htbhf.page.CheckAnswersRowData;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contains step definitions for the Check Your Details page.
 */
public class CheckAnswersSteps extends BaseSteps {

    @When("^I choose to change my answer to Do you have children")
    public void changeDoYouHaveChildrenAnswer() {
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        checkAnswersPage.clickChangeLinkFor("Children under 4 years old?");
    }

    @Then("^there are no children displayed")
    public void thereAreNoChildrenDisplayed() {
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        List<WebElement> childrenSummary = checkAnswersPage.getChildrenSummaryElements();
        assertThat(childrenSummary).isEmpty();
    }

    @Then("^I am shown the check answers page with correct page content$")
    public void verifyCheckDetailsPageCorrect() {
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        assertThat(checkAnswersPage.getH1Text())
                .as("expected check page H1 text to not be empty")
                .isNotBlank();
        assertThat(checkAnswersPage.getH2Text())
                .as("expected check page H2 text to not be empty")
                .isNotBlank();
        assertThat(checkAnswersPage.getSubmitButtonText())
                .as("expected submit button text to not be empty")
                .isNotBlank();
        List<CheckAnswersRowData> claimContents = checkAnswersPage.getClaimSummaryListContents();
        assertHeaderAndChangeLinkShownForEachRow(claimContents);
        List<CheckAnswersRowData> childrenContents = checkAnswersPage.getChildrenSummaryListContents();
        assertHeaderTextShownForEachRow(childrenContents);
    }

    private void assertHeaderAndChangeLinkShownForEachRow(List<CheckAnswersRowData> claimContents) {
        List<CheckAnswersRowData> matchingRows = claimContents.stream()
                .filter(CheckAnswersRowData::hasChangeLinkAndHeaderText)
                .collect(Collectors.toList());
        assertThat(matchingRows)
                .as("Change link and header must be shown for each row in the claim summary")
                .hasSize(claimContents.size());
    }

    private void assertHeaderTextShownForEachRow(List<CheckAnswersRowData> childrenContents) {
        List<CheckAnswersRowData> matchingRows = childrenContents.stream()
                .filter(CheckAnswersRowData::hasHeaderText)
                .collect(Collectors.toList());
        assertThat(matchingRows)
                .as("Header text must be shown for each row in the children's date of birth summary")
                .hasSize(childrenContents.size());
    }

}
