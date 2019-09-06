package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.When;
import uk.gov.dhsc.htbhf.page.GuidancePage;
import uk.gov.dhsc.htbhf.page.PageName;

/**
 * Steps used when testing the complete application flow.
 */
public class CompleteFlowSteps extends CommonSteps {

    private GuidancePage applyPage;

    @When("^I complete the application with valid details for a pregnant woman")
    public void completeTheApplicationAsAPregnantWoman() {
        //TODO MRS 2019-09-04: Change to use NavigationSteps.
        applyPage = getPages().getGuidancePage(PageName.APPLY);
        applyPage.clickStartButton();
        enterDoYouLiveInScotlandNoAndSubmit();
        enterDateOfBirthAndSubmit();
        enterDoYouHaveChildrenYesAndSubmit();
        enterOneChildsDateOfBirth();
        selectYesOnPregnancyPage();
        enterDefaultName();
        enterNino();
        enterDefaultManualAddress();
        enterPhoneNumber();
        enterEmailAddress();
        selectTextOnSendCode();
        enterConfirmationCodeAndSubmit();
    }
}
