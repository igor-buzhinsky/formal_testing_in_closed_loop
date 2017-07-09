package formal_testing.variable;

import formal_testing.value.BooleanValue;

import java.util.Arrays;
import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class BooleanVariable extends Variable<BooleanValue> {
    public BooleanVariable(String name, BooleanValue initialValue, boolean isArrayPart, int arrayLength,
                           int arrayIndex) {
        super(name, initialValue, isArrayPart, arrayLength, arrayIndex);
    }

    @Override
    public BooleanValue readValue(String value) {
        return BooleanValue.read(value);
    }

    public BooleanVariable(String name, BooleanValue initialValue) {
        super(name, initialValue);
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
    public List<BooleanValue> values() {
        return Arrays.asList(BooleanValue.FALSE, BooleanValue.TRUE);
    }

    @Override
    public BooleanValue valueFromBits(List<Boolean> bits) {
        if (bits.size() != 1) {
            throw new RuntimeException("Invalid number of bits! Expected: 1, given: " + bits.size());
        }
        return bits.get(0) ? BooleanValue.TRUE : BooleanValue.FALSE;
    }
}
