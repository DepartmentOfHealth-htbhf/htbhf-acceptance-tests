package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.*;

import static uk.gov.dhsc.htbhf.steps.Constants.*;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends BaseSteps {

    private OverviewPage overviewPage;
    private DoYouLiveInScotlandPage doYouLiveInScotlandPage;
    private EnterDobPage enterDobPage;
    private DoYouHaveChildren doYouHaveChildren;
    private EnterChildrensDobPage enterChildrensDobPage;
    private AreYouPregnantPage areYouPregnantPage;
    private EnterNamePage enterNamePage;
    private EnterNinoPage enterNinoPage;

    //TODO MRS 2019-08-01: Keep building this up...
    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        overviewPage = new OverviewPage(webDriver, baseUrl, webDriverWait);
        overviewPage.clickStartButton();
        enterDoYouLiveInScotlandNoAndSubmit();
        enterDateOfBirthAndSubmit();
        enterDoYouHaveChildrenYesAndSubmit();
        enterTwoChildrensDatesOfBirth();
        selectYesOnPregnancyPage();
        enterName();
        enterNinoPage = new EnterNinoPage(webDriver, baseUrl, webDriverWait);
        enterNinoPage.waitForPageToLoad();
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
        enterDobPage.getDayInputField().enterValue(DAY);
        enterDobPage.getMonthInputField().enterValue(MONTH);
        enterDobPage.getYearInputField().enterValue(YEAR);
        enterDobPage.clickContinue();
    }

    private void enterDoYouLiveInScotlandNoAndSubmit() {
        doYouLiveInScotlandPage = new DoYouLiveInScotlandPage(webDriver, baseUrl, webDriverWait);
        doYouLiveInScotlandPage.waitForPageToLoad();
        doYouLiveInScotlandPage.selectNoRadioButton();
        doYouLiveInScotlandPage.clickContinue();
    }

}
