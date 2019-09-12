package uk.gov.dhsc.htbhf.steps;

import lombok.Builder;
import lombok.Data;

/**
 * Values that can be given to a page action to alter the default behaviour
 */
@Data
@Builder
public class ClaimValues {
    private boolean isClaimantPregnant;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String townOrCity;
    private String county;
    private String postcode;
    private String nino;
}
