package formal_testing.value;

import java.util.List;

/**
 * Created by buzhinsky on 7/3/17.
 */
public class SetValue extends Value {
    public final String value;

    public SetValue(String value) {
        this.value = value;
    }

    public static SetValue read(String s, List<String> possibleValues) {
        try {
            int value = Integer.parseInt(s);
            return new SetValue(possibleValues.get(value));
        } catch (NumberFormatException e) {
            if (possibleValues.contains(s)) {
                return new SetValue(s);
            } else {
                throw new RuntimeException("Unknown value " + s + ", expected one of " + possibleValues);
            }
        }
    }

    @Override
    public String toPromelaString() {
        return value;
    }

    @Override
    public String toNuSMVString() {
        return value;
    }
}
