package formal_testing;

import formal_testing.variable.Variable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class TestCase {
    private final Map<String, List<String>> values = new LinkedHashMap<>();
    int length = 0;

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

    public String promelaHeader() {
        return "int _test_step;";
    }

    public String promelaBody() {
        // looping scenario
        final StringBuilder sb = new StringBuilder();
        sb.append("d_step {\n").append("    if\n");
        for (int i = 0; i < length; i++) {
            sb.append("    :: _test_step == ").append(i).append(" -> \n");
            for (String varName : values.keySet()) {
                sb.append(varName).append(" = ").append(values.get(varName).get(i)).append("; ");
            }
            sb.append("\n");
        }
        sb.append("    fi\n")
                .append("    _test_step = (_test_step + 1) % ").append(length).append(";\n")
                .append("    _test_passed = (_test_step == 0 -> true : _test_passed);\n")
                .append("}\n");
        return sb.toString();
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
