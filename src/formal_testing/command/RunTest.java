package formal_testing.command;

import formal_testing.ProblemData;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class RunTest extends Command {
    private final String filename;

    public RunTest(ProblemData data, String filename) {
        super("run-test", data);
        this.filename = filename;
    }

    @Override
    public void execute() throws IOException {
        final String header = new String(Files.readAllBytes(Paths.get(filename + ".header")));
        final String body = new String(Files.readAllBytes(Paths.get(filename + ".body")));
        final String code = modelCode(true, false, true, Optional.of(header), Optional.of(body));
        try (final PrintWriter pw = new PrintWriter(MODEL_FILENAME)) {
            pw.println(code);
        }
        try (final Scanner sc = runSpin(0)) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        }
    }
}
