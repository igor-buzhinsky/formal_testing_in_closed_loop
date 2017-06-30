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
    public final ProblemData data;

    public Command(ProblemData data) {
        this.data = data;
    }

    protected List<String> propsFromCode(String code) {
        return Arrays.stream(code.split("\n"))
                .filter(s -> s.matches("ltl .*\\{.*\\}.*"))
                .map(s -> s.replaceAll("^ltl ", "").replaceAll(" .*$", ""))
                .collect(Collectors.toList());
    }

    private String nondetSelection() {
        final StringBuilder sb = new StringBuilder();
        for (Variable var : data.conf.nondetVars) {
            sb.append("if\n");
            for (String value : var.promelaValues()) {
                sb.append(":: ").append(var.indexedName()).append(" = ").append(value).append(";\n");
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

    private void printVars(StringBuilder sb, List<Variable> variables, String text) {
        final Function<String, String> addNl = s -> s.isEmpty() ? "" : (s + "\n");
        if (!variables.isEmpty()) {
            sb.append("// ").append(text).append("\n");
        }
        variables.forEach(v -> sb.append(addNl.apply(v.toPromelaString())));
    }

    protected String modelCode(boolean testing, boolean nondetSelection, boolean spec, Optional<String> testHeader,
                               Optional<String> testBody) {
        final StringBuilder code = new StringBuilder();

        final Set<String> mtypeValues = new LinkedHashSet<>();
        for (List<Variable> list : Arrays.asList(data.conf.inputVars, data.conf.outputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.controllerInternalVars)) {
            list.stream().filter(var -> var instanceof SetVariable)
                    .forEach(var -> mtypeValues.addAll(var.promelaValues()));
        }
        if (!mtypeValues.isEmpty()) {
            code.append("mtype ").append(mtypeValues.toString().replace("[", "{").replace("]", "}")).append("\n");
        }

        printVars(code, data.conf.inputVars, "Inputs");
        printVars(code, data.conf.outputVars, "Outputs");
        printVars(code, data.conf.nondetVars, "Nondeterministic variables");
        printVars(code, data.conf.plantInternalVars, "Plant internal variables");
        printVars(code, data.conf.controllerInternalVars, "Controller internal variables");

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

    protected String coverageProperties(List<CoveragePoint> coveragePoints) {
        return String.join("\n", coveragePoints.stream().map(CoveragePoint::promelaLtlProperty)
                .collect(Collectors.toList()));
    }

    protected String coverageProperties(List<CoveragePoint> coveragePoints, int steps) {
        return String.join("\n", coveragePoints.stream().map(cp -> cp.promelaLtlProperty(steps, false))
                .collect(Collectors.toList()));
    }

    public abstract void execute() throws IOException;
}
