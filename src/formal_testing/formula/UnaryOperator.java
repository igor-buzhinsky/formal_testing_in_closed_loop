package formal_testing.formula;

import formal_testing.value.Value;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by buzhinsky on 4/18/17.
 */
public class UnaryOperator extends LTLFormula {
    public final String name;
    public final LTLFormula argument;

    private final boolean isBoolean;

    public UnaryOperator(String name, LTLFormula argument) {
        this.name = name;
        this.argument = argument;
        isBoolean = name.equals("!") && argument.isBoolean();
    }

    @Override
    public String toString() {
        return name + par(argument.toString());
    }

    @Override
    boolean isBoolean() {
        return isBoolean;
    }

    @Override
    public void allBooleanSubformulas(Collection<LTLFormula> subformulas) {
        argument.allBooleanSubformulas(subformulas);
        if (isBoolean && argument.isBoolean()) {
            subformulas.add(this);
        }
    }

    @Override
    public boolean booleanValue(Map<String, List<Value>> values, int index) throws InconclusiveException {
        if (!isBoolean) {
            throw new RuntimeException();
        }
        return !argument.booleanValue(values, index);
    }
}
