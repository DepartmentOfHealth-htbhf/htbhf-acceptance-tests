package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import uk.gov.dhsc.htbhf.page.InScotlandPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for for I Live In Scotland page
 */
public class InScotlandSteps extends CommonSteps {

    @Then("^I am informed that I cannot apply and should use the Scottish scheme")
    public void verifyILiveInScotlandScheme() {
        InScotlandPage inScotlandPage = getPages().getInScotlandPage();
        String h1Text = inScotlandPage.getH1Text();
        assertThat(h1Text).as("expected I live in Scotland page H1 text to be correct")
                .isEqualTo("You cannot apply if you live in Scotland");
        List<String> bodyValues = inScotlandPage.getAllBodyText();
        assertThat(bodyValues.get(0)).as("expected first paragraph body text for the I live in Scotland page to be as expected")
                .isEqualTo("You can only apply for this scheme if you live in England, Wales or Northern Ireland.");
        assertThat(bodyValues.get(1)).as("expected second paragraph body text for the I live in Scotland page to be as expected")
                .isEqualTo("If you live in Scotland you can apply for Best Start Foods.");
    }
}
