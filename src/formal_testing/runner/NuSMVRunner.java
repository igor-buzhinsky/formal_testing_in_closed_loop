package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;
import formal_testing.enums.Language;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class NuSMVRunner extends Runner {
    private static int NUSMV_DIR_INDEX = 0;

    private static final String MODEL_FILENAME = "model.smv";

    NuSMVRunner(ProblemData data, String modelCode, Integer maxTestLength)
            throws IOException {
        super(data, "nusmvdir-" + NUSMV_DIR_INDEX++, "nusmvdir-last", modelCode, maxTestLength);
    }

    private void writeModel(String property) throws FileNotFoundException {
        try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME)) {
            pw.println(modelCode);
            if (property != null) {
                pw.println(property);
            }
        }
    }

    private int runBatchBMC(List<String> result, int timeout, String solveCommand, boolean coi) throws IOException {
        final String language = Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv";
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, language, "-df", "-pre", "cpp", "-int"));
        if (coi) {
            command.add("-coi");
        }
        command.add(MODEL_FILENAME);
        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();
        try (PrintWriter pw = new PrintWriter(process.getOutputStream())) {
            pw.println("read_model\n"
                    + "flatten_hierarchy\n"
                    + "encode_variables\n"
                    + "build_boolean_model\n"
                    + "bmc_setup\n"
                    + "go_bmc\n"
                    + solveCommand + "\n"
                    + "quit\n");
        }
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(line -> result.add(line.replace(language + " > ", "")));
        }
        inspectResourceConsumption(result);
        //System.out.println(result);
        return waitFor();
    }

    private int runBMCIterative(List<String> result, int k, int timeout) throws IOException {
        return runBatchBMC(result, timeout, "check_ltlspec_sbmc_inc -n 0 -k " + k, Settings.NUSMV_COI);
    }

    private int run(List<String> result, boolean disableCounterexamples, Integer nusmvBMCK, int timeout)
            throws IOException {
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv", "-df",
                "-pre", "cpp"));
        if (nusmvBMCK != null) {
            command.addAll(Arrays.asList("-bmc", "-bmc_length", String.valueOf(nusmvBMCK)));
        }
        if (disableCounterexamples) {
            command.add("-dcx");
        }
        if (Settings.NUSMV_DYNAMIC) {
            command.add("-dynamic");
        }
        if (Settings.NUSMV_COI) {
            command.add("-coi");
        }
        command.add(MODEL_FILENAME);

        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(result::add);
        }
        inspectResourceConsumption(result);
        return waitFor();
    }

    private final static String NOT_FOUND = "-- no counterexample found with bound ";

    @Override
    public RunnerResult synthesize(CoveragePoint cp, boolean minimize, Collection<CoveragePoint> otherCps,
                                   int timeout) throws IOException {
        if (maxTestLength == null) {
            throw new RuntimeException("Unbounded test case synthesis is not supported.");
        }
        final String trailRegexp = "    " + trailVarLineRegexp(minimize);
        final RunnerResult result = new RunnerResult();
        final String neverClaim = cp.ltlProperty(null);
        writeModel(neverClaim);
        final List<String> log = new ArrayList<>();
        final int retCode = runBMCIterative(log, maxTestLength, timeout);
        if (retCode == 124) {
            log.add("*** TIMEOUT ***");
            // assuming that there is no counterexample
            result.outcome(neverClaim, true);
        } else {
            TestCase testCase = null;
            Integer loopPosition = null;
            int effectiveLength = 0;
            for (String line : log) {
                //System.out.println(line);
                if (line.startsWith(NOT_FOUND)) {
                    effectiveLength++;
                }
                if (line.startsWith(NOT_FOUND + maxTestLength)) {
                    result.outcome(neverClaim, true);
                } else if (line.startsWith("-- specification") && line.endsWith(" is false")) {
                    result.outcome(neverClaim, false);
                    // more variables will be added to the test suite as they appear:
                    // (nondet vars are included anyway: they will be replaced with default values
                    // if they are missing in the counterexample)
                    testCase = new TestCase(data.conf, false);
                    result.set(testCase);
                    result.cover(cp);
                } else if (testCase != null) {
                    if (line.equals("  -- Loop starts here")) {
                        loopPosition = testCase.length();
                    } else if (line.matches("  -> State: [0-9]++\\.[0-9]+ <-")) {
                        if (testCase.length() > 0) {
                            testCase.padMissing(data.conf);
                        }
                        testCase.newElement();
                    } else if (line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((    )|( = ))");
                        testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                    }
                }
            }
            if (testCase != null) {
                if (effectiveLength == 0) {
                    effectiveLength = 1;
                }
                testCase.padMissing(data.conf);
                if (loopPosition != null) {
                    testCase.loopFromPosition(loopPosition, effectiveLength + 1);
                }
                testCase.removeInitial();
                if (loopPosition == null) {
                    testCase.loopFromPosition(0, effectiveLength);
                }
                testCase.crop(effectiveLength);
                if (minimize) {
                    // test suite minimization: check coverage of remaining goals
                    CoveragePoint.checkCovered(otherCps, testCase).forEach(result::cover);
                }
                testCase.validate();
                if (minimize) {
                    result.set(testCase.reduceToNondetVars(data.conf));
                }
            }
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult checkCoverage(CoveragePoint cp) throws IOException {
        if (maxTestLength == null) {
            throw new RuntimeException("Unbounded coverage check is not supported.");
        }
        final RunnerResult result = new RunnerResult();
        final String neverClaim = cp.ltlProperty(null);
        writeModel(neverClaim);
        final List<String> log = new ArrayList<>();
        runBMCIterative(log, maxTestLength, 0);
        for (String line : log) {
            //System.out.println(line);
            if (line.startsWith(NOT_FOUND + maxTestLength)) {
                result.outcome(neverClaim, true);
            } else if (line.startsWith("-- specification") && line.endsWith(" is false")) {
                result.outcome(neverClaim, false);
            }
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult verify(int timeout, boolean disableCounterexamples, Integer nusmvBMCK) throws IOException {
        final List<String> log = new ArrayList<>();
        writeModel(null);
        final int retCode = run(log, disableCounterexamples, nusmvBMCK, timeout);
        if (retCode == 124) {
            log.add("*** TIMEOUT ***");
        }
        final RunnerResult result = new RunnerResult();
        result.log(log);
        log.stream().filter(line -> line.startsWith("-- ")).forEach(line -> {
            if (line.endsWith(" is false")) {
                result.outcome(line.replace("-- specification ", "").replaceAll(" +is false", ""), false);
            } else if (line.endsWith(" is true")) {
                result.outcome(line.replace("-- specification ", "").replaceAll(" +is true", ""), true);
            }
        });
        return result;
    }

    @Override
    public String creationReport() {
        return "Created " + NuSMVRunner.class.getSimpleName();
    }
}
