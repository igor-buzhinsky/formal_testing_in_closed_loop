package formal_testing.variable;

import formal_testing.value.SetValue;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class SetVariable extends Variable<SetValue> {
    private final List<SetValue> values;

    @Override
    public String toString() {
        return indexedName() + " : " + values.toString().replace("[", "{").replace("]", "}");
    }

    public SetVariable(String name, SetValue initialValue, List<SetValue> values, boolean isArrayPart,
                       int arrayLength, int arrayIndex) {
        super(name, initialValue, isArrayPart, arrayLength, arrayIndex);
        this.values = values;
    }

    @Override
    public SetValue readValue(String value) {
        return SetValue.read(value, values.stream().map(Object::toString).collect(Collectors.toList()));
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
    public List<SetValue> values() {
        return Collections.unmodifiableList(values);
    }
}
