package formal_testing.runner;

import formal_testing.enums.Language;
import formal_testing.ProblemData;
import formal_testing.Util;
import formal_testing.coverage.CoveragePoint;
import formal_testing.variable.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 7/3/17.
 */
public abstract class Runner implements AutoCloseable {
    final int timeout;
    Process process;
    final String dirName;

    final ProblemData data;
    final String modelCode;
    final List<String> coverageClaims;

    final String TIME = "/usr/bin/time";

    Runner(ProblemData data, int timeout, String dirName, String modelCode, List<CoveragePoint> coveragePoints,
           int claimSteps,
           boolean claimNegate) throws IOException {
        this.data = data;
        this.timeout = timeout;
        this.dirName = dirName;
        this.modelCode = modelCode;
        this.coverageClaims = coveragePoints.stream().map(cp -> cp.ltlProperty(claimSteps, claimNegate))
                .collect(Collectors.toList());
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

    public static Runner create(ProblemData data, String modelCode, List<CoveragePoint> coveragePoints, int claimSteps,
                                boolean claimNegate, int timeout) throws IOException {
        return Util.LANGUAGE == Language.PROMELA
                ? new SpinRunner(data, modelCode, coveragePoints, claimSteps, claimNegate, timeout)
                : new NuSMVRunner(data, modelCode, coveragePoints, claimSteps, claimNegate, timeout);
    }

    public static Runner create(ProblemData data, String modelCode, int timeout) throws IOException {
        return create(data, modelCode, Collections.emptyList(), 0, false, timeout);
    }

    public abstract RunnerResult verify(String property, int stepsLimit, boolean disableCounterexample)
            throws IOException;

    public RunnerResult verify(CoveragePoint point, int steps, boolean negate, int stepsLimit,
                               boolean disableCounterexample) throws IOException {
        return verify(Util.LANGUAGE == Language.PROMELA ? point.promelaLtlName() : point.ltlProperty(steps, negate),
                stepsLimit, disableCounterexample);
    }

    String trailRegexp() {
        return "(" + String.join("|", data.conf.nondetVars.stream().map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + ") = [\\w]+";
    }

    @Override
    public void close() throws IOException {
        delete(new File(dirName));
    }

    public abstract String creationReport();
}
