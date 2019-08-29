package uk.gov.dhsc.htbhf.browserstack;

import lombok.Data;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class TestResultSummary {
    private String testName;
    private boolean passed;
    private LocalDateTime startedTime;
    private LocalDateTime finishedTime;
    private String duration;
    private int attempts;
    private Throwable failure;
    private String sessionId;

    public TestResultSummary(TestExecutionSummary testExecutionSummary, String testName, int attempts, String sessionId) {
        this.testName = testName;
        this.passed = testExecutionSummary.getFailures().isEmpty();
        this.startedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(testExecutionSummary.getTimeStarted()), ZoneId.systemDefault());
        this.finishedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(testExecutionSummary.getTimeFinished()), ZoneId.systemDefault());
        long durationLong = testExecutionSummary.getTimeFinished() - testExecutionSummary.getTimeStarted();
        this.duration = DurationFormatUtils.formatDuration(durationLong, "mm:ss", true);
        this.failure = passed ? null : testExecutionSummary.getFailures().get(0).getException();
        this.attempts = attempts;
        this.sessionId = sessionId;
    }
}
