package formal_testing.coverage;

import formal_testing.variable.Variable;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class DataCoveragePoint extends CoveragePoint {
    private final Variable var;
    private final String value;

    public DataCoveragePoint(Variable var, String value) {
        this.var = var;
        this.value = value;
    }

    @Override
    public String promelaLtlName() {
        return "covered_" + var.indexedName().replace("[", "__").replace("]", "__") + "__" + value;
    }

    @Override
    protected String promelaLtlProperty(String opStart, String opEnd) {
        return "ltl " + promelaLtlName() + " { " + opStart + var.indexedName() + " == " + value + opEnd + " }";
    }

    @Override
    public String toString() {
        return (covered ? "[+] " : "[-] ") + var.indexedName() + " == " + value;
    }
}
