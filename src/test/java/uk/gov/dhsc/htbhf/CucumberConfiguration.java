package uk.gov.dhsc.htbhf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class CucumberConfiguration {

    @Bean(destroyMethod = "close")
    public WebDriver webDriver(@Value("${test.browser}") String browser) {
        WebDriver webdriver = null;
        switch (browser) {
            case "firefox":
                webdriver = new FirefoxDriver();
                break;

            case "chrome":
                webdriver = new ChromeDriver();
                break;
        }
        return webdriver;
    }

    @Bean
    public WebDriverWait webDriverWait(WebDriver webDriver, @Value("${wait.timeout.seconds}") int waitTimeoutInSeconds) {
        return new WebDriverWait(webDriver, waitTimeoutInSeconds);
    }

}
