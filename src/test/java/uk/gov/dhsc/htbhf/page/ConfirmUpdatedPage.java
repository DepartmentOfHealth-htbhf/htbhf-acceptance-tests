package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the confirm updated page.
 */
public class ConfirmUpdatedPage extends ConfirmationPage {

    public ConfirmUpdatedPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    PageName getPageName() {
        return PageName.CONFIRM_UPDATED;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Application updated";
    }

}
