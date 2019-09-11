package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.CheckAnswersPage;
import uk.gov.dhsc.htbhf.page.CheckAnswersRowData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.page.component.RadioButton.YES_LABEL;
import static uk.gov.dhsc.htbhf.steps.Constants.EMAIL_ADDRESS;
import static uk.gov.dhsc.htbhf.steps.Constants.FULL_NAME;
import static uk.gov.dhsc.htbhf.steps.Constants.PHONE_NUMBER;
import static uk.gov.dhsc.htbhf.steps.Constants.VALID_ELIGIBLE_NINO;
import static uk.gov.dhsc.htbhf.utils.DateUtils.getFormattedDateInMonths;
import static uk.gov.dhsc.htbhf.utils.DateUtils.getFormattedDateLastYear;

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

    @Then("^I am shown the check answers page with correct page content" +
            "|all page content is present on the check answers page")
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

    @Then("^the check answers page contains all data entered for a pregnant woman")
    public void checkAnswerPageContainsAllDataForPregnantWoman() {
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        List<CheckAnswersRowData> claimContents = checkAnswersPage.getClaimSummaryListContents();
        List<CheckAnswersRowData> childrenContents = checkAnswersPage.getChildrenSummaryListContents();
        assertNameShown(claimContents);
        assertNinoShown(claimContents);
        assertDobShown(claimContents);
        assertAreYouPregnantValueShown(claimContents, YES_LABEL);
        assertDueDateShownInSixMonths(claimContents);
        assertFullAddressShown(claimContents);
        assertPhoneNumberShown(claimContents);
        assertEmailAddressShown(claimContents);
        assertDoYouHaveChildrenIsShown(claimContents, YES_LABEL);
        Map<String, String> childsDateOfBirth = Map.of(
                "Name", "Child1",
                "Date of birth", getFormattedDateLastYear());
        assertChildrensDatesOfBirthIsShown(childrenContents, childsDateOfBirth);
    }

    private void assertNameShown(List<CheckAnswersRowData> claimContents) {
        String nameValue = getValueForField(claimContents, "Your name");
        assertThat(nameValue).isEqualTo(FULL_NAME);
    }

    private void assertNinoShown(List<CheckAnswersRowData> claimContents) {
        String ninoValue = getValueForField(claimContents, "National Insurance number");
        assertThat(ninoValue).isEqualTo(VALID_ELIGIBLE_NINO);
    }

    private void assertDobShown(List<CheckAnswersRowData> claimContents) {
        String dobValue = getValueForField(claimContents, "Date of birth");
        assertThat(dobValue).isEqualTo(Constants.DATE_OF_BIRTH);
    }

    private void assertAreYouPregnantValueShown(List<CheckAnswersRowData> claimContents, String expectedValue) {
        String areYouPregnantValue = getValueForField(claimContents, "Are you pregnant?");
        assertThat(areYouPregnantValue).isEqualTo(expectedValue);
    }

    private void assertDueDateShownInSixMonths(List<CheckAnswersRowData> claimContents) {
        String dueDateValue = getValueForField(claimContents, "Baby’s due date");
        assertThat(dueDateValue).isEqualTo(getFormattedDateInMonths(2));
    }

    private void assertFullAddressShown(List<CheckAnswersRowData> claimContents) {
        assertAddressShown(claimContents, Constants.FULL_ADDRESS);
    }

    private void assertAddressShown(List<CheckAnswersRowData> claimContents, String expectedAddress) {
        String addressValue = getValueForField(claimContents, "Address");
        assertThat(addressValue).isEqualTo(expectedAddress);
    }

    private void assertPhoneNumberShown(List<CheckAnswersRowData> claimContents) {
        String phoneNumberValue = getValueForField(claimContents, "Mobile telephone number");
        assertThat(phoneNumberValue).isEqualTo(PHONE_NUMBER);
    }

    private void assertEmailAddressShown(List<CheckAnswersRowData> claimContents) {
        String emailAddressValue = getValueForField(claimContents, "Email address");
        assertThat(emailAddressValue).isEqualTo(EMAIL_ADDRESS);
    }

    private void assertDoYouHaveChildrenIsShown(List<CheckAnswersRowData> claimContents, String expectedValue) {
        String doYouHaveChildrenValue = getValueForField(claimContents, "Children under 4 years old?");
        assertThat(doYouHaveChildrenValue).isEqualTo(expectedValue);
    }

    private void assertChildrensDatesOfBirthChangeLinkIsShown() {
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        List<WebElement> changeLinks = checkAnswersPage.getChangeLinksFor("Children’s date of birth");
        assertThat(changeLinks).hasSize(1);
    }

    private void assertChildrensDatesOfBirthIsShown(List<CheckAnswersRowData> childrenContents, Map<String, String> expectedValues) {
        expectedValues.forEach((expectedHeader, expectedValue) -> {
            String actualValue = getValueForField(childrenContents, expectedHeader);
            assertThat(actualValue).isEqualTo(expectedValue);
        });

        assertChildrensDatesOfBirthChangeLinkIsShown();
    }

    private String getValueForField(List<CheckAnswersRowData> claimContents, String fieldName) {
        List<CheckAnswersRowData> matchingRows = claimContents.stream()
                .filter(value -> value.getHeader().equals(fieldName))
                .collect(Collectors.toList());
        assertThat(matchingRows).as("must only have one row matching key: " + fieldName).hasSize(1);
        return matchingRows.get(0).getValue();
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
