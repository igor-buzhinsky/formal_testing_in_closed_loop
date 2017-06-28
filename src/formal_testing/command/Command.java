package formal_testing.command;

import formal_testing.ProblemData;
import formal_testing.Util;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.util.function.Function;

/**
 * Created by buzhinsky on 6/27/17.
 */
public abstract class Command {
    public final String name;
    public final ProblemData data;

    protected static final String MODEL_FILENAME = ".model.pml";

    public Command(String name, ProblemData data) {
        this.name = name;
        this.data = data;
    }

    private String nondetSelection() {
        final StringBuilder sb = new StringBuilder();
        for (Variable var : data.conf.nondetVars) {
            sb.append("if\n");
            for (String value : var.promelaValues()) {
                sb.append(":: " + var.indexedName() + " = " + value + ";\n");
            }
            sb.append("fi\n");
        }
        return sb.toString();
    }

    protected String modelCode(boolean testing, boolean nondetSelection) {
        final StringBuilder code = new StringBuilder();
        code.append("// Inputs" + "\n");
        final Function<String, String> addNl = s -> s.isEmpty() ? "" : (s + "\n");
        data.conf.inputVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        code.append("// Outputs" + "\n");
        data.conf.outputVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        code.append("// Nondeterministic variables" + "\n");
        data.conf.nondetVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        code.append("// Plant internal variables" + "\n");
        data.conf.plantInternalVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        code.append("// Controller internal variables" + "\n");
        data.conf.controllerInternalVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (testing) {
            code.append("\n");
            code.append("bool _test_passed;" + "\n");
        }
        code.append("\n");
        code.append(data.header);
        code.append("\n");
        code.append("init { do :: atomic {" + "\n");
        if (nondetSelection) {
            code.append(Util.indent(nondetSelection()));
            code.append("\n");
        }
        code.append(Util.indent(data.plantModel));
        code.append("\n");
        code.append(Util.indent(data.controllerModel));
        code.append("\n");
        code.append("} od }" + "\n");
        code.append(data.spec);
        if (testing) {
            code.append("\n");
            code.append("ltl test_passed { <>_test_passed }" + "\n");
        }
        return code.toString();
    }

    public abstract void execute() throws IOException;
}
