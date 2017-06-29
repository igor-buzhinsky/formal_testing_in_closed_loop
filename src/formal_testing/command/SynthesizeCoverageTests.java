package formal_testing.command;

import formal_testing.CoveragePoint;
import formal_testing.ProblemData;
import formal_testing.TestCase;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends Command {
    private final boolean includeInternal;
    private final int maxLength;

    public SynthesizeCoverageTests(ProblemData data, boolean includeInternal, int maxLength) {
        super("synthesize-coverage-tests", data);
        this.includeInternal = includeInternal;
        this.maxLength = maxLength;
    }

    @Override
    public void execute() throws IOException {
        final List<CoveragePoint> coveragePoints = coveragePoints(includeInternal);
        final int totalPoints = coveragePoints.size();
        int coveredPoints = 0;
        System.out.println("*** Number of coverage points: " + totalPoints);
        final Set<TestCase> allTestCases = new LinkedHashSet<>();
        for (int len = 1; len <= maxLength; len++) {
            System.out.println("*** Test synthesis for length " + len + "...");
            final StringBuilder code = new StringBuilder();
            code.append(modelCode(false, true, false, Optional.empty(), Optional.empty())).append("\n");
            for (final CoveragePoint cp : coveragePoints) {
                if (!cp.covered()) {
                    code.append(cp.promelaLtlProperty(len)).append("\n");
                }
            }
            try (final PrintWriter pw = new PrintWriter(MODEL_FILENAME)) {
                pw.println(code);
            }

            final String nondetName = "((" + String.join(")|(", data.conf.nondetVars.stream().map(Variable::indexedName)
                    .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + "))";
            final String trailRegexp = "^.*proc.*state.*\\[" + nondetName + " = [0-9]+\\].*$";
            //System.out.println(trailRegexp);

            TestCase currentTC = null;

            try (final Scanner sc = runSpin(0)) {
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    if (line.startsWith("***") && line.endsWith("***")) {
                        if (currentTC != null) {
                            allTestCases.add(currentTC);
                            currentTC = null;
                        }
                        System.out.println(line);
                        if (line.endsWith(" = FALSE ***")) {
                            for (CoveragePoint cp : coveragePoints) {
                                if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                                    cp.cover();
                                    coveredPoints++;
                                    currentTC = new TestCase(data.conf);
                                    break;
                                }
                            }
                        }
                    }
                    if (line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                        currentTC.addValue(tokens[1], tokens[2]);
                    }
                }
            }

            // TODO walk through the tests, adjusting coverage information and removing redundant tests

            if (coveredPoints == totalPoints) {
                break;
            }
        }

        for (TestCase tc : allTestCases) {
            System.out.println(tc);
        }
        System.out.println("Covered points: " + coveredPoints + " / " + totalPoints);

        System.exit(0);
    }
}
