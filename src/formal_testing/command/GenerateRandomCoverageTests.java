package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandomCoverageTests extends Command {
    public GenerateRandomCoverageTests(ProblemData data) {
        super("generate-random-coverage-tests", data);
    }

    @Override
    public void execute() throws IOException {

    }
}
