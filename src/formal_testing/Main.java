package formal_testing;

import formal_testing.command.ClosedLoopVerify;
import formal_testing.command.Command;
import formal_testing.variable.BooleanVariable;
import formal_testing.variable.IntegerVariable;
import formal_testing.variable.Variable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final String configurationFilename = args[0];
        final String headerFilename = args[1];
        final String plantModelFilename = args[2];
        final String controllerModelFilename = args[3];
        final String specFilename = args[4];
        final Configuration conf = new Configuration();
        try (Scanner sc = new Scanner(new File(configurationFilename))) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine().replaceAll("#.*$", "").trim();
                if (line.isEmpty()) {
                    continue;
                }
                final String[] tokens = line.split(" +");
                final String varType = tokens[0];
                final String datatype = tokens[2];
                final List<Variable> list;
                switch (varType) {
                    case "input": list = conf.inputVars; break;
                    case "output": list = conf.outputVars; break;
                    case "nondet": list = conf.nondetVars; break;
                    case "plant_internal": list = conf.plantInternalVars; break;
                    case "controller_internal": list = conf.controllerInternalVars; break;
                    default: throw new RuntimeException("Invalid variable type " + varType);
                }
                final String name = tokens[1];
                final String realName;
                final int size;
                final boolean isArray;
                if (name.contains("[") && name.contains("]")) {
                    final String[] nameTokens = name.split("[\\[\\]]");
                    realName = nameTokens[0];
                    size = Integer.parseInt(nameTokens[1]);
                    isArray = true;
                } else {
                    realName = name;
                    size = 1;
                    isArray = false;
                }
                for (int i = 0; i < size; i++) {
                    list.add(datatype.equals("bool")
                            ? new BooleanVariable(realName, isArray, size, i)
                            : new IntegerVariable(realName, datatype, isArray, size, i));
                }
            }
        }
        System.out.println(conf);

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
                    commands.add(new ClosedLoopVerify(timeout, data));
                    // TODO "closed-loop-verify" option - run closed-loop model checking in SPIN
                    break;
                case "random-test-generate":
                    // TODO "random-test-generate[length,name]" option - generate a random test
                    break;
                case "run-test":
                    break;
                // TODO "run-test[name]" option - run a test
                case "random-coverage-test-generate":
                    // TODO "random-coverage-test-generate[length,fraction,include_internal]" option
                    // TODO     - generate random tests until coverage is reached
                    break;
                case "synthesis-coverage-test-generate":
                    // TODO "synthesis-coverage-test-generate[length,fraction,include_internal]" option
                    // TODO     - use model checking to generate coverage tests
                    // TODO     - nondeterministic choices can be extracted from the lines of "if" statements
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
