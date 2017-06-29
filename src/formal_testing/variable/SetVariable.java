package formal_testing.variable;

import java.util.Collections;
import java.util.List;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class SetVariable extends Variable {
    final List<String> values;

    @Override
    public String toString() {
        return indexedName() + " : " + values.toString().replace("[", "{").replace("]", "}");
    }

    public SetVariable(String name, List<String> values, boolean isArrayPart, int arrayLength, int arrayIndex) {
        super(name, isArrayPart, arrayLength, arrayIndex);
        this.values = values;
    }

    @Override
    public String toPromelaString() {
        return promelaString("mtype");
    }

    @Override
    public List<String> promelaValues() {
        return Collections.unmodifiableList(values);
    }
}
