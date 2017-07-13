package formal_testing.enums;

/**
 * Created by buzhinsky on 7/6/17.
 */
public enum NuSMVMode {
    LINEAR_BMC("X", "F", "LTLSPEC"),
    EXPONENTIAL_BMC("X", "F", "LTLSPEC"),
    INVARSPEC_BMC("next", null, "INVARSPEC"),
    FINITE_CTL("AX", "AF", "CTLSPEC"),
    INFINITE_CTL("AX", "AF", "CTLSPEC");

    public final String xOperator;
    public final String fOperator;
    public final String specDeclaration;

    NuSMVMode(String xOperator, String fOperator, String specDeclaration) {
        this.xOperator = xOperator;
        this.fOperator = fOperator;
        this.specDeclaration = specDeclaration;
    }
}
