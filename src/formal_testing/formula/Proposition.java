package formal_testing.formula;

import formal_testing.value.Value;
import formal_testing.variable.Variable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by buzhinsky on 4/18/17.
 */
public class Proposition extends LTLFormula {
    private final String varName;
    private final String operator;
    private final String constant;

    private static String boolToInt(String s) {
        return s.equals("TRUE") ? "1" : s.equals("FALSE") ? "0" : s;
    }

    public Proposition(String varName, String operator, String constant) {
        this.varName = varName;
        this.operator = operator;
        this.constant = constant;
    }

    private boolean calculate(String value) {
        final String c = boolToInt(constant);
        final String v = boolToInt(value);
        switch (operator) {
            case "=": return c.equals(v);
            case "!=": return !c.equals(v);
            default:
                final int intValue = Integer.parseInt(v);
                final int intConstant = Integer.parseInt(c);
                switch (operator) {
                    case "<": return intValue < intConstant;
                    case "<=": return intValue <= intConstant;
                    case ">": return intValue > intConstant;
                    case ">=": return intValue >= intConstant;
                    default: throw new RuntimeException("Unknown comparison operator " + operator + "!");
                }
        }
    }

    private String simpleToString() {
        return varName + " " + operator + " " + constant;
    }

    @Override
    public String toString() {
        if (constant.equals("TRUE") && operator.equals("=") || constant.equals("FALSE") && operator.equals("!=") ) {
            return varName;
        }
        if (constant.equals("TRUE") && operator.equals("!=") || constant.equals("FALSE") && operator.equals("=") ) {
            return "!" + varName;
        }
        return simpleToString();
    }

    @Override
    boolean isBoolean() {
        return true;
    }

    @Override
    public void allBooleanSubformulas(Collection<LTLFormula> subformulas) {
        subformulas.add(this);
    }

    @Override
    public boolean booleanValue(Map<String, List<Value>> values, int index) {
        return calculate(values.get(varName).get(index).toString());
    }
}
