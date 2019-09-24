package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.EmailAddressPage;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.steps.Constants.LONG_STRING;

/**
 * Steps for the enter your email address page
 */
public class EmailAddressSteps extends CommonSteps {

    @When("^I enter a valid email address")
    public void enterValidEmailAddress() {
        enterEmailAddress();
    }

    @When("^I do not enter an email address")
    public void doNotEnterEmailAddress() {
        enterEmailAddress("");
    }

    @When("^I enter an email address that is too long")
    public void enterTooLongEmailAddress() {
        enterEmailAddress(LONG_STRING);
    }

    @When("^I enter (.*) as my email address")
    public void enterGivenEmailAddress(String emailAddress) {
        enterEmailAddress(emailAddress);
    }

    @Then("^I am informed that I must enter in a valid email address")
    public void verifyEmailAddressErrorPresent() {
        EmailAddressPage emailAddressPage = getPages().getEmailAddressPage();
        assertErrorHeaderTextPresent(emailAddressPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(
                emailAddressPage,
                emailAddressPage.getInputErrorId(),
                emailAddressPage.getInputErrorLinkCss(),
                "Enter an email address in the correct format, like name@example.com");
    }

    @Then("^I see the email address (.*) in the textbox")
    public void verifyEmailCorrectInTextBox(String expectedEmailAddress) {
        EmailAddressPage emailAddressPage = getPages().getEmailAddressPage();
        String enteredEmailAddress = emailAddressPage.getEmailAddress();
        assertThat(enteredEmailAddress).isEqualTo(expectedEmailAddress);
    }
}
