package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the final confirmation page when the application is unsuccessful
 */
public class UnsuccessfulApplicationPage extends BasePage {

    UnsuccessfulApplicationPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        // uses the same url as confirm but has different page content.
        return "/confirm";
    }

    @Override
    PageName getPageName() {
        return PageName.UNSUCCESSFUL_APPLICATION;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Application not successful";
    }

}
