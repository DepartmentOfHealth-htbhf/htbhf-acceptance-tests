package uk.gov.dhsc.htbhf.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.gov.dhsc.htbhf.page.PageName;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.utils.ToggleConfiguration.ENVIRONMENT_PROPERTY;

class ToggleConfigurationTest {

    private static final String VALID_TOGGLE_CONFIG = "{\n" +
            "  \"ADDRESS_LOOKUP_ENABLED\": false,\n" +
            "  \"FOOBAR_ENABLED\": true\n" +
            "}";
    private static final String INVALID_TOGGLE_JSON = "{}}";
    private static final String EMPTY_TOGGLE_JSON = "{}";
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldLoadTogglesFromEnvironment() {
        //Given
        Properties properties = setupPropertiesForToggleJson(VALID_TOGGLE_CONFIG);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
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
        Properties properties = setupPropertiesForToggleJson(VALID_TOGGLE_CONFIG);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        boolean somethingEnabled = toggleConfiguration.isEnabled("SOMETHING_ENABLED");
        //Then
        assertThat(somethingEnabled).isFalse();
    }

    @Test
    void shouldEnablePageThatHasNoToggle() {
        //Given
        Properties properties = setupPropertiesForToggleJson(VALID_TOGGLE_CONFIG);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        boolean pageEnabled = toggleConfiguration.isPageEnabled(PageName.HOW_IT_WORKS);
        //Then
        assertThat(pageEnabled).isTrue();
    }

    @Test
    void shouldBeToggledOffForToggledPageWithNoConfig() {
        //Given
        Properties properties = setupPropertiesForToggleJson(EMPTY_TOGGLE_JSON);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        boolean pageEnabled = toggleConfiguration.isPageEnabled(PageName.POSTCODE);
        //Then
        assertThat(pageEnabled).isFalse();
    }

    @Test
    void shouldBeToggledOnForPageWithNoToggleWithNoConfig() {
        //Given
        Properties properties = setupPropertiesForToggleJson(EMPTY_TOGGLE_JSON);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        boolean toggle = toggleConfiguration.isPageEnabled(PageName.HOW_IT_WORKS);
        //Then
        assertThat(toggle).isTrue();
    }

    @Test
    void shouldReturnNoTogglesWhenEnvironmentVariableDoesntExist() {
        //Given
        Properties properties = new Properties();
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        Map<String, Boolean> allToggles = toggleConfiguration.getAllToggles();
        //Then
        assertThat(allToggles).isEmpty();
    }

    @Test
    void shouldReturnNoTogglesWhenEnvironmentVariableIsInvalidJson() {
        //Given
        Properties properties = setupPropertiesForToggleJson(INVALID_TOGGLE_JSON);
        ToggleConfiguration toggleConfiguration = new ToggleConfiguration(properties, objectMapper);
        //When
        Map<String, Boolean> allToggles = toggleConfiguration.getAllToggles();
        //Then
        assertThat(allToggles).isEmpty();
    }

    private Properties setupPropertiesForToggleJson(String toggleJson) {
        Properties properties = new Properties();
        properties.put(ENVIRONMENT_PROPERTY, toggleJson);
        return properties;
    }
}
