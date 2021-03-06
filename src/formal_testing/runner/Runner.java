package formal_testing.runner;

import formal_testing.ProblemData;
import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.coverage.CoveragePoint;
import formal_testing.enums.Language;
import formal_testing.variable.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 7/3/17.
 */
public abstract class Runner implements AutoCloseable {
    Process process;
    final String dirName;
    private final String logDirName;
    final ProblemData data;
    String modelCode;
    final Integer maxTestLength;

    final String TIME = "/usr/bin/time";

    ResourceMeasurement totalResourceMeasurement = new ResourceMeasurement(0, 0, 0, 0, "total tool execution time");

    public String totalResourceReport() {
        return totalResourceMeasurement.toString();
    }

    void inspectResourceConsumption(List<String> log) {
        log.stream().filter(ResourceMeasurement::isMeasurement).forEach(
                line -> totalResourceMeasurement = totalResourceMeasurement.add(new ResourceMeasurement(line)));
    }

    Runner(ProblemData data, String dirName, String logDirName, String modelCode, Integer maxTestLength)
            throws IOException {
        this.data = data;
        this.dirName = dirName;
        this.logDirName = logDirName;
        this.modelCode = modelCode;
        this.maxTestLength = maxTestLength;
        createDir();
    }

    private void createDir() throws IOException {
        final File dir = new File(dirName);
        if (dir.exists()) {
            delete(dir);
        }
        Files.createDirectories(Paths.get(dirName));
    }

    void waitFor(List<String> report) {
        final int code = waitFor();
        if (code != 0) {
            throw new RuntimeException("Problems, see below:\n" + String.join("\n", report));
        }
    }

    int waitFor() {
        try {
            return process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void delete(File f) throws IOException {
        final File[] files = f.listFiles();
        if (files != null) {
            for (File c : files) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    public static Runner create(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints,
                                Integer maxLength) throws IOException {
        return Settings.LANGUAGE == Language.PROMELA
                ? new SpinRunner(data, modelCode, coveragePoints, maxLength)
                : new NuSMVRunner(data, modelCode, maxLength);
    }

    public abstract RunnerResult synthesize(CoveragePoint cp, boolean minimize, Collection<CoveragePoint> otherCps,
                                            int timeout) throws IOException;

    public abstract RunnerResult checkCoverage(CoveragePoint cp) throws IOException;

    public abstract RunnerResult verify(int timeout, boolean disableCounterexamples, Integer nusmvBMCK,
                                        boolean inheritIO) throws IOException;

    String trailVarLineRegexp(boolean allVars) {
        // parentheses can appear in SPIN with negative values, e.g. VAR = -(2)
        return "(" + String.join("|", (allVars ? data.conf.allVariables() : data.conf.nondetVars).stream()
                .map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + ") = [-\\(\\)\\w]+";
    }

    @Override
    public void close() throws IOException {
        final File logDir = new File(logDirName);
        if (logDir.exists()) {
            delete(logDir);
        }
        final File movedDir = new File(dirName);
        Files.move(Paths.get(movedDir.toURI()), Paths.get(logDir.toURI()));
    }

    public abstract String creationReport();
}
