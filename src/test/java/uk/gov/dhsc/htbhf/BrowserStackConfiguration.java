package uk.gov.dhsc.htbhf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import uk.gov.dhsc.htbhf.utils.NoopWireMockManager;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static uk.gov.dhsc.htbhf.BrowserStackCapabilities.buildDesiredCapabilities;
import static uk.gov.dhsc.htbhf.BrowserStackCapabilities.getBrowserStackCapabilities;

@Configuration
@PropertySource("classpath:application-browserstack.properties")
@Profile("browserstack")
@Slf4j
public class BrowserStackConfiguration {

    private static final String BROWSER_STACK_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @Value("${BROWSER_STACK_USER:}")
    private String browserStackUser;

    @Value("${BROWSER_STACK_KEY:}")
    private String browserStackKey;

    @Bean(destroyMethod = "close")
    public WebDriver browserStackWebDriver() throws MalformedURLException {
        Validate.notBlank(browserStackUser, "BrowserStack user must be provided, set the BROWSER_STACK_USER environment variable");
        Validate.notBlank(browserStackKey, "BrowserStack key must be provided, set the BROWSER_STACK_KEY environment variable");
        log.info("Using login: {} {}", browserStackUser, browserStackKey);
        Map<String, String> browserStackCapabilities = getBrowserStackCapabilities(System.getProperties());
        log.info("Using capabilities: {}", browserStackCapabilities);

        return new RemoteWebDriver(new URL(BROWSER_STACK_URL), buildDesiredCapabilities(browserStackCapabilities, browserStackUser, browserStackKey));
    }

    @Bean
    public WireMockManager noopWireMockManager() {
        return new NoopWireMockManager();
    }
}
