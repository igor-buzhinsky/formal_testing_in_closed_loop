package formal_testing;

import formal_testing.variable.Variable;

import java.util.Collections;

/**
 * Created by buzhinsky on 6/29/17.
 */
public class CoveragePoint {
    private final Variable var;
    private final String value;
    private boolean covered = false;

    public CoveragePoint(Variable var, String value) {
        this.var = var;
        this.value = value;
    }

    public String promelaLtlName() {
        return "covered_" + var.indexedName().replace("[", "__").replace("]", "__") + "__" + value;
    }

    private String promelaLtlProperty(String opStart, String opEnd) {
        return "ltl " + promelaLtlName() + " { " + opStart + var.indexedName() + " == " + value + opEnd + " }";
    }

    public String promelaLtlProperty() {
        return promelaLtlProperty("X(<>(", "))");
    }

    public String promelaLtlProperty(int steps, boolean negate) {
        return promelaLtlProperty((negate ? "!" : "") + String.join("", Collections.nCopies(steps, "X(")),
                String.join("", Collections.nCopies(steps, ")")));
    }

    public boolean covered() {
        return covered;
    }

    public void cover() {
        covered = true;
    }

    @Override
    public String toString() {
        return (covered ? "[+] " : "[-] ") + var.indexedName() + " == " + value;
    }
}
