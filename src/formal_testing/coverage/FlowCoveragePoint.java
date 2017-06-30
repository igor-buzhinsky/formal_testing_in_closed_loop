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
    protected String promelaLtlProperty(String opStart, String opEnd) {
        return "ltl " + promelaLtlName() + " { " + opStart + " _cover[" + index + "]" + opEnd + " }";
    }

    @Override
    public String toString() {
        return (covered ? "[+]" : "[-]") + " _cover[" + index + "]";
    }
}
