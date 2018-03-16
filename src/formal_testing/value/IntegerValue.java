package formal_testing.value;

/**
 * Created by buzhinsky on 7/3/17.
 */
public class IntegerValue extends Value {
    public final int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    public static IntegerValue read(String s) {
        // replacements of parentheses is needed to handle SPIN trail assignments like VAR = -(2)
        return new IntegerValue(Integer.parseInt(s.replace("(", "").replace(")", "")));
    }

    @Override
    public String toPromelaString() {
        return String.valueOf(value);
    }

    @Override
    public String toNuSMVString() {
        return String.valueOf(value);
    }
}
