package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A GenericPage object which can be used to have generic access to any submittable page's
 * functionality.
 */
public class GenericPage extends SubmittablePage {

    protected GenericPage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, baseUrl, wait);
    }

    @Override
    String getPath() {
        throw new UnsupportedOperationException("Should not be getting the path from Generic page");
    }

    @Override
    PageName getPageName() {
        throw new UnsupportedOperationException("Should not be getting the page name from Generic page");
    }

    @Override
    String getPageTitle() {
        throw new UnsupportedOperationException("Should not be getting the page title from Generic page");
    }
}
