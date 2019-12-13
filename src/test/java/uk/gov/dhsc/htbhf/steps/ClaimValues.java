package uk.gov.dhsc.htbhf.steps;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private boolean selectAddress;
    @Builder.Default
    private List<LocalDate> childrenDob = new ArrayList<>();

    public void addChildDob(LocalDate dob) {
        this.childrenDob.add(dob);
    }
}
