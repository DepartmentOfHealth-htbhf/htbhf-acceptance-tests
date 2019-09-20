package uk.gov.dhsc.htbhf.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void shouldFormatDate() {
        //Given
        String day = "1";
        String month = "5";
        String year = "1978";
        //When
        String formattedDate = DateUtils.formatYearMonthDay(day, month, year);
        //Then
        assertThat(formattedDate).isEqualTo("1978-05-01");
    }

}
