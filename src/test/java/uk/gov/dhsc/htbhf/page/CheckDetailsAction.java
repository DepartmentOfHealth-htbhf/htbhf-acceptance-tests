package uk.gov.dhsc.htbhf.page;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CheckDetailsAction {
    private String url;
    private String text;
    private String hiddenText;
}
