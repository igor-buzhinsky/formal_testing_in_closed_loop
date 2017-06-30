package formal_testing;

import formal_testing.variable.BooleanVariable;
import formal_testing.variable.IntegerVariable;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class Configuration {
    public final List<Variable> inputVars = new ArrayList<>();
    public final List<Variable> outputVars = new ArrayList<>();
    public final List<Variable> nondetVars = new ArrayList<>();
    public final List<Variable> plantInternalVars = new ArrayList<>();
    public final List<Variable> controllerInternalVars = new ArrayList<>();

    @Override
    public String toString() {
        return "input: " + inputVars + "\n" +
                "output: " + outputVars + "\n" +
                "nondet: " + nondetVars + "\n" +
                "plant_internal: " + plantInternalVars + "\n" +
                "controller_internal: " + controllerInternalVars;
    }

    public static Configuration fromFile(String filename) throws FileNotFoundException {
        final Configuration conf = new Configuration();
        try (final Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                final String line = sc.nextLine().replaceAll("#.*$", "").trim();
                if (line.isEmpty()) {
                    continue;
                }
                final String[] tokens = line.split(" +", 3);
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
                    list.add(datatype.startsWith("{") ? new SetVariable(realName,
                            Arrays.asList(datatype.replace("{", "").replace("}", "").trim().split(" *, *")),
                            isArray, size, i) : datatype.equals("bool")
                            ? new BooleanVariable(realName, isArray, size, i)
                            : new IntegerVariable(realName, datatype, isArray, size, i));
                }
            }
        }
        return conf;
    }
}
