package formal_testing;

import formal_testing.variable.Variable;

import java.io.Serializable;
import java.util.*;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class TestCase implements Serializable {
    private final Map<String, List<String>> values = new LinkedHashMap<>();
    private int length = 0;

    public int length() {
        return length;
    }

    public Map<String, List<String>> values() {
        return Collections.unmodifiableMap(values);
    }

    public TestCase(Configuration conf) {
        for (Variable v : conf.nondetVars) {
            values.put(v.indexedName(), new ArrayList<>());
        }
    }

    public void addValue(String varName, String value) {
        final List<String> varValues = values.get(varName);
        varValues.add(value);
        length = Math.max(length, varValues.size());
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public String promelaHeader(boolean addOracle) {
        return new TestSuite(addOracle, this).promelaHeader();
    }

    public void validate() {
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            if (entry.getValue().size() != length) {
                throw new RuntimeException("The supposed length is " + length + ", but the test case is " + toString());
            }
        }
    }

    public String promelaBody(boolean addOracle) {
        return new TestSuite(addOracle, this).promelaBody();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return values.equals(testCase.values);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
