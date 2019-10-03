package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the privacy notice page
 */
public class PrivacyNoticePage extends BasePage {

    PrivacyNoticePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/privacy-notice";
    }

    @Override
    PageName getPageName() {
        return PageName.PRIVACY_NOTICE;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Privacy notice";
    }

}
