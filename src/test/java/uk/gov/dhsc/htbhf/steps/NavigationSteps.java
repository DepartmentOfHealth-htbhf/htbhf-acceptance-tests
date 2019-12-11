package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.PageName;

import static uk.gov.dhsc.htbhf.page.PageName.CHECK_ANSWERS;

/**
 * Steps that help with navigation through the application flow.
 */
public class NavigationSteps extends CommonSteps {

    @Given("^I have entered my details up to the (.*) page")
    public void enterDetailsUpToPage(String page) {
        enterDetailsUpToPage(PageName.toPageName(page));
    }

    @Given("^I am starting a new application")
    public void givenIAmStartingANewApplication() {
        openApplyPage();
    }

    @Given("^I have completed my application")
    public void givenIHaveCompletedMyApplication() {
        enterDetailsUpToPage(CHECK_ANSWERS);
        acceptTermsAndConditionsAndSubmitSuccessfulApplication();
        getPages().getSuccessfulDecisionPage();
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

    @Then("^I am shown the first page of the application")
    public void verifyOnFirstPageOfApplication() {
        BasePage page = getPages().getFirstPage();
        page.waitForPageToLoad();
    }

}
