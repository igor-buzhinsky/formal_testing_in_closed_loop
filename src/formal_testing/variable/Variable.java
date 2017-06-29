package formal_testing.variable;

import java.util.List;

/**
 * Created by buzhinsky on 6/26/17.
 */
public abstract class Variable {
    public final String name;
    public boolean isArrayPart;
    public int arrayLength;
    public int arrayIndex;

    public Variable(String name, boolean isArrayPart, int arrayLength, int arrayIndex) {
        this.name = name;
        this.isArrayPart = isArrayPart;
        this.arrayLength = arrayLength;
        this.arrayIndex = arrayIndex;
    }

    public abstract String toPromelaString();

    public abstract List<String> promelaValues();

    protected String indexPart() {
        return isArrayPart ? ("[" + arrayIndex + "]") : "";
    }

    public String indexedName() {
        return name + indexPart();
    }

    protected String promelaString(String type) {
        if (isArrayPart && arrayIndex > 0) {
            return "";
        } else if (isArrayPart && arrayIndex == 0) {
            return type + " " + name + "[" + arrayLength + "];";
        } else {
            return type + " " + name + ";";
        }
    }
}
