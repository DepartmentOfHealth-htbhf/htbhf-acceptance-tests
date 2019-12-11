package uk.gov.dhsc.htbhf.steps;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ClaimFailureScenario {

    IDENTITY("claim-identity-failed.json"),
    ELIGIBILITY("claim-eligibility-failed.json"),
    DUPLICATE("claim-duplicate.json");

    private String jsonFile;

    public static ClaimFailureScenario fromString(String failureString) {
        return Arrays.stream(ClaimFailureScenario.values()).filter(failureScenario -> failureScenario.name().equalsIgnoreCase(failureString))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No failure scenario found for value: " + failureString));
    }
}
