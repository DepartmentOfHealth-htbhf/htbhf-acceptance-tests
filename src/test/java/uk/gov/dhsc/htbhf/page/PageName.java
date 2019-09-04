package uk.gov.dhsc.htbhf.page;

import java.util.Arrays;

/**
 * Enum of all the page names
 */
public enum PageName {

    ARE_YOU_PREGNANT("are you pregnant"),
    CHECK_ANSWERS("check answers"),
    CHILD_DATE_OF_BIRTH("enter your childrens dates of birth"),
    CONFIRMATION("confirmation"),
    DATE_OF_BIRTH("enter date of birth"),
    DO_YOU_HAVE_CHILDREN("do you have children under four years old"),
    EMAIL_ADDRESS("email address"),
    ENTER_CODE("enter code"),
    MANUAL_ADDRESS("address"),
    NAME("enter name"),
    NATIONAL_INSURANCE_NUMBER("enter national insurance number"),
    PHONE_NUMBER("phone number"),
    SCOTLAND("do you live in Scotland"),
    SEND_CODE("send code"),
    TERMS_AND_CONDITIONS("terms and conditions"),
    HOW_IT_WORKS("How it works"),
    ELIGIBILITY("Eligibility"),
    WHAT_YOU_GET("What you’ll get"),
    WHAT_YOU_CAN_BUY("What you can buy"),
    USING_YOUR_CARD("Using your card"),
    APPLY("Apply for Healthy Start"),
    REPORT_A_CHANGE("Report a change");

    private String pageName;

    PageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return this.pageName;
    }

    public static PageName getPageName(String pageName) {
        return Arrays.stream(PageName.values())
                .filter(page -> pageName.equals(page.pageName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No page name found for name: " + pageName));
    }
}
