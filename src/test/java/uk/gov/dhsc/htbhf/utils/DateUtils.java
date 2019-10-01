package uk.gov.dhsc.htbhf.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter REVERSE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getFormattedDateInMonths(int plusMonths) {
        LocalDate date = LocalDate.now().plusMonths(plusMonths);
        return date.format(DATE_TIME_FORMATTER);
    }

    public static String getFormattedDateLastYear() {
        LocalDate date = LocalDate.now().minusYears(1);
        return date.format(DATE_TIME_FORMATTER);
    }

    /**
     * Formats the given Strings as yyyy-mm-dd where the month and day will be padded out.
     */
    public static String formatYearMonthDay(String day, String month, String year) {
        return year + "-" + StringUtils.leftPad(month, 2, "0") + "-" + StringUtils.leftPad(day, 2, "0");
    }

    /**
     * Formats the given date as yyyy-mm-dd.
     */
    public static String formatYearMonthDay(LocalDate dateToFormat) {
        return dateToFormat.format(REVERSE_DATE_FORMATTER);
    }

}
