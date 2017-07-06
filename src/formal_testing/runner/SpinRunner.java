package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class SpinRunner extends Runner {
    private final Map<String, Integer> propertyToPart = new HashMap<>();

    private static int SPIN_DIR_INDEX = 0;

    private static final int MAX_CLAIMS_IN_ONE_PAN = 50;
    private static final int OPTIMIZATION_LEVEL = 2;
    private static final String MODEL_FILENAME = "model.pml";

    public final ResourceMeasurement creationMeasurement;

    SpinRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, int claimSteps,
               boolean claimNegate, int timeout) throws IOException {
        super(data, timeout, "spindir." + SPIN_DIR_INDEX++, modelCode, coveragePoints, claimSteps, claimNegate);

        final int parts = coverageClaims.size() / MAX_CLAIMS_IN_ONE_PAN + 1;
        for (int i = 0; i < parts; i++) {
            try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME + "." + i)) {
                pw.println(modelCode);
                final int start = coverageClaims.size() / parts * i;
                final int end = coverageClaims.size() / parts * (i + 1);
                for (int j = start; j < end; j++) {
                    pw.println(coverageClaims.get(j));
                    propertyToPart.put(coveragePoints.get(j).promelaLtlName(), i);
                }
            }
        }

        // generate pan source
        ResourceMeasurement measurement = new ResourceMeasurement();
        for (int i = 0; i < parts; i++) {
            process = new ProcessBuilder(TIME, "-f", ResourceMeasurement.FORMAT, "spin", "-a",
                    MODEL_FILENAME + "." + i).redirectErrorStream(true).directory(new File(dirName)).start();
            final List<String> allLines = new ArrayList<>();
            try (final Scanner sc = new Scanner(process.getInputStream())) {
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    if (ResourceMeasurement.isMeasurement(line)) {
                        measurement = measurement.add(new ResourceMeasurement(line, "generating pan source"));
                    }
                    allLines.add(line);
                }
            }
            waitFor(allLines);

            // compile pan
            process = new ProcessBuilder(TIME, "-f", ResourceMeasurement.FORMAT, "cc",
                    "-O" + OPTIMIZATION_LEVEL, "-DVECTORSZ=1024", "-o", "pan", "pan.c")
                    .redirectErrorStream(true).directory(new File(dirName)).start();
            try (final Scanner sc = new Scanner(process.getInputStream())) {
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    if (ResourceMeasurement.isMeasurement(line)) {
                        measurement = measurement.add(new ResourceMeasurement(line, "compiling pan"));
                    }
                    allLines.add(line);
                }
            }
            waitFor(allLines);

            Files.move(Paths.get(dirName + "/pan"), Paths.get(dirName + "/pan." + i));
        }
        creationMeasurement = measurement;
    }

    @Override
    public RunnerResult verify(String property, int stepsLimit, boolean disableCounterexample) throws IOException {
        final RunnerResult result = new RunnerResult();
        final String trailRegexp = "^.*proc.*state.*\\[" + trailRegexp() + "\\].*$";

        final int part = (propertyToPart.containsKey(property) ? propertyToPart.get(property) : 0);
        final String suffix = "." + part;
        final String trailPath = dirName + "/" + MODEL_FILENAME + suffix + ".trail";
        final List<String> log = new ArrayList<>();
        File trailFile = null;
        try {
            process = new ProcessBuilder("timeout", timeout + "s", TIME, "-f", ResourceMeasurement.FORMAT,
                    "./pan" + suffix, "-a", "-N", property, "-m5000000")
                    .redirectErrorStream(true).directory(new File(dirName)).start();
            try (final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                reader.lines().forEach(log::add);
            }
            final int retCode = waitFor();
            trailFile = new File(trailPath);
            if (retCode == 124) {
                log.add("*** " + property + " : TIMEOUT ***");
            } else if (trailFile.exists()) {
                result.outcome(false);
                final TestCase testCase = new TestCase(data.conf);
                result.set(testCase);
                if (!disableCounterexample) {
                    testCase.setMaxLength(stepsLimit);
                    // counterexample trace reading
                    process = new ProcessBuilder("spin", "-k", MODEL_FILENAME + suffix + ".trail", "-pglrs",
                            MODEL_FILENAME + suffix).redirectErrorStream(true).directory(new File(dirName)).start();

                    try (final BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                        reader.lines().forEach(line -> {
                            if (line.matches(trailRegexp)) {
                                final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                                testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                            }
                        });
                    }
                    waitFor();
                }
            } else {
                result.outcome(true);
            }
        } finally {
            if (trailFile != null && trailFile.exists()) {
                try {
                    Files.delete(Paths.get(trailPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        result.log(log);
        return result;
    }

    @Override
    public String creationReport() {
        return creationMeasurement.toString();
    }
}
