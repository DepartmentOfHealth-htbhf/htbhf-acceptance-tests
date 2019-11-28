package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page object for the decision updated page.
 */
public class DecisionUpdatedPage extends DecisionPage {

    public DecisionUpdatedPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    PageName getPageName() {
        return PageName.DECISION_UPDATED;
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - Application updated";
    }

}
