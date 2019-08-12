package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object representing the T's and C's page.
 */
public class TermsAndConditionsPage extends SubmittablePage {

    public TermsAndConditionsPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/terms-and-conditions";
    }

    @Override
    String getPageName() {
        return "terms and conditions";
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Terms and conditions";
    }

    public void clickAcceptTermsAndConditionsCheckBox() {
        WebElement checkbox = findByCss("input[value=\"agree\"]");
        WebElement parentDiv = checkbox.findElement(By.xpath(".."));
        WebElement label = parentDiv.findElement(By.className("govuk-checkboxes__label"));
        label.click();
    }
}
