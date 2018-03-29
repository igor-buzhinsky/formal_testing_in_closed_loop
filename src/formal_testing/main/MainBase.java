package formal_testing.main;

import formal_testing.*;
import formal_testing.coverage.CoveragePoint;
import formal_testing.coverage.FlowCoveragePoint;
import formal_testing.coverage.FormulaCoveragePoint;
import formal_testing.enums.Language;
import formal_testing.formula.BinaryOperator;
import formal_testing.formula.LTLFormula;
import formal_testing.formula.UnaryOperator;
import formal_testing.generated.nusmv_ltlLexer;
import formal_testing.generated.nusmv_ltlParser;
import formal_testing.runner.Runner;
import formal_testing.runner.RunnerResult;
import formal_testing.value.BooleanValue;
import formal_testing.value.IntegerValue;
import formal_testing.value.Value;
import formal_testing.variable.BooleanVariable;
import formal_testing.variable.IntegerVariable;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.apache.commons.lang3.tuple.Pair;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

    @Option(name = "--panO", usage = "optimization level to compile pan, default = 2", metaVar = "<number>")
    private int panO = 2;

    @Option(name = "--dynamic", handler = BooleanOptionHandler.class, usage = "enable NuSMV -dynamic")
    private boolean dynamic;

    @Option(name = "--coi", handler = BooleanOptionHandler.class, usage = "enable NuSMV -coi")
    private boolean coi;

    ProblemData data;

    static void fillRandom(TestSuite ts, ProblemData data, Long seed, int length, int number) {
        final Random random = seed == null ? new Random() : new Random(seed);
        final List<List<? extends Value>> allValues = data.conf.nondetVars.stream()
                .map(Variable::values).collect(Collectors.toList());
        for (int i = 0; i < number; i++) {
            final TestCase tc = new TestCase(data.conf, false);
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < data.conf.nondetVars.size(); k++) {
                    final Variable<?> var = data.conf.nondetVars.get(k);
                    tc.addValue(var.indexedName(), allValues.get(k).get(random.nextInt(allValues.get(k).size())));
                }
            }
            ts.add(tc);
        }
    }

    private void setup() {
        try {
            Settings.LANGUAGE = Language.valueOf(language);
            System.out.println("Language: " + Settings.LANGUAGE);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported language " + language);
        }
        Settings.PAN_OPTIMIZATION_LEVEL = panO;
        Settings.NUSMV_DYNAMIC = dynamic;
        Settings.NUSMV_COI = coi;
    }

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
        return Arrays.stream(code.split("\n")).filter(s -> s.matches("[A-Z]+SPEC .*")).collect(Collectors.toList());
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

    private List<FormulaCoveragePoint> specCoveragePoints(String nusmvSpecCoverage) {
        final Set<LTLFormula> subformulas = new LinkedHashSet<>();

        class MyRecognitionException extends RuntimeException {
            MyRecognitionException(String message) {
                super(message);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(nusmvSpecCoverage)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("--.*$", "").trim();
                if (!line.startsWith("LTLSPEC ")) {
                    continue;
                }
                //System.out.println(line);

                try (InputStream in = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8))) {
                    final nusmv_ltlLexer lexer = new nusmv_ltlLexer(CharStreams.fromStream(in, StandardCharsets.UTF_8));
                    final CommonTokenStream tokens = new CommonTokenStream(lexer);
                    final nusmv_ltlParser parser = new nusmv_ltlParser(tokens);
                    parser.addErrorListener(new ANTLRErrorListener() {
                        @Override
                        public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s,
                                                RecognitionException e) {
                            throw new MyRecognitionException("syntaxError");
                        }

                        @Override
                        public void reportAmbiguity(Parser parser, DFA dfa, int i, int i1, boolean b, BitSet bitSet,
                                                    ATNConfigSet atnConfigSet) {
                            throw new MyRecognitionException("reportAmbiguity");
                        }

                        @Override
                        public void reportAttemptingFullContext(Parser parser, DFA dfa, int i, int i1, BitSet bitSet,
                                                                ATNConfigSet atnConfigSet) {
                            throw new MyRecognitionException("reportAttemptingFullContext");
                        }

                        @Override
                        public void reportContextSensitivity(Parser parser, DFA dfa, int i, int i1, int i2,
                                                             ATNConfigSet atnConfigSet) {
                            throw new MyRecognitionException("reportContextSensitivity");
                        }
                    });
                    try {
                        final LTLFormula f = parser.formula().f;
                        f.allBooleanSubformulas(subformulas);
                    } catch (NullPointerException | RecognitionException e) {
                        e.printStackTrace();
                    } catch (MyRecognitionException e) {
                        System.err.println("LTL parse error: " + e.getMessage()
                                + " (may be connected with support of a reduced subset of LTL)");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add negations
        subformulas.addAll(subformulas.stream().map(f -> f instanceof UnaryOperator
                ? ((UnaryOperator) f).argument : new UnaryOperator("!", f)).collect(Collectors.toList()));

        return subformulas.stream().map(FormulaCoveragePoint::new).collect(Collectors.toList());
    }

    private List<CoveragePoint> coveragePoints(boolean includeInternal, boolean valuePairCoverage, int coverageClaims,
                                               String nusmvSpecCoverage, int maxGoals) {
        final List<Variable> variables = new ArrayList<>();
        variables.addAll(data.conf.inputVars);
        variables.addAll(data.conf.nondetVars);
        variables.addAll(data.conf.outputVars);
        if (includeInternal) {
            variables.addAll(data.conf.plantInternalVars);
            variables.addAll(data.conf.controllerInternalVars);
        }

        final List<FormulaCoveragePoint> formulas = new ArrayList<>();

        if (valuePairCoverage && variables.size() > 1) {
            for (int i = 0; i < variables.size(); i++) {
                final Variable<?> varI = variables.get(i);
                final List<LTLFormula> listI = oneVariableGoalFormulas(varI, maxGoals);
                for (int j = i + 1; j < variables.size(); j++) {
                    final Variable<?> varJ = variables.get(j);
                    final List<LTLFormula> listJ = oneVariableGoalFormulas(varJ, maxGoals);
                    for (LTLFormula fI : listI) {
                        for (LTLFormula fJ : listJ) {
                            formulas.add(new FormulaCoveragePoint(new BinaryOperator("&", fI, fJ)));
                        }
                    }
                }
            }
            // TODO add equivalence classes for pair coverage (if this is ever needed)
            // TODO maybe remove pair coverage completely?
        } else {
            for (Variable<?> var : variables) {
                final Set<CoveragePoint> cps = oneVariableGoalFormulas(var, maxGoals).stream()
                        .map(FormulaCoveragePoint::new).collect(Collectors.toCollection(LinkedHashSet::new));
                for (CoveragePoint cp : cps) {
                    final FormulaCoveragePoint casted = (FormulaCoveragePoint) cp;
                    casted.setEquivalenceClass(cps);
                    formulas.add(casted);
                }
            }
        }

        if (nusmvSpecCoverage != null) {
            formulas.addAll(specCoveragePoints(nusmvSpecCoverage));
        }

        // filter out equal FORMULA coverage points
        final Map<String, FormulaCoveragePoint> unique = new LinkedHashMap<>();
        for (FormulaCoveragePoint cp : formulas) {
            final String key = cp.toString();
            if (!unique.containsKey(key)) {
                unique.put(key, cp);
            }
        }
        final List<CoveragePoint> result = new ArrayList<>(unique.values());

        for (int i = 0; i < coverageClaims; i++) {
            result.add(new FlowCoveragePoint(i));
        }

        return result;
    }

    private List<LTLFormula> oneVariableGoalFormulas(Variable<?> var, int maxGoals) {
        final List<? extends Value> values = var.values();
        final List<LTLFormula> result = new ArrayList<>();
        if (values.size() < maxGoals || !(var instanceof IntegerVariable)) {
            values.forEach(value -> result.add(LTLFormula.equality(var, value)));
        } else {
            for (int i = 0; i < maxGoals; i++) {
                final int fromIndex = i * values.size() / maxGoals;
                final int toIndex = (i + 1) * values.size() / maxGoals - 1;
                result.add(LTLFormula.between(var, values.get(fromIndex), values.get(toIndex)));
            }
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
            transformed.append(code, lastPos, m.end());
            int index = m.end();
            int balance = 0;
            while (index < code.length()) {
                char cCur = code.charAt(index);
                char cLast = code.charAt(index - 1);
                balance += cCur == '(' ? 1 : cCur == ')' ? -1 : 0;
                if (balance == 0 && cLast == '-' && cCur == '>') {
                    transformed.append(code, m.end(), index + 1).append(" _cover[")
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
                     CodeCoverageCounter counter, boolean includePrintf) {
        return Settings.LANGUAGE == Language.PROMELA
                ? promelaModelCode(testing, nondetSelection, spec, testHeader, testBody, plantCodeCoverage,
                controllerCodeCoverage, counter, includePrintf)
                : nuSMVModelCode(testing, spec, testHeader, testBody);
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

        final String nondetArgList = Util.argList(Arrays.asList(data.conf.nondetVars,
                testBody != null ? Arrays.asList(testIndexVar, testStepVar) : Collections.emptyList(),
                testing ? Collections.singletonList(testingVar) : Collections.emptyList()));
        code.append("MODULE _NONDET_VAR_SELECTION").append(nondetArgList).append("\n");
        if (testBody != null) {
            code.append(testBody);
        }
        code.append("\n");

        code.append("MODULE _PLANT").append(Util.argList(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.outputVars))).append("\n")
                .append(Util.indent(data.plantModel)).append("\n\n");

        code.append("MODULE _CONTROLLER").append(Util.argList(Arrays.asList(data.conf.inputVars,
                data.conf.controllerInternalVars, data.conf.outputVars))).append("\n")
                .append(Util.indent(data.controllerModel)).append("\n\n");

        code.append("MODULE main\n").append("VAR\n");
        printAllVars(code, "    ");
        code.append("    -- Misc\n");
        if (testing) {
            code.append(varFormat(testingVar.toNusmvString(), "    "));
        }
        code.append("    _nondet_var_selection: _NONDET_VAR_SELECTION").append(nondetArgList).append(";\n");
        code.append("    _plant: _PLANT").append(Util.argList(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
                data.conf.plantInternalVars, data.conf.outputVars))).append(";\n");
        code.append("    _controller: _CONTROLLER").append(Util.argList(Arrays.asList(data.conf.inputVars,
                data.conf.controllerInternalVars, data.conf.outputVars))).append(";\n");
        if (testHeader != null) {
            code.append(testHeader).append("\n");
        }

        code.append("ASSIGN\n");
        for (Variable<?> var : Util.merge(Arrays.asList(data.conf.inputVars, data.conf.nondetVars,
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

    private void promelaPrintf(StringBuilder sb, String indent) {
        sb.append(indent).append("d_step {\n");
        sb.append(indent).append("    printf(\"\\n  -> New State <-\\n\");\n");
        for (Variable<?> var : data.conf.allVariables()) {
            final String symbol = var instanceof SetVariable ? "e" : "d";
            sb.append(indent).append("    printf(\"").append(var.indexedName()).append(" = %").append(symbol)
                    .append("\\n\", ").append(var.indexedName()).append(");\n");
        }
        sb.append(indent).append("}\n");
    }

    private String promelaModelCode(boolean testing, boolean nondetSelection, boolean spec, String testHeader,
                                    String testBody, boolean plantCodeCoverage, boolean controllerCodeCoverage,
                                    CodeCoverageCounter counter, boolean includePrintf) {
        final StringBuilder code = new StringBuilder();

        final Set<String> mtypeValues = new LinkedHashSet<>();

        Util.merge(Arrays.asList(data.conf.inputVars, data.conf.outputVars, data.conf.nondetVars,
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
                .append(Util.indent(controllerCode)).append("\n");
        if (includePrintf) {
            promelaPrintf(code, "    ");
        }
        code.append("} od }\n");
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
        return modelCode(false, true, false, null, null, plantCodeCoverage, controllerCodeCoverage, counter, false);
    }

    int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints, Integer steps, boolean plantCodeCoverage,
                        boolean controllerCodeCoverage) throws IOException {
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        final String code = modelCode(false, false, false, tc.header(false), tc.body(false, data.conf),
                plantCodeCoverage, controllerCodeCoverage, null, false);

        int newCovered = 0;
        try (final Runner runner = Runner.create(data, code, uncovered, steps)) {
            System.out.println(runner.creationReport());
            final String prefix = "    (" + uncovered.size() + ") ";
            System.out.print(prefix);
            for (int i = 0; i < uncovered.size(); i++) {
                final CoveragePoint cp = uncovered.get(i);
                final RunnerResult result = runner.checkCoverage(cp);
                if (i > 0 && i % 100 == 0) {
                    System.out.print("\n" + new String(new char[prefix.length()]).replace('\0', ' '));
                }
                if (result.outcomes().containsValue(false)) {
                    cp.cover();
                    newCovered++;
                    System.out.print("+");
                } else {
                    System.out.print("-");
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
                     boolean valuePairCoverage, String nusmvSpecCoverage, int maxGoals) {
            final CodeCoverageCounter counter = new CodeCoverageCounter();
            usualModelCode(counter, plantCodeCoverage, controllerCodeCoverage);
            coveragePoints = coveragePoints(includeInternal, valuePairCoverage, counter.coverageClaims,
                    nusmvSpecCoverage, maxGoals);
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

    void verifyAll(String code, int timeout, boolean verbose, Integer nusmvBMCK, boolean inheritIO) throws IOException {
        try (final Runner runner = Runner.create(data, code, Collections.emptyList(), null)) {
            final RunnerResult result = runner.verify(timeout, false, nusmvBMCK, inheritIO);
            for (Map.Entry<String, Boolean> outcome : result.outcomes().entrySet()) {
                System.out.println("*** " + outcome.getKey() + " = " + outcome.getValue() + " ***");
            }
            if (verbose) {
                result.log().forEach(System.out::println);
            }
            System.out.println(runner.totalResourceReport());
        }
    }
}
