package uk.gov.dhsc.htbhf;

public interface TestResultHandler {

    default void handleResults(TestResult testResult, String sessionId) {

    }
}
