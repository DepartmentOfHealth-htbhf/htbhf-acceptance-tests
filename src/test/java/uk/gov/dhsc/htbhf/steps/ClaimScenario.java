package uk.gov.dhsc.htbhf.steps;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ClaimScenario {

    IDENTITY_NOT_MATCHED("claim-identity-failed.json"),
    ELIGIBILITY_NOT_CONFIRMED("claim-eligibility-failed.json"),
    DUPLICATE_CLAIM("claim-duplicate.json"),
    CHILD_DOB_MISMATCH("claim-child-dob-mismatch.json"),
    POSTCODE_MISMATCH("claim-postcode-mismatch.json"),
    FULL_ADDRESS_MISMATCH("claim-full-address-mismatch.json"),
    ADDRESS_LINE_ONE_MISMATCH("claim-address-line-one-mismatch.json");

    private String jsonFile;

    public static ClaimScenario fromString(String scenarioString) {
        return Arrays.stream(ClaimScenario.values()).filter(scenario -> normaliseEnumName(scenario).equalsIgnoreCase(scenarioString))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No failure scenario found for value: " + scenarioString));
    }

    private static String normaliseEnumName(ClaimScenario scenario) {
        return scenario.name().replace('_', ' ');
    }
}
