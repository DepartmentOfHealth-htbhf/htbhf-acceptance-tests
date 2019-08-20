package uk.gov.dhsc.htbhf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Retrieves and builds the BrowserStack capabilities from the system properties provided by Gradle
 */
@Slf4j
public class BrowserStackCapabilities {

    public static final String BROWSER_STACK_PREFIX = "BROWSERSTACK.TEST.";

    /**
     * Get the browser stack properties from the System Properties provided, which should have been populated
     * by the Gradle build file all prefixed with BROWSERSTACK.TEST
     *
     * @param systemProperties The system properties to use
     * @return A Map of the filtered properties without the prefix attached
     */
    public static Map<String, String> getBrowserStackCapabilities(Properties systemProperties) {
        Map<String, String> browserStackCapabilities = new HashMap<>();
        systemProperties.stringPropertyNames().stream()
                .filter(propertyName -> propertyName.startsWith(BROWSER_STACK_PREFIX))
                .forEach(browserStackProperty -> {
                    String browserStackCapabilityKey = StringUtils.substringAfter(browserStackProperty, BROWSER_STACK_PREFIX);
                    String capabilityValue = systemProperties.getProperty(browserStackProperty);
                    browserStackCapabilities.put(browserStackCapabilityKey, capabilityValue);
                });
        return browserStackCapabilities;
    }

    /**
     * Builds up the {@link DesiredCapabilities} from the given Map of capabilities and the given BrowserStack username and key to use.
     *
     * @param capabilitiesMap      The Map of key value pairs of capabilities to use
     * @param browserStackUsername The user name to use
     * @param browserStackKey      The key to use
     * @return The built capabilities
     */
    public static DesiredCapabilities buildDesiredCapabilities(Map<String, String> capabilitiesMap, String browserStackUsername, String browserStackKey) {
        log.info("Using config from system properties: {}", capabilitiesMap);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilitiesMap.forEach((key, value) -> {
            //Sadly if we pass a "true" or "false" String into here it doesn't get treated as a boolean so we must convert it.
            Object valueToSet = ("true".equals(value) || "false".equals(value)) ? BooleanUtils.toBooleanObject(value) : value;
            capabilities.setCapability(key, valueToSet);
        });
        capabilities.setCapability("browserstack.user", browserStackUsername);
        capabilities.setCapability("browserstack.key", browserStackKey);
        capabilities.setCapability("browserstack.use_w3c", true);
        return capabilities;
    }

}
