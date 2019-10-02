package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.EnterCodePage;
import uk.gov.dhsc.htbhf.page.PageName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the enter code page.
 */
public class EnterCodeSteps extends CommonSteps {

    @When("^I enter my confirmation code")
    public void enterConfirmationCode() {
        enterConfirmationCodeAndSubmit();
    }

    @When("^I do not enter a confirmation code")
    public void doNotEnterConfirmationCode() {
        EnterCodePage enterCodePage = getPages().getEnterCodePage();
        enterCodePage.clickContinue();
    }

    @When("^I enter in the wrong confirmation code")
    public void enterWrongConfirmationCode() {
        EnterCodePage enterCodePage = getPages().getEnterCodePage();
        enterCodePage.enterCode("1234567890");
        enterCodePage.clickContinue();
    }

    @Then("^I am informed that I must enter in the code that was sent to me")
    public void errorMessageShown() {
        EnterCodePage enterCodePage = getPages().getEnterCodePage();
        assertFieldErrorAndLinkTextPresentAndCorrect(enterCodePage,
                enterCodePage.getInputErrorId(),
                enterCodePage.getInputErrorLinkCss(),
                "Enter the 6 digit code we sent you");
    }

    @Then("^The request a new code link points to the send code page")
    public void verifyRequestNewCodeLink() {
        EnterCodePage enterCodePage = getPages().getEnterCodePage();
        String requestNewCodeLinkHref = enterCodePage.getRequestNewCodeLinkHref();
        String sendCodePagePath = getPages().getPageByName(PageName.SEND_CODE).getFullPath();
        assertThat(requestNewCodeLinkHref).isEqualTo(sendCodePagePath);
    }
}
