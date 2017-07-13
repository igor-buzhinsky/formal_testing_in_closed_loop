package formal_testing.coverage;

import formal_testing.Settings;
import formal_testing.enums.Language;

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

    public String ltlProperty(Integer steps, boolean negate) {
        return Settings.LANGUAGE == Language.PROMELA
                ? promelaLtlProperty(steps, negate)
                : nuSMVTemporalProperty(steps, negate);
    }

    private String promelaLtlProperty(Integer steps, boolean negate) {
        return steps == null
                ? promelaLtlProperty((negate ? "!" : "") + "X(<>(", "))") : promelaLtlProperty((negate ? "!" : "")
                + String.join("", Collections.nCopies(steps, "X(")), String.join("", Collections.nCopies(steps, ")")));
    }

    private String nuSMVTemporalProperty(Integer steps, boolean negate) {
        final String x = Settings.NUSMV_MODE.xOperator;
        final String f = Settings.NUSMV_MODE.fOperator;
        return steps == null
                ? nuSMVTemporalProperty((negate ? "!" : "") + x + "(" + f + "(", "))")
                : nuSMVTemporalProperty(nTimes(steps, x + "(") + (negate ? "!(" : "("), nTimes(steps + 1, ")"));
    }

    private String nTimes(int times, String str) {
        return String.join("", Collections.nCopies(times, str));
    }
}
