package formal_testing.formula;

import formal_testing.value.Value;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by buzhinsky on 4/18/17.
 */
public class BinaryOperator extends LTLFormula {
    public final String name;
    private final LTLFormula leftArgument;
    private final LTLFormula rightArgument;

    private final boolean isBoolean;

    public BinaryOperator(String name, LTLFormula leftArgument, LTLFormula rightArgument) {
        this.name = name;
        this.leftArgument = leftArgument;
        this.rightArgument = rightArgument;

        isBoolean = Arrays.asList("&", "|", "->", "<->").contains(name)
                && leftArgument.isBoolean() && rightArgument.isBoolean();
    }

    @Override
    public String toString() {
        return par(leftArgument + " " + name + " " + rightArgument);
    }

    @Override
    boolean isBoolean() {
        return isBoolean;
    }

    @Override
    public void allBooleanSubformulas(Collection<LTLFormula> subformulas) {
        leftArgument.allBooleanSubformulas(subformulas);
        rightArgument.allBooleanSubformulas(subformulas);
        if (isBoolean && leftArgument.isBoolean() && rightArgument.isBoolean()) {
            subformulas.add(this);
            // remove chained subformulas
            if (leftArgument instanceof BinaryOperator && (
                    (name.equals("&") && ((BinaryOperator) leftArgument).name.equals("&")) ||
                            (name.equals("|") && ((BinaryOperator) leftArgument).name.equals("|")))) {
                subformulas.remove(leftArgument);
            }
        }
    }

    @Override
    public boolean booleanValue(Map<String, List<Value>> values, int index) {
        if (!isBoolean) {
            throw new AssertionError();
        }
        switch (name) {
            case "&": return leftArgument.booleanValue(values, index) && rightArgument.booleanValue(values, index);
            case "|": return leftArgument.booleanValue(values, index) || rightArgument.booleanValue(values, index);
            case "->": return !leftArgument.booleanValue(values, index) || rightArgument.booleanValue(values, index);
            case "<->": return leftArgument.booleanValue(values, index) == rightArgument.booleanValue(values, index);
            default: throw new AssertionError();
        }
    }
}
