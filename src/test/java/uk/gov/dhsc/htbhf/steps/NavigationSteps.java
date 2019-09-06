package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.GuidancePage;
import uk.gov.dhsc.htbhf.page.PageName;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static uk.gov.dhsc.htbhf.page.PageName.*;
import static uk.gov.dhsc.htbhf.steps.Constants.DEFAULT_ACTION_OPTIONS;

/**
 * Steps that help with navigation through the application flow.
 */
public class NavigationSteps extends CommonSteps {

    private Map<PageName, Consumer<ActionOptions>> pageActions;

    public NavigationSteps() {
        this.pageActions = buildDefaultStepPageActions();
    }

    @Given("^I have entered my details up to the (.*) page")
    public void enterDetailsUpToPage(String page) {
        enterDetailsUpToPage(PageName.toPageName(page));
    }

    private void enterDetailsUpToPage(PageName pageName) {
        GuidancePage applyPage = openApplyPage();
        applyPage.clickStartButton();
        for (Map.Entry<PageName, Consumer<ActionOptions>> entry : pageActions.entrySet()) {
            if (pageName == entry.getKey()) {
                break;
            }
            Consumer<ActionOptions> actionsForPage = entry.getValue();
            actionsForPage.accept(DEFAULT_ACTION_OPTIONS);
        }
    }

    @Given("^I am starting a new application")
    public void givenIAmStartingANewApplication() {
        openApplyPage();
    }

    @Given("^I have completed my application")
    public void givenIHaveCompletedMyApplication() {
        enterDetailsUpToPage(CHECK_ANSWERS);
        acceptTermsAndConditionsAndSubmitApplication();
        getPages().getConfirmationPage();
    }

    private GuidancePage openApplyPage() {
        GuidancePage applyPage = getPages().getGuidancePageNoWait(PageName.APPLY);
        applyPage.open();
        return applyPage;
    }

    @When("^I navigate to the (.*) page")
    public void navigateToPage(String pageName) {
        BasePage page = getPages().getPageByName(PageName.toPageName(pageName));
        page.openDirect();
    }

    @Then("^I am shown the (.*) page")
    public void verifyOnCorrectPage(String pageName) {
        BasePage page = getPages().getPageByName(PageName.toPageName(pageName));
        page.waitForPageToLoad();
    }

    private Map<PageName, Consumer<ActionOptions>> buildDefaultStepPageActions() {
        Map<PageName, Consumer<ActionOptions>> pageActions = new LinkedHashMap<>();
        pageActions.put(SCOTLAND, (actionOptions) -> enterDoYouLiveInScotlandNoAndSubmit());
        pageActions.put(DATE_OF_BIRTH, (actionOptions) -> enterDateOfBirthAndSubmit());
        pageActions.put(DO_YOU_HAVE_CHILDREN, (actionOptions) -> enterDoYouHaveChildrenYesAndSubmit());
        pageActions.put(CHILD_DATE_OF_BIRTH, (actionOptions) -> enterOneChildsDateOfBirth());
        pageActions.put(ARE_YOU_PREGNANT, (actionOptions) -> {
            if (actionOptions.isClaimantPregnant()) {
                selectYesOnPregnancyPage();
            } else {
                selectNoOnPregnancyPage();
            }
        });
        pageActions.put(NAME, (actionOptions) -> enterName(actionOptions.getFirstName(), actionOptions.getLastName()));
        pageActions.put(NATIONAL_INSURANCE_NUMBER, (actionOptions) -> enterNino());
        //TODO MRS 2019-09-04: Need to add postcode lookup here when required.
//        pageActions.put(POSTCODE, (actionOptions) -> {
//            setupPostcodeLookupWithResults(POSTCODE);
//            enterPostcode();
//        });
        pageActions.put(MANUAL_ADDRESS, (actionOptions) ->
                enterManualAddress(actionOptions.getAddressLine1(),
                        actionOptions.getAddressLine2(),
                        actionOptions.getTownOrCity(),
                        actionOptions.getCounty(),
                        actionOptions.getPostcode()));
        pageActions.put(PHONE_NUMBER, (actionOptions) -> enterPhoneNumber());
        pageActions.put(EMAIL_ADDRESS, (actionOptions) -> enterEmailAddress());
        pageActions.put(SEND_CODE, (actionOptions) -> selectTextOnSendCode());
        pageActions.put(ENTER_CODE, (actionOptions) -> enterConfirmationCodeAndSubmit());
        pageActions.put(CHECK_ANSWERS, (actionOptions) -> {
        });
        return pageActions;
    }

}
