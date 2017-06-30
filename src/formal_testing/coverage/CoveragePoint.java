package formal_testing.coverage;

import java.util.Collections;

/**
 * Created by buzhinsky on 6/29/17.
 */
public abstract class CoveragePoint {
    protected boolean covered = false;

    public boolean covered() {
        return covered;
    }

    public void cover() {
        covered = true;
    }

    public String promelaLtlProperty() {
        return promelaLtlProperty("X(<>(", "))");
    }

    protected abstract String promelaLtlProperty(String opStart, String opEnd);

    public abstract String promelaLtlName();

    public String promelaLtlProperty(int steps, boolean negate) {
        return promelaLtlProperty((negate ? "!" : "") + String.join("", Collections.nCopies(steps, "X(")),
                String.join("", Collections.nCopies(steps, ")")));
    }
}
