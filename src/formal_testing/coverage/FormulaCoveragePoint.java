package formal_testing.coverage;

import formal_testing.TestCase;
import formal_testing.formula.LTLFormula;

import java.util.Set;

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
        final StringBuilder sb = new StringBuilder("spec_");
        for (char c : target.toString().toCharArray()) {
            sb.append(String.format("%02x", (int) c));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return (covered ? "[+] " : "[-] ") + "FORMULA " + target;
    }
}
