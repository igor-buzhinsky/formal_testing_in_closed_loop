package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ClosedLoopVerify extends Command {
    private final int timeout;

    public ClosedLoopVerify(ProblemData data, int timeout) {
        super("closed-loop-verify", data);
        this.timeout = timeout;
    }

    @Override
    public void execute() throws IOException {
        final String code = modelCode(false, true, true, Optional.empty(), Optional.empty());
        try (final PrintWriter pw = new PrintWriter(MODEL_FILENAME)) {
            pw.println(code);
        }
        try (final Scanner sc = runSpin(timeout)) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        }
    }
}
