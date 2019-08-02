package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Page object for the Do You Live In Scotland page.
 */
public class DoYouLiveInScotlandPage extends BasePage {

    public static final String YES = "yes";
    public static final String NO = "no";

    public DoYouLiveInScotlandPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/do-you-live-in-scotland";
    }

    @Override
    String getPageName() {
        return "do you live in Scotland";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Do you live in Scotland?";
    }

    public List<WebElement> findRadioButtons() {
        return webDriver.findElements(By.className("govuk-label govuk-radios__label"));
    }

    public void selectYesRadioButton() {
        selectRadioButton(YES);
    }

    public void selectNoRadioButton() {
        selectRadioButton(NO);
    }

    public void selectRadioButton(String option) {
        WebElement radioButton = getRadioButton(option);
        WebElement parentDiv = radioButton.findElement(By.xpath(".."));
        WebElement label = parentDiv.findElement(By.className("govuk-radios__label"));
        label.click();
    }

    public WebElement getRadioButton(String option) {
        String formattedCss = String.format("input[value=\"%s\"]", option);
        return findByCss(formattedCss);
    }
}
