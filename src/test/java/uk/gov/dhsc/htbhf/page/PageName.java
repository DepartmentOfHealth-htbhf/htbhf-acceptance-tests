package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;
import java.util.Optional;

import static uk.gov.dhsc.htbhf.page.ToggleName.ADDRESS_LOOKUP;

/**
 * Enum of all the page names
 */
public enum PageName {

    APPLY("Apply for Healthy Start"),
    ARE_YOU_PREGNANT("are you pregnant"),
    CHECK_ANSWERS("check answers"),
    CHILD_DATE_OF_BIRTH("enter your childrens dates of birth"),
    COOKIES("cookies"),
    DATE_OF_BIRTH("enter date of birth"),
    DECISION("decision"),
    DO_YOU_HAVE_CHILDREN("do you have children under four years old"),
    ELIGIBILITY("Eligibility"),
    EMAIL_ADDRESS("email address"),
    ENTER_CODE("enter code"),
    HOW_IT_WORKS("How it works"),
    IN_SCOTLAND("I live in Scotland"),
    MANUAL_ADDRESS("manual address"),
    NAME("enter name"),
    NATIONAL_INSURANCE_NUMBER("enter national insurance number"),
    NHS_NUMBER("nhs number"),
    PAGE_NOT_FOUND("page not found"),
    PHONE_NUMBER("phone number"),
    POSTCODE("postcode", ADDRESS_LOOKUP),
    PRIVACY_NOTICE("privacy notice"),
    REPORT_A_CHANGE("Report a change"),
    SCOTLAND("do you live in Scotland"),
    SELECT_ADDRESS("select address", ADDRESS_LOOKUP),
    SEND_CODE("send code"),
    SERVER_ERROR("Server Error"),
    TEST_NAMES("test name"),
    TERMS_AND_CONDITIONS("terms and conditions"),
    UNSUCCESSFUL_APPLICATION("application unsuccessful"),
    USING_YOUR_CARD("Using your card"),
    WHAT_YOU_CAN_BUY("What you can buy"),
    WHAT_YOU_GET("What you’ll get");

    private String pageName;
    private Optional<ToggleName> toggleName;

    PageName(String pageName) {
        this.pageName = pageName;
        this.toggleName = Optional.empty();
    }

    PageName(String pageName, ToggleName toggleName) {
        this.pageName = pageName;
        this.toggleName = Optional.of(toggleName);
    }

    public String getPageName() {
        return this.pageName;
    }

    public static PageName toPageName(String pageName) {
        return Arrays.stream(PageName.values())
                .filter(page -> pageName.equals(page.pageName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No page name found for name: " + pageName));
    }

    public boolean hasToggle() {
        return toggleName.isPresent();
    }

    public Optional<ToggleName> getToggle() {
        return toggleName;
    }
}
