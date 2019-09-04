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
    private ScotlandPage scotlandPage;
    private DateOfBirthPage dateOfBirthPage;
    private DoYouHaveChildrenPage doYouHaveChildrenPage;
    private ChildDateOfBirthPage childDateOfBirthPage;
    private AreYouPregnantPage areYouPregnantPage;
    private NamePage namePage;
    private NationalInsuranceNumberPage nationalInsuranceNumberPage;
    private ManualAddressPage manualAddressPage;
    private PhoneNumberPage phoneNumberPage;
    private EmailAddressPage emailAddressPage;
    private SendCodePage sendCodePage;
    private EnterCodePage enterCodePage;
    private ConfirmationCodePage confirmationCodePage;

    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        guidancePage = getPages().getGuidancePage(PageName.APPLY);
        guidancePage.clickStartButton();
        enterDoYouLiveInScotlandNoAndSubmit();
        enterDateOfBirthAndSubmit();
        enterDoYouHaveChildrenYesAndSubmit();
        enterOneChildsDateOfBirth();
        selectYesOnPregnancyPage();
        enterName();
        enterNino();
        enterManualAddress();
        enterPhoneNumber();
        enterEmailAddress();
        selectTextOnSendCode();
        enterConfirmationCodeAndSubmit();
    }

    private void enterConfirmationCodeAndSubmit() {
        enterCodePage = getPages().getEnterCodePage();
        confirmationCodePage = new ConfirmationCodePage(getWebDriver(), sessionDetailsUrl);
        String confirmationCode = confirmationCodePage.getConfirmationCodeForSession();
        enterCodePage.open();
        enterCodePage.enterCode(confirmationCode);
        enterCodePage.clickContinue();
    }

    private void selectTextOnSendCode() {
        sendCodePage = getPages().getSendCodePage();
        sendCodePage.selectText();
        sendCodePage.clickContinue();
    }

    private void enterEmailAddress() {
        emailAddressPage = getPages().getEmailAddressPage();
        emailAddressPage.enterEmailAddress(EMAIL_ADDRESS);
        emailAddressPage.clickContinue();
    }

    private void enterPhoneNumber() {
        phoneNumberPage = getPages().getPhoneNumberPage();
        phoneNumberPage.enterPhoneNumber(PHONE_NUMBER);
        phoneNumberPage.clickContinue();
    }

    private void enterManualAddress() {
        manualAddressPage = getPages().getManualAddressPage();
        manualAddressPage.enterAddressLine1(ADDRESS_LINE_1);
        manualAddressPage.enterAddressLine2(ADDRESS_LINE_2);
        manualAddressPage.enterTownOrCity(TOWN);
        manualAddressPage.enterCounty(COUNTY);
        manualAddressPage.enterPostcode(POSTCODE);
        manualAddressPage.clickContinue();
    }

    private void enterNino() {
        nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        nationalInsuranceNumberPage.enterNino(generateEligibleNino());
        nationalInsuranceNumberPage.clickContinue();
    }

    private void enterName() {
        namePage = getPages().getNamePage();
        namePage.enterName(FIRST_NAME, LAST_NAME);
        namePage.clickContinue();
    }

    private void selectYesOnPregnancyPage() {
        areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectYes();
        areYouPregnantPage.enterExpectedDeliveryDate(2);
        areYouPregnantPage.clickContinue();
    }

    private void enterOneChildsDateOfBirth() {
        childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChild3OrUnderDetails(1);
        childDateOfBirthPage.clickContinue();
    }

    private void enterDoYouHaveChildrenYesAndSubmit() {
        doYouHaveChildrenPage = getPages().getDoYouHaveChildrenPage();
        doYouHaveChildrenPage.selectYesRadioButton();
        doYouHaveChildrenPage.clickContinue();
    }

    private void enterDateOfBirthAndSubmit() {
        dateOfBirthPage = getPages().getDateOfBirthPage();
        dateOfBirthPage.getDayInputField().enterValue(DOB_DAY);
        dateOfBirthPage.getMonthInputField().enterValue(DOB_MONTH);
        dateOfBirthPage.getYearInputField().enterValue(DOB_YEAR);
        dateOfBirthPage.clickContinue();
    }

    private void enterDoYouLiveInScotlandNoAndSubmit() {
        scotlandPage = getPages().getScotlandPage();
        scotlandPage.selectNoRadioButton();
        scotlandPage.clickContinue();
    }

}
