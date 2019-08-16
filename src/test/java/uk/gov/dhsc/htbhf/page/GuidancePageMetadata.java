package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;

import static uk.gov.dhsc.htbhf.page.GuidancePage.APPLY_PAGE;

/**
 * Enum representing metadata about each guidance page. The order of the enums
 * is important and should mirror the order of the pages in the application.
 */
public enum GuidancePageMetadata {

    HOW_IT_WORKS("How it works", "/"),
    ELIGIBILITY("Eligibility", "/eligibility"),
    WHAT_YOU_GET("What you’ll get", "/what-you-get"),
    WHAT_YOU_CAN_BUY("What you can buy", "/buy"),
    USING_YOUR_CARD("Using your card", "/using-your-card"),
    APPLY(APPLY_PAGE, "/apply"),
    REPORT_A_CHANGE("Report a change", "/report-a-change");

    private String pageName;
    private String pagePath;

    GuidancePageMetadata(String pageName, String pagePath) {
        this.pageName = pageName;
        this.pagePath = pagePath;
    }

    public String getPageName() {
        return pageName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public static GuidancePageMetadata findByName(String pageName) {
        return Arrays.stream(GuidancePageMetadata.values())
                .filter(page -> pageName.equals(page.pageName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No page metadata found for page with name: " + pageName));
    }

    public static GuidancePageMetadata getFirst() {
        return getPageByOrder(0);
    }

    public static GuidancePageMetadata getLast() {
        int totalPages = GuidancePageMetadata.values().length;
        return getPageByOrder(totalPages - 1);
    }

    /**
     * Gets the page by its order, note that this is 0 based as it uses the enums in built ordinal.
     */
    public static GuidancePageMetadata getPageByOrder(int order) {
        return Arrays.stream(GuidancePageMetadata.values())
                .filter(page -> page.ordinal() == order)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No page metadata found for page with order: " + order));
    }
}
