package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ClosedLoopVerify extends Command {
    public final int timeout;

    public ClosedLoopVerify(int timeout, ProblemData data) {
        super("closed-loop-verify", data);
        this.timeout = timeout;
    }

    @Override
    public void execute() throws IOException {
        final String code = modelCode(false, true);
        try (final PrintWriter pw = new PrintWriter(MODEL_FILENAME)) {
            pw.println(code);
        }
        final ProcessBuilder pb = new ProcessBuilder("/bin/bash", "run.sh", MODEL_FILENAME, String.valueOf(timeout));
        pb.redirectError();
        final Process p = pb.start();
        final Scanner sc = new Scanner(p.getInputStream());
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
}
