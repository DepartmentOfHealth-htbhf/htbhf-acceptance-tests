package uk.gov.dhsc.htbhf;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * This is not run via a normal Gradle test task, we control its lifecycle via BrowserStackLauncher,
 * please see that class for how the Cucumber Compatibility tests are run.
 *
 * @see uk.gov.dhsc.htbhf.browserstack.BrowserStackLauncher
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "src/test/resources/features/compatibility",
        strict = true
)
public class RunCompatibilityTests {

}
