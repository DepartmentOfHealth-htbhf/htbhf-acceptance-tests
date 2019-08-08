package uk.gov.dhsc.htbhf.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

/**
 * Page object for the guidance pages of the application
 */
public class GuidancePage {

    private static final String APPLY_PAGE = "Apply for Healthy Start";

    //TODO MRS 2019-08-07: Guidance page enum? Look into this when guidance-pages.feature is done
    private static final Map<String, String> GUIDANCE_PAGES = Map.of(
            "How it works", "/",
            "Eligibility", "/eligibility",
            "What you’ll get", "/what-you-get",
            "What you can buy", "/buy",
            "Using your card", "/using-your-card",
            APPLY_PAGE, "/apply",
            "Report a change", "/report-a-change"
    );

    private WebDriver webDriver;
    private String baseUrl;
    private WebDriverWait wait;

    public GuidancePage(WebDriver webDriver, String baseUrl, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.baseUrl = baseUrl;
        this.wait = wait;
    }

    public void openGuidancePage(String pageName) {
        String path = GUIDANCE_PAGES.get(pageName);
        webDriver.get(baseUrl + path);
        wait.until(ExpectedConditions.titleIs(getPageTitleForPath(pageName)));
    }

    public void clickStartButton() {
        WebElement startButton = webDriver.findElement(By.className("govuk-button--start"));
        startButton.click();
    }

    public void openApplyPage() {
        openGuidancePage(APPLY_PAGE);
    }

    private String getPageTitleForPath(String pageName) {
        return "GOV.UK - " + pageName;
    }
}
