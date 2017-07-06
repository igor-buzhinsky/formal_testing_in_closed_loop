package formal_testing.enums;

/**
 * Created by buzhinsky on 7/6/17.
 */
public enum NuSMVMode {
    LTL("X", "F", "LTLSPEC"), BMC("X", "F", "LTLSPEC"), CTL("AX", "AF", "CTLSPEC");

    public final String xOperator;
    public final String fOperator;
    public final String specDeclaration;

    NuSMVMode(String xOperator, String fOperator, String specDeclaration) {
        this.xOperator = xOperator;
        this.fOperator = fOperator;
        this.specDeclaration = specDeclaration;
    }
}
