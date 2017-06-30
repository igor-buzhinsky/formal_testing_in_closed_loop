package formal_testing;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class TestSuite {
    private final Set<TestCase> testCases = new LinkedHashSet<>();

    public TestSuite(TestCase... testCases) {
        Arrays.stream(testCases).forEach(this.testCases::add);
    }

    public void add(TestCase testCase) {
        testCases.add(testCase);
    }

    @Override
    public String toString() {
        return String.join("\n", testCases.stream().map(Object::toString).collect(Collectors.toList()));
    }

    private boolean trivial() {
        return testCases.size() <= 1;
    }

    public String promelaHeader() {
        return "int _test_step;" + (trivial() ? "" : "\nint _test_index = -1;");
    }

    public String promelaBody(boolean addOracle) {
        // looping scenario
        final List<TestCase> list = new ArrayList<>(testCases);
        final StringBuilder sb = new StringBuilder();

        if (!trivial()) {
            sb.append("if\n");
            for (int i = 0; i < list.size(); i++) {
                sb.append(":: _test_index == -1 -> _test_index = ").append(i).append(";\n");
            }
            sb.append(":: else -> ;\n").append("fi\n");
        }

        sb.append("d_step {\n").append("    if\n");
        for (int i = 0; i < list.size(); i++) {
            final TestCase tc = list.get(i);
            for (int j = 0; j < tc.length(); j++) {
                sb.append("    :: ").append(trivial() ? "" : ("_test_index == " + i + " && "))
                        .append("_test_step == ").append(j).append(" -> ");
                for (String varName : tc.values().keySet()) {
                    sb.append(varName).append(" = ").append(tc.values().get(varName).get(j)).append("; ");
                }
                sb.append("\n");
            }
        }
        sb.append("    fi\n");

        if (trivial()) {
            sb.append("    _test_step = (_test_step + 1) % ").append(list.get(0).length()).append(";\n");
        } else {
            sb.append("    if\n");
            for (int i = 0; i < list.size(); i++) {
                sb.append("    :: _test_index == ").append(i).append(" -> _test_step = ")
                        .append("(_test_step + 1) % ").append(list.get(i).length()).append(";\n");
            }
            sb.append("    fi\n");
        }
        sb.append(addOracle ? "    _test_passed = (_test_step == 0 -> true : _test_passed);\n" : "")
                .append("}\n");
        return sb.toString();
    }

    public void print(String filename) throws FileNotFoundException {
        try (final PrintWriter pw = new PrintWriter(filename + ".header")) {
            pw.print(promelaHeader());
        }
        try (final PrintWriter pw = new PrintWriter(filename + ".body")) {
            pw.print(promelaBody(true));
        }
    }
}
