package uk.gov.dhsc.htbhf.page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ToggleNameTest {

    @Test
    void shouldGetFullFeatureKeyFromEnum() {
        assertThat(ToggleName.ADDRESS_LOOKUP.getFeatureKey()).isEqualTo("ADDRESS_LOOKUP_ENABLED");
    }

    @Test
    void shouldGetValidToggleName() {
        assertThat(ToggleName.toToggleName("ADDRESS_LOOKUP_ENABLED")).isEqualTo(ToggleName.ADDRESS_LOOKUP);
    }

    @ValueSource(strings = {
            "FOO_BAR_ENABLED",
            "ADDRESS_LOOKUP",
            ""
    })
    @ParameterizedTest
    void shouldThrowExceptionForInvalidToggleName(String invalidToggle) {
        IllegalArgumentException exception = catchThrowableOfType(() -> ToggleName.toToggleName(invalidToggle), IllegalArgumentException.class);
        assertThat(exception).hasMessage("No toggle found for value: " + invalidToggle);
    }
}
