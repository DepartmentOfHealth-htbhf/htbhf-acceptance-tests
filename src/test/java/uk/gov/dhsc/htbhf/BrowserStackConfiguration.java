package uk.gov.dhsc.htbhf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import uk.gov.dhsc.htbhf.utils.NoopWireMockManager;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

@Configuration
@PropertySource("classpath:application-browserstack.properties")
@Profile("browserstack")
@Slf4j
public class BrowserStackConfiguration {

    @Value("${BROWSER_STACK_USER:}")
    private String browserStackUser;

    @Value("${BROWSER_STACK_KEY:}")
    private String browserStackKey;

    @Value("${wait.timeout.seconds}")
    private int waitTimeoutInSeconds;

    @Bean(destroyMethod = "closeDriver")
    public WebDriverWrapper browserStackDriverBuilder() {
        return new BrowserStackDriverWrapper(browserStackUser, browserStackKey, waitTimeoutInSeconds);
    }

    @Bean
    public WireMockManager noopWireMockManager() {
        return new NoopWireMockManager();
    }
}
