package uk.gov.dhsc.htbhf.steps;

import lombok.extern.slf4j.Slf4j;
import uk.gov.dhsc.htbhf.page.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.page.PageName.*;
import static uk.gov.dhsc.htbhf.steps.ClaimValuesTestDataFactory.buildDefaultClaimValues;
import static uk.gov.dhsc.htbhf.steps.Constants.DOB_DAY;
import static uk.gov.dhsc.htbhf.steps.Constants.DOB_MONTH;
import static uk.gov.dhsc.htbhf.steps.Constants.DOB_YEAR;

/**
 * Contains common steps used by more than one step
 */
@Slf4j
public class CommonSteps extends BaseSteps {

    protected static ThreadLocal<ClaimValues> claimValuesThreadLocal = new ThreadLocal<>();

    protected void enterDetailsUpToPage(PageName pageName) {
        performPageActions(pageName, buildDefaultClaimValues());
    }

    protected void enterDetailsUpToPage(PageName pageName, ClaimValues claimValues) {
        performPageActions(pageName, claimValues);
    }

    private void performPageActions(PageName pageName, ClaimValues claimValues) {
        claimValuesThreadLocal.set(claimValues);
        GuidancePage applyPage = openApplyPage();
        applyPage.clickStartButton();
        Map<PageName, Consumer<ClaimValues>> pageActions = buildStepPageActions(claimValues);
        for (Map.Entry<PageName, Consumer<ClaimValues>> entry : pageActions.entrySet()) {
            if (pageName == entry.getKey()) {
                break;
            }
            Consumer<ClaimValues> actionsForPage = entry.getValue();
            actionsForPage.accept(claimValues);
        }
    }

    protected GuidancePage openApplyPage() {
        GuidancePage applyPage = getPages().getGuidancePageNoWait(PageName.APPLY);
        applyPage.open();
        return applyPage;
    }

    private Map<PageName, Consumer<ClaimValues>> buildStepPageActions(ClaimValues values) {
        Map<PageName, Consumer<ClaimValues>> pageActions = new LinkedHashMap<>();
        addActionToMapRespectingToggle(pageActions, SCOTLAND, (claimValues) -> enterDoYouLiveInScotlandNoAndSubmit());
        addActionToMapRespectingToggle(pageActions, DATE_OF_BIRTH, (claimValues) -> enterDateOfBirthAndSubmit());
        addActionToMapRespectingToggle(pageActions, DO_YOU_HAVE_CHILDREN, (claimValues) -> enterDoYouHaveChildrenYesAndSubmit());
        addActionToMapRespectingToggle(pageActions, CHILD_DATE_OF_BIRTH, (claimValues) -> enterOneChildsDateOfBirth());
        addActionToMapRespectingToggle(pageActions, ARE_YOU_PREGNANT, (claimValues) -> completePregnancyPage(claimValues));
        addActionToMapRespectingToggle(pageActions, NAME, (claimValues) -> enterName(claimValues.getFirstName(), claimValues.getLastName()));
        addActionToMapRespectingToggle(pageActions, NATIONAL_INSURANCE_NUMBER, (claimValues) -> enterNino(claimValues.getNino()));
        addActionToMapRespectingToggle(pageActions, POSTCODE, (claimValues) -> enterPostcode(claimValues));
        addActionToMapRespectingToggle(pageActions, SELECT_ADDRESS, (claimValues) -> completeSelectAddressPage(claimValues));
        if (!values.isSelectAddress() || !toggleConfiguration.isEnabled(ToggleName.ADDRESS_LOOKUP)) {
            addActionToMapRespectingToggle(pageActions, MANUAL_ADDRESS, (claimValues) -> enterManualAddress(claimValues));
        }
        addActionToMapRespectingToggle(pageActions, PHONE_NUMBER, (claimValues) -> enterPhoneNumber());
        addActionToMapRespectingToggle(pageActions, EMAIL_ADDRESS, (claimValues) -> enterEmailAddress());
        addActionToMapRespectingToggle(pageActions, SEND_CODE, (claimValues) -> selectTextOnSendCode());
        addActionToMapRespectingToggle(pageActions, ENTER_CODE, (claimValues) -> enterConfirmationCodeAndSubmit());
        addActionToMapRespectingToggle(pageActions, CHECK_ANSWERS, (claimValues) -> {
        });
        return pageActions;
    }

