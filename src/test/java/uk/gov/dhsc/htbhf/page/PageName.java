package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;
import java.util.Optional;

import static uk.gov.dhsc.htbhf.page.ToggleName.ADDRESS_LOOKUP;

/**
 * Enum of all the page names
 */
public enum PageName {

    ARE_YOU_PREGNANT("are you pregnant"),
    CHECK_ANSWERS("check answers"),
    CHILD_DATE_OF_BIRTH("enter your childrens dates of birth"),
    CONFIRMATION("confirmation"),
    CONFIRM_UPDATED("confirm updated"),
    DATE_OF_BIRTH("enter date of birth"),
    DO_YOU_HAVE_CHILDREN("do you have children under four years old"),
    EMAIL_ADDRESS("email address"),
    ENTER_CODE("enter code"),
    POSTCODE("postcode", ADDRESS_LOOKUP),
    SELECT_ADDRESS("select address", ADDRESS_LOOKUP),
    MANUAL_ADDRESS("manual address"),
    NAME("enter name"),
    NATIONAL_INSURANCE_NUMBER("enter national insurance number"),
    PHONE_NUMBER("phone number"),
    SCOTLAND("do you live in Scotland"),
    IN_SCOTLAND("I live in Scotland"),
    SEND_CODE("send code"),
    TERMS_AND_CONDITIONS("terms and conditions"),
    HOW_IT_WORKS("How it works"),
    ELIGIBILITY("Eligibility"),
    WHAT_YOU_GET("What you’ll get"),
    WHAT_YOU_CAN_BUY("What you can buy"),
    USING_YOUR_CARD("Using your card"),
    APPLY("Apply for Healthy Start"),
    REPORT_A_CHANGE("Report a change"),
    COOKIES("cookies"),
    SERVER_ERROR("Server Error"),
    PRIVACY_NOTICE("privacy notice");

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
