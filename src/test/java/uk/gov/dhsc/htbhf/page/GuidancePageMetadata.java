package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;

/**
 * Enum representing metadata about each guidance page. The order of the enums
 * is important and should mirror the order of the pages in the application.
 */
public enum GuidancePageMetadata {

    HOW_IT_WORKS(PageName.HOW_IT_WORKS, "/"),
    ELIGIBILITY(PageName.ELIGIBILITY, "/eligibility"),
    WHAT_YOU_GET(PageName.WHAT_YOU_GET, "/what-you-get"),
    WHAT_YOU_CAN_BUY(PageName.WHAT_YOU_CAN_BUY, "/buy"),
    USING_YOUR_CARD(PageName.USING_YOUR_CARD, "/using-your-card"),
    APPLY(PageName.APPLY, "/apply"),
    REPORT_A_CHANGE(PageName.REPORT_A_CHANGE, "/report-a-change");

    private PageName pageName;
    private String pagePath;

    GuidancePageMetadata(PageName pageName, String pagePath) {
        this.pageName = pageName;
        this.pagePath = pagePath;
    }

    public PageName getPageName() {
        return pageName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public static GuidancePageMetadata findByName(PageName pageName) {
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
