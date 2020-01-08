package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.dhsc.htbhf.page.component.BaseComponent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Base page all page objects which contains all common utility and navigation methods.
 */
public abstract class BasePage extends BaseComponent {

    private static final String GOV_UK_TABLE_CSS = ".govuk-table";
    private static final String GOV_UK_BODY_CSS = ".govuk-body";
    private static final String RADIO_INPUT_CLASSNAME = "govuk-radios__input";
    private static final String RADIO_LABEL_CLASSNAME = "govuk-radios__label";
    private static final String ERROR_HEADER_SELECTOR = "h2#error-summary-title";
    private static final String BACK_LINK_CSS = ".govuk-back-link";
    private static final String SET_NO_VALIDATE =
            "const form = document.querySelectorAll('form')[0]; " +
                    "form.setAttribute('novalidate', true);";

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

    abstract PageName getPageName();

    abstract String getPageTitle();

    public String getFullPath() {
        return baseUrl + getPath();
    }

    public void open() {
        openDirect();
        waitForPageToLoad();
    }

    public void openDirect() {
        String fullUrl = baseUrl + getPath();
        webDriver.get(fullUrl);
    }

    public void waitForPageToLoad() {
        if (getPath() == null) {
            waitForPageToLoadWithTitle(getPageTitle());
        } else {
            waitForPageToLoadWithPath(getPath());
        }
    }

    public void waitForPageToLoadWithPath(String path) {
        wait.until(ExpectedConditions.urlToBe(baseUrl + path));
    }

    public void waitForPageToLoadWithTitle(String title) {
        wait.until(ExpectedConditions.titleIs(title));
    }

    public List<WebElement> getRadioButtons() {
        return webDriver.findElements(By.className(RADIO_INPUT_CLASSNAME));
    }

    public List<String> getAllRadioLabels() {
        List<WebElement> radioLabels = webDriver.findElements(By.className(RADIO_LABEL_CLASSNAME));
        return radioLabels.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public WebElement findH1() {
        return findByCss("h1");
    }

    public String getH1Text() {
        return findH1().getText().trim();
    }

    public WebElement findH2() {
        return findByCss("h2");
    }

    public String getH2Text() {
        return findH2().getText().trim();
    }

    public WebElement findLinkForHref(String href) {
        String linkCss = String.format("a[href=\"%s\"]", href);
        return findByCss(linkCss);
    }

    public String getPageErrorHeaderText() {
        WebElement pageError = findByCss(ERROR_HEADER_SELECTOR);
        return pageError.getText();
    }

    public String getFieldErrorText(String fieldErrorId) {
        WebElement fieldError = findById(fieldErrorId);
        return getVisibleTextFromFieldError(fieldError);
    }

    public String getErrorLinkText(String errorLinkCss) {
        WebElement errorLink = findByCss(errorLinkCss);
        return errorLink.getText();
    }

    public WebElement getBackLink() {
        return findByCss(BACK_LINK_CSS);
    }

    public String getBackLinkHref() {
        WebElement backLink = getBackLink();
        return getHrefForElement(backLink);
    }

    /**
     * If checking for the absence of something, you need to get all elements otherwise if
     * there are none, then an Exception will be thrown
     */
    public boolean isBackLinkPresent() {
        List<WebElement> backLink = findAllByCss(BACK_LINK_CSS);
        return !backLink.isEmpty();
    }

    /**
     * Used to disable html5 form validation as this brings up a dialog box on chrome which interferes with the test.
     * This is most prevalent in the enter email address tests.
     */
    public void executeSetNoValidateScript() {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript(SET_NO_VALIDATE);
    }

    public List<WebElement> findAllTablesOnPage() {
        return findAllByCss(GOV_UK_TABLE_CSS);
    }

    public List<WebElement> findAllBodyElementsOnPage() {
        return findAllByCss(GOV_UK_BODY_CSS);
    }

    public String getBodyText() {
        WebElement body = findByCss(GOV_UK_BODY_CSS);
        return body.getText().trim();
    }

    public String getCurrentSessionId() {
        Cookie sessionCookie = getCookieByName("htbhf.sid");
        //TODO MRS 16/10/2019: This will not retrieve secure cookies (iOS devices) so won't work for compatibility tests, but is ok at the moment
        // because the value isn't used by compatibility tests even though it is retrieved when the apply page is opened.
        return (sessionCookie != null) ? sessionCookie.getValue() : null;
    }

    public Cookie getLangCookie() {
        return this.getCookieByName("lang");
    }

    private Cookie getCookieByName(String cookieName) {
        return webDriver.manage().getCookieNamed(cookieName);
    }

    private String getVisibleTextFromFieldError(WebElement errorElement) {
        String fullErrorText = errorElement.getText();
        WebElement hiddenError = errorElement.findElement(By.className("govuk-visually-hidden"));
        String hiddenErrorText = hiddenError.getText();
        return fullErrorText.replace(hiddenErrorText, "").trim();
    }

}
