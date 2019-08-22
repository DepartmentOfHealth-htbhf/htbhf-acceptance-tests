package uk.gov.dhsc.htbhf;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
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

    @Bean(destroyMethod = "closeDriver")
    public WebDriverWrapper localWebDriverWrapper() {
        return new LocalWebDriverWrapper(browser, headless, waitTimeoutInSeconds);
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
}
