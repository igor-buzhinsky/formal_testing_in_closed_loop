package formal_testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class SpinRunner {
    private final String dirName;
    private final int timeout;
    private final String modelName;

    private Process spinProcess;
    private ProcessBuilder pb;

    private static int SPIN_DIR_INDEX = 0;

    public static final String MODEL_FILENAME = ".model.pml";
    public static final String NESTED_MODEL_FILENAME = ".nested.model.pml";
    private static final String FORMAT = "*** %U user, %S system, %e elapsed, %Mk maxresident ***\\n";

    public SpinRunner(String modelCode, int timeout, boolean nested, int optimizationLevel) throws IOException {
        this.timeout = timeout;

        dirName = "spindir" + SPIN_DIR_INDEX++;
        Files.createDirectories(Paths.get(dirName));

        modelName = nested ? NESTED_MODEL_FILENAME : MODEL_FILENAME;
        try (final PrintWriter pw = new PrintWriter(modelName)) {
            pw.println(modelCode);
        }

        // generate pan source
        pb = new ProcessBuilder("/usr/bin/time", "-f", FORMAT, "spin", "-a", "../" + modelName)
                .redirectErrorStream(true).directory(new File(dirName));
        spinProcess = pb.start();
        try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                if (line.startsWith("***")) {
                    System.out.println(line);
                }
            }
        }
        waitFor();

        // compile pan
        pb = new ProcessBuilder("/usr/bin/time", "-f", FORMAT, "cc", "-O" + optimizationLevel, "-DVECTORSZ=1024",
                "-o", "pan", "pan.c").redirectErrorStream(true).directory(new File(dirName));
        spinProcess = pb.start();
        try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
                if (line.startsWith("***")) {
                    System.out.println(line);
                }
            }
        }
        waitFor();
    }

    private int waitFor() {
        try {
            return spinProcess.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> pan(String propertyName) throws IOException {
        // timeout "$timeout"s /usr/bin/time -f "$format" ./pan -a -N "$prop" -m5000000 2>&1
        pb = new ProcessBuilder("timeout", timeout + "s", "/usr/bin/time", "-f", FORMAT, "./pan", "-a", "-N",
                propertyName, "-m5000000").redirectErrorStream(true).directory(new File(dirName));
        spinProcess = pb.start();
        final int retCode = waitFor();
        final File trailFile = new File(dirName + "/" + modelName + ".trail");
        final List<String> result = new ArrayList<>();
        if (retCode == 124) {
            result.add("*** " + propertyName + " : TIMEOUT ***");
        } else if (trailFile.exists()) {
            result.add("*** " + propertyName + " = FALSE ***");

            pb = new ProcessBuilder("/usr/bin/time", "-f", FORMAT, "spin", "-k", modelName + ".trail", "-pglrs",
                    "../" + modelName).redirectErrorStream(true).directory(new File(dirName));
            spinProcess = pb.start();
            try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
                while (sc.hasNextLine()) {
                    result.add(sc.nextLine());
                }
            }
            waitFor();
        } else {
            result.add("*** " + propertyName + " = TRUE ***");
        }
        if (trailFile.exists()) {
            trailFile.delete();
        }
        return result;
    }

    private void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

    public void cleanup() throws IOException {
        delete(new File(dirName));
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
