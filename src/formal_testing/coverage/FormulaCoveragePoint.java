package formal_testing.coverage;

import formal_testing.TestCase;
import formal_testing.formula.LTLFormula;

/**
 * Created by buzhinsky on 9/24/17.
 */
public class FormulaCoveragePoint extends CoveragePoint {
    private final LTLFormula target;

    public FormulaCoveragePoint(LTLFormula target) {
        this.target = target;
    }

    @Override
    protected String promelaLtlProperty(String opStart, String opEnd, Integer steps) {
        return "ltl " + promelaLtlName() + "__" + steps + " { " + opStart + target.toString()
                .replace(" & ", " && ").replace(" | ", " || ").replace(" = ", " == ") + opEnd + " }";
    }

    @Override
    protected String nuSMVTemporalProperty(String opStart, String opEnd, Integer steps) {
        return "LTLSPEC " + opStart + target + opEnd;
    }

    @Override
    public boolean isCoveredBy(TestCase tc) {
        return tc.hasFormulaSatisfied(target);
    }

    @Override
    public String promelaLtlName() {
        return "spec_" + target.toString().replace(" ", "_").replace("(", "_popen_").replace(")", "_pclose_")
                .replace(".", "__").replace("&", "_and_").replace("|", "_or_").replace("->", "_implies_")
                .replace("<->", "_eq_").replace("[", "_sopen_").replace("]", "_sclose_").replace("!", "_not_");
    }

    @Override
    public String toString() {
        return (covered ? "[+] " : "[-] ") + "FORMULA " + target;
    }
}
