package formal_testing.variable;

import formal_testing.Language;
import formal_testing.Util;
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
        return Util.LANGUAGE == Language.PROMELA ? toPromelaString() : toNusmvString();
    }

    public abstract String toPromelaString();
    public abstract String toNusmvString();

    protected abstract List<? extends Value> values();

    public List<String> stringValues() {
        return values().stream().map(Value::toString).collect(Collectors.toList());
    }

    public String initialValue() {
        return initialValue.toString();
    }

    protected String indexPart() {
        return isArrayPart ? ("[" + arrayIndex + "]") : "";
    }

    public String indexedName() {
        return name + indexPart();
    }

    protected final String languageString(String type) {
        return Util.LANGUAGE == Language.PROMELA ? promelaString(type) : nusmvString(type);
    }

    /*
     * with the initial value
     */
    private String promelaString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return type + " " + name + "[" + arrayLength + "] = " + initialValue() + ";";
        } else {
            return type + " " + name + " = " + initialValue() + ";";
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
}
