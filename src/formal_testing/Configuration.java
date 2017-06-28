package formal_testing;

import formal_testing.variable.Variable;

import java.util.ArrayList;
import java.util.List;

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
        final StringBuilder sb = new StringBuilder();
        sb.append("input: " + inputVars + "\n");
        sb.append("output: " + outputVars + "\n");
        sb.append("nondet: " + nondetVars + "\n");
        sb.append("plant_internal: " + plantInternalVars + "\n");
        sb.append("controller_internal: " + controllerInternalVars);
        return sb.toString();
    }
}
