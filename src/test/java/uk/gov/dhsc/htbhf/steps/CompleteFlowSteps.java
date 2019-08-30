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
    private DoYouHaveChildren doYouHaveChildren;
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
        guidancePage = GuidancePage.buildApplyGuidancePage(getWebDriver(), baseUrl, getWebDriverWait());
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
        enterCodePage = new EnterCodePage(getWebDriver(), baseUrl, getWebDriverWait());
        enterCodePage.waitForPageToLoad();
        confirmationCodePage = new ConfirmationCodePage(getWebDriver(), sessionDetailsUrl);
        String confirmationCode = confirmationCodePage.getConfirmationCodeForSession();
        enterCodePage.open();
        enterCodePage.enterCode(confirmationCode);
        enterCodePage.clickContinue();
    }

    private void selectTextOnSendCode() {
        sendCodePage = new SendCodePage(getWebDriver(), baseUrl, getWebDriverWait());
        sendCodePage.waitForPageToLoad();
        sendCodePage.selectText();
        sendCodePage.clickContinue();
    }

    private void enterEmailAddress() {
        emailAddressPage = new EmailAddressPage(getWebDriver(), baseUrl, getWebDriverWait());
        emailAddressPage.waitForPageToLoad();
        emailAddressPage.enterEmailAddress(EMAIL_ADDRESS);
        emailAddressPage.clickContinue();
    }

    private void enterPhoneNumber() {
        phoneNumberPage = new PhoneNumberPage(getWebDriver(), baseUrl, getWebDriverWait());
        phoneNumberPage.waitForPageToLoad();
        phoneNumberPage.enterPhoneNumber(PHONE_NUMBER);
        phoneNumberPage.clickContinue();
    }

    private void enterManualAddress() {
        manualAddressPage = new ManualAddressPage(getWebDriver(), baseUrl, getWebDriverWait());
        manualAddressPage.waitForPageToLoad();
        manualAddressPage.enterAddressLine1(ADDRESS_LINE_1);
        manualAddressPage.enterAddressLine2(ADDRESS_LINE_2);
        manualAddressPage.enterTownOrCity(TOWN);
        manualAddressPage.enterCounty(COUNTY);
        manualAddressPage.enterPostcode(POSTCODE);
        manualAddressPage.clickContinue();
    }

    private void enterNino() {
        nationalInsuranceNumberPage = new NationalInsuranceNumberPage(getWebDriver(), baseUrl, getWebDriverWait());
        nationalInsuranceNumberPage.waitForPageToLoad();
        nationalInsuranceNumberPage.enterNino(generateEligibleNino());
        nationalInsuranceNumberPage.clickContinue();
    }

    private void enterName() {
        namePage = new NamePage(getWebDriver(), baseUrl, getWebDriverWait());
        namePage.waitForPageToLoad();
        namePage.enterName(FIRST_NAME, LAST_NAME);
        namePage.clickContinue();
    }

    private void selectYesOnPregnancyPage() {
        areYouPregnantPage = new AreYouPregnantPage(getWebDriver(), baseUrl, getWebDriverWait());
        areYouPregnantPage.waitForPageToLoad();
        areYouPregnantPage.selectYes();
        areYouPregnantPage.enterExpectedDeliveryDate(2);
        areYouPregnantPage.clickContinue();
    }

    private void enterOneChildsDateOfBirth() {
        childDateOfBirthPage = new ChildDateOfBirthPage(getWebDriver(), baseUrl, getWebDriverWait());
        childDateOfBirthPage.waitForPageToLoad();
        childDateOfBirthPage.enterChild3OrUnderDetails(1);
        childDateOfBirthPage.clickContinue();
    }

    private void enterDoYouHaveChildrenYesAndSubmit() {
        doYouHaveChildren = new DoYouHaveChildren(getWebDriver(), baseUrl, getWebDriverWait());
        doYouHaveChildren.waitForPageToLoad();
        doYouHaveChildren.selectYesRadioButton();
        doYouHaveChildren.clickContinue();
    }

    private void enterDateOfBirthAndSubmit() {
        dateOfBirthPage = new DateOfBirthPage(getWebDriver(), baseUrl, getWebDriverWait());
        dateOfBirthPage.waitForPageToLoad();
        dateOfBirthPage.getDayInputField().enterValue(DOB_DAY);
        dateOfBirthPage.getMonthInputField().enterValue(DOB_MONTH);
        dateOfBirthPage.getYearInputField().enterValue(DOB_YEAR);
        dateOfBirthPage.clickContinue();
    }

    private void enterDoYouLiveInScotlandNoAndSubmit() {
        scotlandPage = new ScotlandPage(getWebDriver(), baseUrl, getWebDriverWait());
        scotlandPage.waitForPageToLoad();
        scotlandPage.selectNoRadioButton();
        scotlandPage.clickContinue();
    }

}
