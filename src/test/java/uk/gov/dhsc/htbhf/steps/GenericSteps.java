package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Cookie;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.GenericPage;
import uk.gov.dhsc.htbhf.page.PageName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Contains steps which can be run from a number of different pages. We need to have this because we are not
 * allowed to have one class which defines steps extend another which defines steps, so these step definitions
 * need to be in their own step definition class.
 */
public class GenericSteps extends CommonSteps {

    @When("^I do not select an option$")
    public void whenIDoNotSelectAnOption() {
        // Specifically does nothing
    }

    @When("^I click continue$")
    public void whenIClickContinue() {
        GenericPage genericPage = getPages().getGenericPage();
        genericPage.clickContinue();
    }

    @When("^I click on the privacy notice link")
    public void clickPrivacyNoticeLink() {
        getPages().getGenericPage().clickPrivacyNoticeLink();
    }

    @When("^I click on the cookies link")
    public void clickCookieLink() {
        getPages().getGenericPage().clickCookieLink();
    }

    @Then("^the beta banner is shown")
    public void betaBannerIsShown() {
        String bannerText = getPages().getGenericPage().getBetaBannerText();
        assertThat(bannerText).isEqualTo("This is a new service – your feedback will help us improve it.");
    }

    @Then("^the beta banner has the correct survey link")
    public void betaBannerHasCorrectLink() {
        String feedbackUrl = getPages().getGenericPage().getBetaBannerFeedbackLinkHref();
        assertThat(feedbackUrl).isEqualTo("https://www.smartsurvey.co.uk/s/apply-for-healthy-start-feedback/");
    }

    @Then("^the back link points to the (.*) page")
    public void verifyBackLink(String pageNameString) {
        PageName pageName = PageName.toPageName(pageNameString);
        assertBackLinkPointsToPage(pageName);
    }

    @Then("^no back link is shown on the (.*) page")
    public void noBackLinkShownOnCookiesPage(String pageNameString) {
        PageName pageName = PageName.toPageName(pageNameString);
        boolean backLinkPresent = getPages().getPageByName(pageName).isBackLinkPresent();
        assertThat(backLinkPresent).isFalse();
    }

    @Then("^Yes and No options are displayed on the (.*) page$")
    public void yesAndNoOptionsAreDisplayed(String pageNameString) {
        BasePage basePage = getPages().getPageByName(PageName.toPageName(pageNameString));
        assertYesNoOptionsAreDisplayed(basePage);
    }

    @Then("^my session has not been destroyed")
    public void verifySessionNotDestroyed() {
        String pageSessionId = sessionIdThreadLocal.get();
        //Navigate to the first page of the application so we can assert the session id
        BasePage basePage = getPages().getFirstPageNoWait();
        basePage.open();
        Cookie langCookie = basePage.getLangCookie();
        assertThat(langCookie).isNotNull();
        String newSessionId = basePage.getCurrentSessionId();
        assertThat(pageSessionId).isEqualTo(newSessionId);
    }

    @Then("^my session has been destroyed")
    public void verifySessionDestroyed() {
        String pageSessionId = sessionIdThreadLocal.get();
        //Navigate to the first page of the application so we can assert the session id
        BasePage basePage = getPages().getFirstPageNoWait();
        basePage.open();
        Cookie langCookie = basePage.getLangCookie();
        assertThat(langCookie).isNotNull();
        String newSessionId = basePage.getCurrentSessionId();
        assertThat(pageSessionId).isNotEqualTo(newSessionId);
    }

}
