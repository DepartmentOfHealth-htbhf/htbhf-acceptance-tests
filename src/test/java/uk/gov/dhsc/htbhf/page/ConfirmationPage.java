package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the confirmation page.
 */
public class ConfirmationPage extends BasePage {

    public ConfirmationPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/confirm";
    }

    @Override
    String getPageName() {
        return "confirmation";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Application complete";
    }
}
