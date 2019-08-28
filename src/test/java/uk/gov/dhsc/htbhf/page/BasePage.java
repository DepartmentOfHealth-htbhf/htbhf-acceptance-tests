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
    protected final WebDriverWait wait;

    private final String baseUrl;

    BasePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        super(webDriver, wait);
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

    public WebElement findH1() {
        return findByCss("h1");
    }

    public String getH1Text() {
        return findH1().getText();
    }

    public WebElement findH2() {
        return findByCss("h2");
    }

    public String getH2Text() {
        return findH2().getText();
    }

    public WebElement findLinkForHref(String href) {
        String linkCss = String.format("a[href=\"%s\"]", href);
        return findByCss(linkCss);
    }

}
