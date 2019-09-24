package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.DateOfBirthPage;

/**
 * Steps for the date of birth page
 */
public class DateOfBirthSteps extends CommonSteps {

    @When("^I enter my date of birth as day: (.*), month: (.*) and year: (.*)")
    public void enterDateOfBirth(String day, String month, String year) {
        enterDateOfBirthAndSubmit(day, month, year);
    }

    @Then("^I am informed that my date of birth should be in the past")
    public void dateOfBirthShouldBeInPast() {
        assertDateOfBirthErrorPresent("Date of birth must be in the past");
    }

    @Then("^I am informed that a valid date of birth is required")
    public void validDateOfBirthIsRequired() {
        assertDateOfBirthErrorPresent("Enter your date of birth");
    }

    private void assertDateOfBirthErrorPresent(String expectedErrorMessage) {
        DateOfBirthPage dateOfBirthPage = getPages().getDateOfBirthPage();
        assertErrorHeaderTextPresent(dateOfBirthPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(dateOfBirthPage,
                dateOfBirthPage.getDateOfBirthFieldErrorId(),
                dateOfBirthPage.getDateOfBirthErrorLinkCss(),
                expectedErrorMessage);
    }

}
