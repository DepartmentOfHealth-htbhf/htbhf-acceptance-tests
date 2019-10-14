package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.GuidancePage;
import uk.gov.dhsc.htbhf.page.PageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the overview page of the application
 */
public class GuidanceSteps extends CommonSteps {

    private GuidancePage guidancePage;

    @Given("^I am on the first page of the application")
    public void givenIAmOnTheFirstPageOfTheApplication() {
        guidancePage = getPages().getGuidancePageNoWait(PageName.APPLY);
        guidancePage.open();
    }

    @When("^I open the (.*) guidance page$")
    public void openGuidancePage(String pageName) {
        guidancePage = getPages().getGuidancePageNoWait(PageName.toPageName(pageName));
        guidancePage.open();
    }

    @Then("^all the (.*) guidance page content is present$")
    public void allTheGuidancePageContentIsPresent(String pageName) {
        assertGuidancePageHeadersCorrect(pageName);
        assertGuidancePageContentsCorrect();
        assertGuidancePageNavigationLinksCorrect();
    }

    @Then("^my session has not been destroyed")
    public void verifySessionNotDestroyed() {
        String pageSessionId = sessionIdThreadLocal.get();
        //Navigate to another page so we can assert the session id
        GuidancePage guidancePage = getPages().getGuidancePageNoWait(PageName.APPLY);
        guidancePage.open();
        Cookie langCookie = guidancePage.getLangCookie();
        assertThat(langCookie).isNotNull();
        String newSessionId = guidancePage.getCurrentSessionId();
        assertThat(pageSessionId).isEqualTo(newSessionId);
    }

    // To verify that the session has been destroyed we need to navigate to a subsequent page
    // (back to the Guidance page is sufficient) to check for a new session id
    @Then("^my session has been destroyed")
    public void verifySessionDestroyed() {
        String pageSessionId = sessionIdThreadLocal.get();
        //Navigate to another page so we can assert the session id
        GuidancePage guidancePage = getPages().getGuidancePageNoWait(PageName.APPLY);
        guidancePage.open();
        Cookie langCookie = guidancePage.getLangCookie();
        assertThat(langCookie).isNotNull();
        String newSessionId = guidancePage.getCurrentSessionId();
        assertThat(pageSessionId).isNotEqualTo(newSessionId);
    }

    // Make sure there are the correct previous and/or next links on the page
    private void assertGuidancePageNavigationLinksCorrect() {
        if (guidancePage.shouldHavePreviousLink()) {
            WebElement previousLink = guidancePage.findPreviousLinkForCurrentPage();
            assertThat(previousLink).isNotNull();
        }

        if (guidancePage.shouldHaveNextLink()) {
            WebElement nextLink = guidancePage.findNextLinkForCurrentPage();
            assertThat(nextLink).isNotNull();
        }
    }

    private void assertGuidancePageContentsCorrect() {
        List<WebElement> contentLinks = guidancePage.getContentsLinks();
        assertThat(contentLinks).as("must have correct number of contents links")
                .hasSize(6);
    }

    private void assertGuidancePageHeadersCorrect(String pageName) {
        String h1Text = guidancePage.getH1Text();
        assertThat(h1Text).as("expected guidance page H1 text to be correct")
                .isEqualTo("Get money off milk, food and vitamins (Healthy Start)");
        String h2Text = guidancePage.getH2Text();
        assertThat(h2Text).as("expected guidance page H2 text to be correct")
                .isEqualTo(pageName);
    }
}
