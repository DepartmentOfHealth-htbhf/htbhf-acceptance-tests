package uk.gov.dhsc.htbhf;

import io.cucumber.core.api.Scenario;
import lombok.Data;

@Data
public class TestResult {
    private String name;
    private String status;

    public TestResult(Scenario scenario) {
        this.name = scenario.getName();
        this.status = scenario.isFailed() ? "failed" : "passed";
    }
}
