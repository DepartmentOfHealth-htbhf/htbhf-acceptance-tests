package uk.gov.dhsc.htbhf.browserstack;

import lombok.Data;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The summary of an individual compatibility test run
 */
@Data
public class TestResultSummary {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String testName;
    private boolean passed;
    private LocalDateTime startedTime;
    private String formattedStartTime;
    private LocalDateTime finishedTime;
    private String formattedEndTime;
    private String duration;
    private int attempts;
    private Throwable failure;
    private String sessionId;

    public TestResultSummary(TestExecutionSummary testExecutionSummary, String testName, int attempts, String sessionId) {
        this.testName = testName;
        this.passed = testExecutionSummary.getFailures().isEmpty();
        this.startedTime = toLocalDateTime(testExecutionSummary.getTimeStarted());
        this.formattedStartTime = startedTime.format(DATE_TIME_FORMATTER);
        this.finishedTime = toLocalDateTime(testExecutionSummary.getTimeFinished());
        this.formattedEndTime = finishedTime.format(DATE_TIME_FORMATTER);
        long durationLong = testExecutionSummary.getTimeFinished() - testExecutionSummary.getTimeStarted();
        this.duration = DurationFormatUtils.formatDuration(durationLong, "mm:ss", true);
        this.failure = passed ? null : testExecutionSummary.getFailures().get(0).getException();
        this.attempts = attempts;
        this.sessionId = sessionId;
    }

    private LocalDateTime toLocalDateTime(long timeMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault());
    }

}
