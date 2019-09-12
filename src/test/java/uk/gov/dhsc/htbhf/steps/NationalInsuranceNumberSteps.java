package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.NationalInsuranceNumberPage;
import uk.gov.dhsc.htbhf.utils.NinoGenerator;

import static org.assertj.core.api.Assertions.assertThat;

public class NationalInsuranceNumberSteps extends BaseSteps {
    String INVALID_NINO_MESSAGE = "Enter a National Insurance number in the correct format";

    @When("^I enter a valid national insurance number")
    public void enterAValidNationalInsuranceNumber() {
        enterANationalInsuranceNumber(NinoGenerator.generateEligibleNino());
    }

    @When("^I do not enter a national insurance number")
    public void doNotEnterANationalInsuranceNumber() {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        nationalInsuranceNumberPage.clickContinue();
    }

    @When("^I enter (.*) as my national insurance number")
    public void enterAnInvalidNationalInsuranceNumber(String invalidNino) {
        enterANationalInsuranceNumber(invalidNino);
    }

    @Then("^I am informed that the national insurance number is in the wrong format")
    public void assertNationalInsuranceNumberFormatErrorIsPresent() {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        assertErrorHeaderTextPresent(nationalInsuranceNumberPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(nationalInsuranceNumberPage,
                nationalInsuranceNumberPage.getNinoInputErrorId(),
                nationalInsuranceNumberPage.getNinoInputErrorLinkCss(),
                INVALID_NINO_MESSAGE);
    }

    @Then("^I see the value (.*) in the input")
    public void assertNationalInsuranceNumberInputValue(String nino) {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        String inputValue = nationalInsuranceNumberPage.getNinoInputValue();
        assertThat(inputValue).isEqualTo(nino);
    }

    public void enterANationalInsuranceNumber(String nino) {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        nationalInsuranceNumberPage.enterNino(nino);
        nationalInsuranceNumberPage.clickContinue();
    }
}
