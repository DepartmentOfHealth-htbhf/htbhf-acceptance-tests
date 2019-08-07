package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the check your details page.
 */
public class CheckDetailsPage extends SubmittablePage {

    public CheckDetailsPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/check";
    }

    @Override
    String getPageName() {
        return "check details";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Check your answers";
    }
}
