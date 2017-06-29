package formal_testing.command;

import formal_testing.CoveragePoint;
import formal_testing.ProblemData;
import formal_testing.Util;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    protected List<CoveragePoint> coveragePoints(boolean includeInternal) {
        final List<Variable> variables = new ArrayList<>();
        variables.addAll(data.conf.inputVars);
        variables.addAll(data.conf.nondetVars);
        variables.addAll(data.conf.outputVars);
        if (includeInternal) {
            variables.addAll(data.conf.plantInternalVars);
            variables.addAll(data.conf.controllerInternalVars);
        }
        final List<CoveragePoint> result = new ArrayList<>();
        for (Variable var : variables) {
            result.addAll(var.promelaValues().stream().map(value -> new CoveragePoint(var, value))
                    .collect(Collectors.toList()));
        }
        return result;
    }

    protected String modelCode(boolean testing, boolean nondetSelection, boolean spec, Optional<String> testHeader,
                               Optional<String> testBody) {
        final StringBuilder code = new StringBuilder();

        final Set<String> mtypeValues = new LinkedHashSet<>();
        for (List<Variable> list : Arrays.asList(data.conf.inputVars, data.conf.outputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.controllerInternalVars)) {
            for (Variable var : list) {
                if (var instanceof SetVariable) {
                    mtypeValues.addAll(var.promelaValues());
                }
            }
        }
        if (!mtypeValues.isEmpty()) {
            code.append("mtype " + mtypeValues.toString().replace("[", "{").replace("]", "}")).append("\n");
        }

        if (!data.conf.inputVars.isEmpty()) {
            code.append("// Inputs" + "\n");
        }
        final Function<String, String> addNl = s -> s.isEmpty() ? "" : (s + "\n");
        data.conf.inputVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (!data.conf.outputVars.isEmpty()) {
            code.append("// Outputs" + "\n");
        }
        data.conf.outputVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (!data.conf.nondetVars.isEmpty()) {
            code.append("// Nondeterministic variables" + "\n");
        }
        data.conf.nondetVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (!data.conf.plantInternalVars.isEmpty()) {
            code.append("// Plant internal variables" + "\n");
        }
        data.conf.plantInternalVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (!data.conf.controllerInternalVars.isEmpty()) {
            code.append("// Controller internal variables" + "\n");
        }
        data.conf.controllerInternalVars.forEach(v -> code.append(addNl.apply(v.toPromelaString())));
        if (testing) {
            code.append("\n").append("bool _test_passed;" + "\n");
        }
        code.append("\n").append(data.header);
        if (testHeader.isPresent()) {
            code.append("\n").append(testHeader.get());
        }
        code.append("\n").append("\n").append("init { do :: atomic {" + "\n");
        if (nondetSelection) {
            code.append(Util.indent(nondetSelection())).append("\n");
        }
        if (testBody.isPresent()) {
            code.append(Util.indent(testBody.get())).append("\n");
        }
        code.append("\n").append(Util.indent(data.plantModel)).append("\n").append("\n")
                .append(Util.indent(data.controllerModel)).append("\n").append("} od }").append("\n");
        if (spec) {
            code.append("\n").append(data.spec);
        }
        if (testing) {
            code.append("\n").append("ltl test_passed { <>_test_passed }" + "\n");
        }
        return code.toString();
    }

    protected Scanner runSpin(int timeout) throws IOException {
        final ProcessBuilder pb = new ProcessBuilder("/bin/bash", "run.sh", MODEL_FILENAME, String.valueOf(timeout));
        pb.redirectError();
        final Process p = pb.start();
        return new Scanner(p.getInputStream());
    }

    public abstract void execute() throws IOException;
}
