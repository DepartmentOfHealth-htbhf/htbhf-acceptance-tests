package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A GenericPage object which can be used to have generic access to any submittable page's
 * functionality.
 */
public class GenericPage extends SubmittablePage {

    private static final String PRIVACY_NOTICE_LINK_CSS = "a[href=\"/privacy-notice\"]";
    private static final String COOKIES_LINK_CSS = "a[href=\"/cookies\"]";
    private static final String BETA_BANNER_CLASSNAME = "govuk-phase-banner__text";

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

    public void clickPrivacyNoticeLink() {
        WebElement privacyLink = findByCss(PRIVACY_NOTICE_LINK_CSS);
        privacyLink.click();
    }

    public void clickCookieLink() {
        WebElement cookieLink = findByCss(COOKIES_LINK_CSS);
        cookieLink.click();
    }

    public String getBetaBannerText() {
        WebElement betaBanner = getBetaBanner();
        return betaBanner.getText();
    }

    public String getBetaBannerFeedbackLinkHref() {
        WebElement bannerLink = getBetaBanner().findElement(By.className("govuk-link"));
        return getHrefForElement(bannerLink);
    }

    private WebElement getBetaBanner() {
        return findByClassName(BETA_BANNER_CLASSNAME);
    }
}
