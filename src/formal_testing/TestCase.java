package formal_testing;

import formal_testing.formula.InconclusiveException;
import formal_testing.formula.LTLFormula;
import formal_testing.value.Value;
import formal_testing.variable.Variable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class TestCase implements Serializable {
    private final Map<String, List<Value>> values = new LinkedHashMap<>();
    private int length = 0;

    public TestCase reduceToNondetVars(Configuration conf) {
        final TestCase result = new TestCase(conf, false);
        final Set<String> properNames = conf.nondetVars.stream().map(Variable::indexedName).collect(Collectors.toSet());
        values.entrySet().stream().filter(entry -> properNames.contains(entry.getKey()))
                .forEach(entry -> result.values.put(entry.getKey(), entry.getValue()));
        result.length = length;
        return result;
    }

    public boolean hasFormulaSatisfied(LTLFormula f) {
        for (int i = 0; i < length; i++) {
            try {
                if (f.booleanValue(values, i)) {
                    return true;
                }
            } catch(InconclusiveException ignored) {
            }
        }
        return false;
    }

    public TestCase(Configuration conf, boolean allVars) {
        for (Variable v : allVars ? conf.allVariables() : conf.nondetVars) {
            values.put(v.indexedName(), new ArrayList<>());
        }
    }

    @Override
    public String toString() {
        return "TestCase[length = " + length + "; values = " + values.toString() + "]";
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

    public int length() {
        return length;
    }

    Map<String, List<Value>> values() {
        return Collections.unmodifiableMap(values);
    }

    public String header(boolean addOracle) {
        return new TestSuite(addOracle, this).header();
    }

    public String body(boolean addOracle, Configuration conf) {
        return new TestSuite(addOracle, this).body(conf);
    }

    public void newElement() {
        length++;
    }

    public void addValue(String varName, Value value) {
        List<Value> varValues = values.get(varName);
        if (varValues == null) {
            // create this value
            varValues = new ArrayList<>();
            values.put(varName, varValues);
        }
        varValues.add(value);
        length = Math.max(length, varValues.size());
    }

    public void crop(int maxLength) {
        values.values().stream().filter(l -> l.size() > maxLength).forEach(l -> l.remove(l.size() - 1));
        length = values.values().stream().mapToInt(List::size).max().orElse(0);
    }

    public void validate() {
        for (Map.Entry<String, List<Value>> entry : values.entrySet()) {
            if (entry.getValue().size() != length) {
                throw new RuntimeException("The supposed length is " + length + ", but the test case is " + toString());
            }
        }
    }

    public void removeInitial() {
        values.values().forEach(l -> l.remove(0));
        length--;
    }

    public void padMissing(Configuration conf) {
        for (Map.Entry<String, List<Value>> entry : values.entrySet()) {
            final List<Value> list = entry.getValue();
            while (list.size() < length) {
                final Value padValue = list.isEmpty()
                        ? conf.byName(entry.getKey()).initialValue() : list.get(list.size() - 1);
                list.add(padValue);
            }
        }
    }

    public void loopFromPosition(int position, int desiredLength) {
        int curPosition = position;
        final int loopEnd = length;
        while (length < desiredLength) {
            for (List<Value> list : values.values()) {
                list.add(list.get(curPosition));
            }
            curPosition++;
            if (curPosition == loopEnd) {
                curPosition = position;
            }
            length++;
        }
    }
}
