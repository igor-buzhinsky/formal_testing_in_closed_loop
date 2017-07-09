package formal_testing.runner;

import formal_testing.*;
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

    NuSMVRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, int claimSteps, boolean claimNegate,
                int timeout) throws IOException {
        super(data, timeout, "nusmvdir." + NUSMV_DIR_INDEX++, modelCode, coveragePoints, claimSteps, claimNegate);
    }

    private void writeModel(String property) throws FileNotFoundException {
        try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME)) {
            pw.println(modelCode);
            if (property != null) {
                pw.println(property);
            }
        }
    }

    private int runBatchBMC(List<String> result, int steps) throws IOException {
        final String language = Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv";
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, language, "-df", "-cpp", "-int"));
        command.add(MODEL_FILENAME);

        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();
        try (PrintWriter pw = new PrintWriter(process.getOutputStream())) {
            pw.println("read_model\n" + "flatten_hierarchy\n" + "encode_variables\n" + "build_boolean_model\n" +
                    "bmc_setup\n" + "go_bmc\n" + "check_ltlspec_bmc_onepb -o out.dimacs -n 0 -l X -k " + steps + "\n" + "quit\n");
        }
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(line -> result.add(line.replace(language + " > ", "")));
        }
        inspectResourceConsumption(result);
        return waitFor();
    }

    private int run(List<String> result, boolean disableCounterexamples, Integer stepsLimit, Integer verificationBMCK)
            throws IOException {
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv", "-df", "-cpp"));
        if (verificationBMCK != null) {
            // for ordinary verification
            command.addAll(Arrays.asList("-bmc", "-bmc_length", String.valueOf(verificationBMCK)));
        } else if (Settings.NUSMV_MODE == NuSMVMode.BMC && stepsLimit != null) {
            if (!disableCounterexamples) {
                // for test case synthesis
                return runBatchBMC(result, stepsLimit);
            } else {
                // for checking coverage
                command.addAll(Arrays.asList("-bmc", "-bmc_length", String.valueOf(stepsLimit)));
            }
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

    public List<String> verifyAll(boolean disableCounterexamples, Integer verificationBMCK) throws IOException {
        final List<String> result = new ArrayList<>();
        writeModel(null);
        final int retCode = run(result, disableCounterexamples, null, verificationBMCK);
        if (retCode == 124) {
            result.add("*** TIMEOUT ***");
        }
        return result;
    }

    @Override
    public RunnerResult verify(String property, int stepsLimit, boolean disableCounterexample) throws IOException {
        final RunnerResult result = new RunnerResult();
        writeModel(property);
        final List<String> log = new ArrayList<>();
        final int retCode = run(log, disableCounterexample, stepsLimit, null);
        final String trailRegexp = "    " + trailRegexp();
        if (retCode == 124) {
            log.add("*** " + property + " : TIMEOUT ***");
        } else {
            TestCase testCase = null;
            Integer loopPosition = null;
            for (String line : log) {
                //System.out.println(line);
                final boolean bmcNoCE = line.startsWith("-- no counterexample found with bound ");
                if (line.startsWith("-- specification") | bmcNoCE) {
                    if (line.endsWith(" is true") | bmcNoCE) {
                        result.outcome(true);
                    } else if (line.endsWith(" is false")) {
                        result.outcome(false);
                        testCase = new TestCase(data.conf);
                        result.set(testCase);
                    }
                } else if (testCase != null) {
                    if (line.equals("  -- Loop starts here")) {
                        loopPosition = testCase.length();
                    } else if (line.matches("  -> State: [0-9]++\\.[0-9]+ <-")) {
                        if (testCase.length() == stepsLimit + 1) {
                            break;
                        } else if (testCase.length() > 0) {
                            testCase.padMissing(data.conf);
                        }
                        testCase.newElement();
                    } else if (line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((    )|( = ))");
                        testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                    }
                }
            }
            if (testCase != null && !disableCounterexample) {
                testCase.padMissing(data.conf);
                if (loopPosition != null) {
                    testCase.loopFromPosition(loopPosition, stepsLimit + 1);
                }
                testCase.removeInitial();
                testCase.validate();
                if (testCase.length() != stepsLimit) {
                    throw new RuntimeException("Bad test case length, expected " + stepsLimit + " but in fact "
                            + testCase.length() + "; log: \n" + String.join("\n", log));
                }
            }
        }

        result.log(log);
        return result;
    }

    @Override
    public String creationReport() {
        return "Created " + NuSMVRunner.class.getSimpleName();
    }
}
