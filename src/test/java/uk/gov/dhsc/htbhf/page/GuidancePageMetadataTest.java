package uk.gov.dhsc.htbhf.page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class GuidancePageMetadataTest {

    @CsvSource({"How it works, HOW_IT_WORKS",
            "Eligibility, ELIGIBILITY",
            "What you’ll get, WHAT_YOU_GET",
            "What you can buy, WHAT_YOU_CAN_BUY",
            "Using your card, USING_YOUR_CARD",
            "Apply for Healthy Start, APPLY",
            "Report a change, REPORT_A_CHANGE"
    })
    @ParameterizedTest
    void shouldFindByName(String pageName, GuidancePageMetadata expectedGuidancePageMetadata) {
        assertThat(GuidancePageMetadata.findByName(pageName)).isEqualTo(expectedGuidancePageMetadata);
    }

    @Test
    void shouldThrowErrorWhenPageNotFoundByName() {
        IllegalArgumentException exception = catchThrowableOfType(() -> GuidancePageMetadata.findByName("foo"), IllegalArgumentException.class);
        assertThat(exception).hasMessage("No page metadata found for page with name: foo");
    }

    @Test
    void shouldGetFirst() {
        assertThat(GuidancePageMetadata.getFirst()).isEqualTo(GuidancePageMetadata.HOW_IT_WORKS);
    }

    @Test
    void shouldGetLast() {
        assertThat(GuidancePageMetadata.getLast()).isEqualTo(GuidancePageMetadata.REPORT_A_CHANGE);
    }

    @CsvSource({"0, HOW_IT_WORKS",
            "1, ELIGIBILITY",
            "2, WHAT_YOU_GET",
            "3, WHAT_YOU_CAN_BUY",
            "4, USING_YOUR_CARD",
            "5, APPLY",
            "6, REPORT_A_CHANGE"
    })
    @ParameterizedTest
    void shouldGetPageByOrder(int order, GuidancePageMetadata expectedPage) {
        assertThat(GuidancePageMetadata.getPageByOrder(order)).isEqualTo(expectedPage);
    }

    @Test
    void shouldThrowErrorWhenPageNotFoundByIndex() {
        IllegalArgumentException exception = catchThrowableOfType(() -> GuidancePageMetadata.getPageByOrder(345), IllegalArgumentException.class);
        assertThat(exception).hasMessage("No page metadata found for page with order: 345");
    }
}
