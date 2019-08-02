package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the overview page of the application
 */
public class OverviewPage extends BasePage {

    public OverviewPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/";
    }

    @Override
    String getPageName() {
        return "Apply for Healthy Start overview";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Overview";
    }

    public WebElement getStartButton() {
        return findByClassName("govuk-button--start");
    }

    public void clickStartButton() {
        getStartButton().click();
    }
}
