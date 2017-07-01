package formal_testing.main;

import formal_testing.ProblemData;
import formal_testing.SpinRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class RunTest extends Command {
    private final String filename;
    private final boolean verbose;

    public RunTest(ProblemData data, String filename, boolean verbose) {
        super(data);
        this.filename = filename;
        this.verbose = verbose;
    }

    @Override
    public void execute() throws IOException {
        final String header = new String(Files.readAllBytes(Paths.get(filename + ".header")));
        final String body = new String(Files.readAllBytes(Paths.get(filename + ".body")));
        final String code = modelCode(true, false, true, Optional.of(header), Optional.of(body), false, false,
                Optional.empty());

        System.out.println("Running test suite " + filename + "...");
        try (final SpinRunner spinRunner = new SpinRunner(code, 0, 2)) {
            for (String prop : propsFromCode(code)) {
                final List<String> result = spinRunner.pan(prop);
                result.stream().filter(line -> verbose || line.startsWith("***")).forEach(System.out::println);
            }
        }
    }
}
