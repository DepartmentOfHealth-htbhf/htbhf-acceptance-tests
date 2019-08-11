package uk.gov.dhsc.htbhf;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Configuration
@PropertySource("classpath:application.properties")
public class CucumberConfiguration {

    @Bean(destroyMethod = "close")
    public WebDriver webDriver(@Value("${test.browser}") String browser, @Value("${test.headless}") boolean headless) {
        WebDriver webdriver = null;
        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(headless);
                webdriver = new FirefoxDriver(firefoxOptions);
                break;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setHeadless(headless);
                webdriver = new ChromeDriver(chromeOptions);
                break;
        }
        return webdriver;
    }

    @Bean
    public WebDriverWait webDriverWait(WebDriver webDriver, @Value("${wait.timeout.seconds}") int waitTimeoutInSeconds) {
        return new WebDriverWait(webDriver, waitTimeoutInSeconds);
    }

    @Bean(destroyMethod = "stop")
    public WireMockServer wireMockServer(@Value("${wiremock.port}") int wiremockPort) {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(wiremockPort));
        wireMockServer.start();
        //Configure the WireMock client to use the same port configured for the server.
        WireMock.configureFor(wiremockPort);
        return wireMockServer;
    }

    @Bean
    public WireMockManager wireMockManager(WireMockServer wireMockServer) {
        return new WireMockManager(wireMockServer);
    }

}
