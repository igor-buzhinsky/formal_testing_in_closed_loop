package formal_testing;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class TestSuite {
    private final Set<TestCase> testCases = new LinkedHashSet<>();

    public void add(TestCase testCase) {
        testCases.add(testCase);
    }

    @Override
    public String toString() {
        return String.join("\n", testCases.stream().map(Object::toString).collect(Collectors.toList()));
    }
}
