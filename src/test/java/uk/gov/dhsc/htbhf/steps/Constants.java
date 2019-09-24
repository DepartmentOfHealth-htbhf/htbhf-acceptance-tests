package uk.gov.dhsc.htbhf.steps;

import java.nio.CharBuffer;

public class Constants {

    public static final String DOB_DAY = "30";
    public static final String DOB_MONTH = "12";
    public static final String DOB_YEAR = "1980";
    public static final String DATE_OF_BIRTH = DOB_DAY + " December " + DOB_YEAR;
    public static final String FIRST_NAME = "Lisa";
    public static final String LAST_NAME = "Simpson";
    public static final String FULL_NAME = FIRST_NAME + " " + LAST_NAME;
    public static final String ADDRESS_LINE_1 = "Flat B";
    public static final String ADDRESS_LINE_2 = "123 Fake Street";
    public static final String TOWN = "Springfield";
    public static final String COUNTY = "Devon";
    public static final String POSTCODE = "AA11BB";
    public static final String POSTCODE_WITH_NO_RESULTS = "BS11AA";
    public static final String FULL_ADDRESS = ADDRESS_LINE_1 + "\n" + ADDRESS_LINE_2 + "\n" + TOWN + "\n" + COUNTY + "\n" + POSTCODE;
    public static final String FULL_ADDRESS_NO_LINE_2 = ADDRESS_LINE_1 + "\n" + TOWN + "\n" + COUNTY + "\n" + POSTCODE;
    public static final String FULL_ADDRESS_NO_COUNTY = ADDRESS_LINE_1 + "\n" + ADDRESS_LINE_2 + "\n" + TOWN + "\n" + POSTCODE;
    public static final String PHONE_NUMBER = "07123456789";
    public static final String FORMATTED_PHONE_NUMBER = "+447123456789";
    public static final String PHONE_NUMBER_2 = "07111111111";
    public static final String EMAIL_ADDRESS = "test@email.com";
    public static final String EMAIL_ADDRESS_2 = "different-email-address@email.com";
    public static final String LONG_STRING = CharBuffer.allocate(501).toString().replace('\0', 'A');
    public static final int VALID_PREGNANCY_MONTH_INCREMENT = 6;
}
