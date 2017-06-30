package formal_testing.command;

import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.ProblemData;
import formal_testing.SpinRunner;
import formal_testing.TestCase;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends Command {
    private final boolean includeInternal;
    private final int maxLength;
    private final boolean checkFiniteCoverage;
    private final boolean plantCodeCoverage;
    private final boolean controllerCodeCoverage;
    private final String outputFilename;

    public SynthesizeCoverageTests(ProblemData data, boolean includeInternal, int maxLength, boolean checkFiniteCoverage,
                                   boolean plantCodeCoverage, boolean controllerCodeCoverage, String outputFilename) {
        super(data);
        this.includeInternal = includeInternal;
        this.maxLength = maxLength;
        this.checkFiniteCoverage = checkFiniteCoverage;
        this.plantCodeCoverage = plantCodeCoverage;
        this.controllerCodeCoverage = controllerCodeCoverage;
        this.outputFilename = outputFilename;
    }

    private int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints, int steps) throws IOException {
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        final String code = modelCode(false, false, false, Optional.of(tc.promelaHeader()),
                Optional.of(tc.promelaBody(false)), plantCodeCoverage, controllerCodeCoverage, Optional.empty())
                + "\n" + (checkFiniteCoverage ? coverageProperties(uncovered, steps) : coverageProperties(uncovered));

        int newCovered = 0;
        try (final SpinRunner spinRunner = new SpinRunner(code, 0, true, 2)) {
            System.out.print("(" + uncovered.size() + ") ");
            for (CoveragePoint cp : uncovered) {
                final List<String> result = spinRunner.pan(cp.promelaLtlName());
                for (String line : result) {
                    if (line.equals("*** " + cp.promelaLtlName() + " = TRUE ***")) {
                        cp.cover();
                        newCovered++;
                        System.out.print("+");
                    } else if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                        System.out.print("-");
                    }
                }
            }
            System.out.println();
        }
        return newCovered;
    }

    private String usualModelCode(Optional<CodeCoverageCounter> counter) {
        return modelCode(false, true, false, Optional.empty(), Optional.empty(), plantCodeCoverage,
                controllerCodeCoverage, counter);
    }

    @Override
    public void execute() throws IOException {
        final CodeCoverageCounter counter = new CodeCoverageCounter();
        usualModelCode(Optional.of(counter));
        final List<CoveragePoint> coveragePoints = coveragePoints(includeInternal, counter.coverageClaims);
        final int totalPoints = coveragePoints.size();
        int coveredPoints = 0;
        System.out.println("*** Number of coverage points: " + totalPoints);

        final TestSuite testSuite = new TestSuite();
        final String nondetName = "(" + String.join("|", data.conf.nondetVars.stream().map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + ")";
        final String trailRegexp = "^.*proc.*state.*\\[" + nondetName + " = [0-9]+\\].*$";
        for (int len = 1; len <= maxLength; len++) {
            System.out.println("*** Test synthesis for length " + len + "...");
            final StringBuilder code = new StringBuilder();
            code.append(usualModelCode(Optional.empty())).append("\n");

            for (final CoveragePoint cp : coveragePoints) {
                if (!cp.covered()) {
                    code.append(cp.promelaLtlProperty(len, true)).append("\n");
                }
            }

            try (final SpinRunner spinRunner = new SpinRunner(code.toString(), 0, false, 2)) {
                for (final CoveragePoint cp : coveragePoints) {
                    if (cp.covered()) {
                        continue;
                    }
                    final List<String> result = spinRunner.pan(cp.promelaLtlName());
                    TestCase tc = null;
                    for (String line : result) {
                        if (line.startsWith("***")) {
                            System.out.println(line);
                        }
                        if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                            cp.cover();
                            coveredPoints++;
                            tc = new TestCase(data.conf);
                        } else if (tc != null && line.matches(trailRegexp)) {
                            final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                            tc.addValue(tokens[1], tokens[2]);
                        }
                    }
                    if (tc != null) {
                        tc.validate();
                        coveredPoints += examineTestCase(tc, coveragePoints, len);
                        System.out.println(tc);
                        testSuite.add(tc);
                    }
                }
            }
            if (coveredPoints == totalPoints) {
                break;
            }
        }

        System.out.println(testSuite);
        testSuite.print(outputFilename);
        System.out.println("Covered points: " + coveredPoints + " / " + totalPoints);
        if (coveredPoints < totalPoints) {
            System.out.println("Not covered:");
            coveragePoints.stream().filter(cp -> !cp.covered()).forEach(System.out::println);
        }
    }
}
