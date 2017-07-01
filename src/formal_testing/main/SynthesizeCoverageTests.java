package formal_testing.main;

import formal_testing.SpinRunner;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.coverage.CoveragePoint;
import formal_testing.variable.Variable;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends MainBase {
    @Argument(usage = "configuration filename", metaVar = "<filename>", required = true, index = 0)
    private String configurationFilename;

    @Argument(usage = "header filename", metaVar = "<filename>", required = true, index = 1)
    private String headerFilename;

    @Argument(usage = "plant model filename", metaVar = "<filename>", required = true, index = 2)
    private String plantModelFilename;

    @Argument(usage = "controller model filename", metaVar = "<filename>", required = true, index = 3)
    private String controllerModelFilename;

    @Argument(usage = "specification filename", metaVar = "<filename>", required = true, index = 4)
    private String specFilename;

    @Option(name = "--output", usage = "output filename", metaVar = "<filename>")
    private String outputFilename;

    @Option(name = "--maxlen", usage = "maximum test length, default = 10", metaVar = "<length>")
    private int maxLength = 10;

    @Option(name = "--includeInternal", handler = BooleanOptionHandler.class, usage = "cover internal variables")
    private boolean includeInternal;

    @Option(name = "--checkFiniteCoverage", handler = BooleanOptionHandler.class,
            usage = "while checking coverage, ignore infinite continuations of test cases")
    private boolean checkFiniteCoverage;

    @Option(name = "--minimize", handler = BooleanOptionHandler.class,
            usage = "check coverage of each new test, thus making the resultant test suite smaller")
    private boolean minimize;

    @Option(name = "--valuePairCoverage", handler = BooleanOptionHandler.class, usage = "cover value pairs (slow)")
    private boolean valuePairCoverage;

    @Option(name = "--plantCodeCoverage", handler = BooleanOptionHandler.class, usage = "cover plant code")
    private boolean plantCodeCoverage;

    @Option(name = "--controllerCodeCoverage", handler = BooleanOptionHandler.class, usage = "cover controller code")
    private boolean controllerCodeCoverage;

    public static void main(String[] args) throws IOException, InterruptedException {
        new SynthesizeCoverageTests().run(args);
    }

    private int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints, int steps) throws IOException {
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        final String code = modelCode(false, false, false, Optional.of(tc.promelaHeader()),
                Optional.of(tc.promelaBody(false)), plantCodeCoverage, controllerCodeCoverage, Optional.empty());

        final List<String> claims = Arrays.asList((checkFiniteCoverage ? coverageProperties(uncovered, steps)
                : coverageProperties(uncovered)).split("\n"));

        int newCovered = 0;
        int lines = 0;
        try (final SpinRunner spinRunner = new SpinRunner(code, uncovered, claims, 0, 2)) {
            final String prefix = "(" + uncovered.size() + ") ";
            System.out.print(prefix);
            for (CoveragePoint cp : uncovered) {
                final List<String> result = spinRunner.pan(cp.promelaLtlName());
                for (String line : result) {
                    final String suffix = lines > 0 && lines % 150 == 0
                            ? ("\n" + new String(new char[prefix.length()]).replace('\0', ' ')) : "";
                    if (line.equals("*** " + cp.promelaLtlName() + " = TRUE ***")) {
                        lines++;
                        cp.cover();
                        newCovered++;
                        System.out.print("+" + suffix);
                    } else if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                        lines++;
                        System.out.print("-" + suffix);
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
    protected void launcher() throws IOException, InterruptedException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final CodeCoverageCounter counter = new CodeCoverageCounter();
        usualModelCode(Optional.of(counter));
        final List<CoveragePoint> coveragePoints = coveragePoints(includeInternal, valuePairCoverage,
                counter.coverageClaims);
        final int totalPoints = coveragePoints.size();
        int coveredPoints = 0;
        System.out.println("*** Number of coverage points: " + totalPoints);

        final TestSuite testSuite = new TestSuite();
        final String nondetName = "(" + String.join("|", data.conf.nondetVars.stream().map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + ")";
        final String trailRegexp = "^.*proc.*state.*\\[" + nondetName + " = [0-9]+\\].*$";
        for (int len = 1; len <= maxLength; len++) {
            System.out.println("*** Test synthesis for length " + len + "...");
            System.out.println("*** Covered points: " + coveredPoints + " / " + totalPoints);

            final String code = usualModelCode(Optional.empty());
            final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                    .collect(Collectors.toList());
            final List<String> claims = new ArrayList<>();
            for (CoveragePoint cp : uncovered) {
                claims.add(cp.promelaLtlProperty(len, true));
            }

            try (final SpinRunner spinRunner = new SpinRunner(code, uncovered, claims, 0, 2)) {
                for (final CoveragePoint cp : coveragePoints) {
                    if (cp.covered()) {
                        continue;
                    }
                    final List<String> result = spinRunner.pan(cp.promelaLtlName());
                    TestCase tc = null;
                    for (String line : result) {
                        if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                            cp.cover();
                            coveredPoints++;
                            tc = new TestCase(data.conf);
                            System.out.println(cp);
                        } else if (line.equals("*** " + cp.promelaLtlName() + " = TRUE ***")) {
                            System.out.println(cp);
                        } else if (tc != null && line.matches(trailRegexp)) {
                            final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                            tc.addValue(tokens[1], tokens[2]);
                        } else if (line.startsWith("***")) {
                            System.out.println(line);
                        }
                    }
                    if (tc != null) {
                        //tc.validate();
                        if (minimize) {
                            coveredPoints += examineTestCase(tc, coveragePoints, len);
                        }
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
        System.out.println("*** Covered points: " + coveredPoints + " / " + totalPoints);
        if (coveredPoints < totalPoints) {
            System.out.println("*** Not covered:");
            coveragePoints.stream().filter(cp -> !cp.covered()).forEach(System.out::println);
        }
    }
}
