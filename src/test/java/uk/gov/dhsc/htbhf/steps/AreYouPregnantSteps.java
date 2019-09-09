package uk.gov.dhsc.htbhf.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import uk.gov.dhsc.htbhf.page.AreYouPregnantPage;
import uk.gov.dhsc.htbhf.page.component.RadioButton;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.dhsc.htbhf.page.AreYouPregnantPage.getAreYouPregnantErrorLinkCss;
import static uk.gov.dhsc.htbhf.page.AreYouPregnantPage.getAreYouPregnantFieldErrorId;
import static uk.gov.dhsc.htbhf.page.AreYouPregnantPage.getExpectedDeliveryDateErrorLinkCss;
import static uk.gov.dhsc.htbhf.page.AreYouPregnantPage.getExpectedDeliveryDateFieldErrorId;

public class AreYouPregnantSteps extends CommonSteps {

    @When("^I say No to the are you pregnant question$")
    public void whenISelectNoToAreYouPregnant() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectNo();
    }

    @When("^I say Yes to the are you pregnant question$")
    public void whenISelectYesToAreYouPregnant() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.selectYes();
    }

    @When("^I do not select an option$")
    public void whenIDoNotSelectAnOption() {
        // Specifically does nothing
    }

    //TODO MRS 09/09/2019: Create a GenericSteps class where methods like this can be shared.
    @When("^I click continue$")
    public void whenIClickContinue() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.clickContinue();
    }

    @When("^I enter text in the expected delivery date fields")
    public void enterTextInExpectedDeliveryDateFields() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        areYouPregnantPage.enterTextIntoDateFields();
    }

    @When("^I enter a valid expected delivery date")
    public void enterValidExpectedDeliveryDate() {
        enterExpectedDeliveryDateOnPregnancyPage(2);
    }

    @When("^I enter my expected delivery date too far in the past")
    public void enterExpectedDeliveryDateTooFarInThePast() {
        enterExpectedDeliveryDateOnPregnancyPage(-2);
    }

    @When("^I enter my expected delivery date too far in the future")
    public void enterExpectedDeliveryDateTooFarInTheFuture() {
        enterExpectedDeliveryDateOnPregnancyPage(9);
    }

    @Then("no values are present in the expected delivery date fields")
    public void noValuesPresentInExpectedDeliveryDateFields() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        assertThat(areYouPregnantPage.getDayValue()).isEmpty();
        assertThat(areYouPregnantPage.getMonthValue()).isEmpty();
        assertThat(areYouPregnantPage.getYearValue()).isEmpty();
    }

    @Then("I am informed that I need to select an option for are you pregnant")
    public void iAmInformedINeedToSelectAnOptionsForAreYouPregnant() {
        assertAreYouPregnantPageErrorPresent();
    }

    @Then("^No option is selected$")
    public void noOptionIsSelected() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        List<WebElement> radioButtons = areYouPregnantPage.getRadioButtons();
        assertThat(radioButtons).hasSize(2);

        for (WebElement radioButton : radioButtons) {
            String checked = radioButton.getAttribute("checked");
            assertThat(checked).isNotEqualTo("true");
        }
    }

    @Then("^Yes and No options are displayed on the are you pregnant page$")
    public void yesAndNoOptionsAreDisplayed() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        List<String> radioButtonLabels = areYouPregnantPage.getAllRadioLabels();
        assertThat(radioButtonLabels).containsExactly(RadioButton.YES_LABEL, RadioButton.NO_LABEL);
    }

    @Then("^expected date of delivery instructional text is displayed$")
    public void expectedDeliveryDateInstructionalTextIsDisplayed() {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        String instructionalText = areYouPregnantPage.getExpectedDeliveryDateInstructionalText();
        assertThat(instructionalText).isNotBlank();
    }

    @Then("^I am informed that I need to enter an expected delivery date")
    public void iAmInformedINeedToEnterAnExpectedDeliveryDate() {
        assertExpectedDeliveryDateErrorPresent("Enter the due date");
    }

    @Then("^I am informed that the date is too far in the past|I am informed that the date is too far in the future")
    public void iAmInformedTheExpectedDeliveryDateIsInvalid() {
        assertExpectedDeliveryDateErrorPresent("Enter a due date to show you’re at least 10 weeks pregnant");
    }

    private void assertAreYouPregnantPageErrorPresent() {
        assertErrorPresent(getAreYouPregnantFieldErrorId(), getAreYouPregnantErrorLinkCss(), "Select yes if you’re pregnant");
    }

    private void assertExpectedDeliveryDateErrorPresent(String expectedErrorMessage) {
        assertErrorPresent(getExpectedDeliveryDateFieldErrorId(), getExpectedDeliveryDateErrorLinkCss(), expectedErrorMessage);
    }

    private void assertErrorPresent(String fieldErrorId, String errorLinkCss, String expectedErrorMessage) {
        AreYouPregnantPage areYouPregnantPage = getPages().getAreYouPregnantPage();
        assertErrorHeaderTextPresent(areYouPregnantPage);
        assertFieldErrorAndLinkTextPresentAndCorrect(areYouPregnantPage, fieldErrorId, errorLinkCss, expectedErrorMessage);
    }

}
