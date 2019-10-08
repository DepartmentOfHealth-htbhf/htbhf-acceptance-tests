package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.PageName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the page not found page
 */
public class PageNotFoundSteps extends BaseSteps {

    @When("^I go to a non-existent page")
    public void gotToNonExistantPage() {
        BasePage page = getPages().getPageByName(PageName.PAGE_NOT_FOUND);
        page.openDirect();
    }

    @Then("^I am shown the Page Not Found page")
    public void pageNotFoundPageShown() {
        String heading = getPages().getPageNotFoundPage().getH1Text();
        assertThat(heading).isEqualTo("Page not found");
    }

}
