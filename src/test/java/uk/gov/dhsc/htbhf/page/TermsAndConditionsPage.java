package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object representing the T's and C's page.
 */
public class TermsAndConditionsPage extends SubmittablePage {

    private static final String FIELD_ERROR_ID = "agree-error";
    private static final String ERROR_LINK_CSS = "a[href=\"#agree-error\"]";

    public TermsAndConditionsPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/terms-and-conditions";
    }

    @Override
    PageName getPageName() {
        return PageName.TERMS_AND_CONDITIONS;
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

    public String getFieldErrorId() {
        return FIELD_ERROR_ID;
    }

    public String getErrorLinkCss() {
        return ERROR_LINK_CSS;
    }
}
