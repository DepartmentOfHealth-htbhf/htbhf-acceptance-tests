package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;

/**
 * An enum of all the toggle values. By default the toggle name is expected to be the name
 * of this enum with _ENABLED as a suffix in the configuration.
 */
public enum ToggleName {
    ADDRESS_LOOKUP;

    public String getFeatureKey() {
        return this.name() + "_ENABLED";
    }

    public static ToggleName toToggleName(String toggleString) {
        return Arrays.stream(values())
                .filter(toggleName -> toggleName.getFeatureKey().equals(toggleString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No toggle found for value: " + toggleString));
    }
}
