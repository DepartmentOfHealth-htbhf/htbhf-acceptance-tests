package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.BaseComponent;

import java.util.List;

/**
 * Base page all page objects which contains all common utility and navigation methods.
 */
public abstract class BasePage extends BaseComponent {

    private static final String RADIO_ITEM_CLASSNAME = "govuk-radios__item";
    private static final String RADIO_LABEL_CLASSNAME = "govuk-label govuk-radios__label";

    protected final WebDriver webDriver;

    private final String baseUrl;
    private final WebDriverWait wait;

    BasePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver);
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
        this.wait = wait;
    }

    abstract String getPath();

    abstract String getPageName();

    abstract String getPageTitle();

    public void open() {
        String fullUrl = baseUrl + getPath();
        webDriver.get(fullUrl);
        waitForPageToLoad();
    }

    public void waitForPageToLoad() {
        wait.until(ExpectedConditions.titleIs(getPageTitle()));
    }

    public List<WebElement> getRadioButtons() {
        return webDriver.findElements(By.className(RADIO_ITEM_CLASSNAME));
    }

    public List<WebElement> getAllRadioLabels() {
        return webDriver.findElements(By.className(RADIO_LABEL_CLASSNAME));
    }

}
