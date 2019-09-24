package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.CookiesPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps methods for dealing with the cookies page
 */
public class CookiesSteps extends BaseSteps {

    @Then("^the cookies page is shown")
    public void waitForCookiesPageToLoad() {
        getPages().getCookiesPage();
    }

    @Then("^all page content is present on the cookies page")
    public void allContentPresentOnCookiesPage() {
        checkAllCookiePageContentIsPresentAndCorrect();
    }

    @Then("^no back link is shown")
    public void noBackLinkShownOnCookiesPage() {
        boolean backLinkPresent = getPages().getCookiesPage().isBackLinkPresent();
        assertThat(backLinkPresent).isFalse();
    }

    private void checkAllCookiePageContentIsPresentAndCorrect() {
        CookiesPage cookiesPage = getPages().getCookiesPage();
        assertThat(cookiesPage.getH1Text()).isEqualTo("Cookies");
        assertThat(cookiesPage.getH2Text()).isEqualTo("Cookie settings");

        // Make sure that there is a table on the page (this is the specific cookie details)
        List<WebElement> allTables = cookiesPage.getCookieDetailsTables();
        assertThat(allTables.size()).isGreaterThanOrEqualTo(1);
    }

}
