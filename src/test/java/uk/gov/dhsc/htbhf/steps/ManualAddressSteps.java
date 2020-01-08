package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.ManualAddressPage;
import uk.gov.dhsc.htbhf.page.PageName;

import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildClaimValuesForManualAddress;
import static uk.gov.dhsc.htbhf.steps.Constants.*;

/**
 * Steps for manually enter an address
 */
public class ManualAddressSteps extends CommonSteps {

    @Given("^I have entered my details and wish to manually enter my address")
    public void enterDetailsUpToPage() {
        enterDetailsUpToPage(PageName.MANUAL_ADDRESS, buildClaimValuesForManualAddress());
    }

    @When("^I enter an address with postcode (.*)")
    public void enterAddressWithPostcode(String postcode) {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, TOWN, COUNTY, postcode);
    }

    @When("^I do not enter the first line of an address")
    public void enterAddressWithoutFirstLine() {
        enterManualAddress(BLANK_STRING, ADDRESS_LINE_2, TOWN, COUNTY, POSTCODE);
    }

    @When("^I enter in an address where the first line is too long")
    public void enterAddressWithFirstLineTooLong() {
        enterManualAddress(LONG_STRING, ADDRESS_LINE_2, TOWN, COUNTY, POSTCODE);
    }

    @When("^I enter in an address where the second line is too long")
    public void enterAddressWithSecondLineTooLong() {
        enterManualAddress(ADDRESS_LINE_1, LONG_STRING, TOWN, COUNTY, POSTCODE);
    }

    @When("^I enter in an address where the 'town or city' is too long")
    public void enterAddressWithTownOrCityTooLong() {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, LONG_STRING, COUNTY, POSTCODE);
    }

    @When("^I enter in an address where the 'county' is too long")
    public void enterAddressWithCountyTooLong() {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, TOWN, LONG_STRING, POSTCODE);
    }

    @When("^I do not enter the second line of an address")
    public void enterAddressWithoutSecondLine() {
        enterManualAddress(ADDRESS_LINE_1, BLANK_STRING, TOWN, COUNTY, POSTCODE);
    }

    @When("^I do not enter the 'town or city' of an address")
    public void enterAddressWithoutTown() {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, BLANK_STRING, COUNTY, POSTCODE);
    }

    @When("^I do not enter the 'county' of an address")
    public void enterAddressWithoutCounty() {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, TOWN, BLANK_STRING, POSTCODE);
    }

    @When("^I do not enter in any address fields")
    public void enterNoAddressFields() {
        enterManualAddress(BLANK_STRING, BLANK_STRING, BLANK_STRING, BLANK_STRING, BLANK_STRING);
    }

    @Then("^I am informed that I need to enter an address on the 'address line 1', 'town or city' and 'postcode' fields")
    public void verifyErrorForLine1TownAndPostcode() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertAddressLine1ErrorFieldAndLink(manualAddressPage, "Enter a building and street");
        assertTownOrCityErrorFieldAndLink(manualAddressPage, "Enter a town or city");
        assertPostcodeErrorFieldAndLink(manualAddressPage, "Enter a correct postcode, like AA1 1AA");
    }

    @Then("^I am informed that I need to enter an address on the 'address line 1' field")
    public void verifyErrorForLine1() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertAddressLine1ErrorFieldAndLink(manualAddressPage, "Enter a building and street");
    }

    @Then("^I am informed that the first line of the address is too long")
    public void verifyTooLongErrorForLine1() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertAddressLine1ErrorFieldAndLink(manualAddressPage, "The information you entered is too long");
    }

    @Then("^I am informed that the second line of the address is too long")
    public void verifyTooLongErrorForLine2() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertAddressLine2ErrorFieldAndLink(manualAddressPage, "The information you entered is too long");
    }

    @Then("^I am informed that the 'town or city' of the address is too long")
    public void verifyTooLongErrorForTownOrCity() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertTownOrCityErrorFieldAndLink(manualAddressPage, "The information you entered is too long");
    }

    @Then("^I am informed that the 'county' of the address is too long")
    public void verifyTooLongErrorForCounty() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertCountyErrorFieldAndLink(manualAddressPage, "The information you entered is too long");
    }

    @Then("^I am informed that I need to enter an address on the 'town or city' field")
    public void verifyErrorForTownOrCity() {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertTownOrCityErrorFieldAndLink(manualAddressPage, "Enter a town or city");
    }

    @Then("^I am informed that you can only apply if you live in England, Wales or Northern Ireland on the manual address page")
    public void assertPostcodeNotInEnglandWalesOrNorthernIreland() {
        assertPostcodeErrorPresent("You can only apply if you live in England, Wales or Northern Ireland");
    }

    @Then("^I am informed that the postcode is in the wrong format on the manual address page")
    public void assertPostcodeInWrongFormat() {
        assertPostcodeErrorPresent("Enter a correct postcode, like AA1 1AA");
    }

    private void assertPostcodeErrorPresent(String expectedErrorMessage) {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        assertErrorHeaderTextPresent(manualAddressPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getPostcodeInputErrorId(),
                manualAddressPage.getPostcodeInputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertAddressLine1ErrorFieldAndLink(ManualAddressPage manualAddressPage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getLine1InputErrorId(),
                manualAddressPage.getLine1InputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertAddressLine2ErrorFieldAndLink(ManualAddressPage manualAddressPage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getLine2InputErrorId(),
                manualAddressPage.getLine2InputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertTownOrCityErrorFieldAndLink(ManualAddressPage manualAddressPage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getTownInputErrorId(),
                manualAddressPage.getTownInputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertCountyErrorFieldAndLink(ManualAddressPage manualAddressPage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getCountyInputErrorId(),
                manualAddressPage.getCountyInputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void assertPostcodeErrorFieldAndLink(ManualAddressPage manualAddressPage, String expectedErrorMessage) {
        assertFieldErrorAndLinkTextPresentAndCorrect(manualAddressPage,
                manualAddressPage.getPostcodeInputErrorId(),
                manualAddressPage.getPostcodeInputErrorLinkCss(),
                expectedErrorMessage);
    }

}
