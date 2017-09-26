package formal_testing.coverage;

import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.enums.Language;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    protected abstract String promelaLtlProperty(String opStart, String opEnd, Integer steps);
    protected abstract String nuSMVTemporalProperty(String opStart, String opEnd, Integer steps);
    public abstract boolean isCoveredBy(TestCase tc);

    public abstract String promelaLtlName();

    public String ltlProperty(Integer steps) {
        return Settings.LANGUAGE == Language.PROMELA
                ? promelaLtlProperty(steps)
                : nuSMVTemporalProperty(steps);
    }

    private String promelaLtlProperty(Integer steps) {
        return steps == null
                ? promelaLtlProperty("!X(<>(", "))", null)
                : promelaLtlProperty("!" + nTimes(steps, "X("), nTimes(steps, ")"), steps);
    }

    private String nuSMVTemporalProperty(Integer steps) {
        return steps == null
                ? nuSMVTemporalProperty("X G !(", ")", null)
                : nuSMVTemporalProperty(nTimes(steps, "X ") + "!(", ")", steps);
    }

    private String nTimes(int times, String str) {
        return String.join("", Collections.nCopies(times, str));
    }

    public static Set<CoveragePoint> checkCovered(Collection<CoveragePoint> cps, TestCase tc) {
        return cps.stream().filter(cp -> cp.isCoveredBy(tc)).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
