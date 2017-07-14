package formal_testing.coverage;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class FlowCoveragePoint extends CoveragePoint {
    private int index;

    public FlowCoveragePoint(int index) {
        this.index = index;
    }

    @Override
    public String promelaLtlName() {
        return "flow_covered_" + index;
    }

    @Override
    protected String promelaLtlProperty(String opStart, String opEnd, Integer steps) {
        return "ltl " + promelaLtlName() + "__" + steps + " { " + opStart + " _cover[" + index + "]" + opEnd + " }";
    }

    @Override
    protected String nuSMVTemporalProperty(String opStart, String opEnd, Integer steps) {
        throw new RuntimeException("Flow coverage points and not supported for NuSMV.");
    }

    @Override
    public String toString() {
        return (covered ? "[+]" : "[-]") + " _cover[" + index + "]";
    }
}
