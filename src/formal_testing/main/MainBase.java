package formal_testing.main;

import formal_testing.*;
import formal_testing.coverage.CoveragePoint;
import formal_testing.coverage.DataCoveragePoint;
import formal_testing.coverage.FlowCoveragePoint;
import formal_testing.enums.Language;
import formal_testing.enums.NuSMVMode;
import formal_testing.runner.NuSMVRunner;
import formal_testing.runner.Runner;
import formal_testing.runner.RunnerResult;
import formal_testing.value.BooleanValue;
import formal_testing.value.IntegerValue;
import formal_testing.variable.BooleanVariable;
import formal_testing.variable.IntegerVariable;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;
import org.apache.commons.lang3.tuple.Pair;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

abstract class MainBase {
    @Option(name = "--language", aliases = { "-l" }, usage = "PROMELA, NUSMV, NUXMV", metaVar = "<language>",
            required = true)
    private String language;

    @Option(name = "--nusmv_mode", usage = "NuSMV/nuXmv mode: LTL, CTL, BMC (default)", metaVar = "<mode>")
    private String nuSMVMode = NuSMVMode.BMC.toString();

    @Option(name = "--panO", usage = "optimization level to compile pan, default = 2", metaVar = "<number>")
    private int panO = 2;

    @Option(name = "--dynamic", handler = BooleanOptionHandler.class, usage = "enable NuSMV -dynamic")
    private boolean dynamic;

    @Option(name = "--coi", handler = BooleanOptionHandler.class, usage = "enable NuSMV -coi")
    private boolean coi;

    ProblemData data;

    protected abstract void launcher() throws IOException, InterruptedException;

