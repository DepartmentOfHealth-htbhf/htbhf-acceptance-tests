package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

import static uk.gov.dhsc.htbhf.page.component.RadioButton.EMAIL;
import static uk.gov.dhsc.htbhf.page.component.RadioButton.TEXT;

/**
 * Page object for the send code page
 */
public class SendCodePage extends SubmittablePage {

    private RadioButton selectTextRadioButton;
    private RadioButton selectEmailRadioButton;

    public SendCodePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.selectTextRadioButton = new RadioButton(webDriver, TEXT);
        this.selectEmailRadioButton = new RadioButton(webDriver, EMAIL);
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

    public void selectText() {
        selectTextRadioButton.select();
    }

    public void selectEmail() {
        selectEmailRadioButton.select();
    }

}
