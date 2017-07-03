package formal_testing.main;

import formal_testing.SpinRunner;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class Run extends MainBase {
    @Option(name = "--input", usage = "input filename", metaVar = "<filename>")
    private String filename;

    @Option(name = "--verbose", handler = BooleanOptionHandler.class, usage = "verbose output")
    private boolean verbose;

    @Option(name = "--measureCoverage", handler = BooleanOptionHandler.class, usage = "measure coverage")
    private boolean measureCoverage;

    @Option(name = "--includeInternal", handler = BooleanOptionHandler.class,
            usage = "if measureCoverage is on, measure coverage of internal variables")
    private boolean includeInternal;

    @Option(name = "--valuePairCoverage", handler = BooleanOptionHandler.class,
            usage = "if measureCoverage is on, measure value pair coverage")
    private boolean valuePairCoverage;

    @Option(name = "--plantCodeCoverage", handler = BooleanOptionHandler.class,
            usage = "if measureCoverage is on, measure plant code coverage")
    private boolean plantCodeCoverage;

    @Option(name = "--controllerCodeCoverage", handler = BooleanOptionHandler.class,
            usage = "if measureCoverage is on, measure controller code coverage")
    private boolean controllerCodeCoverage;

    public static void main(String[] args) throws IOException {
        new Run().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final TestSuite ts = TestSuite.read(filename);
        final String code = modelCode(true, false, true, Optional.of(ts.promelaHeader()), Optional.of(ts.promelaBody()),
                false, false, Optional.empty());

        System.out.println("Running test suite " + filename + "...");
        try (final SpinRunner spinRunner = new SpinRunner(code, 0, 2)) {
            for (String prop : promelaPropsFromCode(code)) {
                final List<String> result = spinRunner.pan(prop);
                result.stream().filter(line -> verbose || line.startsWith("***")).forEach(System.out::println);
            }
        }

        if (measureCoverage) {
            System.out.println("Measuring test suite coverage...");
            final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                    valuePairCoverage);

            for (TestCase tc : ts.testCases()) {
                if (info.allCovered()) {
                    break;
                }
                info.coveredPoints += examineTestCase(tc, info.coveragePoints, 0, plantCodeCoverage,
                        controllerCodeCoverage);
            }

            info.report();
        }
    }
}
