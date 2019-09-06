package uk.gov.dhsc.htbhf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uk.gov.dhsc.htbhf.browserstack.BrowserStackConfiguration;
import uk.gov.dhsc.htbhf.local.LocalConfiguration;

@Configuration
@Import(value = {LocalConfiguration.class, BrowserStackConfiguration.class})
public class CucumberConfiguration {

}
