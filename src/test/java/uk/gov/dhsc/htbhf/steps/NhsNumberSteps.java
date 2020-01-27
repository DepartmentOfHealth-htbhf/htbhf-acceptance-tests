package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.NhsNumberPage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the enter Nhs Number page
 */
public class NhsNumberSteps extends BaseSteps {
    private static final String BLANK_NHSNO_MESSAGE = "Enter your NHS Number";
    private static final String INVALID_NHSNO_MESSAGE = "Enter a shorter NHS Number";

    @When("^I enter a valid nhs number")
    public void enterAValidNhsNumber() {
        enterNhsNumber("AB123");
    }

    @When("^I do not enter a nhs number")
    public void doNotEnterANhsNumber() {
        NhsNumberPage nhsNumberPage = getPages().getNhsNumberPage();
        nhsNumberPage.clickContinue();
    }

    @When("^I enter (.*) as my nhs number")
    public void enterAnInvalidNhsNumber(String invalidNhsno) {
        enterNhsNumber(invalidNhsno);
    }

    @Then("^I am informed that the nhs number is blank")
    public void assertNhsNumberBlankErrorIsPresent() {
        NhsNumberPage nhsNumberPage = getPages().getNhsNumberPage();
        assertErrorHeaderTextPresent(nhsNumberPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(nhsNumberPage,
                nhsNumberPage.getNhsnoInputErrorId(),
                nhsNumberPage.getNhsnoInputErrorLinkCss(),
                BLANK_NHSNO_MESSAGE);
    }

    @Then("^I am informed that the nhs number is in the wrong format")
    public void assertNhsNumberFormatErrorIsPresent() {
        NhsNumberPage nhsNumberPage = getPages().getNhsNumberPage();
        assertErrorHeaderTextPresent(nhsNumberPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(nhsNumberPage,
                nhsNumberPage.getNhsnoInputErrorId(),
                nhsNumberPage.getNhsnoInputErrorLinkCss(),
                INVALID_NHSNO_MESSAGE);
    }

    @Then("^I see the value (.*) in the input in the same format as I entered it in")
    public void assertNhsNumberInputValue(String nhsno) {
        NhsNumberPage nhsNumberPage = getPages().getNhsNumberPage();
        String inputValue = nhsNumberPage.getNhsnoInputValue();
        assertThat(inputValue).isEqualTo(nhsno);
    }

    private void enterNhsNumber(String nhsno) {
        NhsNumberPage nhsNumberPage = getPages().getNhsNumberPage();
        nhsNumberPage.enterNhsno(nhsno);
        nhsNumberPage.clickContinue();
    }

}

