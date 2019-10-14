package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.PageName;
import uk.gov.dhsc.htbhf.page.SendCodePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.page.component.RadioButton.EMAIL_LABEL;
import static uk.gov.dhsc.htbhf.page.component.RadioButton.TEXT_LABEL;
import static uk.gov.dhsc.htbhf.steps.Constants.EMAIL_ADDRESS;
import static uk.gov.dhsc.htbhf.steps.Constants.PHONE_NUMBER;

/**
 * Contains steps for the Send Code page
 */
public class SendCodeSteps extends CommonSteps {

    @When("I select Text as the method to receive the code")
    public void selectText() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        sendCodePage.selectText();
    }

    @When("I select Email as the method to receive the code")
    public void selectEmail() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        sendCodePage.selectEmail();
    }

    @Then("^Text and Email options are displayed on the send code page")
    public void textAndEmailOptionsDisplayed() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        List<String> labels = sendCodePage.getAllRadioLabels();

        assertThat(labels).containsExactly(TEXT_LABEL, EMAIL_LABEL);

        String textHint = sendCodePage.getTextHint();
        assertThat(textHint).containsSequence(PHONE_NUMBER);
        String emailHint = sendCodePage.getEmailHint();
        assertThat(emailHint).containsSequence(EMAIL_ADDRESS);
    }

    @Then("^The change text link points to the phone number page")
    public void changeLinkPointsToPhoneNumberPage() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        String changeLink = sendCodePage.getChangeTextLink();
        assertLinkPointsToPage(changeLink, PageName.PHONE_NUMBER);
    }

    @Then("^The change email link points to the email address page")
    public void changeLinkPointsToEmailAddressPage() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        String changeLink = sendCodePage.getChangeEmailLink();
        assertLinkPointsToPage(changeLink, PageName.EMAIL_ADDRESS);
    }

    @Then("I am informed that I need to select an option for send code")
    public void assertSendCodeErrorShown() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        assertErrorHeaderTextPresent(sendCodePage);
        assertFieldErrorAndLinkTextPresentAndCorrect(
                sendCodePage,
                sendCodePage.getFieldErrorId(),
                sendCodePage.getErrorLinkCss(),
                "Select if you want to receive a code by text or email");
    }

}
