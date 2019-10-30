package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.PhoneNumberPage;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.steps.Constants.BLANK_STRING;

/**
 * Steps for the enter phone number page
 */
public class PhoneNumberSteps extends CommonSteps {

    @When("^I enter (.*) as my phone number")
    public void enterGivenPhoneNumber(String phoneNumber) {
        enterPhoneNumber(phoneNumber);
    }

    @When("^I do not enter a phone number")
    public void doNotEnterAPhoneNumber() {
        enterPhoneNumber(BLANK_STRING);
    }

    @Then("^I am informed that I must enter in a valid phone number")
    public void verifyPhoneNumberError() {
        PhoneNumberPage phoneNumberPage = getPages().getPhoneNumberPage();
        assertErrorHeaderTextPresent(phoneNumberPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(phoneNumberPage,
                phoneNumberPage.getPhoneNumberInputErrorId(),
                phoneNumberPage.getPhoneNumberInputErrorLinkCss(),
                "Enter a UK mobile number");
    }

    @Then("^I see the value (.*) in the phone number textbox")
    public void valueShownInPhoneNumberTextBox(String phoneNumber) {
        String enteredPhoneNumber = getPages().getPhoneNumberPage().getPhoneNumber();
        assertThat(enteredPhoneNumber).isEqualTo(phoneNumber);
    }
}
