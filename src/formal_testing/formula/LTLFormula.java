package formal_testing.formula;

import formal_testing.value.Value;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by buzhinsky on 4/18/17.
 */
public abstract class LTLFormula {
    static String par(String s) {
        return "(" + s + ")";
    }

    abstract boolean isBoolean();

    public abstract void allBooleanSubformulas(Collection<LTLFormula> subformulas);

    public abstract boolean booleanValue(Map<String, List<Value>> values, int index);

    @Override
    public boolean equals(Object other) {
        return other instanceof LTLFormula && toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public static LTLFormula TRUE = new LTLFormula() {
        @Override
        public void allBooleanSubformulas(Collection<LTLFormula> subformulas) {
        }

        @Override
        public boolean booleanValue(Map<String, List<Value>> values, int index) {
            return true;
        }

        @Override
        boolean isBoolean() {
            return true;
        }

        @Override
        public String toString() {
            return "TRUE";
        }
    };

    public static LTLFormula FALSE = new LTLFormula() {
        @Override
        public void allBooleanSubformulas(Collection<LTLFormula> subformulas) {
        }

        @Override
        public boolean booleanValue(Map<String, List<Value>> values, int index) {
            return false;
        }

        @Override
        boolean isBoolean() {
            return true;
        }

        @Override
        public String toString() {
            return "FALSE";
        }
    };
}
