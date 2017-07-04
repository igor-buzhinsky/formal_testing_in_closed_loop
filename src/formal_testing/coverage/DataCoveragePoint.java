package formal_testing.coverage;

import formal_testing.Util;
import formal_testing.variable.Variable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class DataCoveragePoint extends CoveragePoint {
    private final List<Variable> vars;
    private final List<String> values;

    public DataCoveragePoint(Variable var, String value) {
        this.vars = Collections.singletonList(var);
        this.values = Collections.singletonList(value);
    }

    public DataCoveragePoint(Variable var1, String value1, Variable var2, String value2) {
        this.vars = Arrays.asList(var1, var2);
        this.values = Arrays.asList(value1, value2);
    }

    private String nameParts() {
        final String[] arr = new String[vars.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = vars.get(i).indexedName() + "__" + values.get(i);
        }
        return String.join("__", Arrays.asList(arr)).replace("[", "__").replace("]", "__");
    }

    private String conditions() {
        final String[] arr = new String[vars.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = vars.get(i).indexedName() + Util.LANGUAGE.equals + values.get(i);
        }
        return String.join(Util.LANGUAGE.and, Arrays.asList(arr));
    }

    @Override
    public String promelaLtlName() {
        return "covered_" + nameParts();
    }

    @Override
    protected String promelaLtlProperty(String opStart, String opEnd) {
        return "ltl " + promelaLtlName() + " { " + opStart + conditions() + opEnd + " }";
    }

    @Override
    protected String nusmvLtlProperty(String opStart, String opEnd) {
        return "LTLSPEC " + opStart + conditions() + opEnd;
    }

    @Override
    public String toString() {
        return (covered ? "[+] " : "[-] ") + conditions();
    }
}
