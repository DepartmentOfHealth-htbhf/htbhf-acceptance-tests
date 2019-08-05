package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the enter your national insurance number page.
 */
public class EnterNinoPage extends SubmittablePage {

    public EnterNinoPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/enter-nino";
    }

    @Override
    String getPageName() {
        return "enter national insurance";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your National Insurance number?";
    }
}
