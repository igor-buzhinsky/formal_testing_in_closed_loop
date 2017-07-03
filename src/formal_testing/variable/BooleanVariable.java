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

    public BooleanVariable(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return indexedName() + " : bool";
    }

    @Override
    public String toPromelaString() {
        return languageString("bool");
    }

    @Override
    public String toNusmvString() {
        return languageString("boolean");
    }

    @Override
    public List<String> promelaValues() {
        return Arrays.asList("0", "1");
    }

    @Override
    public List<String> nusmvValues() {
        return Arrays.asList("FALSE", "TRUE");
    }

    @Override
    public String promelaInitialValue() {
        return "0";
    }

    @Override
    public String nusmvInitialValue() {
        return "FALSE";
    }
}
