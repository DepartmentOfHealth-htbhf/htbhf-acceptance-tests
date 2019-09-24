package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Page object for the cookies page
 */
public class CookiesPage extends BasePage {

    private static final String GOV_UK_TABLE_CSS = ".govuk-table";

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

    public List<WebElement> getCookieDetailsTables() {
        return findAllByCss(GOV_UK_TABLE_CSS);
    }
}
