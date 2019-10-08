package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the page not found page
 */
public class PageNotFoundPage extends BasePage {

    PageNotFoundPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/invalid";
    }

    @Override
    PageName getPageName() {
        return PageName.PAGE_NOT_FOUND;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Page not found";
    }
}
