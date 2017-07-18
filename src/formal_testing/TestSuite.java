package formal_testing;

import formal_testing.enums.Language;
import formal_testing.value.Value;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class TestSuite implements Serializable {
    private final Set<TestCase> testCases = new LinkedHashSet<>();
    private final boolean addOracle;

    public int size() {
        return testCases.size();
    }

    public int totalLength() {
        return testCases.stream().mapToInt(TestCase::length).sum();
    }

    public TestSuite(boolean addOracle, TestCase... testCases) {
        this.addOracle = addOracle;
        Arrays.stream(testCases).forEach(this.testCases::add);
    }

    private void lengthReorder() {
        final ArrayList<TestCase> ordered = new ArrayList<>(testCases);
        Collections.sort(ordered, (o1, o2) -> o1.length() - o2.length());
        testCases.clear();
        testCases.addAll(ordered);
    }

    public Collection<TestCase> testCases() {
        return Collections.unmodifiableSet(testCases);
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

    private int maxLength() {
        return testCases.stream().mapToInt(TestCase::length).max().getAsInt();
    }

    public String header() {
        return Settings.LANGUAGE == Language.PROMELA ? promelaHeader() : nuSMVHeader();
    }

    public String body(Configuration conf) {
        return Settings.LANGUAGE == Language.PROMELA ? promelaBody(conf) : nuSMVBody();
    }

    private String promelaHeader() {
        return "int _test_step;" + (trivial() ? "" : "\nint _test_index = -1;");
    }

    private String nuSMVHeader() {
        return "VAR\n    _test_step: -1.." + (maxLength() - 1) + ";\n    _test_index: -1.." + (testCases.size() - 1) +
                ";\nASSIGN\n    init(_test_step) := -1;\n    init(_test_index) := -1;";
    }

    private String nuSMVBody() {
        final StringBuilder sb = new StringBuilder();
        final List<TestCase> list = new ArrayList<>(testCases);
        sb.append("ASSIGN\n");
        if (trivial()) {
            sb.append("    next(_test_index) := 0;\n");
        } else {
            sb.append("    next(_test_index) := _test_index = -1 ? 0..").append(testCases.size() - 1)
                    .append(" : _test_index;\n");
        }

        for (String varName : list.get(0).values().keySet()) {
            sb.append("    next(").append(varName).append(") := case\n");
            final Map<String, Map<Integer, Set<Integer>>> buckets = valueBuckets(list, varName);
            final List<String> allValues = new ArrayList<>(buckets.keySet());
            for (Map.Entry<String, Map<Integer, Set<Integer>>> entry : buckets.entrySet()) {
                final String value = entry.getKey();
                final Map<Integer, Set<Integer>> bucket = entry.getValue();
                if (bucket.isEmpty()) {
                    continue;
                }
                final String condition = value.equals(allValues.get(allValues.size() - 1)) ? "TRUE"
                        : String.join("\n            | ", bucket.entrySet().stream()
                        .map(e -> (trivial() ? "" : ("next(_test_index) = " + e.getKey() + " & "))
                                + "next(_test_step) in "
                                + Util.expressWithIntervalsNuSMV(e.getValue())).collect(Collectors.toList()));
                sb.append("        ").append(condition).append(": ").append(value).append(";\n");
            }
            sb.append("    esac;\n");
        }
        sb.append("    next(_test_step) := (_test_step + 1) mod (case\n");
        final Map<Integer, Set<Integer>> lengthBuckets = lengthBuckets(list);
        final List<Integer> allLengths = new ArrayList<>(lengthBuckets.keySet());
        for (Map.Entry<Integer, Set<Integer>> e : lengthBuckets.entrySet()) {
            final int length = e.getKey();
            final String condition = length == allLengths.get(allLengths.size() - 1)
                ? "TRUE" : ("next(_test_index) in " + Util.expressWithIntervalsNuSMV(e.getValue()));
            sb.append("        ").append(condition).append(": ").append(e.getKey()).append(";\n");
        }

        sb.append("    esac);\n");

        if (addOracle) {
            sb.append("    next(_test_passed) := next(_test_step) = 0 ? TRUE : _test_passed;\n");
        }

        return sb.toString();
    }

    private static final boolean ALTERNATIVE_PROMELA_UPDATES = false;
    private static final boolean LOGARITHMIC_LENGTH_EXPANSION_IN_PROMELA = false;
    private static final boolean LARGE_D_STEPS = false;

    private String promelaBody(Configuration conf) {
        // looping scenario
        final StringBuilder sb = new StringBuilder();
        final List<TestCase> list = new ArrayList<>(testCases);

        if (!trivial()) {
            sb.append("if\n:: _test_index == -1 ->\n    if\n");
            for (int i = 0; i < list.size(); i++) {
                sb.append("    :: _test_index = ").append(i).append(";\n");
            }
            sb.append("    fi\n:: else -> ;\n").append("fi\n");
        }

        if (ALTERNATIVE_PROMELA_UPDATES) {
            for (String varName : list.get(0).values().keySet()) {
                sb.append("if\n");
                final Map<String, Map<Integer, Set<Integer>>> buckets = valueBuckets(list, varName);
                final List<String> allValues = new ArrayList<>(buckets.keySet());
                for (Map.Entry<String, Map<Integer, Set<Integer>>> entry : buckets.entrySet()) {
                    final String value = entry.getKey();
                    final Map<Integer, Set<Integer>> bucket = entry.getValue();
                    if (bucket.isEmpty()) {
                        continue;
                    }
                    final String condition = value.equals(allValues.get(allValues.size() - 1)) ? "else"
                            : String.join(" || ", bucket.entrySet().stream()
                            .map(e -> (trivial() ? "" : ("_test_index == " + e.getKey() + " && ("))
                                    + Util.expressWithIntervalsSPIN(e.getValue(), "_test_step") + ")")
                            .collect(Collectors.toList()));
                    sb.append(":: ").append(condition).append(" -> ").append(varName).append(" = ").append(value)
                            .append(";\n");
                }
                sb.append("fi\n");
            }
        } else {
            final List<String> strTests = new ArrayList<>();
            for (TestCase tc : list) {
                final StringBuilder testBuilder = new StringBuilder();

                if (LOGARITHMIC_LENGTH_EXPANSION_IN_PROMELA) {
                    final List<String> parts = new ArrayList<>();
                    for (int j = 0; j < tc.length(); j++) {
                        final List<String> updates = new ArrayList<>();
                        for (String varName : tc.values().keySet()) {
                            final List<Value> values = tc.values().get(varName);
                            final String initialValue = conf.byName(varName).initialValue().toString();
                            final String lastValue = values.get(tc.length() - 1).toString();
                            final String value = values.get(j).toString();
                            final boolean omitInitial = j == 0 && value.equals(initialValue) && value.equals(lastValue);
                            final boolean omitUsual = j > 0 && value.equals(values.get(j - 1).toString());
                            if (!omitInitial && !omitUsual) {
                                updates.add(varName + " = " + value + "; ");
                            }
                        }
                        final String strUpdates = updates.size() > 1 ? ("d_step { " + String.join("", updates) + "}")
                                : updates.size() == 1 ? updates.get(0) : ";";
                        parts.add(strUpdates);
                    }
                    logarithmicValueChoice(testBuilder, parts, "_test_step");
                } else {
                    final Map<String, Set<Integer>> buckets = new TreeMap<>();
                    for (int j = 0; j < tc.length(); j++) {
                        final List<String> updates = new ArrayList<>();
                        for (String varName : tc.values().keySet()) {
                            final List<Value> values = tc.values().get(varName);
                            final String initialValue = conf.byName(varName).initialValue().toString();
                            final String lastValue = values.get(tc.length() - 1).toString();
                            final String value = values.get(j).toString();
                            final boolean omitInitial = j == 0 && value.equals(initialValue) && value.equals(lastValue);
                            final boolean omitUsual = j > 0 && value.equals(values.get(j - 1).toString());
                            if (!omitInitial && !omitUsual) {
                                updates.add(varName + " = " + value + "; ");
                            }
                        }
                        final String strUpdates = updates.size() > 1 ? ((LARGE_D_STEPS ? "" : "d_step { ")
                                + String.join("", updates) + (LARGE_D_STEPS ? "" : "}"))
                                : updates.size() == 1 ? updates.get(0) : ";";
                        Set<Integer> steps = buckets.get(strUpdates);
                        if (steps == null) {
                            steps = new TreeSet<>();
                            buckets.put(strUpdates, steps);
                        }
                        steps.add(j);
                    }

                    final List<String> conditions = new ArrayList<>();
                    final List<String> actions = new ArrayList<>();
                    for (Map.Entry<String, Set<Integer>> entry : buckets.entrySet()) {
                        final String updates = entry.getKey();
                        final Set<Integer> steps = entry.getValue();
                        conditions.add(Util.expressWithIntervalsSPIN(steps, "_test_step"));
                        actions.add(updates);
                    }
                    conditions.set(conditions.size() - 1, "else");
                    testBuilder.append((LARGE_D_STEPS ? "d_step { " : "") + "if\n");
                    for (int j = 0; j < conditions.size(); j++) {
                        testBuilder.append(":: ").append(conditions.get(j)).append(" -> ").append(actions.get(j))
                                .append("\n");
                    }
                    testBuilder.append("fi" + (LARGE_D_STEPS ? " }" : "") + "\n");
                }
                strTests.add(testBuilder.toString());
            }
            logarithmicValueChoice(sb, strTests, "_test_index");
        }
        /*sb.append("d_step {\n");
        if (trivial()) {
            sb.append("    _test_step = (_test_step + 1) % ").append(list.get(0).length()).append(";\n");
        } else {
            sb.append("    if\n");
            final Map<Integer, Set<Integer>> lengthBuckets = lengthBuckets(list);
            final List<Integer> allLengths = new ArrayList<>(lengthBuckets.keySet());
            for (Map.Entry<Integer, Set<Integer>> e : lengthBuckets.entrySet()) {
                final int length = e.getKey();
                final String condition = length == allLengths.get(allLengths.size() - 1) ? "else"
                        : Util.expressWithIntervalsSPIN(e.getValue(), "_test_index");
                sb.append("    :: ").append(condition).append(" -> _test_step = (_test_step + 1) % ")
                        .append(e.getKey()).append(";\n");
            }
            sb.append("    fi\n");
        }
        sb.append(addOracle ? "    _test_passed = (_test_step == 0 -> 1 : _test_passed);\n" : "")
                .append("}\n");*/
        sb.append("c_code {\n");
        sb.append("    now._test_step++;\n");
        if (trivial()) {
            sb.append("    now._test_step %= ").append(list.get(0).length()).append(";\n");
        } else {
            sb.append("    switch (now._test_index) {\n");
            final Map<Integer, Set<Integer>> lengthBuckets = lengthBuckets(list);
            for (Map.Entry<Integer, Set<Integer>> e : lengthBuckets.entrySet()) {
                sb.append("        ");
                final int length = e.getKey();
                for (int index : e.getValue()) {
                    sb.append("case ").append(index).append(": ");
                }
                sb.append("now._test_step %= ").append(length).append("; break;\n");
            }
            sb.append("    }\n");
        }
        sb.append(addOracle ? "    now._test_passed |= now._test_step == 0;\n" : "")
                .append("}\n");
        return sb.toString();
    }

    private void logarithmicValueChoice(StringBuilder sb, List<String> innerParts, String varName) {
        logarithmicValueChoice(sb, innerParts, varName, 0, innerParts.size(), 0);
    }

    private void logarithmicValueChoice(StringBuilder sb, List<String> innerParts, String varName, int start, int end,
                                        int depth) {
        final String indent = String.join("", Collections.nCopies(depth, "    "));
        if (end - start == 1) {
            sb.append(Util.indent(innerParts.get(start), indent)).append("\n");
        } else {
            final int mid = (end + start) / 2;
            sb.append(indent).append("if\n");
            sb.append(indent).append(":: ").append(varName).append(" < ").append(mid).append(" ->\n");
            logarithmicValueChoice(sb, innerParts, varName, start, mid, depth + 1);
            sb.append(indent).append(":: else ->\n");
            logarithmicValueChoice(sb, innerParts, varName, mid, end, depth + 1);
            sb.append(indent).append("fi\n");
        }
    }

    private Map<String, Map<Integer, Set<Integer>>> valueBuckets(List<TestCase> list, String varName) {
        final Map<String, Map<Integer, Set<Integer>>> buckets = new TreeMap<>();
        for (int i = 0; i < list.size(); i++) {
            final TestCase tc = list.get(i);
            for (int j = 0; j < tc.length(); j++) {
                final String value = tc.values().get(varName).get(j).toString();
                Map<Integer, Set<Integer>> bucket = buckets.get(value);
                if (bucket == null) {
                    bucket = new TreeMap<>();
                    buckets.put(value, bucket);
                }
                Set<Integer> valueSet = bucket.get(i);
                if (valueSet == null) {
                    valueSet = new TreeSet<>();
                    bucket.put(i, valueSet);
                }
                valueSet.add(j);
            }
        }
        return buckets;
    }

    private Map<Integer, Set<Integer>> lengthBuckets(List<TestCase> list) {
        final Map<Integer, Set<Integer>> lengthBuckets = new TreeMap<>();
        for (int i = 0; i < list.size(); i++) {
            final int length = list.get(i).length();
            Set<Integer> valueSet = lengthBuckets.get(length);
            if (valueSet == null) {
                valueSet = new TreeSet<>();
                lengthBuckets.put(length, valueSet);
            }
            valueSet.add(i);
        }
        return lengthBuckets;
    }

    public void print(String filename) throws IOException {
        lengthReorder();
        try (final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static TestSuite read(String filename) throws IOException {
        try (final ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            try {
                return (TestSuite) in.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            }
        }
    }
}
