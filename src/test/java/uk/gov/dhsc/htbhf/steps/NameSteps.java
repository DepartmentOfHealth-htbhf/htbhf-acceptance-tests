package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.NamePage;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.steps.Constants.FIRST_NAME;
import static uk.gov.dhsc.htbhf.steps.Constants.LAST_NAME;
import static uk.gov.dhsc.htbhf.steps.Constants.LONG_STRING;

/**
 * Steps for the enter name page
 */
public class NameSteps extends CommonSteps {

    private static final String BLANK_STRING = "";

    @When("^I enter a first name which is too long")
    public void enterFirstNameWhichIsTooLong() {
        enterName(LONG_STRING, LAST_NAME);
    }

    @When("^I enter a last name which is too long")
    public void enterLastNameWhichIsTooLong() {
        enterName(FIRST_NAME, LONG_STRING);
    }

    @When("^I enter last name only")
    public void enterLastNameOnly() {
        enterName(BLANK_STRING, LONG_STRING);
    }

    @When("^I enter first name only")
    public void enterFirstNameOnly() {
        enterName(FIRST_NAME, BLANK_STRING);
    }

    @When("^I enter (.*) and (.*) values")
    public void enterFirstNameOnly(String firstName, String lastName) {
        enterName(firstName, lastName);
    }

    @Then("^I am informed that the first name is too long")
    public void verifyFirstNameTooLong() {
        NamePage namePage = getPages().getNamePage();
        assertErrorHeaderTextPresent(namePage);
        assertFirstNameErrorFieldAndLink(namePage, "Enter a shorter first or given name");
    }

    @Then("^I am informed that the last name is too long")
    public void verifyLastNameTooLong() {
        NamePage namePage = getPages().getNamePage();
        assertErrorHeaderTextPresent(namePage);
        assertLastNameErrorFieldAndLink(namePage, "Enter a shorter last or family name");
    }

    @Then("^I am informed that a first name is required")
    public void verifyFirstNameRequired() {
        NamePage namePage = getPages().getNamePage();
        assertErrorHeaderTextPresent(namePage);
        assertFirstNameErrorFieldAndLink(namePage, "Enter your first or given name");
    }

    @Then("^I am informed that a last name is required")
    public void verifyLastNameRequired() {
        NamePage namePage = getPages().getNamePage();
        assertErrorHeaderTextPresent(namePage);
        assertLastNameErrorFieldAndLink(namePage, "Enter your last or family name");
    }

    @Then("^I see the invalid first name I entered in the textbox")
    public void verifyFirstNameValueStillLongValue() {
        String enteredFirstName = getPages().getNamePage().getFirstName();
        assertThat(enteredFirstName).isEqualTo(LONG_STRING);
    }

    @Then("^I see the last name I entered in the textbox")
    public void verifyLastNameValueStillPresent() {
        String enteredLastName = getPages().getNamePage().getLastName();
        assertThat(enteredLastName).isEqualTo(LONG_STRING);
    }

    private void assertFirstNameErrorFieldAndLink(NamePage namePage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(namePage,
                namePage.getFirstNameInputErrorId(),
                namePage.getFirstNameInputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertLastNameErrorFieldAndLink(NamePage namePage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(namePage,
                namePage.getLastNameInputErrorId(),
                namePage.getLastNameInputErrorLinkCss(),
                expectedErrorMessage);
    }

}
