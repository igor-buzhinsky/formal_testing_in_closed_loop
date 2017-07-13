package formal_testing.main;

import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.runner.Runner;
import formal_testing.runner.RunnerResult;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends MainArgs {
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

    @Option(name = "--lengthExponent", usage = "in the exponential NuSMV mode, test length will grow as 2^lengthExponent," +
            " default = 1.5", metaVar = "<real>")
    private Double lengthExponent = 1.5;

    public static void main(String[] args) throws IOException {
        new SynthesizeCoverageTests().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        Settings.NUSMV_LENGTH_EXPONENT = lengthExponent;
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                valuePairCoverage);
        final TestSuite testSuite = new TestSuite(true);

        System.out.println("Coverage test synthesis...");

        for (int i = 0; i < info.coveragePoints.size(); i++) {
            final CoveragePoint cp = info.coveragePoints.get(i);
            if (cp.covered()) {
                continue;
            }
            System.out.println("Coverage test synthesis for " + cp + "...");
            final String code = usualModelCode(null, plantCodeCoverage, controllerCodeCoverage);
            try (final Runner runner = Runner.create(data, code, Collections.singletonList(cp), maxLength)) {
                System.out.println("  " + runner.creationReport());
                final RunnerResult result = runner.coverageSynthesis(cp, maxLength);
                if (result.found()) {
                    cp.cover();
                    info.coveredPoints++;
                    System.out.println("    " + cp);
                    final TestCase tc = result.testCase();
                    if (minimize) {
                        info.coveredPoints += examineTestCase(tc, info.coveragePoints,
                                checkFiniteCoverage ? tc.length() : null, plantCodeCoverage, controllerCodeCoverage);
                    }
                    System.out.println("    Generated: " + tc);
                    testSuite.add(tc);
                } else {
                    System.out.println("    " + cp);
                }
                result.log().stream().filter(ResourceMeasurement::isMeasurement).forEach(line ->
                        System.out.println("    " + new ResourceMeasurement(line, "test synthesis")));
            }
        }
        System.out.println(testSuite);
        testSuite.print(outputFilename);
        info.report();
    }
}
