package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class SynthesizeCoverageTests extends Command {
    public SynthesizeCoverageTests(ProblemData data) {
        super("synthesize-coverage-tests", data);
    }

    @Override
    public void execute() throws IOException {

    }
}
