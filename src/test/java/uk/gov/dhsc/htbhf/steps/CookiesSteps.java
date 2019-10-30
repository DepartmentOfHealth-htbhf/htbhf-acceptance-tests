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

    @Then("^all page content is present on the cookies page")
    public void allContentPresentOnCookiesPage() {
        checkAllCookiePageContentIsPresentAndCorrect();
    }

    private void checkAllCookiePageContentIsPresentAndCorrect() {
        CookiesPage cookiesPage = getPages().getCookiesPage();
        assertThat(cookiesPage.getH1Text()).isEqualTo("Cookies");
        assertThat(cookiesPage.getH2Text()).isEqualTo("Cookie settings");

        // Make sure that there is a table on the page (this is the specific cookie details)
        List<WebElement> allTables = cookiesPage.findAllTablesOnPage();
        assertThat(allTables).hasSizeGreaterThanOrEqualTo(1);
    }

}
