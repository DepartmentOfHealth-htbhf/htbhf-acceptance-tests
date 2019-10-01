package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

/**
 * Page object for the Do You Have Children page.
 */
public class DoYouHaveChildrenPage extends SubmittablePage {

    private static final String DO_YOU_HAVE_CHILDREN_ERROR_LINK_CSS = "a[href=\"#do-you-have-children-error\"]";
    private static final String DO_YOU_HAVE_CHILDREN_FIELD_ERROR_ID = "doYouHaveChildren-error";

    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;

    public DoYouHaveChildrenPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.yesRadioButton = new RadioButton(webDriver, wait, RadioButton.YES);
        this.noRadioButton = new RadioButton(webDriver, wait, RadioButton.NO);
    }

    @Override
    String getPath() {
        return "/do-you-have-children";
    }

    @Override
    PageName getPageName() {
        return PageName.DO_YOU_HAVE_CHILDREN;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Do you have any children under 4 years old?";
    }

    public void selectYesRadioButton() {
        yesRadioButton.select();
    }

    public void selectNoRadioButton() {
        noRadioButton.select();
    }

    public String getFieldErrorId() {
        return DO_YOU_HAVE_CHILDREN_FIELD_ERROR_ID;
    }

    public String getErrorLinkCss() {
        return DO_YOU_HAVE_CHILDREN_ERROR_LINK_CSS;
    }
}
