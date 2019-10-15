package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

import static uk.gov.dhsc.htbhf.page.component.RadioButton.EMAIL;
import static uk.gov.dhsc.htbhf.page.component.RadioButton.TEXT;

/**
 * Page object for the page where the claimant chooses how to receive their code.
 */
public class SendCodePage extends SubmittablePage {

    private RadioButton selectTextRadioButton;
    private RadioButton selectEmailRadioButton;

    public SendCodePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.selectTextRadioButton = new RadioButton(webDriver, wait, TEXT);
        this.selectEmailRadioButton = new RadioButton(webDriver, wait, EMAIL);
    }

    @Override
    String getPath() {
        return "/send-code";
    }

    @Override
    PageName getPageName() {
        return PageName.SEND_CODE;
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

    public String getTextHint() {
        return selectTextRadioButton.getHint();
    }

    public String getEmailHint() {
        return selectEmailRadioButton.getHint();
    }

    public String getChangeTextLink() {
        WebElement changeTextLink = this.findById("change-text");
        return getHrefForElement(changeTextLink);
    }

    public String getChangeEmailLink() {
        WebElement changeEmailLink = this.findById("change-email");
        return getHrefForElement(changeEmailLink);
    }

    public String getFieldErrorId() {
        return "channel-for-code-error";
    }

    public String getErrorLinkCss() {
        return "a[href=\"#channel-for-code-error\"]";
    }

}
