package formal_testing.main;

import formal_testing.ResourceMeasurement;
import formal_testing.SpinRunner;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.variable.Variable;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        final String nondetName = "(" + String.join("|", data.conf.nondetVars.stream().map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + ")";
        final String trailRegexp = "^.*proc.*state.*\\[" + nondetName + " = [0-9]+\\].*$";
        for (int len = 1; len <= maxLength; len++) {
            System.out.println("  Test synthesis for length " + len + "...");
            System.out.println("  Covered points: " + info.coveredPoints + " / " + info.totalPoints
                    + ", test synthesis for length " + len + "...");

            final String code = usualModelCode(Optional.empty(), plantCodeCoverage, controllerCodeCoverage);
            final List<CoveragePoint> uncovered = info.coveragePoints.stream().filter(cp -> !cp.covered())
                    .collect(Collectors.toList());
            final List<String> claims = new ArrayList<>();
            for (CoveragePoint cp : uncovered) {
                claims.add(cp.promelaLtlProperty(len, true));
            }

            try (final SpinRunner spinRunner = new SpinRunner(code, uncovered, claims, 0, 2)) {
                System.out.println("  " + spinRunner.creationMeasurement);
                for (final CoveragePoint cp : info.coveragePoints) {
                    if (cp.covered()) {
                        continue;
                    }
                    System.out.println("  Test synthesis for " + cp + "...");
                    final List<String> result = spinRunner.pan(cp.promelaLtlName());
                    TestCase tc = null;
                    for (String line : result) {
                        if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                            cp.cover();
                            info.coveredPoints++;
                            tc = new TestCase(data.conf);
                            System.out.println("    " + cp);
                        } else if (line.equals("*** " + cp.promelaLtlName() + " = TRUE ***")) {
                            System.out.println("    " + cp);
                        } else if (tc != null && line.matches(trailRegexp)) {
                            final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                            tc.addValue(tokens[1], tokens[2]);
                        } else if (ResourceMeasurement.isMeasurement(line)) {
                            System.out.println("    " + new ResourceMeasurement(line, "test synthesis"));
                        }
                    }
                    if (tc != null) {
                        //tc.validate();
                        if (minimize) {
                            info.coveredPoints += examineTestCase(tc, info.coveragePoints,
                                    checkFiniteCoverage ? len : 0, plantCodeCoverage, controllerCodeCoverage);
                        }
                        System.out.println("    Generated: " + tc);
                        testSuite.add(tc);
                    }
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
