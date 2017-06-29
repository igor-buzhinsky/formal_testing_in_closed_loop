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

    private int examineTestCase(TestCase tc, List<CoveragePoint> coveragePoints) throws IOException {
        final String nestedExecutionCode = modelCode(true, false, false, Optional.of(tc.promelaHeader()),
                Optional.of(tc.promelaBody()));
        final List<CoveragePoint> uncovered = coveragePoints.stream().filter(cp -> !cp.covered())
                .collect(Collectors.toList());

        try (final PrintWriter pw = new PrintWriter(NESTED_MODEL_FILENAME)) {
            pw.println(nestedExecutionCode);
            pw.println(coverageProperties(uncovered));
        }

        int newCovered = 0;

        System.out.print("(" + uncovered.size() + ") ");
        try (final Scanner sc = runSpin(0, 2)) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                if (line.endsWith(" = FALSE ***")) {
                    for (CoveragePoint cp : uncovered) {
                        if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                            cp.cover();
                            newCovered++;
                            System.out.print("+");
                        }
                    }
                } else if (line.endsWith(" = TRUE ***")) {
                    System.out.print("-");
                }
            }
        }
        System.out.println();
        return newCovered;
    }

    @Override
    public void execute() throws IOException {
        final List<CoveragePoint> coveragePoints = coveragePoints(includeInternal);
        final int totalPoints = coveragePoints.size();
        int coveredPoints = 0;
        System.out.println("*** Number of coverage points: " + totalPoints);
        final Set<TestCase> allTestCases = new LinkedHashSet<>();
        final String nondetName = "((" + String.join(")|(", data.conf.nondetVars.stream().map(Variable::indexedName)
                .map(s -> s.replace("[", "\\[").replace("]", "\\]")).collect(Collectors.toList())) + "))";
        final String trailRegexp = "^.*proc.*state.*\\[" + nondetName + " = [0-9]+\\].*$";
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

            TestCase currentTC = null;

            try (final Scanner sc = runSpin(0, 2)) {
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    if (line.startsWith("***") && line.endsWith("***")) {
                        if (currentTC != null) {
                            final int newCovered = examineTestCase(currentTC, coveragePoints);
                            if (newCovered > 0) {
                                allTestCases.add(currentTC);
                                coveredPoints += newCovered;
                            }
                            currentTC = null;
                        }
                        System.out.println(line);
                        if (line.endsWith(" = FALSE ***")) {
                            for (CoveragePoint cp : coveragePoints) {
                                if (line.equals("*** " + cp.promelaLtlName() + " = FALSE ***")) {
                                    currentTC = cp.covered() ? null : new TestCase(data.conf);
                                    break;
                                }
                            }
                        }
                    }
                    if (currentTC != null && line.matches(trailRegexp)) {
                        final String[] tokens = line.split("((\t\\[)|( = )|(\\]$))");
                        currentTC.addValue(tokens[1], tokens[2]);
                    }
                }
            }
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
