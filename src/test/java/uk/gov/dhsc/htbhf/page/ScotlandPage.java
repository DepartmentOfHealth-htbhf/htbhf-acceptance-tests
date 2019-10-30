package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

/**
 * Page object for Scotland page where the claimant enters whether they live in Scotland or not.
 */
public class ScotlandPage extends SubmittablePage {
    private static final String DO_YOU_LIVE_IN_SCOTLAND_ERROR_LINK_CSS = "a[href=\"#scotland-error\"]";
    private static final String DO_YOU_LIVE_IN_SCOTLAND_FIELD_ERROR_ID = "scotland-error";

    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;

    public ScotlandPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.yesRadioButton = new RadioButton(webDriver, wait, RadioButton.YES);
        this.noRadioButton = new RadioButton(webDriver, wait, RadioButton.NO);
    }

    @Override
    String getPath() {
        return "/scotland";
    }

    @Override
    PageName getPageName() {
        return PageName.SCOTLAND;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Do you live in Scotland?";
    }

    public void selectYesRadioButton() {
        yesRadioButton.select();
    }

    public void selectNoRadioButton() {
        noRadioButton.select();
    }

    public String getFieldErrorId() {
        return DO_YOU_LIVE_IN_SCOTLAND_FIELD_ERROR_ID;
    }

    public String getFieldErrorLinkCss() {
        return DO_YOU_LIVE_IN_SCOTLAND_ERROR_LINK_CSS;
    }
}
