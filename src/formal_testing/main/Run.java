package formal_testing.main;

import formal_testing.TestCase;
import formal_testing.TestSuite;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class Run extends MainArgs {
    @Option(name = "--input", usage = "input filename", metaVar = "<filename>", required = true)
    private String filename;

    @Option(name = "--verbose", handler = BooleanOptionHandler.class, usage = "verbose output")
    private boolean verbose;

    @Option(name = "--verify", handler = BooleanOptionHandler.class, usage = "verify temporal specification")
    private boolean verify;

    @Option(name = "--output", usage = "output filename for formal model", metaVar = "<filename>")
    private String outputFilename = "test.bin";

    @Option(name = "--checkFiniteCoverage", handler = BooleanOptionHandler.class,
            usage = "while checking coverage, ignore infinite continuations of test cases")
    private boolean checkFiniteCoverage;

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

    @Option(name = "--maxGoals", usage = "maximum coverage goals per integer variable", metaVar = "<maxGoals>")
    private int maxGoals = Integer.MAX_VALUE;

    @Option(name = "--inheritIO", handler = BooleanOptionHandler.class, usage = "output to stdout/stderr online")
    private boolean inheritIO;

    public static void main(String[] args) {
        new Run().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final TestSuite ts = TestSuite.read(filename);
        final String code = modelCode(true, false, true, ts.header(), ts.body(data.conf), false, false, null, true);

        System.out.println("Loaded test suite " + filename + ", #test_cases = " + ts.size()
                + ", #elements = " + ts.totalLength());

        if (outputFilename != null) {
            System.out.println("Writing formal model to " + outputFilename + "...");
            try (PrintWriter pw = new PrintWriter(outputFilename)) {
                pw.println(code);
            }
        }
        if (verify) {
            System.out.println("Running verification for test suite " + filename + "...");
            verifyAll(code, 0, verbose, null, inheritIO);
        }
        if (measureCoverage) {
            System.out.println("\nMeasuring coverage of test suite " + filename + "...");
            final CoverageInfo info = new CoverageInfo(plantCodeCoverage, controllerCodeCoverage, includeInternal,
                    valuePairCoverage, nusmvSpecCoverage, maxGoals);

            for (TestCase tc : ts.testCases()) {
                if (info.allCovered()) {
                    break;
                }
                info.coveredPoints += examineTestCase(tc, info.coveragePoints, checkFiniteCoverage ? tc.length() : null,
                        plantCodeCoverage, controllerCodeCoverage);
            }

            info.report();
        }
    }
}
