package formal_testing.variable;

import formal_testing.value.IntegerValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class IntegerVariable extends Variable<IntegerValue> {
    private final int min;
    private final int max;

    public IntegerVariable(String name, IntegerValue initialValue, int min, int max, boolean isArrayPart,
                           int arrayLength, int arrayIndex) {
        super(name, initialValue, isArrayPart, arrayLength, arrayIndex);
        this.min = min;
        this.max = max;
    }

    public IntegerVariable(String name, IntegerValue initialValue, String typeInterval, boolean isArrayPart,
                           int arrayLength, int arrayIndex) {
        this(name, initialValue, Integer.parseInt(typeInterval.split("\\.\\.")[0]),
                Integer.parseInt(typeInterval.split("\\.\\.")[1]), isArrayPart, arrayLength, arrayIndex);
    }

    @Override
    public String toString() {
        return indexedName() + " : " + min + ".." + max;
    }

    @Override
    public IntegerValue readValue(String value) {
        return IntegerValue.read(value);
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
    public List<IntegerValue> values() {
        final List<IntegerValue> values = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            values.add(new IntegerValue(i));
        }
        return values;
    }

    @Override
    public IntegerValue valueFromBits(List<Boolean> bits) {
        final int requiredLength = (int) Math.round(Math.ceil(Math.log(max - min + 1) / Math.log(2)));
        return new IntegerValue(min + intFromBits(requiredLength, bits));
        // FIXME conversion may be different
    }
}
