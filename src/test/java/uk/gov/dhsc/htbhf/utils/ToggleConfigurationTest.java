package uk.gov.dhsc.htbhf.utils;

import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.page.PageName;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ToggleConfigurationTest {

    private static final String VALID_TOGGLE_CONFIG = "{\n" +
            "  \"ADDRESS_LOOKUP_ENABLED\": false,\n" +
            "  \"FOOBAR_ENABLED\": true\n" +
            "}";
    private static final String INVALID_TOGGLE_JSON = "{}}";
    private static final String EMPTY_TOGGLE_JSON = "{}";

    @Test
    void shouldLoadTogglesFromEnvironment() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(VALID_TOGGLE_CONFIG);
        //When
        Map<String, Boolean> allToggles = toggleConfiguration.getAllToggles();
        boolean addressToggled = toggleConfiguration.isEnabled("ADDRESS_LOOKUP_ENABLED");
        boolean fooBarToggled = toggleConfiguration.isEnabled("FOOBAR_ENABLED");
        //Then
        assertThat(allToggles).hasSize(2);
        assertThat(addressToggled).isFalse();
        assertThat(fooBarToggled).isTrue();
    }

    @Test
    void shouldNotBeEnabledForMissingToggle() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(VALID_TOGGLE_CONFIG);
        //When
        boolean somethingEnabled = toggleConfiguration.isEnabled("SOMETHING_ENABLED");
        //Then
        assertThat(somethingEnabled).isFalse();
    }

    @Test
    void shouldEnablePageThatHasNoToggle() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(VALID_TOGGLE_CONFIG);
        //When
        boolean pageEnabled = toggleConfiguration.isPageEnabled(PageName.HOW_IT_WORKS);
        //Then
        assertThat(pageEnabled).isTrue();
    }

    @Test
    void shouldNotBeEnabledForToggledPageWithNoConfig() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(EMPTY_TOGGLE_JSON);
        //When
        boolean pageEnabled = toggleConfiguration.isPageEnabled(PageName.POSTCODE);
        //Then
        assertThat(pageEnabled).isFalse();
    }

    @Test
    void shouldBeEnabledForPageWithNoToggleWithNoConfig() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(EMPTY_TOGGLE_JSON);
        //When
        boolean toggle = toggleConfiguration.isPageEnabled(PageName.HOW_IT_WORKS);
        //Then
        assertThat(toggle).isTrue();
    }

    @Test
    void shouldReturnNoTogglesWhenEnvironmentVariableDoesntExist() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(null);
        //When
        Map<String, Boolean> allToggles = toggleConfiguration.getAllToggles();
        //Then
        assertThat(allToggles).isEmpty();
    }

    @Test
    void shouldReturnNoTogglesWhenEnvironmentVariableIsInvalidJson() {
        //Given
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(INVALID_TOGGLE_JSON);
        //When
        Map<String, Boolean> allToggles = toggleConfiguration.getAllToggles();
        //Then
        assertThat(allToggles).isEmpty();
    }
}
