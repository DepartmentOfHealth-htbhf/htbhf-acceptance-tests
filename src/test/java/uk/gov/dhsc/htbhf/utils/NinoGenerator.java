package uk.gov.dhsc.htbhf.utils;

import com.google.common.base.Strings;

/**
 * Utility methods to generate random NINOs.
 */
public class NinoGenerator {

    /**
     * Represents 1 child under 1 and 2 under 4 to make the value returned from the stub consistent
     */
    private static final String CHILDREN_NUMBER = "12";
    private static final String NINO_PREFIX_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ELIGIBLE_CHAR = "E";
    private static final String NINO_SUFFIX_CHARS = "ABC";

    public static String generateEligibleNino() {
        return randomEligibleTwoChars() + CHILDREN_NUMBER + randomFourDigitInteger() + randomCharAtoC();
    }

    private static String randomFourDigitInteger() {
        int number = (int) Math.floor((Math.random() * (9999)) + 1);
        return Strings.padStart(String.valueOf(number), 4, '0');
    }

    private static char randomCharFromChars(String allChars) {
        double randomCharPosition = Math.floor(Math.random() * allChars.length());
        return allChars.charAt((int) randomCharPosition);
    }

    /**
     * Generates a two character string where one character is always `E` to ensure an eligible
     * response is returned when mapping NINO to smart stub application
     */
    private static String randomEligibleTwoChars() {
        char randomChar = randomCharFromChars(NINO_PREFIX_CHARS);
        return Math.random() < 0.5 ? randomChar + ELIGIBLE_CHAR : ELIGIBLE_CHAR + randomChar;
    }

    /**
     * Generates a random character from A, B or C. D is not included as this is used
     * by performance tests.
     */
    private static char randomCharAtoC() {
        return randomCharFromChars(NINO_SUFFIX_CHARS);
    }
}
