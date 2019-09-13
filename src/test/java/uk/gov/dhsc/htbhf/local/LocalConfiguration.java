package uk.gov.dhsc.htbhf.local;

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

    @Value("${claimant.service.port}")
    private int claimantServicePort;

    @Value("${os.places.port}")
    private int osPlacesPort;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${FEATURE_TOGGLES}")
    private String featureToggles;

    @Bean()
    public WebDriverWrapper localWebDriverWrapper() {
        return new LocalWebDriverWrapper(browser, headless, waitTimeoutInSeconds, baseUrl);
    }

    @Bean(name="claimantServiceMock", destroyMethod = "stop")
    public WireMockServer claimantServiceMock() {
        return startWireMockServer(this.claimantServicePort);
    }

    @Bean(name="osPlacesMock", destroyMethod = "stop")
    public WireMockServer osPlacesMock() {
        return startWireMockServer(this.osPlacesPort);
    }

    private WireMockServer startWireMockServer(int port) {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(port));
        wireMockServer.start();
        return wireMockServer;
    }

    @Bean
    public WireMockManager wireMockManagerImpl(WireMockServer claimantServiceMock, WireMockServer osPlacesMock) {
        return new WireMockManagerImpl(claimantServiceMock, osPlacesMock);
    }

    @Bean
    public TestResultHandler testResultHandler() {
        return new NoopTestResultHandler();
    }

    @Bean
    public ToggleConfiguration toggleConfiguration() {
        return new ToggleConfiguration(featureToggles);
    }
}
