package uk.gov.dhsc.htbhf.page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class GuidancePageMetadataTest {

    @CsvSource({"HOW_IT_WORKS, HOW_IT_WORKS",
            "ELIGIBILITY, ELIGIBILITY",
            "WHAT_YOU_GET, WHAT_YOU_GET",
            "WHAT_YOU_CAN_BUY, WHAT_YOU_CAN_BUY",
            "USING_YOUR_CARD, USING_YOUR_CARD",
            "APPLY, APPLY",
            "REPORT_A_CHANGE, REPORT_A_CHANGE"
    })
    @ParameterizedTest
    void shouldFindByName(PageName pageName, GuidancePageMetadata expectedGuidancePageMetadata) {
        assertThat(GuidancePageMetadata.findByName(pageName)).isEqualTo(expectedGuidancePageMetadata);
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
