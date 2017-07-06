package formal_testing.main;

import formal_testing.TestCase;
import formal_testing.TestSuite;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;

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
        final String code = modelCode(true, false, true, ts.header(), ts.body(), false, false, null);

        System.out.println("Running test suite " + filename + "...");
        verifyAll(code, 0, verbose);
        if (measureCoverage) {
            System.out.println("\nMeasuring test suite coverage...");
            final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                    valuePairCoverage);

            for (TestCase tc : ts.testCases()) {
                if (info.allCovered()) {
                    break;
                }
                info.coveredPoints += examineTestCase(tc, info.coveragePoints, -1, plantCodeCoverage,
                        controllerCodeCoverage);
            }

            info.report();
        }
    }
}
