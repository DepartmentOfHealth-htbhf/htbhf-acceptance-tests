package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.PageName;

import static uk.gov.dhsc.htbhf.steps.Constants.EMAIL_ADDRESS_2;
import static uk.gov.dhsc.htbhf.steps.Constants.PHONE_NUMBER_2;

/**
 * Steps for the confirmation code page
 */
public class ConfirmationCodeNavigationSteps extends CommonSteps {

    @When("^I complete the application with valid details, selecting to receive my confirmation code via text")
    public void completeApplicationWithTextConfirmationCode() {
        enterDetailsUpToPage(PageName.SEND_CODE);
        selectTextOnSendCode();
        enterConfirmationCodeAndSubmit();
    }

    @When("^I complete the application with valid details, selecting to receive my confirmation code via email")
    public void completeApplicationWithEmailConfirmationCode() {
        enterDetailsUpToPage(PageName.SEND_CODE);
        selectEmailOnSendCode();
        enterConfirmationCodeAndSubmit();
    }

    @When("^I enter in a new phone number")
    public void enterNewPhoneNumber() {
        enterPhoneNumber(PHONE_NUMBER_2);
    }

    @When("^I enter in a new email address")
    public void enterNewEmailAddress() {
        enterEmailAddress(EMAIL_ADDRESS_2);
    }

    @Then("^I must complete all steps after the phone number page, including entering a new code")
    public void completeAllStepsAfterPhoneNumberIncludingEnterANewCode() {
        // The email address will already be filled in, no need to enter one in
        getPages().getEmailAddressPage().clickContinue();
        selectTextOnSendCode();
        enterConfirmationCodeAndSubmit();
    }

    @Then("^I must complete all steps after the email address page, including entering a new code")
    public void completeAllStepsAfterEmailAddressIncludingEnterANewCode() {
        selectEmailOnSendCode();
        enterConfirmationCodeAndSubmit();
    }
}
