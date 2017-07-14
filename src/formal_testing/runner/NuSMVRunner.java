package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;
import formal_testing.enums.Language;
import formal_testing.enums.NuSMVMode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class NuSMVRunner extends Runner {
    private static int NUSMV_DIR_INDEX = 0;

    private static final String MODEL_FILENAME = "model.smv";

    NuSMVRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, Integer maxTestLength)
            throws IOException {
        super(data, "nusmvdir." + NUSMV_DIR_INDEX++, modelCode, maxTestLength);
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
                ResourceMeasurement.FORMAT, language, "-df", "-cpp", "-int"));
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
        return waitFor();
    }

    private void runBMCIterative(List<String> result, int k) throws IOException {
        runBatchBMC(result, 0, "check_ltlspec_sbmc_inc -n 0 -k " + k, Settings.NUSMV_COI);
    }

    private int run(List<String> result, boolean disableCounterexamples, Integer nusmvBMCK, int timeout)
            throws IOException {
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv", "-df", "-cpp"));
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

    private void runProper(List<String> log, Integer maxTestLength) throws IOException {
        if (Settings.NUSMV_MODE == NuSMVMode.BMC) {
            runBMCIterative(log, maxTestLength);
        } else if (Settings.NUSMV_MODE == NuSMVMode.INFINITE_CTL) {
            run(log, false, null, 0);
            throw new RuntimeException();
            // TODO new test case representation and synthesis of lasso-shaped test cases
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public RunnerResult coverageSynthesis(CoveragePoint claim) throws IOException {
        final String trailRegexp = "    " + trailRegexp();
        final RunnerResult result = new RunnerResult();
        final String strClaim = claim.ltlProperty(null);
        writeModel(strClaim);
        final List<String> log = new ArrayList<>();
        runProper(log, maxTestLength);
        TestCase testCase = null;
        Integer loopPosition = null;
        int effectiveLength = 0;
        for (String line : log) {
            //System.out.println(line);
            if (line.startsWith(NOT_FOUND)) {
                effectiveLength++;
            }
            if (line.startsWith(NOT_FOUND + maxTestLength)) {
                result.outcome(strClaim, true);
            } else if (line.startsWith("-- specification") && line.endsWith(" is false")) {
                result.outcome(strClaim, false);
                testCase = new TestCase(data.conf);
                result.set(testCase);
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
            testCase.validate();
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult coverageCheck(CoveragePoint claim) throws IOException {
        final RunnerResult result = new RunnerResult();
        final String strClaim = claim.ltlProperty(null);
        writeModel(strClaim);
        final List<String> log = new ArrayList<>();
        runProper(log, maxTestLength);
        for (String line : log) {
            //System.out.println(line);
            if (line.startsWith(NOT_FOUND + maxTestLength)) {
                result.outcome(strClaim, true);
            } else if (line.startsWith("-- specification") && line.endsWith(" is false")) {
                result.outcome(strClaim, false);
            }
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult verification(int timeout, boolean disableCounterexamples, Integer nusmvBMCK)
            throws IOException {
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
                result.outcome(line.replace("-- ", "").replaceAll(" +is false", ""), false);
            } else if (line.endsWith(" is true")) {
                result.outcome(line.replace("-- ", "").replaceAll(" +is true", ""), true);
            }
        });
        return result;
    }

    @Override
    public String creationReport() {
        return "Created " + NuSMVRunner.class.getSimpleName();
    }
}
