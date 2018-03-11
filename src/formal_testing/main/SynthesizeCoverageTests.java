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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends MainArgs {
    @Option(name = "--output", usage = "output filename", metaVar = "<filename>")
    private String outputFilename = "test.bin";

    @Option(name = "--maxlen", usage = "maximum test length, default = 10", metaVar = "<length>")
    private int maxLength = 10;

    @Option(name = "--maxGoals", usage = "maximum coverage goals per variable", metaVar = "<maxGoals>")
    private int maxGoals = Integer.MAX_VALUE;

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

    public static void main(String[] args) {
        new SynthesizeCoverageTests().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                valuePairCoverage, nusmvSpecCoverage);
        final TestSuite testSuite = new TestSuite(true);

        System.out.println("Coverage test synthesis...");

        final Set<CoveragePoint> unknown = new LinkedHashSet<>(info.coveragePoints);

        for (CoveragePoint cp : info.coveragePoints) {
            if (cp.covered()) {
                continue;
            }
            System.out.println("Coverage test synthesis for " + cp + "...");
            final String code = usualModelCode(null, plantCodeCoverage, controllerCodeCoverage);
            try (final Runner runner = Runner.create(data, code, Collections.singletonList(cp), maxLength)) {
                System.out.println("  " + runner.creationReport());
                final RunnerResult result = runner.synthesize(cp, minimize, unknown);
                if (result.found()) {
                    for (CoveragePoint covered : result.covered())  {
                        covered.cover();
                        info.coveredPoints++;
                        unknown.remove(covered);
                        System.out.println("    " + covered);
                    }
                    final TestCase tc = result.testCase();
                    testSuite.add(tc);
                    System.out.println("    Generated: " + tc);
                } else {
                    System.out.println("    " + cp);
                }
                result.log().stream().filter(ResourceMeasurement::isMeasurement).forEach(line ->
                        System.out.println("    " + new ResourceMeasurement(line, "test synthesis")));
            }
            unknown.remove(cp);
        }
        System.out.println(testSuite);
        testSuite.print(outputFilename);
        info.report();
    }
}
