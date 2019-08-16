package uk.gov.dhsc.htbhf.utils;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aValidClaimResponseWithVoucherEntitlement;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aValidClaimResponseWithoutVoucherEntitlement;

/**
 * Manages all interactions with WireMock
 */
public class WireMockManagerImpl implements WireMockManager {

    private static final String CLAIMS_ENDPOINT = "/v2/claims";
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String SESSION_ID_HEADER = "X-Session-ID";
    private static final String ID_HEADERS_REGEX = "([A-Za-z0-9_-])+";

    private Map<String, Integer> ELIGIBILITY_RESPONSE_MAPPINGS = Map.of(
            "ELIGIBLE", 201,
            "INELIGIBLE", 200,
            "PENDING", 200,
            "NO_MATCH", 404,
            "ERROR", 200,
            "DUPLICATE", 200
    );

    private WireMockServer wireMockServer;

    public WireMockManagerImpl(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    @Override
    public void startWireMock() {
        wireMockServer.start();
    }

    @Override
    public void stopWireMock() {
        wireMockServer.stop();
    }

    @Override
    public void resetWireMockStubs() {
        wireMockServer.resetAll();
    }

    @Override
    public void setupWireMockMappingsWithStatus(String eligibilityStatus) {
        String wireMockBody = (eligibilityStatus.equals("ELIGIBLE") ?
                aValidClaimResponseWithVoucherEntitlement(eligibilityStatus) : aValidClaimResponseWithoutVoucherEntitlement(eligibilityStatus));
        stubFor(post(urlEqualTo(CLAIMS_ENDPOINT))
                .withHeader(REQUEST_ID_HEADER, matching(ID_HEADERS_REGEX))
                .withHeader(SESSION_ID_HEADER, matching(ID_HEADERS_REGEX))
                .willReturn(aResponse()
                        .withStatus(ELIGIBILITY_RESPONSE_MAPPINGS.get(eligibilityStatus))
                        .withHeader("Content-Type", "application/json")
                        .withBody(wireMockBody)));
    }
}
