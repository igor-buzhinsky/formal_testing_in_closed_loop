package formal_testing.coverage;

import formal_testing.Language;
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
    protected abstract String nusmvLtlProperty(String opStart, String opEnd);

    public abstract String promelaLtlName();

    public String ltlProperty(int steps, boolean negate) {
        return Util.LANGUAGE == Language.PROMELA ? promelaLtlProperty(steps, negate) : nusmvLtlProperty(steps, negate);
    }

    private String promelaLtlProperty(int steps, boolean negate) {
        return steps == -1
                ? promelaLtlProperty((negate ? "!" : "") + "X(<>(", "))") : promelaLtlProperty((negate ? "!" : "")
                + String.join("", Collections.nCopies(steps, "X(")), String.join("", Collections.nCopies(steps, ")")));
    }

    private String nusmvLtlProperty(int steps, boolean negate) {
        return steps == -1
                ? nusmvLtlProperty((negate ? "!" : "") + "X(F(", "))") : nusmvLtlProperty((negate ? "!" : "")
                + String.join("", Collections.nCopies(steps, "X(")), String.join("", Collections.nCopies(steps, ")")));
    }
}
