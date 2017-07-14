package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.coverage.CoveragePoint;
import org.apache.commons.lang3.tuple.Pair;

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
    private static final String MODEL_FILENAME = "model.pml";

    private final ResourceMeasurement creationMeasurement;

    private final List<Pair<String, String>> coverageClaims = new ArrayList<>();

    SpinRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, Integer maxTestLength)
            throws IOException {
        super(data, "spindir." + SPIN_DIR_INDEX++, modelCode, maxTestLength);

        for (CoveragePoint cp : coveragePoints) {
            if (maxTestLength != null) {
                for (int i = 1; i <= maxTestLength; i++) {
                    coverageClaims.add(Pair.of(cp.ltlProperty(i), cp.promelaLtlName() + "__" + i));
                }
            } else {
                coverageClaims.add(Pair.of(cp.ltlProperty(null), cp.promelaLtlName() + "__null"));
            }
        }

        final int parts = coverageClaims.size() / MAX_CLAIMS_IN_ONE_PAN + 1;
        for (int i = 0; i < parts; i++) {
            try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME + "." + i)) {
                pw.println(modelCode);
                final int start = coverageClaims.size() / parts * i;
                final int end = coverageClaims.size() / parts * (i + 1);
                for (int j = start; j < end; j++) {
                    pw.println(coverageClaims.get(j).getLeft());
                    propertyToPart.put(coverageClaims.get(j).getRight(), i);
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
                    "-O" + Settings.PAN_OPTIMIZATION_LEVEL, "-DVECTORSZ=1024", "-o", "pan", "pan.c")
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
        totalResourceMeasurement = totalResourceMeasurement.add(measurement);
    }

    private static void tryDelete(File file, String filename) {
        if (file != null && file.exists()) {
            try {
                Files.delete(Paths.get(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> runPan(String suffix, String strClaim) throws IOException {
        final List<String> log = new ArrayList<>();
        process = new ProcessBuilder("timeout", 0 + "s", TIME, "-f", ResourceMeasurement.FORMAT,
                "./pan" + suffix, "-a", "-N", strClaim, "-m5000000")
                .redirectErrorStream(true).directory(new File(dirName)).start();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines().forEach(log::add);
        }
        inspectResourceConsumption(log);
        waitFor();
        return log;
    }

    @Override
    public RunnerResult coverageSynthesis(CoveragePoint claim) throws IOException {
        final RunnerResult result = new RunnerResult();
        final String trailRegexp = "^.*proc.*state.*\\[" + trailRegexp() + "\\].*$";
        File trailFile = null;
        final List<String> log = new ArrayList<>();
        final String strClaim = claim.promelaLtlName();
        for (int len = 1; len <= maxTestLength; len++) {
            final String suffix = "." + propertyToPart.get(strClaim + "__" + len);
            final String trailPath = dirName + "/" + MODEL_FILENAME + suffix + ".trail";
            try {
                log.addAll(runPan(suffix, strClaim + "__" + len));
                //log.forEach(System.out::println);
                trailFile = new File(trailPath);
                if (trailFile.exists()) {
                    result.outcome(strClaim, false);
                    final TestCase testCase = new TestCase(data.conf);
                    result.set(testCase);
                    // counterexample trace reading
                    process = new ProcessBuilder("spin", "-k", MODEL_FILENAME + suffix + ".trail", "-pglrs",
                            MODEL_FILENAME + suffix).redirectErrorStream(true).directory(new File(dirName)).start();
                    try (final BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                        reader.lines().forEach(line -> {
                            inspectResourceConsumption(Collections.singletonList(line));
                            if (line.matches(trailRegexp)) {
                                final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                                testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                            }
                        });
                    }
                    testCase.crop(len);
                    waitFor();
                    break;
                }
            } finally {
                tryDelete(trailFile, trailPath);
            }
        }
        if (!result.found()) {
            result.outcome(strClaim, true);
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult coverageCheck(CoveragePoint claim) throws IOException {
        final RunnerResult result = new RunnerResult();
        File trailFile = null;
        final List<String> log = new ArrayList<>();
        final String strClaim = claim.promelaLtlName();
        for (int len = 1; len <= maxTestLength; len++) {
            final String suffix = "." + propertyToPart.get(strClaim + "__" + len);
            final String trailPath = dirName + "/" + MODEL_FILENAME + suffix + ".trail";
            try {
                log.addAll(runPan(suffix, strClaim + "__" + len));
                //log.forEach(System.out::println);
                trailFile = new File(trailPath);
                if (trailFile.exists()) {
                    result.outcome(strClaim, false);
                    break;
                }
            } finally {
                tryDelete(trailFile, trailPath);
            }
        }
        if (result.outcomes().isEmpty()) {
            result.outcome(strClaim, true);
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult verification(int timeout, boolean disableCounterexamples, Integer nusmvBMCK)
            throws IOException {
        final List<String> log = new ArrayList<>();
        /*writeModel(null);
        final int retCode = run(log, disableCounterexamples, nusmvBMCK, timeout);
        if (retCode == 124) {
            log.add("*** TIMEOUT ***");
        }*/
        final RunnerResult result = new RunnerResult();
        result.log(log);
        /*log.stream().filter(line -> line.startsWith("-- ")).forEach(line -> {
            if (line.endsWith(" is false")) {
                result.outcome(line.replace("-- ", "").replaceAll(" +is false", ""), false);
            } else if (line.endsWith(" is true")) {
                result.outcome(line.replace("-- ", "").replaceAll(" +is true", ""), true);
            }
        });*/
        return result;

        // TODO
    }

    @Override
    public String creationReport() {
        return creationMeasurement.toString();
    }
}
