package formal_testing.main;

import formal_testing.ResourceMeasurement;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.runner.Runner;
import formal_testing.runner.RunnerResult;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends MainBase {
    @Option(name = "--output", usage = "output filename", metaVar = "<filename>")
    private String outputFilename = "test.bin";

    @Option(name = "--maxlen", usage = "maximum test length, default = 10", metaVar = "<length>")
    private int maxLength = 10;

    @Option(name = "--minimize", handler = BooleanOptionHandler.class,
            usage = "check coverage of each new test, thus making the resultant test suite smaller")
    private boolean minimize;

    @Option(name = "--includeInternal", handler = BooleanOptionHandler.class, usage = "cover internal variables")
    private boolean includeInternal;

    @Option(name = "--checkFiniteCoverage", handler = BooleanOptionHandler.class,
            usage = "while checking coverage, ignore infinite continuations of test cases")
    private boolean checkFiniteCoverage;

    @Option(name = "--valuePairCoverage", handler = BooleanOptionHandler.class, usage = "cover value pairs (slow)")
    private boolean valuePairCoverage;

    @Option(name = "--plantCodeCoverage", handler = BooleanOptionHandler.class, usage = "cover plant code")
    private boolean plantCodeCoverage;

    @Option(name = "--controllerCodeCoverage", handler = BooleanOptionHandler.class, usage = "cover controller code")
    private boolean controllerCodeCoverage;

    public static void main(String[] args) throws IOException {
        new SynthesizeCoverageTests().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                valuePairCoverage);

        System.out.println("Coverage test synthesis...");

        final TestSuite testSuite = new TestSuite(true);
        for (int len = 1; len <= maxLength; len++) {
            System.out.println("  Test synthesis for length " + len + "...");
            System.out.println("  Covered points: " + info.coveredPoints + " / " + info.totalPoints
                    + ", test synthesis for length " + len + "...");

            final String code = usualModelCode(null, plantCodeCoverage, controllerCodeCoverage);
            final List<CoveragePoint> uncovered = info.coveragePoints.stream().filter(cp -> !cp.covered())
                    .collect(Collectors.toList());

            final boolean negate = true;
            try (final Runner runner = Runner.create(data, code, uncovered, len, negate, 0)) {
                System.out.println("  " + runner.creationReport());
                for (final CoveragePoint cp : info.coveragePoints) {
                    if (cp.covered()) {
                        continue;
                    }
                    System.out.println("  Test synthesis for " + cp + "...");
                    final RunnerResult result = runner.verify(cp, len, negate, len, false);
                    if (result.found()) {
                        cp.cover();
                        info.coveredPoints++;
                        System.out.println("    " + cp);
                        final TestCase tc = result.testCase();
                        if (minimize) {
                            info.coveredPoints += examineTestCase(tc, info.coveragePoints,
                                    checkFiniteCoverage ? len : -1, plantCodeCoverage, controllerCodeCoverage);
                        }
                        System.out.println("    Generated: " + tc);
                        testSuite.add(tc);
                    } else {
                        System.out.println("    " + cp);
                    }
                    result.log().stream().filter(ResourceMeasurement::isMeasurement).forEach(line ->
                            System.out.println("    " + new ResourceMeasurement(line, "test synthesis"))
                    );
                }
            }
            if (info.allCovered()) {
                break;
            }
        }

        System.out.println(testSuite);
        testSuite.print(outputFilename);
        info.report();
    }
}
