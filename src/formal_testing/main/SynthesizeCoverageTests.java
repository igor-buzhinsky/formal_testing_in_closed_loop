package formal_testing.main;

import formal_testing.ResourceMeasurement;
import formal_testing.Settings;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.enums.Language;
import formal_testing.runner.Runner;
import formal_testing.runner.RunnerResult;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends MainArgs {
    @Option(name = "--output", usage = "output filename", metaVar = "<filename>")
    private String outputFilename = "test.bin";

    @Option(name = "--maxlen", usage = "maximum test length, default = 10", metaVar = "<length>")
    private int maxLength = 10;

    @Option(name = "--maxGoals", usage = "maximum coverage goals per integer variable", metaVar = "<maxGoals>")
    private int maxGoals = Integer.MAX_VALUE;

    @Option(name = "--timeout", usage = "timeout in seconds, default = 0 = no, NuSMV only", metaVar = "<timeout>")
    private int timeout;

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

    // --startWithRandom

    @Option(name = "--startWithRandom",
            usage = "in the beginning, first generate some random tests; format #tests:#length", metaVar = "<filename>")
    private String startWithRandom = null;

    @Option(name = "--seed", usage = "random seed (used with --startWithRandom)", metaVar = "<seed>")
    private Long seed;

    @Option(name = "--promelaHeaderFilename",
            usage = "header filename (in Promela, used with --startWithRandom)",
            metaVar = "<header-filename>")
    private String promelaHeaderFilename;

    @Option(name = "--promelaPlantModelFilename",
            usage = "plant model filename (in Promela, used with --startWithRandom)",
            metaVar = "<plant-filename>")
    private String promelaPlantModelFilename;

    @Option(name = "--promelaControllerModelFilename",
            usage = "controller model filename (in Promela, used with --startWithRandom)",
            metaVar = "<controller-filename>")
    private String promelaControllerModelFilename;

    public static void main(String[] args) {
        new SynthesizeCoverageTests().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                valuePairCoverage, nusmvSpecCoverage, maxGoals);
        final TestSuite ts = new TestSuite(true);
        final Set<CoveragePoint> unknown = new LinkedHashSet<>(info.coveragePoints);
        final Set<CoveragePoint> tlBanned = new HashSet<>();
        final String timeoutText = "*** TIMEOUT ***";

        if (startWithRandom != null) {
            final String[] tokens = startWithRandom.split(":");
            try {
                if (tokens.length != 2) {
                    throw new NumberFormatException();
                }
                final int number = Integer.parseInt(tokens[0]);
                final int length = Integer.parseInt(tokens[1]);

                System.out.println("Generating random tests...");
                fillRandom(ts, data, seed, length, number);

                System.out.println("Checking coverage of random tests...");
                // if the tool is run in the NuSMV mode, a temporary switch to Promela is needed
                final Language saved = Settings.LANGUAGE;
                Settings.LANGUAGE = Language.PROMELA;
                if (saved != Language.PROMELA) {
                    loadData(configurationFilename, promelaHeaderFilename, promelaPlantModelFilename,
                            promelaControllerModelFilename, specFilename);
                }
                for (TestCase tc : ts.testCases()) {
                    info.coveredPoints += examineTestCase(tc, info.coveragePoints, checkFiniteCoverage ? tc.length()
                                    : null, plantCodeCoverage, controllerCodeCoverage);
                }
                Settings.LANGUAGE = saved;
                if (saved != Language.PROMELA) {
                    loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename,
                            specFilename);
                }
                for (CoveragePoint cp : info.coveragePoints) {
                    if (cp.covered()) {
                        unknown.remove(cp);
                        System.out.println("    " + cp);
                    }
                }
            } catch (NumberFormatException ignored) {
                System.err.println("Warning: invalid --startWithRandom format, ignoring.");
            }
        }

        System.out.println("Coverage test synthesis...");

        for (CoveragePoint cp : info.coveragePoints) {
            if (cp.covered()) {
                continue;
            }
            if (tlBanned.contains(cp)) {
                System.out.println("Skipping coverage goal " + cp + " based on earlier violated time limits.");
                continue;
            }
            System.out.println("Coverage test synthesis for " + cp + "...");
            final String code = usualModelCode(null, plantCodeCoverage, controllerCodeCoverage);
            try (final Runner runner = Runner.create(data, code, Collections.singletonList(cp), maxLength)) {
                System.out.println("  " + runner.creationReport());
                final RunnerResult result = runner.synthesize(cp, minimize, unknown, timeout);
                if (result.found()) {
                    for (CoveragePoint covered : result.covered())  {
                        covered.cover();
                        info.coveredPoints++;
                        unknown.remove(covered);
                        System.out.println("    " + covered);
                    }
                    final TestCase tc = result.testCase();
                    ts.add(tc);
                    System.out.println("    Generated: " + tc);
                } else {
                    System.out.println("    " + cp);
                }
                result.log().stream().filter(ResourceMeasurement::isMeasurement).forEach(line ->
                        System.out.println("    " + new ResourceMeasurement(line, "test synthesis")));
                if (result.log().contains(timeoutText)) {
                    System.out.println("    " + timeoutText);
                    tlBanned.addAll(cp.equivalenceClass());
                }
            }
            unknown.remove(cp);
        }
        System.out.println(ts);
        ts.print(outputFilename);
        info.report();
    }
}
