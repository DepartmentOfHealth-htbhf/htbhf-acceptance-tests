package uk.gov.dhsc.htbhf.utils;

/**
 * Factory for test data coming back from WireMock imitating the ClaimantService.
 */
public class WiremockResponseTestDataFactory {

    public static String aValidClaimResponseWithVoucherEntitlement(String eligibilityStatus) {
        return "{\"claimStatus\": \"NEW\", " +
                "\"eligibilityStatus\": \"" + eligibilityStatus + "\", " +
                "\"voucherEntitlement\": " + aValidVoucherEntitlement() +
                "}";
    }

    public static String aValidClaimResponseWithoutVoucherEntitlement(String eligibilityStatus) {
        return "{\"claimStatus\": \"NEW\"," +
                "\"eligibilityStatus\": \"" + eligibilityStatus + "\"" +
                "}";
    }

    public static String aValidVoucherEntitlement() {
        return "{\"vouchersForChildrenUnderOne\": 2, " +
                "\"vouchersForChildrenBetweenOneAndFour\": 1, " +
                "\"vouchersForPregnancy\": 1, " +
                "\"totalVoucherEntitlement\": 4, " +
                "\"singleVoucherValueInPence\": 310, " +
                "\"totalVoucherValueInPence\": 1240" +
                "}";
    }
}
