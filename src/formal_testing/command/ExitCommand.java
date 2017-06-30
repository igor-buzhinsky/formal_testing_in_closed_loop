package formal_testing.command;

import formal_testing.ProblemData;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class ExitCommand extends Command {
    public ExitCommand(ProblemData data) {
        super(data);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
