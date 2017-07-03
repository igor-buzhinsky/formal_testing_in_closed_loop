package formal_testing.variable;

import java.util.Collections;
import java.util.List;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class SetVariable extends Variable {
    private final List<String> values;

    @Override
    public String toString() {
        return indexedName() + " : " + values.toString().replace("[", "{").replace("]", "}");
    }

    public SetVariable(String name, List<String> values, boolean isArrayPart, int arrayLength, int arrayIndex) {
        super(name, isArrayPart, arrayLength, arrayIndex);
        this.values = values;
    }

    public SetVariable(String name, List<String> values) {
        super(name);
        this.values = values;
    }

    @Override
    public String toPromelaString() {
        return languageString("mtype");
    }

    @Override
    public String toNusmvString() {
        return languageString(values.toString().replace("[", "{").replace("]", "}"));
    }

    @Override
    public List<String> promelaValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public List<String> nusmvValues() {
        return promelaValues();
    }

    @Override
    public String promelaInitialValue() {
        return values.get(0);
    }

    @Override
    public String nusmvInitialValue() {
        return values.get(0);
    }
}
