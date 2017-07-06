package formal_testing.coverage;

import formal_testing.enums.Language;
import formal_testing.Util;

import java.util.Collections;

/**
 * Created by buzhinsky on 6/29/17.
 */
public abstract class CoveragePoint {
    boolean covered = false;

    public boolean covered() {
        return covered;
    }

    public void cover() {
        covered = true;
    }

    protected abstract String promelaLtlProperty(String opStart, String opEnd);
    protected abstract String nuSMVTemporalProperty(String opStart, String opEnd);

    public abstract String promelaLtlName();

    public String ltlProperty(int steps, boolean negate) {
        return Util.LANGUAGE == Language.PROMELA
                ? promelaLtlProperty(steps, negate)
                : nuSMVTemporalProperty(steps, negate);
    }

    private String promelaLtlProperty(int steps, boolean negate) {
        return steps == -1
                ? promelaLtlProperty((negate ? "!" : "") + "X(<>(", "))") : promelaLtlProperty((negate ? "!" : "")
                + String.join("", Collections.nCopies(steps, "X(")), String.join("", Collections.nCopies(steps, ")")));
    }

    private String nuSMVTemporalProperty(int steps, boolean negate) {
        final String x = Util.NUSMV_MODE.xOperator;
        final String f = Util.NUSMV_MODE.fOperator;
        return steps == -1
                ? nuSMVTemporalProperty((negate ? "!" : "") + x + "(" + f + "(", "))")
                : nuSMVTemporalProperty(nTimes(steps, x + "(") + (negate ? "!(" : "("), nTimes(steps + 1, ")"));
    }

    private String nTimes(int times, String str) {
        return String.join("", Collections.nCopies(times, str));
    }
}
