package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class NuSMVRunner extends Runner {
    private static int NUSMV_DIR_INDEX = 0;

    private static final String MODEL_FILENAME = "model.smv";

    NuSMVRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, int claimSteps, boolean claimNegate,
                int timeout) throws IOException {
        super(data, timeout, "nusmv." + NUSMV_DIR_INDEX++, modelCode, coveragePoints, claimSteps, claimNegate);
    }

    private void writeModel(String property) throws FileNotFoundException {
        try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME)) {
            pw.println(modelCode);
            if (property != null) {
                pw.println(property);
            }
        }
    }

    private int run(List<String> result) throws IOException {
        process = new ProcessBuilder("timeout", timeout + "s", TIME, "-f", ResourceMeasurement.FORMAT, "NuSMV",
                "-df", MODEL_FILENAME)
                .redirectErrorStream(true).directory(new File(dirName)).start();
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(result::add);
        }
        return waitFor();
    }

    public List<String> verifyAll() throws IOException {
        final List<String> result = new ArrayList<>();
        writeModel(null);
        final int retCode = run(result);
        if (retCode == 124) {
            result.add("*** TIMEOUT ***");
        }
        return result;
    }

    @Override
    public RunnerResult verify(String property, int stepsLimit) throws IOException {
        final RunnerResult result = new RunnerResult();
        writeModel(property);
        final List<String> log = new ArrayList<>();
        final int retCode = run(log);
        final String trailRegexp = "    " + trailRegexp();
        if (retCode == 124) {
            log.add("*** " + property + " : TIMEOUT ***");
        } else {
            TestCase testCase = null;
            for (String line : log) {
                //System.out.println(line);
                if (line.startsWith("-- specification")) {
                    if (line.endsWith(" is true")) {
                        result.outcome(true);
                    } else if (line.endsWith(" is false")) {
                        result.outcome(false);
                        testCase = new TestCase(data.conf);
                        result.set(testCase);
                    }
                } else if (testCase != null) {
                    if (line.matches("  -> State: [0-9]++\\.[0-9]+ <-")) {
                        if (testCase.length() == stepsLimit) {
                            break;
                        } else if (testCase.length() > 0) {
                            testCase.padMissing();
                        }
                    } else if (line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((    )|( = ))");
                        testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                    }
                }
            }
            if (testCase != null) {
                testCase.padMissing();
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
