package uk.gov.dhsc.htbhf;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "src/test/resources"
)
//TODO MRS 2019-08-14: Allow the configuration of properties (feature files) via config so we can have different feature files for BrowserStack.
// Try this: https://stackoverflow.com/questions/45095557/java-cucumber-take-cucumberoptions-from-external-source-like-a-property-file
public class RunCucumberTest {

}
