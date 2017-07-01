package formal_testing;

import formal_testing.coverage.CoveragePoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by buzhinsky on 6/30/17.
 */
public class SpinRunner implements AutoCloseable {
    private final String dirName;
    private final int timeout;
    private final int parts;
    private final Map<String, Integer> propertyToPart = new HashMap<>();

    private Process spinProcess;

    private static int SPIN_DIR_INDEX = 0;

    private static final int MAX_CLAIMS_IN_ONE_PAN = 50;
    private static final String MODEL_FILENAME = "model.pml";
    private static final String FORMAT_BASE = ": %U user, %S system, %e elapsed, %Mk maxresident ***\\n";
    private static final String FORMAT_GENERATE = "*** Generated pan source" + FORMAT_BASE;
    private static final String FORMAT_COMPILE = "*** Compiled pan" + FORMAT_BASE;
    private static final String FORMAT_RUN = "*** Executed pan" + FORMAT_BASE;

    public SpinRunner(String modelCode, int timeout, int optimizationLevel) throws IOException {
        this(modelCode, Collections.emptyList(), Collections.emptyList(), timeout, optimizationLevel);
    }

    public SpinRunner(String modelCode, List<CoveragePoint> coveragePoints, List<String> coverageClaims,
                      int timeout, int optimizationLevel) throws IOException {
        this.timeout = timeout;

        dirName = "spindir." + SPIN_DIR_INDEX++;
        final File dir = new File(dirName);
        if (dir.exists()) {
            delete(dir);
        }
        Files.createDirectories(Paths.get(dirName));

        parts = coverageClaims.size() / MAX_CLAIMS_IN_ONE_PAN + 1;
        for (int i = 0; i < parts; i++) {
            try (final PrintWriter pw = new PrintWriter(dirName + "/" + MODEL_FILENAME + "." + i)) {
                pw.println(modelCode);
                final int start = coverageClaims.size() / parts * i;
                final int end = coverageClaims.size() / parts * (i + 1);
                for (int j = start; j < end; j++) {
                    pw.println(coverageClaims.get(j));
                    propertyToPart.put(coveragePoints.get(j).promelaLtlName(), i);
                }
            }
        }

        // generate pan source
        for (int i = 0; i < parts; i++) {
            spinProcess = new ProcessBuilder("/usr/bin/time", "-f", FORMAT_GENERATE, "spin", "-a",
                    MODEL_FILENAME + "." + i).redirectErrorStream(true).directory(new File(dirName)).start();
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
            spinProcess = new ProcessBuilder("/usr/bin/time", "-f", FORMAT_COMPILE, "cc", "-O" + optimizationLevel,
                    "-DVECTORSZ=1024", "-o", "pan", "pan.c")
                    .redirectErrorStream(true).directory(new File(dirName)).start();
            try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
                while (sc.hasNextLine()) {
                    final String line = sc.nextLine();
                    if (line.startsWith("***")) {
                        System.out.println(line);
                    }
                }
            }
            waitFor();

            Files.move(Paths.get(dirName + "/pan"), Paths.get(dirName + "/pan." + i));
        }
    }

    private int waitFor() {
        try {
            return spinProcess.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> pan(String propertyName) throws IOException {
        final int part = (propertyToPart.containsKey(propertyName) ? propertyToPart.get(propertyName) : 0);
        final String suffix = "." + part;
        final String trailPath = dirName + "/" + MODEL_FILENAME + suffix + ".trail";
        final List<String> result = new ArrayList<>();
        File trailFile = null;
        try {
            spinProcess = new ProcessBuilder("timeout", timeout + "s", "/usr/bin/time", "-f", FORMAT_RUN,
                    "./pan" + suffix, "-a", "-N", propertyName, "-m5000000")
                    .redirectErrorStream(true).directory(new File(dirName)).start();
            try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
                while (sc.hasNextLine()) {
                    result.add(sc.nextLine());
                }
            }
            final int retCode = waitFor();
            trailFile = new File(trailPath);
            if (retCode == 124) {
                result.add("*** " + propertyName + " : TIMEOUT ***");
            } else if (trailFile.exists()) {
                result.add("*** " + propertyName + " = FALSE ***");

                spinProcess = new ProcessBuilder("/usr/bin/time", "-f", FORMAT_RUN, "spin", "-k",
                        MODEL_FILENAME + suffix + ".trail", "-pglrs", MODEL_FILENAME + suffix)
                        .redirectErrorStream(true).directory(new File(dirName)).start();
                try (final Scanner sc = new Scanner(spinProcess.getInputStream())) {
                    while (sc.hasNextLine()) {
                        result.add(sc.nextLine());
                    }
                }
                waitFor();
            } else {
                result.add("*** " + propertyName + " = TRUE ***");
            }
        } finally {
            if (trailFile != null && trailFile.exists()) {
                try {
                    Files.delete(Paths.get(trailPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    @Override
    public void close() throws IOException {
        delete(new File(dirName));
    }
}
