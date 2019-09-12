package uk.gov.dhsc.htbhf.local;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import uk.gov.dhsc.htbhf.TestResultHandler;
import uk.gov.dhsc.htbhf.WebDriverWrapper;
import uk.gov.dhsc.htbhf.utils.ToggleConfiguration;
import uk.gov.dhsc.htbhf.utils.WireMockManager;
import uk.gov.dhsc.htbhf.utils.WireMockManagerImpl;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Configuration
@PropertySource("classpath:application-local.properties")
@Profile("!browserstack")
public class LocalConfiguration {

    @Value("${test.browser}")
    private String browser;

    @Value("${test.headless}")
    private boolean headless;

    @Value("${wait.timeout.seconds}")
    private int waitTimeoutInSeconds;

    @Value("${wiremock.port}")
    private int wiremockPort;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${FEATURE_TOGGLES}")
    private String featureToggles;

    @Bean()
    public WebDriverWrapper localWebDriverWrapper() {
        return new LocalWebDriverWrapper(browser, headless, waitTimeoutInSeconds, baseUrl);
    }

    @Bean(destroyMethod = "stop")
    public WireMockServer wireMockServer() {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(wiremockPort));
        wireMockServer.start();
        //Configure the WireMock client to use the same port configured for the server.
        WireMock.configureFor(wiremockPort);
        return wireMockServer;
    }

    @Bean
    public WireMockManager wireMockManagerImpl(WireMockServer wireMockServer) {
        return new WireMockManagerImpl(wireMockServer);
    }

    @Bean
    public TestResultHandler testResultHandler() {
        return new NoopTestResultHandler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ToggleConfiguration toggleConfiguration(ObjectMapper objectMapper) {
        return new ToggleConfiguration(featureToggles, objectMapper);
    }
}
