package formal_testing.variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class IntegerVariable extends Variable {
    private final int min;
    private final int max;

    public IntegerVariable(String name, int min, int max, boolean isArrayPart, int arrayLength, int arrayIndex) {
        super(name, isArrayPart, arrayLength, arrayIndex);
        this.min = min;
        this.max = max;
    }

    public IntegerVariable(String name, int min, int max) {
        super(name);
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
        return languageString("int");
    }

    @Override
    public String toNusmvString() {
        return languageString(min + ".." + max);
    }

    @Override
    public List<String> promelaValues() {
        final List<String> values = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            values.add(String.valueOf(i));
        }
        return values;
    }

    @Override
    public List<String> nusmvValues() {
        return promelaValues();
    }

    @Override
    public String promelaInitialValue() {
        return String.valueOf(min);
    }

    @Override
    public String nusmvInitialValue() {
        return String.valueOf(min);
    }
}
