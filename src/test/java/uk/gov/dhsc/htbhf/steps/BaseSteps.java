package uk.gov.dhsc.htbhf.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import uk.gov.dhsc.htbhf.TestResultHandler;
import uk.gov.dhsc.htbhf.WebDriverWrapper;
import uk.gov.dhsc.htbhf.page.BasePage;
import uk.gov.dhsc.htbhf.page.Pages;
import uk.gov.dhsc.htbhf.utils.WireMockManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base setup for all the step classes which autowires in the web driver and anything
 * else need to setup pages and perform steps.
 */

public abstract class BaseSteps {

    @Autowired
    protected WebDriverWrapper webDriverWrapper;

    @Autowired
    protected WireMockManager wireMockManager;

    @Autowired
    protected TestResultHandler testResultHandler;

    @Value("${base.url}")
    protected String baseUrl;

    @Value("${session.details.url}")
    protected String sessionDetailsUrl;

    @Value("${spring.profiles.active}")
    protected String activeProfile;

    protected WebDriver getWebDriver() {
        return webDriverWrapper.getWebDriver();
    }

    protected WebDriverWait getWebDriverWait() {
        return webDriverWrapper.getWebDriverWait();
    }

    protected Pages getPages() {
        return webDriverWrapper.getPages();
    }

    protected void assertErrorHeaderTextPresent(BasePage basePage) {
        basePage.waitForPageToLoad();
        String errorHeader = basePage.getPageErrorHeaderText();
        assertThat(errorHeader).isEqualTo("There’s a problem");
    }

    protected void assertFieldErrorAndLinkTextPresentAndCorrect(BasePage basePage, String fieldErrorId, String errorLinkCss, String expectedErrorMessage) {
        String fieldErrorText = basePage.getFieldErrorText(fieldErrorId);
        String errorLinkText = basePage.getErrorLinkText(errorLinkCss);

        assertThat(fieldErrorText).isEqualTo(expectedErrorMessage);
        assertThat(errorLinkText).isEqualTo(expectedErrorMessage);
    }

}
