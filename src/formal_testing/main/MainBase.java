package formal_testing.main;

import formal_testing.*;
import formal_testing.coverage.CoveragePoint;
import formal_testing.coverage.DataCoveragePoint;
import formal_testing.coverage.FlowCoveragePoint;
import formal_testing.variable.BooleanVariable;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;
import org.apache.commons.lang3.tuple.Pair;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class MainBase {
    @Argument(usage = "configuration filename", metaVar = "<filename>", required = true, index = 0)
    protected String configurationFilename;

    @Argument(usage = "header filename", metaVar = "<filename>", required = true, index = 1)
    protected String headerFilename;

    @Argument(usage = "plant model filename", metaVar = "<filename>", required = true, index = 2)
    protected String plantModelFilename;

    @Argument(usage = "controller model filename", metaVar = "<filename>", required = true, index = 3)
    protected String controllerModelFilename;

    @Argument(usage = "specification filename", metaVar = "<filename>", required = true, index = 4)
    protected String specFilename;

    @Option(name = "--language", aliases = { "-l" }, usage = "PROMELA, NUSMV", metaVar = "<language>")
    private String language;

    protected ProblemData data;

    protected abstract void launcher() throws IOException, InterruptedException;

    public void run(String[] args) {
        if (!parseArgs(args)) {
            return;
        }
        try {
            setLanguage(language);
            launcher();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean parseArgs(String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            return true;
        } catch (CmdLineException e) {
            System.out.print("Usage: java -jar <jar filename> ");
            parser.printSingleLineUsage(System.out);
            System.out.println();
            parser.printUsage(System.out);
            return false;
        }
    }

    protected void loadData(String configurationFilename, String headerFilename, String plantModelFilename,
                            String controllerModelFilename, String specFilename) throws IOException {
        final Configuration conf = Configuration.fromFile(configurationFilename);
        System.out.println("Configuration:");
        System.out.println(conf);
        System.out.println();

        data = new ProblemData(conf,
                new String(Files.readAllBytes(Paths.get(headerFilename))),
                new String(Files.readAllBytes(Paths.get(plantModelFilename))),
                new String(Files.readAllBytes(Paths.get(controllerModelFilename))),
                new String(Files.readAllBytes(Paths.get(specFilename)))
        );
    }

    protected List<String> promelaPropsFromCode(String code) {
        return Arrays.stream(code.split("\n"))
                .filter(s -> s.matches("ltl .*\\{.*\\}.*"))
                .map(s -> s.replaceAll("^ltl ", "").replaceAll(" .*$", ""))
                .collect(Collectors.toList());
    }

    private String nondetSelectionPromela() {
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

    protected List<CoveragePoint> coveragePoints(boolean includeInternal, boolean valuePairCoverage,
                                                 int coverageClaims) {
        final List<Variable> variables = new ArrayList<>();
        variables.addAll(data.conf.inputVars);
        variables.addAll(data.conf.nondetVars);
        variables.addAll(data.conf.outputVars);
        if (includeInternal) {
            variables.addAll(data.conf.plantInternalVars);
            variables.addAll(data.conf.controllerInternalVars);
        }

        final List<CoveragePoint> result = new ArrayList<>();

        if (valuePairCoverage && variables.size() > 1) {
            for (int i = 0; i < variables.size(); i++) {
                final Variable varI = variables.get(i);
                for (int j = i + 1; j < variables.size(); j++) {
                    final Variable varJ = variables.get(j);
                    for (String valueI : varI.promelaValues()) {
                        for (String valueJ : varJ.promelaValues()) {
                            result.add(new DataCoveragePoint(varI, valueI, varJ, valueJ));
                        }
                    }
                }
            }
        } else {
            for (Variable var : variables) {
                result.addAll(var.promelaValues().stream().map(value -> new DataCoveragePoint(var, value))
                        .collect(Collectors.toList()));
            }
        }

        for (int i = 0; i < coverageClaims; i++) {
            result.add(new FlowCoveragePoint(i));
        }
        return result;
    }

    private String varFormat(String s, String indent) {
        return s.isEmpty() ? "" : (indent + s + "\n");
    }

    private void printVars(StringBuilder sb, String indent, List<Variable> variables, String text) {
        if (!variables.isEmpty()) {
            sb.append(indent + Util.LANGUAGE.commentSymbol).append(" ").append(text).append("\n");
        }
        variables.forEach(v -> sb.append(varFormat(v.toLanguageString(), indent)));
    }

    private void printAllVars(StringBuilder sb, String indent) {
        printVars(sb, indent, data.conf.inputVars, "Inputs");
        printVars(sb, indent, data.conf.outputVars, "Outputs");
        printVars(sb, indent, data.conf.nondetVars, "Nondeterministic variables");
        printVars(sb, indent, data.conf.plantInternalVars, "Plant internal variables");
        printVars(sb, indent, data.conf.controllerInternalVars, "Controller internal variables");
    }

    private Pair<String, Integer> promelaInstrument(String code, int firstClaimIndex) {
        int nextClaimIndex = firstClaimIndex;
        final Pattern p = Pattern.compile("::");
        final Matcher m = p.matcher(code);
        final StringBuilder transformed = new StringBuilder();
        int lastPos = 0;
        while (m.find()) {
            transformed.append(code.substring(lastPos, m.end()));
            int index = m.end();
            int balance = 0;
            while (index < code.length()) {
                char cCur = code.charAt(index);
                char cLast = code.charAt(index - 1);
                balance += cCur == '(' ? 1 : cCur == ')' ? -1 : 0;
                if (balance == 0 && cLast == '-' && cCur == '>') {
                    transformed.append(code.substring(m.end(), index + 1)).append(" ").append("_cover[")
                            .append(nextClaimIndex++).append("] = true; ");
                    break;
                }
                index++;
            }
            lastPos = index + 1;
        }
        transformed.append(code.substring(lastPos));

        return Pair.of(transformed.toString(), nextClaimIndex);
    }

    protected String modelCode(boolean testing, boolean nondetSelection, boolean spec, Optional<String> testHeader,
                               Optional<String> testBody, boolean plantCodeCoverage, boolean controllerCodeCoverage,
                               Optional<CodeCoverageCounter> counter) {
        return Util.LANGUAGE == Language.PROMELA
                ? promelaModelCode(testing, nondetSelection, spec, testHeader, testBody, plantCodeCoverage,
                controllerCodeCoverage, counter)
                : nusmvModelCode(testing, nondetSelection, spec, testHeader, testBody);
    }

    private <T> List<T> merge(List<List<T>> list) {
        final List<T> merged = new ArrayList<>();
        list.forEach(merged::addAll);
        return merged;
    }

    private String argList(List<List<Variable>> variables) {
        final List<Variable> allVars = merge(variables);
        final Set<String> indicesRemoved = allVars.stream().map(v -> v.name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return "(" + String.join(", ", indicesRemoved) + ")";
    }

    private final BooleanVariable testingVar = new BooleanVariable("_test_passed");

    private String nusmvModelCode(boolean testing, boolean nondetSelection, boolean spec, Optional<String> testHeader,
                                    Optional<String> testBody) {
        final StringBuilder code = new StringBuilder();

        // can contain e.g. some module declarations
        code.append(data.header).append("\n");

        code.append("MODULE _NONDET_VAR_SELECTION").append(argList(Collections.singletonList(data.conf.nondetVars)))
                .append("\n");
        if (testBody.isPresent()) {
            code.append(testBody.get()).append("\n");
        }

        code.append("MODULE _PLANT").append(argList(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.outputVars))).append("\n")
                .append(Util.indent(data.plantModel)).append("\n\n");

        code.append("MODULE _CONTROLLER").append(argList(Arrays.asList(data.conf.inputVars,
                data.conf.controllerInternalVars, data.conf.outputVars))).append("\n")
                .append(Util.indent(data.controllerModel)).append("\n\n");

        code.append("MODULE main\n").append("VAR\n");
        printAllVars(code, "    ");
        code.append("    -- Misc\n");
        if (testing) {
            code.append(varFormat(testingVar.toNusmvString(), "    "));
        }
        code.append("    _nondet_var_selection: _NONDET_SELECTION")
                .append(argList(Collections.singletonList(data.conf.nondetVars)))
                .append(";\n");
        code.append("    _plant: _PLANT").append(argList(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.outputVars))).append(";\n");
        code.append("    _controller: _CONTROLLER").append(argList(Arrays.asList(data.conf.inputVars,
                data.conf.controllerInternalVars, data.conf.outputVars))).append(";\n");

        code.append("DEFINE\n");
        for (Variable var : merge(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.controllerInternalVars, data.conf.plantInternalVars, data.conf.outputVars,
                testing ? Collections.emptyList() : Collections.singletonList(testingVar)))) {
            code.append("    init(").append(var.indexedName()).append(") := ")
                    .append(var.nusmvInitialValue()).append(";\n");
        }

        if (spec) {
            code.append("\n").append(data.spec);
        }
        if (testing) {
            code.append("\n").append("CTLSPEC AF _test_passed\n");
        }

        return code.toString();
    }

    private String promelaModelCode(boolean testing, boolean nondetSelection, boolean spec, Optional<String> testHeader,
                               Optional<String> testBody, boolean plantCodeCoverage, boolean controllerCodeCoverage,
                               Optional<CodeCoverageCounter> counter) {
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

        printAllVars(code, "");

        int coverageClaims = 0;

        String plantCode = data.plantModel;
        if (plantCodeCoverage) {
            final Pair<String, Integer> p = promelaInstrument(plantCode, coverageClaims);
            plantCode = p.getKey();
            coverageClaims = p.getValue();
        }

        String controllerCode = data.controllerModel;
        if (controllerCodeCoverage) {
            final Pair<String, Integer> p = promelaInstrument(controllerCode, coverageClaims);
            controllerCode = p.getKey();
            coverageClaims = p.getValue();
        }

        if (coverageClaims > 0) {
            code.append("\n").append("bool _cover[").append(coverageClaims).append("];\n");
        }
        if (testing) {
            code.append("\n").append(varFormat(testingVar.toPromelaString(), ""));
        }
        code.append("\n").append(data.header);
        if (testHeader.isPresent()) {
            code.append("\n").append(testHeader.get());
        }
        code.append("\n").append("\n").append("init { do :: atomic {\n");
        if (nondetSelection) {
            code.append(Util.indent(nondetSelectionPromela())).append("\n");
        }
        if (testBody.isPresent()) {
            code.append(Util.indent(testBody.get())).append("\n");
        }
        code.append("\n").append(Util.indent(plantCode)).append("\n\n")
                .append(Util.indent(controllerCode)).append("\n").append("} od }\n");
        if (spec) {
            code.append("\n").append(data.spec);
        }
        if (testing) {
            code.append("\n").append("ltl test_passed { <>_test_passed }\n");
        }

        if (counter.isPresent()) {
            counter.get().coverageClaims = coverageClaims;
        }
        return code.toString();
    }

    public String usualModelCode(Optional<CodeCoverageCounter> counter, boolean plantCodeCoverage,
                                  boolean controllerCodeCoverage) {
        return modelCode(false, true, false, Optional.empty(), Optional.empty(), plantCodeCoverage,
                controllerCodeCoverage, counter);
    }

    protected List<String> coverageProperties(List<CoveragePoint> coveragePoints, int steps) {
        return coveragePoints.stream().map(
                cp -> steps == 0 ? cp.promelaLtlProperty() : cp.promelaLtlProperty(steps, false))
                .collect(Collectors.toList());
    }


    protected int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints, int steps,
                                  boolean plantCodeCoverage, boolean controllerCodeCoverage) throws IOException {
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        final String code = modelCode(false, false, false, Optional.of(tc.promelaHeader(false)),
                Optional.of(tc.promelaBody(false)), plantCodeCoverage, controllerCodeCoverage, Optional.empty());

        final List<String> claims = coverageProperties(uncovered, steps);

        int newCovered = 0;
        int lines = 0;
        try (final SpinRunner spinRunner = new SpinRunner(code, uncovered, claims, 0, 2)) {
            System.out.println("    " + spinRunner.creationMeasurement);
            final String prefix = "    (" + uncovered.size() + ") ";
            System.out.print(prefix);
            for (CoveragePoint cp : uncovered) {
                final List<String> result = spinRunner.pan(cp.promelaLtlName());
                for (String line : result) {
                    final String suffix = lines > 0 && lines % 150 == 0
                            ? ("\n" + new String(new char[prefix.length()]).replace('\0', ' ')) : "";
                    if (line.equals("*** " + cp.promelaLtlName() + " = TRUE ***")) {
                        lines++;
                        cp.cover();
                        newCovered++;
                        System.out.print("+" + suffix);
                    } else if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                        lines++;
                        System.out.print("-" + suffix);
                    }
                }
            }
            System.out.println();
        }
        return newCovered;
    }

    private static class CodeCoverageCounter {
        int coverageClaims;
    }

    class CoverageInfo {
        final List<CoveragePoint> coveragePoints;
        final int totalPoints;
        int coveredPoints = 0;

        CoverageInfo(boolean plantCodeCoverage, boolean controllerCodeCoverage, boolean includeInternal,
                     boolean valuePairCoverage) {
            final CodeCoverageCounter counter = new CodeCoverageCounter();
            usualModelCode(Optional.of(counter), plantCodeCoverage, controllerCodeCoverage);
            coveragePoints = coveragePoints(includeInternal, valuePairCoverage, counter.coverageClaims);
            totalPoints = coveragePoints.size();
        }

        void report() {
            System.out.println("  Covered points: " + coveredPoints + " / " + totalPoints);
            if (coveredPoints < totalPoints) {
                System.out.println("  Not covered:");
                coveragePoints.stream().filter(cp -> !cp.covered()).forEach(s -> System.out.println("  " + s));
            }
        }

        boolean allCovered() {
            return coveredPoints == totalPoints;
        }
    }

    protected void setLanguage(String language) {
        try {
            Util.LANGUAGE = Language.valueOf(language);
            System.out.println("Language: " + Util.LANGUAGE);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported language " + language);
        }
    }
}
