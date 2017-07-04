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
        return new IntegerValue(Integer.parseInt(s));
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
