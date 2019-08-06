package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the send code page
 */
public class SendCodePage extends SubmittablePage {

    public SendCodePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/send-code";
    }

    @Override
    String getPageName() {
        return "send code";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - We’re sending you a code";
    }

}
