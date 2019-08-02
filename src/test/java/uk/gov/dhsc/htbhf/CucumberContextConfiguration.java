package uk.gov.dhsc.htbhf;

import io.cucumber.java.Before;
import org.springframework.test.context.ContextConfiguration;

/**
 * Context configuration class that has the single purpose of linking the cucumber context to the spring
 * context so we can use dependency injection into the step definition files.
 */
@ContextConfiguration(classes = {CucumberConfiguration.class})
public class CucumberContextConfiguration {

    @Before
    public void init() {
        //Does nothing but it links the cucumber context to the spring context.
    }
}
