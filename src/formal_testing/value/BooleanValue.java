package formal_testing.value;

/**
 * Created by buzhinsky on 7/3/17.
 */
public final class BooleanValue extends Value {
    public final boolean value;
    public final static BooleanValue TRUE = new BooleanValue(true);
    public final static BooleanValue FALSE = new BooleanValue(false);

    private BooleanValue(boolean value) {
        this.value = value;
    }

    public static BooleanValue read(String s) {
        if (s.equals("0") || s.toLowerCase().equals("false")) {
            return FALSE;
        } else if (s.equals("1") || s.toLowerCase().equals("true")) {
            return TRUE;
        }
        throw new RuntimeException("Invalid boolean value " + s);
    }

    @Override
    public String toPromelaString() {
        return value ? "1" : "0";
    }

    @Override
    public String toNuSMVString() {
        return value ? "TRUE" : "FALSE";
    }
}
