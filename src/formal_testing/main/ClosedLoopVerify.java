package formal_testing.main;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ClosedLoopVerify extends MainArgs {
    @Option(name = "--timeout", usage = "timeout in seconds, default = 0 = no", metaVar = "<timeout>")
    private int timeout;

    @Option(name = "--verbose", handler = BooleanOptionHandler.class, usage = "verbose output")
    private boolean verbose;

    @Option(name = "--dynamic", handler = BooleanOptionHandler.class, usage = "enable NuSMV -dynamic")
    private boolean dynamic;

    @Option(name = "--coi", handler = BooleanOptionHandler.class, usage = "enable NuSMV -coi")
    private boolean coi;

    public static void main(String[] args) throws IOException {
        new ClosedLoopVerify().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);
        final String code = modelCode(false, true, true, null, null, false, false, null);
        verifyAll(code, timeout, verbose, dynamic, coi);
    }
}
