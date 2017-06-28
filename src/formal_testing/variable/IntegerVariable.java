package formal_testing.variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class IntegerVariable extends Variable {
    final int min;
    final int max;

    public IntegerVariable(String name, int min, int max, boolean isArrayPart, int arrayLength, int arrayIndex) {
        super(name, isArrayPart, arrayLength, arrayIndex);
        this.min = min;
        this.max = max;
    }

    public IntegerVariable(String name, String typeInterval, boolean isArrayPart, int arrayLength, int arrayIndex) {
        this(name, Integer.parseInt(typeInterval.split("\\.\\.")[0]),
                Integer.parseInt(typeInterval.split("\\.\\.")[1]), isArrayPart, arrayLength, arrayIndex);
    }

    @Override
    public String toString() {
        return indexedName() + " : " + min + ".." + max;
    }

    @Override
    public String toPromelaString() {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return "int " + name + "[" + arrayLength + "];";
        } else {
            return "int " + name + ";";
        }
    }

    @Override
    public List<String> promelaValues() {
        final List<String> values = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            values.add(String.valueOf(i));
        }
        return values;
    }
}
