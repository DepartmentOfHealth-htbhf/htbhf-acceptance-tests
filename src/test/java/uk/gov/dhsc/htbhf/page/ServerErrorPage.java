package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the server error page
 */
public class ServerErrorPage extends BasePage {

    public ServerErrorPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return null;
    }

    @Override
    PageName getPageName() {
        return PageName.SERVER_ERROR;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Sorry, there’s a problem with the service";
    }
}
