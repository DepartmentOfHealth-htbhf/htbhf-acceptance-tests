package uk.gov.dhsc.htbhf.utils;

import com.google.common.base.Strings;

import java.util.Random;

/**
 * Utility methods to generate random NINOs.
 */
public class NinoGenerator {

    /**
     * Represents 1 child under 1 to make the value returned from the stub consistent
     */
    private static final String CHILDREN_NUMBER = "11";
    private static final String NINO_PREFIX_CHARS = "ABCDEFGHIJKLMNOPRSTUVWYZ";
    private static final String ELIGIBLE_CHAR = "E";
    private static final String NINO_SUFFIX_CHARS = "ABC";
    private static final String NINO_REGEX = "^(?!BG|GB|NK|KN|TN|NT|ZZ)[A-CEGHJ-PR-TW-Z][A-CEGHJ-NPR-TW-Z](\\d{6})[A-D]$";

    private static final Random RANDOM = new Random();

    public static String generateEligibleNino() {
        String generatedNino;
        do {
            generatedNino = randomEligibleTwoChars() + CHILDREN_NUMBER + randomFourDigitInteger() + randomCharAtoC();
        } while (!generatedNino.matches(NINO_REGEX));
        return generatedNino;
    }

    private static String randomFourDigitInteger() {
        int number = RANDOM.nextInt(9999);
        return Strings.padStart(String.valueOf(number), 4, '0');
    }

    private static char randomCharFromChars(String allChars) {
        int randomCharPosition = RANDOM.nextInt(allChars.length());
        return allChars.charAt(randomCharPosition);
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
