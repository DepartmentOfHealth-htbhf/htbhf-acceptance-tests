package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.*;

import static uk.gov.dhsc.htbhf.steps.Constants.*;
import static uk.gov.dhsc.htbhf.utils.NinoGenerator.generateEligibleNino;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends BaseSteps {

    private GuidancePage guidancePage;
    private DoYouLiveInScotlandPage doYouLiveInScotlandPage;
    private EnterDobPage enterDobPage;
    private DoYouHaveChildren doYouHaveChildren;
    private EnterChildrensDobPage enterChildrensDobPage;
    private AreYouPregnantPage areYouPregnantPage;
    private EnterNamePage enterNamePage;
    private EnterNinoPage enterNinoPage;
    private ManualAddressPage manualAddressPage;
    private PhoneNumberPage phoneNumberPage;
    private EmailAddressPage emailAddressPage;
    private SendCodePage sendCodePage;
    private EnterCodePage enterCodePage;
    private ConfirmationCodePage confirmationCodePage;
    private CheckDetailsPage checkDetailsPage;

    //TODO MRS 2019-08-01: Keep building this up...
    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        guidancePage = new GuidancePage(webDriver, baseUrl, webDriverWait);
        guidancePage.clickStartButton();
        enterDoYouLiveInScotlandNoAndSubmit();
        enterDateOfBirthAndSubmit();
        enterDoYouHaveChildrenYesAndSubmit();
        enterTwoChildrensDatesOfBirth();
        selectYesOnPregnancyPage();
        enterName();
        enterNino();
        enterManualAddress();
        enterPhoneNumber();
        enterEmailAddress();
        selectTextOnSendCode();
        enterConfirmationCodeAndSubmit();
        checkDetailsPage = new CheckDetailsPage(webDriver, baseUrl, webDriverWait);
        checkDetailsPage.waitForPageToLoad();
    }

    private void enterConfirmationCodeAndSubmit() {
        enterCodePage = new EnterCodePage(webDriver, baseUrl, webDriverWait);
        enterCodePage.waitForPageToLoad();
        confirmationCodePage = new ConfirmationCodePage(webDriver, sessionDetailsUrl);
        String confirmationCode = confirmationCodePage.getConfirmationCodeForSession();
        enterCodePage.open();
        enterCodePage.enterCode(confirmationCode);
        enterCodePage.clickContinue();
    }

    private void selectTextOnSendCode() {
        sendCodePage = new SendCodePage(webDriver, baseUrl, webDriverWait);
        sendCodePage.waitForPageToLoad();
        sendCodePage.selectText();
        sendCodePage.clickContinue();
    }

    private void enterEmailAddress() {
        emailAddressPage = new EmailAddressPage(webDriver, baseUrl, webDriverWait);
        emailAddressPage.waitForPageToLoad();
        emailAddressPage.enterEmailAddress(EMAIL_ADDRESS);
        emailAddressPage.clickContinue();
    }

    private void enterPhoneNumber() {
        phoneNumberPage = new PhoneNumberPage(webDriver, baseUrl, webDriverWait);
        phoneNumberPage.waitForPageToLoad();
        phoneNumberPage.enterPhoneNumber(PHONE_NUMBER);
        phoneNumberPage.clickContinue();
    }

    private void enterManualAddress() {
        manualAddressPage = new ManualAddressPage(webDriver, baseUrl, webDriverWait);
        manualAddressPage.enterAddressLine1(ADDRESS_LINE_1);
        manualAddressPage.enterAddressLine2(ADDRESS_LINE_2);
        manualAddressPage.enterTownOrCity(TOWN);
        manualAddressPage.enterCounty(COUNTY);
        manualAddressPage.enterPostcode(POSTCODE);
        manualAddressPage.clickContinue();
    }

    private void enterNino() {
        enterNinoPage = new EnterNinoPage(webDriver, baseUrl, webDriverWait);
        enterNinoPage.waitForPageToLoad();
        enterNinoPage.enterNino(generateEligibleNino());
        enterNinoPage.clickContinue();
    }

    private void enterName() {
        enterNamePage = new EnterNamePage(webDriver, baseUrl, webDriverWait);
        enterNamePage.waitForPageToLoad();
        enterNamePage.enterName(FIRST_NAME, LAST_NAME);
        enterNamePage.clickContinue();
    }

    private void selectYesOnPregnancyPage() {
        areYouPregnantPage = new AreYouPregnantPage(webDriver, baseUrl, webDriverWait);
        areYouPregnantPage.waitForPageToLoad();
        areYouPregnantPage.selectYes();
        areYouPregnantPage.enterExpectedDeliveryDate(2);
        areYouPregnantPage.clickContinue();
    }

    private void enterTwoChildrensDatesOfBirth() {
        enterChildrensDobPage = new EnterChildrensDobPage(webDriver, baseUrl, webDriverWait);
        enterChildrensDobPage.waitForPageToLoad();
        enterChildrensDobPage.enterChild3OrUnderDetails(1);
        enterChildrensDobPage.clickAddAnotherChild();
        enterChildrensDobPage.enterChild3OrUnderDetails(2);
        enterChildrensDobPage.clickContinue();
    }

    private void enterDoYouHaveChildrenYesAndSubmit() {
        doYouHaveChildren = new DoYouHaveChildren(webDriver, baseUrl, webDriverWait);
        doYouHaveChildren.waitForPageToLoad();
        doYouHaveChildren.selectYesRadioButton();
        doYouHaveChildren.clickContinue();
    }

    private void enterDateOfBirthAndSubmit() {
        enterDobPage = new EnterDobPage(webDriver, baseUrl, webDriverWait);
        enterDobPage.waitForPageToLoad();
        enterDobPage.getDayInputField().enterValue(DOB_DAY);
        enterDobPage.getMonthInputField().enterValue(DOB_MONTH);
        enterDobPage.getYearInputField().enterValue(DOB_YEAR);
        enterDobPage.clickContinue();
    }

    private void enterDoYouLiveInScotlandNoAndSubmit() {
        doYouLiveInScotlandPage = new DoYouLiveInScotlandPage(webDriver, baseUrl, webDriverWait);
        doYouLiveInScotlandPage.waitForPageToLoad();
        doYouLiveInScotlandPage.selectNoRadioButton();
        doYouLiveInScotlandPage.clickContinue();
    }

}
