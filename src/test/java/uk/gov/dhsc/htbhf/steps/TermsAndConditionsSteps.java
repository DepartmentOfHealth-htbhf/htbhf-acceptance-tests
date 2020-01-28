package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.TermsAndConditionsPage;

/**
 * Steps for the Terms and Conditions page after the claimant has checked their details.
 */
public class TermsAndConditionsSteps extends CommonSteps {

    @When("^I accept the terms and conditions and submit my application")
    public void iAcceptTheTermsAndConditionsAndSubmitMyApplication() {
        acceptTermsAndConditionsAndSubmitSuccessfulApplication();
    }

    @When("^I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {
        checkAnswersAndAcceptTsAndCs();
    }

    @When("^I click continue without accepting the terms and conditions")
    public void doNotAcceptTermsAndConditions() {
        TermsAndConditionsPage termsAndConditionsPage = getPages().getTermsAndConditionsPageNoWait();
        termsAndConditionsPage.openDirect();
        termsAndConditionsPage.clickContinue();
    }

    @When("^I double click send application button in terms and conditions page")
    public void doubleClickSendApplicationButton(){
        wireMockManager.setupClaimantServiceMappingsForSuccess();
        TermsAndConditionsPage termsAndConditionsPage = checkAnswersAndAcceptTsAndCs();
        termsAndConditionsPage.doubleClickContinue();
    }

    @Then("^I am told I must accept the terms and conditions")
    public void termsAndConditionsErrorShown() {
        TermsAndConditionsPage termsAndConditionsPage = getPages().getTermsAndConditionsPage();
        assertErrorHeaderTextPresent(termsAndConditionsPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(termsAndConditionsPage,
                termsAndConditionsPage.getFieldErrorId(),
                termsAndConditionsPage.getErrorLinkCss(),
                "Confirm that you’ve read and will comply with these terms and conditions");
    }

}
