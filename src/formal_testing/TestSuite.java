package formal_testing;

import formal_testing.enums.Language;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class TestSuite implements Serializable {
    private final Set<TestCase> testCases = new LinkedHashSet<>();
    private final boolean addOracle;

    public TestSuite(boolean addOracle, TestCase... testCases) {
        this.addOracle = addOracle;
        Arrays.stream(testCases).forEach(this.testCases::add);
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

    public String body() {
        return Settings.LANGUAGE == Language.PROMELA ? promelaBody() : nuSMVBody();
    }

    private String promelaHeader() {
        return "int _test_step;" + (trivial() ? "" : "\nint _test_index = -1;");
    }

    private String nuSMVHeader() {
        return "VAR\n    _test_step: -1.." + (maxLength() - 1) + ";\n    _test_index: -1.." + (testCases.size() - 1) +
                ";\nASSIGN\n    init(_test_step) := -1;\n    init(_test_index) := -1;";
    }

    private static List<Pair<Integer, Integer>> intervals(Collection<Integer> values) {
        final List<Pair<Integer, Integer>> intervals = new ArrayList<>();
        int min = -1;
        int max = -1;
        for (int value : values) {
            if (min == -1) {
                min = max = value;
            } else if (value == max + 1) {
                max = value;
            } else if (value <= max) {
                throw new AssertionError("Input set must contain increasing values.");
            } else {
                intervals.add(Pair.of(min, max));
                min = max = value;
            }
        }
        intervals.add(Pair.of(min, max));
        return intervals;
    }

    private static String expressWithIntervalsNuSMV(Collection<Integer> values) {
        final List<Pair<Integer, Integer>> intervals = intervals(values);
        final List<String> stringIntervals = new ArrayList<>();
        final Set<Integer> separate = new TreeSet<>();
        for (Pair<Integer, Integer> interval : intervals) {
            if (interval.getLeft() + 1 >= interval.getRight()) {
                separate.add(interval.getLeft());
                separate.add(interval.getRight());
            } else {
                stringIntervals.add(interval.getLeft() + ".." + interval.getRight());
            }
        }
        if (!separate.isEmpty()) {
            stringIntervals.add(separate.toString().replace("[", "{").replace("]", "}"));
        }
        return String.join(" union ", stringIntervals);
    }

    public static String expressWithIntervalsSPIN(Collection<Integer> values, String varName) {
        final List<Pair<Integer, Integer>> intervals = intervals(values);
        final List<String> stringIntervals = new ArrayList<>();
        final Set<Integer> separate = new TreeSet<>();
        for (Pair<Integer, Integer> interval : intervals) {
            if (interval.getLeft() + 1 >= interval.getRight()) {
                separate.add(interval.getLeft());
                separate.add(interval.getRight());
            } else {
                stringIntervals.add(varName + " >= " + interval.getLeft() + " && " + varName + " <= "
                        + interval.getRight());
            }
        }
        if (!separate.isEmpty()) {
            stringIntervals.addAll(separate.stream().map(value -> varName + " == " + value)
                    .collect(Collectors.toList()));
        }
        return String.join(" || ", stringIntervals);
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
                                + expressWithIntervalsNuSMV(e.getValue())).collect(Collectors.toList()));
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
                ? "TRUE" : ("next(_test_index) in " + expressWithIntervalsNuSMV(e.getValue()));
            sb.append("        ").append(condition).append(": ").append(e.getKey()).append(";\n");
        }

        sb.append("    esac);\n");

        if (addOracle) {
            sb.append("    next(_test_passed) := next(_test_step) = 0 ? TRUE : _test_passed;\n");
        }

        return sb.toString();
    }

    private String promelaBody() {
        // looping scenario
        final StringBuilder sb = new StringBuilder();
        final List<TestCase> list = new ArrayList<>(testCases);

        if (!trivial()) {
            sb.append("if\n");
            for (int i = 0; i < list.size(); i++) {
                sb.append(":: _test_index == -1 -> _test_index = ").append(i).append(";\n");
            }
            sb.append(":: else -> ;\n").append("fi\n");
        }

        sb.append("if\n");
        for (int i = 0; i < list.size(); i++) {
            final TestCase tc = list.get(i);
            for (int j = 0; j < tc.length(); j++) {
                sb.append(":: ").append(trivial() ? "" : ("_test_index == " + i + " && "))
                        .append("_test_step == ").append(j).append(" -> d_step { ");
                for (String varName : tc.values().keySet()) {
                    sb.append(varName).append(" = ").append(tc.values().get(varName).get(j)).append("; ");
                }
                sb.append("}\n");
            }
        }
        sb.append("fi\n");
        sb.append("d_step {\n");
        if (trivial()) {
            sb.append("    _test_step = (_test_step + 1) % ").append(list.get(0).length()).append(";\n");
        } else {
            sb.append("    if\n");
            final Map<Integer, Set<Integer>> lengthBuckets = lengthBuckets(list);
            final List<Integer> allLengths = new ArrayList<>(lengthBuckets.keySet());
            for (Map.Entry<Integer, Set<Integer>> e : lengthBuckets.entrySet()) {
                final int length = e.getKey();
                final String condition = length == allLengths.get(allLengths.size() - 1) ? "else"
                        : expressWithIntervalsSPIN(e.getValue(), "_test_index");
                sb.append("    :: ").append(condition).append(" -> _test_step = (_test_step + 1) % ")
                        .append(e.getKey()).append(";\n");
            }
            sb.append("    fi\n");
        }
        sb.append(addOracle ? "    _test_passed = (_test_step == 0 -> 1 : _test_passed);\n" : "")
                .append("}\n");
        return sb.toString();
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
