package uk.gov.dhsc.htbhf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Import(value = {LocalConfiguration.class, BrowserStackConfiguration.class})
public class CucumberConfiguration {
    @Bean
    public WebDriverWait webDriverWait(WebDriver webDriver, @Value("${wait.timeout.seconds}") int waitTimeoutInSeconds) {
        return new WebDriverWait(webDriver, waitTimeoutInSeconds);
    }

}
