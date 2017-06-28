package formal_testing.variable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class BooleanVariable extends Variable {
    public BooleanVariable(String name, boolean isArrayPart, int arrayLength, int arrayIndex) {
        super(name, isArrayPart, arrayLength, arrayIndex);
    }

    @Override
    public String toString() {
        return indexedName() + " : bool";
    }

    @Override
    public String toPromelaString() {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return "bool " + name + "[" + arrayLength + "];";
        } else {
            return "bool " + name + ";";
        }
    }

    @Override
    public List<String> promelaValues() {
        return Arrays.asList("false", "true");
    }
}
