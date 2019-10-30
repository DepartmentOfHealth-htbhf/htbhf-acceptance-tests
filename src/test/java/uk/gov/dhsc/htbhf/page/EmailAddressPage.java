package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.InputField;

/**
 * Page object for the enter your email address page
 */
public class EmailAddressPage extends SubmittablePage {

    private InputField emailAddressInput;

    public EmailAddressPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.emailAddressInput = new InputField(webDriver, wait, "email-address");
    }

    @Override
    String getPath() {
        return "/email-address";
    }

    @Override
    PageName getPageName() {
        return PageName.EMAIL_ADDRESS;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - What’s your email address?";
    }

    public void enterEmailAddress(String emailAddress) {
        emailAddressInput.enterValue(emailAddress);
    }

    public String getEmailAddress() {
        return emailAddressInput.getValue();
    }

    public String getInputErrorId() {
        return emailAddressInput.getInputErrorId();
    }

    public String getInputErrorLinkCss() {
        return emailAddressInput.getInputErrorLinkCss();
    }
}
