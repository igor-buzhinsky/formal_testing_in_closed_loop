package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;
import formal_testing.enums.Language;
import formal_testing.enums.NuSMVMode;
import formal_testing.value.Value;
import formal_testing.variable.Variable;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    private static class VarMapping {
        final int cnfVarIndex;
        final int time;
        final String indexedVarName;
        final int bitIndex;
        Boolean value;

        VarMapping(String line) {
            final String[] tokens = line.split(" +");
            cnfVarIndex = Integer.parseInt(tokens[3]);
            time = Integer.parseInt(tokens[6].replace(",", ""));
            final String name = tokens[9];
            final String[] moreTokens = name.split("\\.");
            indexedVarName = moreTokens[0];
            bitIndex = moreTokens.length == 2 ? Integer.parseInt(moreTokens[1]) : 0;
        }

        @Override
        public String toString() {
            return cnfVarIndex + " -> @" + time + " " + indexedVarName + "." + bitIndex + " = " + value;
        }
    }

    private int counterexampleFromBMC(List<String> result, int k) throws IOException {
        final String filename = k + ".dimacs";
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, "cryptominisat4", filename));
        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();

        final List<String> ansLines = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().filter(line -> !line.isEmpty() && line.charAt(0) == 'v').forEach(ansLines::add);
        }
        if (ansLines.isEmpty()) {
            result.add("-- no counterexample found with bound " + k);
        } else {
            final List<VarMapping> mappings = new ArrayList<>();
            try (final BufferedReader reader = new BufferedReader(new FileReader(dirName + "/" + filename))) {
                reader.lines().filter(line -> !line.isEmpty() && line.startsWith("c CNF variable"))
                        .forEach(line -> mappings.add(new VarMapping(line)));
            }
            final Map<Integer, VarMapping> cnfVarToMapping = new HashMap<>();
            mappings.forEach(m -> cnfVarToMapping.put(m.cnfVarIndex, m));
            for (String line : ansLines) {
                final String[] tokens = line.split(" +");
                for (int i = 1; i < tokens.length; i++) {
                    final String token = tokens[i];
                    final boolean on = !token.contains("-");
                    final int cnfIndex = Integer.parseInt(token.replace("-", ""));
                    if (cnfVarToMapping.containsKey(cnfIndex)) {
                        cnfVarToMapping.get(cnfIndex).value = on;
                    }
                }
            }
            //System.out.println(cnfVarToMapping);

            // from the largest to the smallest
            final Map<Pair<Integer, String>, List<Boolean>> bits = new HashMap<>();
            for (VarMapping m : mappings) {
                final Pair<Integer, String> p = Pair.of(m.time, m.indexedVarName);
                List<Boolean> list = bits.get(p);
                if (list == null) {
                    list = new ArrayList<>();
                    bits.put(p, list);
                }
                while (list.size() <= m.bitIndex) {
                    list.add(null);
                }
                list.set(m.bitIndex, m.value);
            }
            //System.out.println(bits);

            result.add("-- specification  is false");

            for (int i = 0; i <= k; i++) {
                result.add("  -> State: 0." + i + " <-");
                for (Variable<?> var : data.conf.nondetVars) {
                    final List<Boolean> bitList = bits.get(Pair.of(i, var.indexedName()));
                    final Value value = var.valueFromBits(bitList);
                    result.add("    " + var.indexedName() + " = " + value);
                }
            }
        }

        inspectResourceConsumption(result);
        return waitFor();
    }

    private int runBatchBMC_alternative_slow(List<String> result, int steps) throws IOException {
        final String language = Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv";
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, language, "-df", "-cpp", "-int"));
        command.add(MODEL_FILENAME);

        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();
        try (PrintWriter pw = new PrintWriter(process.getOutputStream())) {
            pw.println("read_model\n" + "flatten_hierarchy\n" + "encode_variables\n" + "build_boolean_model\n" +
                    "bmc_setup\n" + "go_bmc\n" + "gen_ltlspec_bmc -o @k -n 0 -l X -k " + steps + "\n" + "quit\n");
        }
        waitFor();
        return counterexampleFromBMC(result, steps);
    }

    private int runBatchBMC(List<String> result, int steps) throws IOException {
        final String language = Settings.LANGUAGE == Language.NUSMV ? "NuSMV" : "nuXmv";
        final List<String> command = new ArrayList<>(Arrays.asList("timeout", timeout + "s", TIME, "-f",
                ResourceMeasurement.FORMAT, language, "-df", "-cpp", "-int"));
        command.add(MODEL_FILENAME);

        process = new ProcessBuilder(command).redirectErrorStream(true).directory(new File(dirName)).start();
        try (PrintWriter pw = new PrintWriter(process.getOutputStream())) {
            pw.println("read_model\n" + "flatten_hierarchy\n" + "encode_variables\n" + "build_boolean_model\n" +
                    "bmc_setup\n" + "go_bmc\n" + "check_ltlspec_bmc_onepb -n 0 -l X -k " + steps + "\n" + "quit\n");
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
