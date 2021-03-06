package uk.gov.dhsc.htbhf.browserstack;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static uk.gov.dhsc.htbhf.browserstack.BrowserStackLauncher.setTestFilePath;

class BrowserStackCapabilitiesTest {

    @Test
    void shouldGetBrowserStackCapabilities() {
        //Given
        setTestFilePath(Path.of("src/test/resources/browserstack/mobile-ios-iphone8.properties"));
        //When
        Map<String, String> result = BrowserStackCapabilities.getBrowserStackCapabilities();
        //Then
        assertThat(result).hasSize(4);
        assertThat(result).containsOnly(
                entry("browserName", "iPhone"),
                entry("device", "iPhone 8"),
                entry("realMobile", "true"),
                entry("os_version", "11")
        );
    }

    @Test
    void shouldBuildDesiredCapabilities() {
        //Given
        Map<String, String> capabilitiesMap = Map.of("browserName", "iPhone",
                "device", "iPhone 8 Plus",
                "realMobile", "true",
                "os_version", "11");
        //When
        DesiredCapabilities capabilities = BrowserStackCapabilities.buildDesiredCapabilities(capabilitiesMap, "user", "key");
        //Then
        assertThat(capabilities.getBrowserName()).isEqualTo("iPhone");
        assertThat(capabilities.getCapability("device")).isEqualTo("iPhone 8 Plus");
        assertThat(capabilities.getCapability("os_version")).isEqualTo("11");
        assertThat(capabilities.getCapability("realMobile")).isEqualTo(true);
        assertThat(capabilities.getCapability("browserstack.user")).isEqualTo("user");
        assertThat(capabilities.getCapability("browserstack.key")).isEqualTo("key");
        assertThat(capabilities.getCapability("browserstack.use_w3c")).isEqualTo(true);
    }
}
