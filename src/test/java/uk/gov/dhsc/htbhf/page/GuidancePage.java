package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Page object for the guidance pages of the application
 */
public class GuidancePage extends BasePage {

    public static final String APPLY_PAGE = "Apply for Healthy Start";

    private GuidancePageMetadata currentPage;

    public GuidancePage(WebDriver webDriver, String baseUrl, WebDriverWait wait, String pageName) {
        super(webDriver, baseUrl, wait);
        this.currentPage = GuidancePageMetadata.findByName(pageName);
    }

    public static GuidancePage buildApplyGuidancePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        return new GuidancePage(webDriver, baseUrl, wait, APPLY_PAGE);
    }

    @Override
    String getPath() {
        return currentPage.getPagePath();
    }

    @Override
    String getPageName() {
        return currentPage.getPageName();
    }

    @Override
    String getPageTitle() {
        return "GOV.UK - " + currentPage.getPageName();
    }

    public void clickStartButton() {
        WebElement startButton = webDriver.findElement(By.className("govuk-button--start"));
        startButton.click();
    }

    public boolean shouldHavePreviousLink() {
        return !GuidancePageMetadata.getFirst().equals(currentPage);
    }

    public boolean shouldHaveNextLink() {
        return !GuidancePageMetadata.getLast().equals(currentPage);
    }

    // Find links in the contents section, which should be for all the pages except the current page
    public List<WebElement> getContentsLinks() {
        return Arrays.stream(GuidancePageMetadata.values())
                .filter(page -> !page.equals(currentPage))
                .map(page -> findLinkForHref(page.getPagePath()))
                .collect(toList());
    }

    public WebElement findPreviousLinkForCurrentPage() {
        int previous = currentPage.ordinal() - 1;
        return getLinkForGuidancePageByOrder(previous);
    }

    public WebElement findNextLinkForCurrentPage() {
        int next = currentPage.ordinal() + 1;
        return getLinkForGuidancePageByOrder(next);
    }

    private WebElement getLinkForGuidancePageByOrder(int order) {
        GuidancePageMetadata page = GuidancePageMetadata.getPageByOrder(order);
        return this.findLinkForHref(page.getPagePath());
    }

}
