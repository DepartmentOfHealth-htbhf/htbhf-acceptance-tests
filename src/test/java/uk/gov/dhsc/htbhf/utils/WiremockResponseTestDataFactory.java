package uk.gov.dhsc.htbhf.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for test data coming back from WireMock imitating the ClaimantService.
 */
public class WiremockResponseTestDataFactory {

    private static final String DEFAULT_POSTCODE = "AA11BB";
    private static final Map<String, String> RESPONSE_TEMPLATES = new ConcurrentHashMap<>();

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

    private static String aValidVoucherEntitlement() {
        return "{\"vouchersForChildrenUnderOne\": 2, " +
                "\"vouchersForChildrenBetweenOneAndFour\": 1, " +
                "\"vouchersForPregnancy\": 1, " +
                "\"totalVoucherEntitlement\": 4, " +
                "\"singleVoucherValueInPence\": 310, " +
                "\"totalVoucherValueInPence\": 1240" +
                "}";
    }

    public static String aPostcodeLookupResponseWithResults(String postcode) {
        String template = getResponseTemplate("postcode-lookup-2-results.json");
        return template.replace(DEFAULT_POSTCODE, postcode);
    }

    private static String getResponseTemplate(String templateName) {
        return RESPONSE_TEMPLATES.computeIfAbsent(templateName, key -> {
            try {
                URL resource = WiremockResponseTestDataFactory.class.getClassLoader().getResource("wiremock/mappings/" + templateName);
                return Files.readString( Paths.get(resource.toURI()));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
