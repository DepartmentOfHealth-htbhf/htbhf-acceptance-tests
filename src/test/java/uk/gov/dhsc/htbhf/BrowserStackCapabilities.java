package uk.gov.dhsc.htbhf;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static uk.gov.dhsc.htbhf.BrowserStackLauncher.BROWSER_STACK_TEST_NAME;

/**
 * Retrieves and builds the BrowserStack capabilities from the system properties provided by Gradle
 */
@Slf4j
public class BrowserStackCapabilities {

    /**
     * Get the browser stack properties from a Properties file for the test specified from the
     * the BROWSERSTACK.TEST.NAME System property.
     *
     * @param systemProperties The system properties to find the test name from
     * @return A Map of the properties for the requested test
     */
    public static Map<String, String> getBrowserStackCapabilities(Properties systemProperties) {
        String testName = systemProperties.getProperty(BROWSER_STACK_TEST_NAME);
        Properties properties = new Properties();

        try {
            String capabilitiesFileName = testName + ".properties";
            log.info("Loading BrowserStack capabilities from: {}", capabilitiesFileName);
            properties.load(BrowserStackCapabilities.class.getClassLoader().getResourceAsStream(capabilitiesFileName));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load capabilities from file: " + testName, e);
        }

        return Maps.fromProperties(properties);
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
