package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the cookies page
 */
public class CookiesPage extends BasePage {

    CookiesPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/cookies";
    }

    @Override
    PageName getPageName() {
        return PageName.COOKIES;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Cookies";
    }
}
