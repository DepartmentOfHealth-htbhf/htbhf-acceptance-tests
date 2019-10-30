package uk.gov.dhsc.htbhf.utils;

import uk.gov.dhsc.htbhf.steps.ClaimValues;

import java.time.LocalDate;

import static uk.gov.dhsc.htbhf.steps.Constants.*;
import static uk.gov.dhsc.htbhf.utils.DateUtils.formatYearMonthDay;

public class ClaimantRequestTestDataFactory {

    public static String buildClaimantRequestJson(ClaimValues claimValues) {
        //TODO MRS 20/09/2019: Move all values used to fill out form into ClaimValues so we can assert from there rather than Constants.
        String formattedClaimantDob = formatYearMonthDay(DOB_DAY, DOB_MONTH, DOB_YEAR);
        LocalDate dueDate = LocalDate.now().plusMonths(VALID_PREGNANCY_MONTH_INCREMENT);
        String childDob = formatYearMonthDay(LocalDate.now().minusYears(1));
        return "{" +
                "\"dateOfBirth\":\"" + formattedClaimantDob + "\"," +
                "\"childrenDob\":[\"" + childDob + "\"]," +
                "\"expectedDeliveryDate\":\"" + formatYearMonthDay(dueDate) + "\"," +
                "\"firstName\":\"" + claimValues.getFirstName() + "\"," +
                "\"lastName\":\"" + claimValues.getLastName() + "\"," +
                "\"nino\":\"" + claimValues.getNino() + "\"," +
                "\"address\":{" +
                "\"addressLine1\":\"" + claimValues.getAddressLine1() + "\"," +
                "\"addressLine2\":\"" + claimValues.getAddressLine2() + "\"," +
                "\"townOrCity\":\"" + claimValues.getTownOrCity() + "\"," +
                "\"county\":\"" + claimValues.getCounty() + "\"," +
                "\"postcode\":\"" + claimValues.getPostcode() + "\"" +
                "}," +
                "\"phoneNumber\":\"" + FORMATTED_PHONE_NUMBER + "\"," +
                "\"emailAddress\":\"" + EMAIL_ADDRESS + "\"" +
                "}";
    }
}
