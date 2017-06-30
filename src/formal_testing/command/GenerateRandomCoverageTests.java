package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandomCoverageTests extends Command {
    private final boolean includeInternal;

    public GenerateRandomCoverageTests(ProblemData data, boolean includeInternal) {
        super(data);
        this.includeInternal = includeInternal;
    }

    @Override
    public void execute() throws IOException {

        // while (...)
        //  generate random test
        //  run SPIN and verify only reachability properties
        //  record results

        //System.out.println(coverageProperties(includeInternal));
        System.exit(0);
    }
}
