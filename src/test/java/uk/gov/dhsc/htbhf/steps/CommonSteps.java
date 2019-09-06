package uk.gov.dhsc.htbhf.steps;

import uk.gov.dhsc.htbhf.page.*;

import static uk.gov.dhsc.htbhf.steps.Constants.*;
import static uk.gov.dhsc.htbhf.utils.NinoGenerator.generateEligibleNino;

/**
 * Contains common steps used by more than one step
 */
public class CommonSteps extends BaseSteps {

    protected void enterConfirmationCodeAndSubmit() {
        EnterCodePage enterCodePage = getPages().getEnterCodePage();
        ConfirmationCodePage confirmationCodePage = new ConfirmationCodePage(getWebDriver(), sessionDetailsUrl);
        String confirmationCode = confirmationCodePage.getConfirmationCodeForSession();
        enterCodePage.open();
        enterCodePage.enterCode(confirmationCode);
        enterCodePage.clickContinue();
    }

    protected void selectTextOnSendCode() {
        SendCodePage sendCodePage = getPages().getSendCodePage();
        sendCodePage.selectText();
        sendCodePage.clickContinue();
    }

    protected void enterEmailAddress() {
        EmailAddressPage emailAddressPage = getPages().getEmailAddressPage();
        emailAddressPage.enterEmailAddress(EMAIL_ADDRESS);
        emailAddressPage.clickContinue();
    }

    protected void enterPhoneNumber() {
        PhoneNumberPage phoneNumberPage = getPages().getPhoneNumberPage();
        phoneNumberPage.enterPhoneNumber(PHONE_NUMBER);
        phoneNumberPage.clickContinue();
    }

    protected void enterDefaultManualAddress() {
        enterManualAddress(ADDRESS_LINE_1, ADDRESS_LINE_2, TOWN, COUNTY, POSTCODE);
    }

    protected void enterManualAddress(String addressLine1, String addressLine2, String town, String county, String postcode) {
        ManualAddressPage manualAddressPage = getPages().getManualAddressPage();
        manualAddressPage.enterAddressLine1(addressLine1);
        manualAddressPage.enterAddressLine2(addressLine2);
        manualAddressPage.enterTownOrCity(town);
        manualAddressPage.enterCounty(county);
        manualAddressPage.enterPostcode(postcode);
        manualAddressPage.clickContinue();
    }

    protected void enterNino() {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        nationalInsuranceNumberPage.enterNino(generateEligibleNino());
        nationalInsuranceNumberPage.clickContinue();
    }

    protected void enterDefaultName() {
        enterName(FIRST_NAME, LAST_NAME);
    }

    protected void enterName(String firstName, String lastName) {
        NamePage namePage = getPages().getNamePage();
        namePage.enterName(firstName, lastName);
        namePage.clickContinue();
    }

    protected void selectYesOnPregnancyPage() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectYes();
        areYouPregnantPage.enterExpectedDeliveryDate(2);
        areYouPregnantPage.clickContinue();
    }

    protected void selectNoOnPregnancyPage() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectNo();
        areYouPregnantPage.clickContinue();
    }

    protected void enterOneChildsDateOfBirth() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChild3OrUnderDetails(1);
        childDateOfBirthPage.clickContinue();
    }

    protected void enterDoYouHaveChildrenYesAndSubmit() {
        DoYouHaveChildrenPage doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectYesRadioButton();
        doYouHaveChildrenPage.clickContinue();
    }

    protected void enterDateOfBirthAndSubmit() {
        DateOfBirthPage dateOfBirthPage = getPages().getDateOfBirthPage();
        dateOfBirthPage.getDayInputField().enterValue(DOB_DAY);
        dateOfBirthPage.getMonthInputField().enterValue(DOB_MONTH);
        dateOfBirthPage.getYearInputField().enterValue(DOB_YEAR);
        dateOfBirthPage.clickContinue();
    }

    protected void enterDoYouLiveInScotlandNoAndSubmit() {
        ScotlandPage scotlandPage = getPages().getScotlandPage();
        scotlandPage.selectNoRadioButton();
        scotlandPage.clickContinue();
    }

    protected void acceptTermsAndConditionsAndSubmitApplication() {
        wireMockManager.setupWireMockMappingsWithStatus("ELIGIBLE");
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        checkAnswersPage.clickContinue();
        TermsAndConditionsPage termsAndConditionsPage = getPages().getTermsAndConditionsPage();
        termsAndConditionsPage.clickAcceptTermsAndConditionsCheckBox();
        termsAndConditionsPage.clickContinue();
    }
}
