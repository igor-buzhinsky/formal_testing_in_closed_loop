package formal_testing.variable;

import formal_testing.Language;
import formal_testing.Util;

import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public abstract class Variable {
    public final String name;
    private final boolean isArrayPart;
    private final int arrayLength;
    private final int arrayIndex;

    public Variable(String name, boolean isArrayPart, int arrayLength, int arrayIndex) {
        this.name = name;
        this.isArrayPart = isArrayPart;
        this.arrayLength = arrayLength;
        this.arrayIndex = arrayIndex;
    }

    public final String toLanguageString() {
        return Util.LANGUAGE == Language.PROMELA ? toPromelaString() : toNusmvString();
    }

    public abstract String toPromelaString();
    public abstract String toNusmvString();

    public abstract List<String> promelaValues();

    protected String indexPart() {
        return isArrayPart ? ("[" + arrayIndex + "]") : "";
    }

    public String indexedName() {
        return name + indexPart();
    }

    protected final String languageString(String type) {
        return Util.LANGUAGE == Language.PROMELA ? promelaString(type) : nusmvString(type);
    }

    private String promelaString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return type + " " + name + "[" + arrayLength + "];";
        } else {
            return type + " " + name + ";";
        }
    }

    private String nusmvString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return name + "[" + arrayLength + "] : " + type + ";";
        } else {
            return name + " : " + type + ";";
        }
    }
}
