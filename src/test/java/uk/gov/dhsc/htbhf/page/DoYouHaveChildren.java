package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.RadioButton;
import uk.gov.dhsc.htbhf.page.component.SubmitButton;

/**
 * Page object for the Do You Have Children page.
 */
public class DoYouHaveChildren extends BasePage {

    private RadioButton yesRadioButton;
    private RadioButton noRadioButton;
    private SubmitButton submitButton;

    public DoYouHaveChildren(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
        this.yesRadioButton = new RadioButton(webDriver, RadioButton.YES);
        this.noRadioButton = new RadioButton(webDriver, RadioButton.NO);
        this.submitButton = new SubmitButton(webDriver);
    }

    @Override
    String getPath() {
        return "/do-you-have-children";
    }

    @Override
    String getPageName() {
        return "do you have children under four years old";
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

    public void clickContinue() {
        submitButton.click();
    }
}
