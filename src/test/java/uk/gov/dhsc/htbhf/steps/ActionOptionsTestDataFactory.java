package uk.gov.dhsc.htbhf.steps;

import static uk.gov.dhsc.htbhf.steps.Constants.*;
import static uk.gov.dhsc.htbhf.utils.NinoGenerator.generateEligibleNino;

/**
 * Builds ActionOption instances containing the data to be used for navigating routes through the application.
 */
public class ActionOptionsTestDataFactory {

    public static ActionOptions buildDefaultActionOptions() {
        return buildDefaultActionOptionsBuilder().build();
    }

    public static ActionOptions buildActionOptionsWithNoAddressLine2() {
        return buildDefaultActionOptionsBuilder()
                .addressLine2("")
                .build();
    }

    public static ActionOptions buildActionOptionsWithNoCounty() {
        return buildDefaultActionOptionsBuilder()
                .county("")
                .build();
    }

    public static ActionOptions buildActionOptionsForPregnantWoman() {
        return buildDefaultActionOptionsBuilder()
                .isClaimantPregnant(true)
                .build();
    }

    public static ActionOptions buildActionOptionsWithMaliciousFirstName() {
        return buildDefaultActionOptionsBuilder()
                .firstName("<script>window.alert(\'Boo\')</script>")
                .build();
    }

    private static ActionOptions.ActionOptionsBuilder buildDefaultActionOptionsBuilder() {
        return ActionOptions.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isClaimantPregnant(false)
                .addressLine1(ADDRESS_LINE_1)
                .addressLine2(ADDRESS_LINE_2)
                .townOrCity(TOWN)
                .county(COUNTY)
                .postcode(POSTCODE)
                .nino(generateEligibleNino());
    }
}
