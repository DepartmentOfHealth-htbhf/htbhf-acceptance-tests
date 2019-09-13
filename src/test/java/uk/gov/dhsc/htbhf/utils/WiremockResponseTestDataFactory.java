package uk.gov.dhsc.htbhf.utils;

/**
 * Factory for test data coming back from WireMock imitating the ClaimantService.
 */
public class WiremockResponseTestDataFactory {

    public static String aValidClaimResponseWithVoucherEntitlement(String eligibilityStatus) {
        return "{\"claimStatus\": \"NEW\", " +
                "\"eligibilityStatus\": \"" + eligibilityStatus + "\", " +
                "\"voucherEntitlement\": " + aValidVoucherEntitlement() +
                "}";
    }

    public static String aValidClaimResponseWithoutVoucherEntitlement(String eligibilityStatus) {
        return "{\"claimStatus\": \"NEW\"," +
                "\"eligibilityStatus\": \"" + eligibilityStatus + "\"" +
                "}";
    }

    public static String aValidVoucherEntitlement() {
        return "{\"vouchersForChildrenUnderOne\": 2, " +
                "\"vouchersForChildrenBetweenOneAndFour\": 1, " +
                "\"vouchersForPregnancy\": 1, " +
                "\"totalVoucherEntitlement\": 4, " +
                "\"singleVoucherValueInPence\": 310, " +
                "\"totalVoucherValueInPence\": 1240" +
                "}";
    }

    public static String aPostcodeLookupResponseWithResults(String postcode) {
        return "{\n" +
                "  \"header\" : {\n" +
                "    \"uri\" : \"https://api.ordnancesurvey.co.uk/places/v1/addresses/postcode?postcode=" + postcode + "\",\n" +
                "    \"query\" : \"postcode=" + postcode + "\",\n" +
                "    \"offset\" : 0,\n" +
                "    \"totalresults\" : 44,\n" +
                "    \"format\" : \"JSON\",\n" +
                "    \"dataset\" : \"DPA\",\n" +
                "    \"lr\" : \"EN,CY\",\n" +
                "    \"maxresults\" : 100,\n" +
                "    \"epoch\" : \"67\",\n" +
                "    \"output_srs\" : \"EPSG:27700\"\n" +
                "  },\n" +
                "  \"results\" : [ {\n" +
                "    \"DPA\" : {\n" +
                "      \"UPRN\" : \"321323\",\n" +
                "      \"UDPRN\" : \"51094465\",\n" +
                "      \"ADDRESS\" : \"113 RATCLIFFE COURT, CHIMNEY STEPS, BRISTOL, " + postcode + "\",\n" +
                "      \"BUILDING_NAME\" : \"113 RATCLIFFE COURT\",\n" +
                "      \"THOROUGHFARE_NAME\" : \"CHIMNEY STEPS\",\n" +
                "      \"POST_TOWN\" : \"BRISTOL\",\n" +
                "      \"POSTCODE\" : \"" + postcode + "\",\n" +
                "      \"RPC\" : \"2\",\n" +
                "      \"X_COORDINATE\" : 359913.0,\n" +
                "      \"Y_COORDINATE\" : 172758.0,\n" +
                "      \"STATUS\" : \"APPROVED\",\n" +
                "      \"LOGICAL_STATUS_CODE\" : \"1\",\n" +
                "      \"CLASSIFICATION_CODE\" : \"RD06\",\n" +
                "      \"CLASSIFICATION_CODE_DESCRIPTION\" : \"Self Contained Flat (Includes Maisonette / Apartment)\",\n" +
                "      \"LOCAL_CUSTODIAN_CODE\" : 116,\n" +
                "      \"LOCAL_CUSTODIAN_CODE_DESCRIPTION\" : \"BRISTOL CITY\",\n" +
                "      \"POSTAL_ADDRESS_CODE\" : \"D\",\n" +
                "      \"POSTAL_ADDRESS_CODE_DESCRIPTION\" : \"A record which is linked to PAF\",\n" +
                "      \"BLPU_STATE_CODE\" : \"2\",\n" +
                "      \"BLPU_STATE_CODE_DESCRIPTION\" : \"In use\",\n" +
                "      \"TOPOGRAPHY_LAYER_TOID\" : \"osgb1000002529045129\",\n" +
                "      \"PARENT_UPRN\" : \"321210\",\n" +
                "      \"LAST_UPDATE_DATE\" : \"10/02/2016\",\n" +
                "      \"ENTRY_DATE\" : \"19/05/2008\",\n" +
                "      \"BLPU_STATE_DATE\" : \"19/05/2008\",\n" +
                "      \"LANGUAGE\" : \"EN\",\n" +
                "      \"MATCH\" : 1.0,\n" +
                "      \"MATCH_DESCRIPTION\" : \"EXACT\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"DPA\" : {\n" +
                "      \"UPRN\" : \"321324\",\n" +
                "      \"UDPRN\" : \"51094466\",\n" +
                "      \"ADDRESS\" : \"114 RATCLIFFE COURT, CHIMNEY STEPS, BRISTOL, " + postcode + "\",\n" +
                "      \"BUILDING_NAME\" : \"114 RATCLIFFE COURT\",\n" +
                "      \"THOROUGHFARE_NAME\" : \"CHIMNEY STEPS\",\n" +
                "      \"POST_TOWN\" : \"BRISTOL\",\n" +
                "      \"POSTCODE\" : \"" + postcode + "\",\n" +
                "      \"RPC\" : \"2\",\n" +
                "      \"X_COORDINATE\" : 359913.0,\n" +
                "      \"Y_COORDINATE\" : 172758.0,\n" +
                "      \"STATUS\" : \"APPROVED\",\n" +
                "      \"LOGICAL_STATUS_CODE\" : \"1\",\n" +
                "      \"CLASSIFICATION_CODE\" : \"RD06\",\n" +
                "      \"CLASSIFICATION_CODE_DESCRIPTION\" : \"Self Contained Flat (Includes Maisonette / Apartment)\",\n" +
                "      \"LOCAL_CUSTODIAN_CODE\" : 116,\n" +
                "      \"LOCAL_CUSTODIAN_CODE_DESCRIPTION\" : \"BRISTOL CITY\",\n" +
                "      \"POSTAL_ADDRESS_CODE\" : \"D\",\n" +
                "      \"POSTAL_ADDRESS_CODE_DESCRIPTION\" : \"A record which is linked to PAF\",\n" +
                "      \"BLPU_STATE_CODE\" : \"2\",\n" +
                "      \"BLPU_STATE_CODE_DESCRIPTION\" : \"In use\",\n" +
                "      \"TOPOGRAPHY_LAYER_TOID\" : \"osgb1000002529045129\",\n" +
                "      \"PARENT_UPRN\" : \"321210\",\n" +
                "      \"LAST_UPDATE_DATE\" : \"10/02/2016\",\n" +
                "      \"ENTRY_DATE\" : \"19/05/2008\",\n" +
                "      \"BLPU_STATE_DATE\" : \"19/05/2008\",\n" +
                "      \"LANGUAGE\" : \"EN\",\n" +
                "      \"MATCH\" : 1.0,\n" +
                "      \"MATCH_DESCRIPTION\" : \"EXACT\"\n" +
                "    }\n" +
                "  }\n" +
                "  ]\n" +
                "}\n" +
                "  ";
    }
}
