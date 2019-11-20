package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.PostcodePage;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.steps.Constants.POSTCODE;
import static uk.gov.dhsc.htbhf.steps.Constants.POSTCODE_WITH_NO_RESULTS;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aPostcodeLookupResponseWithResults;

/**
 * Steps for looking up addresses for a claimant
 */
public class AddressLookupSteps extends CommonSteps {

    private static final String ADDRESSES_FOUND_SUMMARY = " addresses found";

    @Given("OS places returns an error response")
    public void givenOsPlacesReturnsAnError() {
        wireMockManager.setupPostcodeLookupErrorResponse();
    }

    @Given("OS places resets the connection")
    public void givenOsResetsTheConnection() {
        wireMockManager.setupPostcodeLookupConnectionResetResponse();
    }

    @When("^I enter a postcode that returns no search results")
    public void enterPostcodeWithNoSearchResults() {
        setupPostcodeLookupWithResults(POSTCODE_WITH_NO_RESULTS);
        enterPostcodeAndSubmit(POSTCODE_WITH_NO_RESULTS);
    }

    @When("^I enter a postcode that returns search results")
    public void enterPostcodeWithSearchResults() {
        setupPostcodeLookupWithResults(POSTCODE);
        enterPostcodeAndSubmit(POSTCODE);
    }

    @When("^I enter (.*) as my postcode")
    public void enterPostcode(String postcode) {
        enterPostcodeAndSubmit(postcode);
    }

    @When("^I enter my postcode")
    public void enterPostcode() {
        enterPostcodeAndSubmit(POSTCODE);
    }

    @When("^I select an address")
    public void selectAnAddress() {
        getPages().getSelectAddressPage().selectFirstAddress();
    }

    @When("^I click the address not listed link")
    public void selectAddressNotListedLink() {
        getPages().getSelectAddressPage().clickAddressNotListedLink();
    }

    @When("^I click the change postcode link")
    public void selectChangePostcodeLink() {
        getPages().getSelectAddressPage().clickChangePostcodeLink();
    }

    @Then("^I am shown a list of addresses")
    public void listOfAddressesAreShown() {
        List<WebElement> addressOptions = getPages().getSelectAddressPage().getSelectableAddressOptions();
        assertThat(addressOptions).hasSize(2);
        verifyAddressOptionsText(POSTCODE, addressOptions);
    }

    @Then("^the list of addresses starts with a count of matching addresses")
    public void listOfAddressesIncludesCountOfMatchingAddresses() {
        List<WebElement> addressOptions = getPages().getSelectAddressPage().getAllAddressOptions();
        assertThat(addressOptions).hasSizeGreaterThan(1);
        WebElement countOption = addressOptions.get(0);
        assertThat(countOption.isEnabled()).isFalse();
        assertSummaryRowMatchesAddressList(addressOptions, countOption);
    }

    private void assertSummaryRowMatchesAddressList(List<WebElement> addressOptions, WebElement countOption) {
        String countText = countOption.getText();
        assertThat(countText).endsWith(ADDRESSES_FOUND_SUMMARY);
        int addressCount = Integer.parseInt(substringBefore(countText, ADDRESSES_FOUND_SUMMARY));
        assertThat(addressOptions).hasSize(addressCount + 1);
    }

    @Then("^I am informed that no addresses were found for my postcode")
    public void noAddressesFoundForPostcode() {
        WebElement addressNotFoundElement = getPages().getSelectAddressPage().getAddressNotFoundElement();
        assertThat(addressNotFoundElement.isDisplayed()).isTrue();
    }

    @Then("^I am shown a link to change my postcode")
    public void changePostcodeLinkIsShown() {
        String changePostcodeLinkHref = getPages().getSelectAddressPage().getChangePostcodeLinkHref();
        assertThat(changePostcodeLinkHref).isEqualTo(getPages().getPostcodePage().getFullPath());
    }

    @Then("^I am shown a button to enter my address manually")
    public void enterManualAddressButtonIsShown() {
        String buttonText = getPages().getSelectAddressPage().getSubmitButtonText();
        assertThat(buttonText).isEqualTo("Enter address manually");
    }

    @Then("^I am shown an address not listed link")
    public void addressNotListedLinkShown() {
        String addressNotListedLinkHref = getPages().getSelectAddressPage().getAddressNotListedLinkHref();
        assertThat(addressNotListedLinkHref).isEqualTo(getPages().getManualAddressPage().getFullPath());
    }

    @Then("^I am shown a continue button")
    public void continueButtonIsShown() {
        String buttonText = getPages().getSelectAddressPage().getSubmitButtonText();
        assertThat(buttonText).isEqualTo("Continue");
    }

    @Then("^I am informed that the postcode is in the wrong format")
    public void assertPostcodeInWrongFormat() {
        assertPostcodeErrorPresent("Enter a correct postcode, like AA1 1AA");
    }

    @Then("^I am informed that you can only apply if I live in England, Wales or Northern Ireland")
    public void assertPostcodeNotInEnglandWalesOrNorthernIreland() {
        assertPostcodeErrorPresent("You can only apply if you live in England, Wales or Northern Ireland");
    }

    @Then("^I am informed that there's a problem with the address lookup")
    public void postcodeLookupNotWorkingErrorShown() {
        WebElement postcodeLookupNotWorkingElement = getPages().getSelectAddressPage().getPostcodeLookupNotWorkingElement();
        assertThat(postcodeLookupNotWorkingElement.isDisplayed()).isTrue();
    }

    @Then("^I am shown a link to enter my address manually")
    public void manualAddressLinkIsShown() {
        String manualAddressLink = getPages().getSelectAddressPage().getManualAddressLinkHref();
        assertThat(manualAddressLink).isEqualTo(getPages().getManualAddressPage().getFullPath());
    }

    private void assertPostcodeErrorPresent(String expectedErrorMessage) {
        PostcodePage postcodePage = getPages().getPostcodePage();
        assertErrorHeaderTextPresent(postcodePage);
        assertFieldErrorAndLinkTextPresentAndCorrect(postcodePage,
                postcodePage.getPostcodeInputErrorId(),
                postcodePage.getPostcodeInputErrorLinkCss(),
                expectedErrorMessage);
    }

    private void verifyAddressOptionsText(String postcode, List<WebElement> options) {
        String mockApiResponse = aPostcodeLookupResponseWithResults(postcode);
        List<String> addressLines = mockApiResponse.lines().filter(line -> line.trim().startsWith("\"ADDRESS\"")).collect(Collectors.toList());
        options.forEach(option -> assertAddressOptionTextMatchesResult(addressLines, option));
    }

    private void assertAddressOptionTextMatchesResult(List<String> addressLines, WebElement option) {
        String optionText = option.getText().toUpperCase();
        boolean matchFound = addressLines.stream().anyMatch(addressLine -> addressLine.contains(optionText));
        assertThat(matchFound).as("Expecting to find address option in Wiremock mapping: " + optionText).isTrue();
    }

}
