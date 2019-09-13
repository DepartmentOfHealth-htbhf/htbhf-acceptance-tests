package uk.gov.dhsc.htbhf.steps;

import static uk.gov.dhsc.htbhf.steps.Constants.*;
import static uk.gov.dhsc.htbhf.utils.NinoGenerator.generateEligibleNino;

/**
 * Builds ClaimValues instances containing the data to be used for navigating routes through the application.
 */
public class ClaimValuesTestDataFactory {

    public static ClaimValues buildDefaultClaimValues() {
        return buildDefaultClaimValuesBuilder().build();
    }

    public static ClaimValues buildClaimValuesWithNoAddressLine2() {
        return buildDefaultClaimValuesBuilder()
                .addressLine2("")
                .build();
    }

    public static ClaimValues buildClaimValuesWithNoCounty() {
        return buildDefaultClaimValuesBuilder()
                .county("")
                .build();
    }

    public static ClaimValues buildClaimValuesForPregnantWoman() {
        return buildDefaultClaimValuesBuilder()
                .isClaimantPregnant(true)
                .build();
    }

    public static ClaimValues buildClaimValuesWithMaliciousFirstName() {
        return buildDefaultClaimValuesBuilder()
                .firstName("<script>window.alert(\'Boo\')</script>")
                .build();
    }

    private static ClaimValues.ClaimValuesBuilder buildDefaultClaimValuesBuilder() {
        return ClaimValues.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .isClaimantPregnant(false)
                .addressLine1(ADDRESS_LINE_1)
                .addressLine2(ADDRESS_LINE_2)
                .townOrCity(TOWN)
                .county(COUNTY)
                .postcode(POSTCODE)
                .nino(generateEligibleNino())
                .selectAddress(true);
    }
}
