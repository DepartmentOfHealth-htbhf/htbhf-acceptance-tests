package uk.gov.dhsc.htbhf.browserstack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TestOutputHtmlGeneratorTest {

    private static final String REPORT_LOCATION = "test-report.html";

    @Test
    void shouldOutputReportFile() throws IOException {
        try {
            //Given
            List<TestResultSummary> resultSummaries = new ArrayList<>();
            TestExecutionSummary testExecutionSummary = setupMockTestExecutionSummary();
            resultSummaries.add(new TestResultSummary(testExecutionSummary, "Test1", 3, "kjhndfsd98rkbnjsdfh"));

            //When
            TestOutputHtmlGenerator.generateHtmlReport(resultSummaries, REPORT_LOCATION);

            //Then
            String reportContents = Files.readString(Path.of(REPORT_LOCATION));
            assertThat(reportContents).isNotBlank();
            assertThat(reportContents).startsWith("<!DOCTYPE html>");
            assertThat(reportContents).contains(
                    "<h1>Compatibility Test Summary</h1>",
                    "<td>Test1</td>",
                    "<td>true</td>",
                    "<td>true</td>",
                    "<td>1970-01-01 01:00:00</td>",
                    "<td>1970-01-01 01:00:10</td>",
                    "<td>00:09</td>",
                    "<td>3</td>",
                    "<td>kjhndfsd98rkbnjsdfh</td>"
            );
        } finally {
            Files.delete(Path.of(REPORT_LOCATION));
        }
    }

    private TestExecutionSummary setupMockTestExecutionSummary() {
        TestExecutionSummary testExecutionSummary = mock(TestExecutionSummary.class);
        given(testExecutionSummary.getFailures()).willReturn(Collections.emptyList());
        given(testExecutionSummary.getTimeStarted()).willReturn(100L);
        given(testExecutionSummary.getTimeFinished()).willReturn(10000L);
        given(testExecutionSummary.getTimeFinished()).willReturn(10000L);
        return testExecutionSummary;
    }

}
