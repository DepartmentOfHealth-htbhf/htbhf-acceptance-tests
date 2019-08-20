package uk.gov.dhsc.htbhf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Import(value = {LocalConfiguration.class, BrowserStackConfiguration.class})
public class CucumberConfiguration {

}
