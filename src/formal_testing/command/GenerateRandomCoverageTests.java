package formal_testing.command;

import formal_testing.CoveragePoint;
import formal_testing.ProblemData;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandomCoverageTests extends Command {
    private final boolean includeInternal;

    public GenerateRandomCoverageTests(ProblemData data, boolean includeInternal) {
        super("generate-random-coverage-tests", data);
        this.includeInternal = includeInternal;
    }

    private String coverageProperties() {
        return String.join("\n", coveragePoints(includeInternal).stream()
                .map(CoveragePoint::promelaLtlProperty).collect(Collectors.toList()));
    }

    @Override
    public void execute() throws IOException {

        // while (...)
        //  generate random test
        //  run SPIN and verify only reachability properties
        //  record results

        System.out.println(coverageProperties());
        System.exit(0);
    }
}
