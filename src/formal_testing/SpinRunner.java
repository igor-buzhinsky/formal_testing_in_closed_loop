package formal_testing;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class SpinRunner {
    private static int SPIN_DIR_INDEX = 0;

    public static final String MODEL_FILENAME = ".model.pml";
    public static final String NESTED_MODEL_FILENAME = ".nested.model.pml";

    private Process spinProcess;

    public SpinRunner() {
        // TODO
    }

    public static Scanner runSpin(int timeout, int optimizationLevel, boolean nested) throws IOException {
        final ProcessBuilder pb = new ProcessBuilder("/bin/bash", "run.sh",
                nested ? NESTED_MODEL_FILENAME : MODEL_FILENAME,String.valueOf(timeout),
                String.valueOf(optimizationLevel), "spindir" + SPIN_DIR_INDEX++);
        pb.redirectError();
        final Process p = pb.start();
        return new Scanner(p.getInputStream());
    }
}
