package formal_testing.command;

import formal_testing.ProblemData;
import formal_testing.SpinRunner;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ClosedLoopVerify extends Command {
    private final int timeout;

    public ClosedLoopVerify(ProblemData data, int timeout) {
        super(data);
        this.timeout = timeout;
    }

    @Override
    public void execute() throws IOException {
        final String code = modelCode(false, true, true, Optional.empty(), Optional.empty(), false, false,
                Optional.empty());

        try (final SpinRunner spinRunner = new SpinRunner(code, timeout, false, 2)) {
            for (String prop : propsFromCode(code)) {
                final List<String> result = spinRunner.pan(prop);
                result.forEach(System.out::println);
            }
        }
    }
}
