package uk.gov.dhsc.htbhf.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public static String getFormattedDateInMonths(int plusMonths) {
        LocalDate date = LocalDate.now().plusMonths(plusMonths);
        return date.format(DATE_TIME_FORMATTER);
    }

    public static String getFormattedDateLastYear() {
        LocalDate date = LocalDate.now().minusYears(1);
        return date.format(DATE_TIME_FORMATTER);
    }

}
