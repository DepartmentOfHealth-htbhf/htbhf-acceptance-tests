package uk.gov.dhsc.htbhf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import uk.gov.dhsc.htbhf.utils.NoopWireMockManager;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@PropertySource("classpath:application-browserstack.properties")
@Profile("browserstack")
public class BrowserStackConfiguration {

    @Bean(destroyMethod = "close")
    public WebDriver browserStackWebDriver() throws MalformedURLException {
        //TODO MRS 2019-08-14: Import via config all these values
        String BROWSER_STACK_USER = "";
        String BROWSER_STACK_KEY = "";
        String url = "https://hub-cloud.browserstack.com/wd/hub";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserName", "iPhone");
        caps.setCapability("device", "iPhone 8 Plus");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "11");
        caps.setCapability("browserstack.user", BROWSER_STACK_USER);
        caps.setCapability("browserstack.key", BROWSER_STACK_KEY);
        caps.setCapability("browserstack.use_w3c", "11");

        return new RemoteWebDriver(new URL(url), caps);
    }

    @Bean
    public WireMockManager noopWireMockManager() {
        return new NoopWireMockManager();
    }
}