    void run(String[] args) {
        if (!parseArgs(args)) {
            return;
        }
        setup();
        try {
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

    void loadData(String configurationFilename, String headerFilename, String plantModelFilename,
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

    private List<String> propsFromCode(String code) {
        return Settings.LANGUAGE == Language.PROMELA ? promelaPropsFromCode(code) : nuSMVPropsFromCode(code);
    }

    private List<String> promelaPropsFromCode(String code) {
        return Arrays.stream(code.split("\n"))
                .filter(s -> s.matches("ltl .*\\{.*\\}.*"))
                .map(s -> s.replaceAll("^ltl ", "").replaceAll(" .*$", ""))
                .collect(Collectors.toList());
    }

    private List<String> nuSMVPropsFromCode(String code) {
        return Arrays.stream(code.split("\n"))
                .filter(s -> s.matches("(LTLSTEP|CTLSPEC|SPEC|PSLSPEC) .*"))
                .collect(Collectors.toList());
    }

    private String nondetSelectionPromela() {
        final StringBuilder sb = new StringBuilder();
        for (Variable<?> var : data.conf.nondetVars) {
            sb.append("if\n");
            for (String value : var.stringValues()) {
                sb.append(":: ").append(var.indexedName()).append(" = ").append(value).append(";\n");
            }
            sb.append("fi\n");
        }
        return sb.toString();
    }

    private List<CoveragePoint> coveragePoints(boolean includeInternal, boolean valuePairCoverage, int coverageClaims) {
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
                final Variable<?> varI = variables.get(i);
                for (int j = i + 1; j < variables.size(); j++) {
                    final Variable<?> varJ = variables.get(j);
                    for (String valueI : varI.stringValues()) {
                        for (String valueJ : varJ.stringValues()) {
                            result.add(new DataCoveragePoint(varI, valueI, varJ, valueJ));
                        }
                    }
                }
            }
        } else {
            for (Variable<?> var : variables) {
                result.addAll(var.stringValues().stream().map(value -> new DataCoveragePoint(var, value))
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

    private void printVars(StringBuilder sb, String indent, List<Variable<?>> variables, String text) {
        if (!variables.isEmpty()) {
            sb.append(indent).append(Settings.LANGUAGE.commentSymbol).append(" ").append(text).append("\n");
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

    String modelCode(boolean testing, boolean nondetSelection, boolean spec, String testHeader,
                     String testBody, boolean plantCodeCoverage, boolean controllerCodeCoverage,
                     CodeCoverageCounter counter) {
        return Settings.LANGUAGE == Language.PROMELA
                ? promelaModelCode(testing, nondetSelection, spec, testHeader, testBody, plantCodeCoverage,
                controllerCodeCoverage, counter)
                : nuSMVModelCode(testing, spec, testHeader, testBody);
    }

    private <T> List<T> merge(List<List<T>> list) {
        final List<T> merged = new ArrayList<>();
        list.forEach(merged::addAll);
        return merged;
    }

    private String argList(List<List<Variable<?>>> variables) {
        final List<Variable<?>> allVars = merge(variables);
        final Set<String> indicesRemoved = allVars.stream().map(v -> v.name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return "(" + String.join(", ", indicesRemoved) + ")";
    }

    private final BooleanVariable testingVar = new BooleanVariable("_test_passed", BooleanValue.FALSE);
    private final Variable<?> testIndexVar = new IntegerVariable("_test_index", new IntegerValue(0), 0, 1, false, 1, 0);
    private final Variable<?> testStepVar = new IntegerVariable("_test_step", new IntegerValue(0), 0, 1, false, 1, 0);

    private String nuSMVModelCode(boolean testing, boolean spec, String testHeader, String testBody) {
        final StringBuilder code = new StringBuilder();

        // can contain e.g. some module declarations
        if (!data.header.isEmpty()) {
            code.append(data.header).append("\n");
        }

        final String nondetArgList = argList(Arrays.asList(data.conf.nondetVars,
                testBody != null ? Arrays.asList(testIndexVar, testStepVar) : Collections.emptyList(),
                testing ? Collections.singletonList(testingVar) : Collections.emptyList()));
        code.append("MODULE _NONDET_VAR_SELECTION").append(nondetArgList).append("\n");
        if (testBody != null) {
            code.append(testBody);
        }
        code.append("\n");

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
        code.append("    _nondet_var_selection: _NONDET_VAR_SELECTION").append(nondetArgList).append(";\n");
        code.append("    _plant: _PLANT").append(argList(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.outputVars))).append(";\n");
        code.append("    _controller: _CONTROLLER").append(argList(Arrays.asList(data.conf.inputVars,
                data.conf.controllerInternalVars, data.conf.outputVars))).append(";\n");
        if (testHeader != null) {
            code.append(testHeader).append("\n");
        }

        code.append("ASSIGN\n");
        for (Variable<?> var : merge(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.controllerInternalVars, data.conf.plantInternalVars, data.conf.outputVars,
                testing ?  Collections.singletonList(testingVar) : Collections.emptyList()))) {
            code.append("    init(").append(var.indexedName()).append(") := ")
                    .append(var.initialValue()).append(";\n");
        }

        if (spec) {
            code.append("\n").append(data.spec);
        }
        if (testing) {
            code.append("\nCTLSPEC AF _test_passed\n");
        }

        return code.toString();
    }

    private String promelaModelCode(boolean testing, boolean nondetSelection, boolean spec, String testHeader,
                                    String testBody, boolean plantCodeCoverage, boolean controllerCodeCoverage,
                                    CodeCoverageCounter counter) {
        final StringBuilder code = new StringBuilder();

        final Set<String> mtypeValues = new LinkedHashSet<>();

        merge(Arrays.asList(data.conf.inputVars, data.conf.outputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.controllerInternalVars)).stream()
                .filter(v -> v instanceof SetVariable).forEach(v -> mtypeValues.addAll(v.stringValues()));

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
        if (testHeader != null) {
            code.append("\n").append(testHeader);
        }
        code.append("\n").append("\n").append("init { do :: atomic {\n");
        if (nondetSelection) {
            code.append(Util.indent(nondetSelectionPromela())).append("\n");
        }
        if (testBody != null) {
            code.append(Util.indent(testBody)).append("\n");
        }
        code.append("\n").append(Util.indent(plantCode)).append("\n\n")
                .append(Util.indent(controllerCode)).append("\n").append("} od }\n");
        if (spec) {
            code.append("\n").append(data.spec);
        }
        if (testing) {
            code.append("\n").append("ltl test_passed { <>_test_passed }\n");
        }

        if (counter != null) {
            counter.coverageClaims = coverageClaims;
        }
        return code.toString();
    }

    String usualModelCode(CodeCoverageCounter counter, boolean plantCodeCoverage, boolean controllerCodeCoverage) {
        return modelCode(false, true, false, null, null, plantCodeCoverage,
                controllerCodeCoverage, counter);
    }

    int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints, int steps, boolean plantCodeCoverage,
                        boolean controllerCodeCoverage) throws IOException {
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        final String code = modelCode(false, false, false, tc.header(false), tc.body(false, data.conf),
                plantCodeCoverage, controllerCodeCoverage, null);

        int newCovered = 0;
        final boolean negate = true;
        try (final Runner runner = Runner.create(data, code, uncovered, steps, negate, 0)) {
            System.out.println(runner.creationReport());
            final String prefix = "    (" + uncovered.size() + ") ";
            System.out.print(prefix);
            for (int i = 0; i < uncovered.size(); i++) {
                final CoveragePoint cp = uncovered.get(i);
                final RunnerResult result = runner.verify(cp, steps, negate, steps, true);
                if (result.found()) {
                    cp.cover();
                    newCovered++;
                    System.out.print("+");
                } else {
                    System.out.print("-");
                }
                System.out.print(i > 0 && i % 150 == 0 ? ("\n" +
                        new String(new char[prefix.length()]).replace('\0', ' ')) : "");
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
            usualModelCode(counter, plantCodeCoverage, controllerCodeCoverage);
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

    private void setup() {
        try {
            Settings.LANGUAGE = Language.valueOf(language);
            System.out.println("Language: " + Settings.LANGUAGE);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported language " + language);
        }
        try {
            Settings.NUSMV_MODE = NuSMVMode.valueOf(nuSMVMode);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported NuSMV mode " + nuSMVMode);
        }
        Settings.PAN_OPTIMIZATION_LEVEL = panO;
        Settings.NUSMV_DYNAMIC = dynamic;
        Settings.NUSMV_COI = coi;
    }

    void verifyAll(String code, int timeout, boolean verbose, Integer verificationBMCK) throws IOException {
        try (final Runner runner = Runner.create(data, code, timeout)) {
            if (runner instanceof NuSMVRunner) {
                final List<String> result = ((NuSMVRunner) runner).verifyAll(!verbose, verificationBMCK);
                result.stream().filter(s -> verbose || s.startsWith("-- specification ")).forEach(System.out::println);
            } else {
                for (String prop : propsFromCode(code)) {
                    final RunnerResult result = runner.verify(prop, Integer.MAX_VALUE, !verbose);
                    System.out.println(result.measurement());
                    System.out.println("*** " + prop + " = " + result.outcome() + " ***");
                    if (verbose) {
                        result.log().forEach(System.out::println);
                    }
                }
            }
            System.out.println(runner.totalResourceReport());
        }
    }
}
