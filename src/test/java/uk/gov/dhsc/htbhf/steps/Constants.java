package uk.gov.dhsc.htbhf.steps;

public class Constants {

    public static final String DOB_DAY = "30";
    public static final String DOB_MONTH = "12";
    public static final String DOB_YEAR = "1980";
    public static final String FIRST_NAME = "Lisa";
    public static final String LAST_NAME = "Simpson";
    public static final String ADDRESS_LINE_1 = "Flat b";
    public static final String ADDRESS_LINE_2 = "123 Fake street";
    public static final String TOWN = "Springfield";
    public static final String COUNTY = "Devon";
    public static final String POSTCODE = "AA11BB";
    public static final String PHONE_NUMBER = "07123456789";
    public static final String PHONE_NUMBER_2 = "07111111111";
    public static final String EMAIL_ADDRESS = "test@email.com";
    public static final String EMAIL_ADDRESS_2 = "different-email-address@email.com";

    public static final ActionOptions DEFAULT_ACTION_OPTIONS = ActionOptions.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .isClaimantPregnant(true)
            .addressLine1(ADDRESS_LINE_1)
            .addressLine2(ADDRESS_LINE_2)
            .townOrCity(TOWN)
            .county(COUNTY)
            .postcode(POSTCODE)
            .build();
}
