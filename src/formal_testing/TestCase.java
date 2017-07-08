package formal_testing;

import formal_testing.value.Value;
import formal_testing.variable.Variable;

import java.io.Serializable;
import java.util.*;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class TestCase implements Serializable {
    private final Map<String, List<Value>> values = new LinkedHashMap<>();
    private int length = 0;
    private int maxLength = Integer.MAX_VALUE;

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int length() {
        return length;
    }

    Map<String, List<Value>> values() {
        return Collections.unmodifiableMap(values);
    }

    public TestCase(Configuration conf) {
        for (Variable v : conf.nondetVars) {
            values.put(v.indexedName(), new ArrayList<>());
        }
    }

    public void addValue(String varName, Value value) {
        final List<Value> varValues = values.get(varName);
        if (varValues.size() < maxLength) {
            varValues.add(value);
            length = Math.max(length, varValues.size());
        }
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public String header(boolean addOracle) {
        return new TestSuite(addOracle, this).header();
    }

    public void validate() {
        for (Map.Entry<String, List<Value>> entry : values.entrySet()) {
            if (entry.getValue().size() != length) {
                throw new RuntimeException("The supposed length is " + length + ", but the test case is " + toString());
            }
        }
    }

    public String body(boolean addOracle, Configuration conf) {
        return new TestSuite(addOracle, this).body(conf);
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

    public void newElement() {
        length++;
    }

    public void removeInitial() {
        for (List<Value> list : values.values()) {
            list.remove(0);
        }
        length--;
    }

    public void loopFromPosition(int position, int desiredLength) {
        int curPosition = position;
        int loopEnd = length;
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
