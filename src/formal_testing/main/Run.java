package formal_testing.main;

import formal_testing.SpinRunner;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class Run extends MainBase {
    @Argument(usage = "configuration filename", metaVar = "<filename>", required = true, index = 0)
    private String configurationFilename;

    @Argument(usage = "header filename", metaVar = "<filename>", required = true, index = 1)
    private String headerFilename;

    @Argument(usage = "plant model filename", metaVar = "<filename>", required = true, index = 2)
    private String plantModelFilename;

    @Argument(usage = "controller model filename", metaVar = "<filename>", required = true, index = 3)
    private String controllerModelFilename;

    @Argument(usage = "specification filename", metaVar = "<filename>", required = true, index = 4)
    private String specFilename;

    @Option(name = "--input", usage = "input filename", metaVar = "<filename>")
    private String filename;

    @Option(name = "--verbose", handler = BooleanOptionHandler.class, usage = "verbose output")
    private boolean verbose;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Run().run(args);
    }

    @Override
    protected void launcher() throws IOException, InterruptedException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

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

        // TODO measure coverage
    }
}
