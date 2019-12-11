package uk.gov.dhsc.htbhf.utils;

import uk.gov.dhsc.htbhf.steps.ClaimFailureScenario;

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

    public static String anEligibleClaimResponseWithVoucherEntitlement() {
        return getResponseTemplate("claim-eligible-with-vouchers.json");
    }

    public static String aFailedClaimResponse(ClaimFailureScenario failureScenario) {
        return getResponseTemplate(failureScenario.getJsonFile());
    }

    public static String aPostcodeLookup500ErrorResponse() {
        return getResponseTemplate("postcode-lookup-error-500-response.json");
    }

    public static String aPostcodeLookupResponseWithNoResults() {
        return getResponseTemplate("postcode-lookup-no-results.json");
    }

    public static String aPostcodeLookupResponseWithResults(String postcode) {
        String template = getResponseTemplate("postcode-lookup-2-results.json");
        return template.replace(DEFAULT_POSTCODE, postcode);
    }

    private static String getResponseTemplate(String templateName) {
        return RESPONSE_TEMPLATES.computeIfAbsent(templateName, key -> {
            try {
                URL resource = WiremockResponseTestDataFactory.class.getClassLoader().getResource("wiremock/mappings/" + templateName);
                return Files.readString(Paths.get(resource.toURI()));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
