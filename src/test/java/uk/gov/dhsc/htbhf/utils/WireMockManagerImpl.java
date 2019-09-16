package uk.gov.dhsc.htbhf.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import uk.gov.dhsc.htbhf.steps.Constants;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aPostcodeLookupResponseWithNoResults;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aPostcodeLookupResponseWithResults;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aValidClaimResponseWithVoucherEntitlement;
import static uk.gov.dhsc.htbhf.utils.WiremockResponseTestDataFactory.aValidClaimResponseWithoutVoucherEntitlement;

/**
 * Manages all interactions with WireMock
 */
public class WireMockManagerImpl implements WireMockManager {

    private static final String CLAIMS_ENDPOINT = "/v2/claims";
    private static final String POSTCODE_LOOKUP_ENDPOINT = "/places/v1/addresses/postcode";
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

    private WireMockServer claimantServiceMock;
    private WireMockServer osPlacesMock;


    public WireMockManagerImpl(WireMockServer claimantServiceMock, WireMockServer osPlacesMock) {
        this.claimantServiceMock = claimantServiceMock;
        this.osPlacesMock = osPlacesMock;
    }

    @Override
    public void startWireMock() {
        claimantServiceMock.start();
        osPlacesMock.start();
    }

    @Override
    public void stopWireMock() {
        claimantServiceMock.stop();
        osPlacesMock.stop();
    }

    @Override
    public void resetWireMockStubs() {
        claimantServiceMock.resetAll();
        osPlacesMock.resetAll();
    }

    @Override
    public void setupClaimantServiceMappingsWithStatus(String eligibilityStatus) {
        String wireMockBody = (eligibilityStatus.equals("ELIGIBLE") ?
                aValidClaimResponseWithVoucherEntitlement(eligibilityStatus) : aValidClaimResponseWithoutVoucherEntitlement(eligibilityStatus));
        claimantServiceMock.stubFor(post(urlEqualTo(CLAIMS_ENDPOINT))
                .withHeader(REQUEST_ID_HEADER, matching(ID_HEADERS_REGEX))
                .withHeader(SESSION_ID_HEADER, matching(ID_HEADERS_REGEX))
                .willReturn(aResponse()
                        .withStatus(ELIGIBILITY_RESPONSE_MAPPINGS.get(eligibilityStatus))
                        .withHeader("Content-Type", "application/json")
                        .withBody(wireMockBody)));
    }

    @Override
    public void setupPostcodeLookupWithResultsMapping(String postcode) {
        String wireMockBody =  (postcode.equalsIgnoreCase(Constants.POSTCODE_WITH_NO_RESULTS))
                ? aPostcodeLookupResponseWithNoResults()
                : aPostcodeLookupResponseWithResults(postcode);
        osPlacesMock.stubFor(get(urlPathEqualTo(POSTCODE_LOOKUP_ENDPOINT))
                .withQueryParam("postcode", equalTo(postcode))
                .withQueryParam("key", matching(".*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(wireMockBody)));
    }
}