    private void addActionToMapRespectingToggle(Map<PageName, Consumer<ClaimValues>> pageActions, PageName pageName, Consumer<ClaimValues> pageAction) {
        if (toggleConfiguration.isPageEnabled(pageName)) {
            pageActions.put(pageName, pageAction);
        } else {
            log.debug("Not adding page action for [{}] as it is toggled off", pageName);
        }
    }

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
        emailAddressPage.enterEmailAddress(Constants.EMAIL_ADDRESS);
        emailAddressPage.clickContinue();
    }

    protected void enterPhoneNumber() {
        PhoneNumberPage phoneNumberPage = getPages().getPhoneNumberPage();
        phoneNumberPage.enterPhoneNumber(Constants.PHONE_NUMBER);
        phoneNumberPage.clickContinue();
    }

    protected void enterManualAddress(ClaimValues claimValues) {
        enterManualAddress(claimValues.getAddressLine1(),
                claimValues.getAddressLine2(),
                claimValues.getTownOrCity(),
                claimValues.getCounty(),
                claimValues.getPostcode());
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

    protected void enterPostcode(ClaimValues claimValues) {
        setupPostcodeLookupWithResults(claimValues.getPostcode());
        enterPostcodeAndSubmit(claimValues.getPostcode());
    }

    protected void setupPostcodeLookupWithResults(String postcode) {
        wireMockManager.setupPostcodeLookupWithResultsMapping(postcode);
    }

    protected void enterPostcodeAndSubmit(String postcode) {
        PostcodePage postcodePage = getPages().getPostcodePage();
        postcodePage.enterPostcode(postcode);
        postcodePage.clickContinue();
    }

    protected void completeSelectAddressPage(ClaimValues claimValues) {
        if (claimValues.isSelectAddress()) {
            selectFirstAddressAndSubmit();
        } else {
            clickAddressNotListedLink();
        }
    }

    protected void selectFirstAddressAndSubmit() {
        SelectAddressPage selectAddressPage = getPages().getSelectAddressPage();
        selectAddressPage.selectFirstAddress();
        selectAddressPage.clickContinue();
    }

    protected void clickAddressNotListedLink() {
        SelectAddressPage selectAddressPage = getPages().getSelectAddressPage();
        selectAddressPage.clickAddressNotListedLink();
    }

    protected void enterNino(String nino) {
        NationalInsuranceNumberPage nationalInsuranceNumberPage = getPages().getNationalInsuranceNumberPage();
        nationalInsuranceNumberPage.enterNino(nino);
        nationalInsuranceNumberPage.clickContinue();
    }

    protected void enterName(String firstName, String lastName) {
        NamePage namePage = getPages().getNamePage();
        namePage.enterName(firstName, lastName);
        namePage.clickContinue();
    }

    protected void completePregnancyPage(ClaimValues claimValues) {
        if (claimValues.isClaimantPregnant()) {
            selectYesOnPregnancyPage();
        } else {
            selectNoOnPregnancyPage();
        }
    }

    protected void selectYesOnPregnancyPage() {
        enterExpectedDeliveryDateOnPregnancyPage(2);
        getPages().getAreYouPregnantPage().clickContinue();
    }

    protected void enterExpectedDeliveryDateOnPregnancyPage(int monthIncrement) {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectYes();
        areYouPregnantPage.enterExpectedDeliveryDate(monthIncrement);
    }

    protected void selectNoOnPregnancyPage() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectNo();
        areYouPregnantPage.clickContinue();
    }

    protected void enterOneChildsDateOfBirth() {
        ChildDateOfBirthPage childDateOfBirthPage = getPages().getChildDateOfBirthPage();
        childDateOfBirthPage.enterChild3OrUnderDetails(0);
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
        wireMockManager.setupClaimantServiceMappingsWithStatus("ELIGIBLE");
        CheckAnswersPage checkAnswersPage = getPages().getCheckAnswersPage();
        checkAnswersPage.clickContinue();
        TermsAndConditionsPage termsAndConditionsPage = getPages().getTermsAndConditionsPage();
        termsAndConditionsPage.clickAcceptTermsAndConditionsCheckBox();
        termsAndConditionsPage.clickContinue();
    }

    protected void assertBackLinkPointsToPage(PageName expectedPage) {
        BasePage page = getPages().getPageByName(expectedPage);
        boolean backLinkPresent = page.isBackLinkPresent();
        assertThat(backLinkPresent)
                .as("Back link should be present on the page")
                .isTrue();

        String href = page.getBackLinkHref();
        String expectedUrl = page.getFullPath();
        boolean correctHref = isCorrectHref(href, expectedUrl);
        assertThat(correctHref)
                .as("back link url should be [" + expectedUrl + "], is [" + href + "]")
                .isTrue();
    }

    private boolean isCorrectHref(String href, String expectedUrl) {
        return (href.equals(expectedUrl) || href.startsWith(expectedUrl + "?"));
    }
}
