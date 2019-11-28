package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the decision page.
 */
public class DecisionPage extends BasePage {

    private static final String PANEL_TITLE_CLASS = "govuk-panel__title";
    private static final String PANEL_BODY_CLASS = "govuk-panel__body";

    public DecisionPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        return "/decision";
    }

    @Override
    PageName getPageName() {
        return PageName.DECISION;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Application successful";
    }

    public String getPanelTitleText() {
        WebElement panelTitle = findByClassName(PANEL_TITLE_CLASS);
        return panelTitle.getText();
    }

    public String getPanelBodyText() {
        WebElement panelBody = findByClassName(PANEL_BODY_CLASS);
        return panelBody.getText();
    }
}
