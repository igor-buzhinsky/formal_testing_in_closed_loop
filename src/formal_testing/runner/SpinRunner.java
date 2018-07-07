package formal_testing.runner;

import formal_testing.*;
import formal_testing.coverage.CoveragePoint;
import formal_testing.variable.Variable;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class SpinRunner extends Runner {
    private final Map<String, Integer> propertyToPart = new HashMap<>();

    private static int SPIN_DIR_INDEX = 0;

    private static final int MAX_CLAIMS_IN_ONE_PAN = 50;
    private static final String MODEL_FILENAME = "model.pml";

    private final ResourceMeasurement creationMeasurement;

    private final List<Pair<String, String>> coverageLtl = new ArrayList<>();
    private final List<Pair<String, String>> ltlFromModel = new ArrayList<>();

    private static final boolean DISTRIBUTE_MODEL_LTL = false;

    private void coverageLtl(List<CoveragePoint> coveragePoints) {
        for (CoveragePoint cp : coveragePoints) {
            if (maxTestLength != null) {
                for (int i = 1; i <= maxTestLength; i++) {
                    coverageLtl.add(Pair.of(cp.ltlProperty(i), cp.promelaLtlName() + "__" + i));
                }
            } else {
                coverageLtl.add(Pair.of(cp.ltlProperty(null), cp.promelaLtlName() + "__null"));
            }
        }
    }

    private void ltlFromModel() {
        final List<String> newLines = new ArrayList<>();
        for (String line : modelCode.split("\n")) {
            if (line.startsWith("ltl ")) {
                ltlFromModel.add(Pair.of(line, line.replaceAll("ltl +", "").replaceAll(" .*$", "")));
            } else {
                newLines.add(line);
            }
        }
        if (DISTRIBUTE_MODEL_LTL) {
            modelCode = String.join("\n", newLines);
        }
    }

    SpinRunner(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, Integer maxTestLength)
            throws IOException {
        super(data, "spindir-" + SPIN_DIR_INDEX++, "spindir-last", modelCode, maxTestLength);

        ltlFromModel();
        coverageLtl(coveragePoints);
        final List<Pair<String, String>> allLtl = DISTRIBUTE_MODEL_LTL
                ? Util.merge(Arrays.asList(ltlFromModel, coverageLtl)) : coverageLtl;

        final int parts = allLtl.size() / MAX_CLAIMS_IN_ONE_PAN + 1;
        for (int i = 0; i < parts; i++) {
            try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME + "." + i)) {
                pw.println(modelCode);
                final int start = allLtl.size() / parts * i;
                final int end = allLtl.size() / parts * (i + 1);
                for (int j = start; j < end; j++) {
                    pw.println(allLtl.get(j).getLeft());
                    propertyToPart.put(allLtl.get(j).getRight(), i);
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

    private void createTraceReader(String suffix, boolean inheritIO) throws IOException {
        // -p -B -k enables printing of printf Promela commands
        // previous arguments: -k -pglrs
        // run    grep -Pv "^ *[0-9]+:"   to get rid of usual counterexample lines
        final ProcessBuilder pb = new ProcessBuilder("spin", "-p", "-B", "-k", MODEL_FILENAME + suffix + ".trail",
                MODEL_FILENAME + suffix).redirectErrorStream(true).directory(new File(dirName));
        if (inheritIO) {
            pb.inheritIO();
        }
        process = pb.start();
    }

    private String trailPath(String suffix) {
        return dirName + "/" + MODEL_FILENAME + suffix + ".trail";
    }

    private TestCase reconstructSpinTrace(List<String> lines) {
        final TestCase tc = new TestCase(data.conf, true);
        for (String line : lines) {
            if (line.equals("  -> New State <-") || line.equals("#processes: 1")) {
                tc.newElement();
            } else if (line.matches("^[\t ]+\\w+(\\[[0-9]+\\])? = [-\\(\\)\\w]+$")) {
                // parentheses can appear in SPIN with negative values, e.g. VAR = -(2)
                final String[] tokens = line.split(" = ");
                final String varName = tokens[0].trim();
                final Variable<?> var = data.conf.byName(varName);
                if (var != null) {
                    tc.addValue(varName, var.readValue(tokens[1].trim()));
                }
            }
        }

        return tc;
    }

    @Override
    public RunnerResult synthesize(CoveragePoint cp, boolean minimize, Collection<CoveragePoint> otherCps,
                                   int timeout) throws IOException {
        if (maxTestLength == null) {
            throw new RuntimeException("Unbounded test case synthesis is not supported.");
        }
        final RunnerResult result = new RunnerResult();
        final String trailRegexp = "^.*proc.*state.*\\[" + trailVarLineRegexp(false) + "\\].*$";
        final List<String> log = new ArrayList<>();
        final String neverClaim = cp.promelaLtlName();
        for (int len = 1; len <= maxTestLength; len++) {
            final String suffix = "." + propertyToPart.get(neverClaim + "__" + len);
            final String trailPath = trailPath(suffix);
            log.addAll(runPan(suffix, neverClaim + "__" + len));
            //log.forEach(System.out::println);
            final File trailFile = new File(trailPath);
            if (trailFile.exists()) {
                result.outcome(neverClaim, false);
                result.cover(cp);
                final TestCase testCase = new TestCase(data.conf, false);
                result.set(testCase);
                // counterexample trace reading
                createTraceReader(suffix, false);
                final List<String> counterexample = new ArrayList<>();
                try (final BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                    reader.lines().forEach(counterexample::add);
                }
                for (String line : counterexample) {
                    inspectResourceConsumption(Collections.singletonList(line));
                    if (line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                        testCase.addValue(tokens[1], data.conf.byName(tokens[1]).readValue(tokens[2]));
                    }
                }
                testCase.crop(len);
                waitFor();
                if (minimize) {
                    CoveragePoint.checkCovered(otherCps, reconstructSpinTrace(counterexample))
                            .forEach(result::cover);
                }
                break;
            }
        }
        if (!result.found()) {
            result.outcome(neverClaim, true);
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult checkCoverage(CoveragePoint cp) throws IOException {
        final RunnerResult result = new RunnerResult();
        File trailFile = null;
        final List<String> log = new ArrayList<>();
        final String neverClaim = cp.promelaLtlName();
        final List<Integer> lens = new ArrayList<>();
        if (maxTestLength != null) {
            for (int len = 1; len <= maxTestLength; len++) {
                lens.add(len);
            }
        } else {
            lens.add(null);
        }
        for (Integer len : lens) {
            final String suffix = "." + propertyToPart.get(neverClaim + "__" + len);
            final String trailPath = dirName + "/" + MODEL_FILENAME + suffix + ".trail";
            try {
                log.addAll(runPan(suffix, neverClaim + "__" + len));
                //log.forEach(System.out::println);
                trailFile = new File(trailPath);
                if (trailFile.exists()) {
                    result.outcome(neverClaim, false);
                    break;
                }
            } finally {
                tryDelete(trailFile, trailPath);
            }
        }
        if (result.outcomes().isEmpty()) {
            result.outcome(neverClaim, true);
        }
        result.log(log);
        return result;
    }

    @Override
    public RunnerResult verify(int timeout, boolean disableCounterexamples, Integer nusmvBMCK, boolean inheritIO)
            throws IOException {
        final RunnerResult result = new RunnerResult();
        final List<String> log = new ArrayList<>();
        for (String ltlName : ltlFromModel.stream().map(Pair::getRight).collect(Collectors.toList())) {
            final String suffix = "." + (DISTRIBUTE_MODEL_LTL ? propertyToPart.get(ltlName) : 0);
            final String trailPath = trailPath(suffix);
            File trailFile = null;
            try {
                log.addAll(runPan(suffix, ltlName));
                //log.forEach(System.out::println);
                trailFile = new File(trailPath);
                if (trailFile.exists()) {
                    result.outcome(ltlName, false);
                    createTraceReader(suffix, inheritIO);
                    if (!inheritIO) {
                        try (final BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                            reader.lines().forEach(log::add);
                        }
                    }
                    waitFor();
                } else {
                    result.outcome(ltlName, true);
                }
            } finally {
                tryDelete(trailFile, trailPath);
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
