package formal_testing;

import formal_testing.value.BooleanValue;
import formal_testing.value.IntegerValue;
import formal_testing.value.SetValue;
import formal_testing.variable.BooleanVariable;
import formal_testing.variable.IntegerVariable;
import formal_testing.variable.SetVariable;
import formal_testing.variable.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/26/17.
 */
public class Configuration {
    public final List<Variable<?>> inputVars = new ArrayList<>();
    public final List<Variable<?>> outputVars = new ArrayList<>();
    public final List<Variable<?>> nondetVars = new ArrayList<>();
    public final List<Variable<?>> plantInternalVars = new ArrayList<>();
    public final List<Variable<?>> controllerInternalVars = new ArrayList<>();

    private final Map<String, Variable<?>> variablesByNames = new LinkedHashMap<>();

    public Variable<?> byName(String indexedName) {
        return variablesByNames.get(indexedName);
    }

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
                final String[] tokens = line.split(" +");
                final String varType = tokens[0];
                final String datatype = tokens[2];
                final List<Variable<?>> list;
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
                final String initialValue = tokens[3];
                for (int i = 0; i < size; i++) {
                    final Variable<?> var;

                    if (datatype.equals("bool")) {
                        var = new BooleanVariable(realName, BooleanValue.read(initialValue), isArray, size, i);
                    } else if (datatype.contains("..")) {
                        var = new IntegerVariable(realName, IntegerValue.read(initialValue), datatype, isArray, size, i);
                    } else {
                        final List<String> stringValues = Arrays.asList(datatype.split(","));
                        final List<SetValue> values = stringValues.stream().map(SetValue::new)
                                .collect(Collectors.toList());
                        var = new SetVariable(realName, SetValue.read(initialValue, stringValues), values, isArray,
                                size, i);
                    }
                    list.add(var);
                    conf.variablesByNames.put(var.indexedName(), var);
                }
            }
        }
        return conf;
    }
}
