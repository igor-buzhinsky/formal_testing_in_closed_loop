package formal_testing;

import formal_testing.main.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        final String configurationFilename = args[0];
        final String headerFilename = args[1];
        final String plantModelFilename = args[2];
        final String controllerModelFilename = args[3];
        final String specFilename = args[4];
        final Configuration conf = Configuration.fromFile(configurationFilename);
        System.out.println(conf);
        System.out.println();

        final ProblemData data = new ProblemData(conf,
                new String(Files.readAllBytes(Paths.get(headerFilename))),
                new String(Files.readAllBytes(Paths.get(plantModelFilename))),
                new String(Files.readAllBytes(Paths.get(controllerModelFilename))),
                new String(Files.readAllBytes(Paths.get(specFilename)))
        );
        final List<Command> commands = new ArrayList<>();
        for (int i = 5; i < args.length; i++) {
            final String command = args[i];
            switch (command) {
                case "closed-loop-verify":
                    final int timeout = Integer.parseInt(args[++i]);
                    commands.add(new ClosedLoopVerify(data, timeout));
                    break;
                case "generate-random":
                    final int length = Integer.parseInt(args[++i]);
                    final int number = Integer.parseInt(args[++i]);
                    final String filename1 = args[++i];
                    commands.add(new GenerateRandom(data, length, number, filename1, new Random()));
                    break;
                case "run-test":
                    final String filename2 = args[++i];
                    final boolean verbose = Boolean.parseBoolean(args[++i]);
                    commands.add(new RunTest(data, filename2, verbose));
                    break;
                case "synthesize-coverage-tests":
                    final boolean includeInternal2 = Boolean.parseBoolean(args[++i]);
                    final int maxLength = Integer.parseInt(args[++i]);
                    final boolean checkFiniteCoverage = Boolean.parseBoolean(args[++i]);
                    final boolean minimize = Boolean.parseBoolean(args[++i]);
                    final boolean valuePairCoverage = Boolean.parseBoolean(args[++i]);
                    final boolean plantCodeCoverage = Boolean.parseBoolean(args[++i]);
                    final boolean controllerCodeCoverage = Boolean.parseBoolean(args[++i]);
                    final String filename3 = args[++i];
                    commands.add(new SynthesizeCoverageTests(data, includeInternal2, maxLength, checkFiniteCoverage,
                            minimize, valuePairCoverage, plantCodeCoverage, controllerCodeCoverage, filename3));
                    break;
                default:
                    throw new RuntimeException("Unknown command " + command);
            }
        }

        for (Command command : commands) {
            command.execute();
        }
    }
}
