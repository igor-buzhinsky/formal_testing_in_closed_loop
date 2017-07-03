package formal_testing.main;

import formal_testing.SpinRunner;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ClosedLoopVerify extends MainBase {
    @Option(name = "--timeout", usage = "timeout in seconds, default = 0 = no", metaVar = "<timeout>")
    private int timeout;

    public static void main(String[] args) throws IOException {
        new ClosedLoopVerify().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);

        final String code = modelCode(false, true, true, Optional.empty(), Optional.empty(), false, false,
                Optional.empty());

        try (final SpinRunner spinRunner = new SpinRunner(code, timeout, 2)) {
            for (String prop : promelaPropsFromCode(code)) {
                final List<String> result = spinRunner.pan(prop);
                result.forEach(System.out::println);
            }
        }
    }

}
