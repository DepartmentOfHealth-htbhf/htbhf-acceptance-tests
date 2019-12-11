package uk.gov.dhsc.htbhf.utils;

import uk.gov.dhsc.htbhf.steps.ClaimFailureScenario;

/**
 * Interface for WireMock manager implementations so that we can have a No-op implementation
 * as well as a full implementation so it can be swapped out when using BrowserStack.
 */
public interface WireMockManager {
    default void startWireMock() {
    }

    default void stopWireMock(){
    }

    default void resetWireMockStubs(){
    }

    default void setupClaimantServiceMappingsForSuccess() {
    }

    default void setupClaimantServiceMappingsForFailureScenario(ClaimFailureScenario failureScenario) {
    }

    default void setupPostcodeLookupWithResultsMapping(String postcode){
    }

    default void setupPostcodeLookupErrorResponse() {
    }

    default void setupPostcodeLookupConnectionResetResponse() {
    }

    default void verifyClaimantServiceRequestMatching(String expectedBody) {
    }

    default void setupErrorWiremockClaimMapping() {
    }
}
