package formal_testing.runner;

import formal_testing.ResourceMeasurement;
import formal_testing.TestCase;

import java.util.*;

/**
 * Created by buzhinsky on 7/4/17.
 */
public class RunnerResult {
    private TestCase testCase;
    private final Map<String, Boolean> outcomes = new LinkedHashMap<>();
    private final List<String> log = new ArrayList<>();
    private ResourceMeasurement measurement = new ResourceMeasurement();

    void set(TestCase testCase) {
        this.testCase = testCase;
    }

    public boolean found() {
        return testCase != null;
    }

    public ResourceMeasurement measurement() {
        return measurement;
    }

    public Map<String, Boolean> outcomes() {
        return Collections.unmodifiableMap(outcomes);
    }

    public TestCase testCase() {
        if (!found()) {
            throw new RuntimeException("No test case.");
        }
        return testCase;
    }

    void outcome(String property, boolean satisfied) {
        outcomes.put(property, satisfied);
    }

    void log(List<String> lines) {
        lines.stream().filter(ResourceMeasurement::isMeasurement).forEach(line ->
                measurement = measurement.add(new ResourceMeasurement(line, "verification run")));
        log.addAll(lines);
    }

    public List<String> log() {
        return Collections.unmodifiableList(log);
    }
}
