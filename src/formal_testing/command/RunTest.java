package formal_testing.command;

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

    public RunTest(ProblemData data, String filename) {
        super(data);
        this.filename = filename;
    }

    @Override
    public void execute() throws IOException {
        final String header = new String(Files.readAllBytes(Paths.get(filename + ".header")));
        final String body = new String(Files.readAllBytes(Paths.get(filename + ".body")));
        final String code = modelCode(true, false, true, Optional.of(header), Optional.of(body));

        try (final SpinRunner spinRunner = new SpinRunner(code, 0, false, 2)) {
            for (String prop : propsFromCode(code)) {
                final List<String> result = spinRunner.pan(prop);
                result.forEach(System.out::println);
            }
        }
    }
}
