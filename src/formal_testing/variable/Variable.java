package formal_testing.variable;

import formal_testing.Settings;
import formal_testing.enums.Language;
import formal_testing.value.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/26/17.
 */
public abstract class Variable<T extends Value> {
    public final String name;
    private final T initialValue;
    private final boolean isArrayPart;
    private final int arrayLength;
    private final int arrayIndex;

    public Variable(String name, T initialValue) {
        this(name, initialValue, false, 1, 0);
    }

    public Variable(String name, T initialValue, boolean isArrayPart, int arrayLength, int arrayIndex) {
        this.name = name;
        this.initialValue = initialValue;
        this.isArrayPart = isArrayPart;
        this.arrayLength = arrayLength;
        this.arrayIndex = arrayIndex;
    }

    public final String toLanguageString() {
        return Settings.LANGUAGE == Language.PROMELA ? toPromelaString() : toNusmvString();
    }

    public abstract T readValue(String value);

    public abstract String toPromelaString();
    public abstract String toNusmvString();

    public abstract List<? extends Value> values();

    public List<String> stringValues() {
        return values().stream().map(Value::toString).collect(Collectors.toList());
    }

    public T initialValue() {
        return initialValue;
    }

    private String indexPart() {
        return isArrayPart ? ("[" + arrayIndex + "]") : "";
    }

    public String indexedName() {
        return name + indexPart();
    }

    final String languageString(String type) {
        return Settings.LANGUAGE == Language.PROMELA ? promelaString(type) : nusmvString(type);
    }

    /*
     * with the initial value
     */
    private String promelaString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return type + " " + name + "[" + arrayLength + "] = " + initialValue.toString() + ";";
        } else {
            return type + " " + name + " = " + initialValue.toString() + ";";
        }
    }

    private String nusmvString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return name + ": array 0.." + (arrayLength - 1) + " of " + type + ";";
        } else {
            return name + ": " + type + ";";
        }
    }

    public abstract T valueFromBits(List<Boolean> bits);

    int intFromBits(int requiredLength, List<Boolean> bits) {
        if (bits.size() != 1) {
            throw new RuntimeException("Invalid number of bits! Expected: " + requiredLength
                    + ", given: " + bits.size());
        }
        int index = 0;
        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                index |= 1 << (bits.size() - i - 1);
            }
        }
        return index;
    }
}
