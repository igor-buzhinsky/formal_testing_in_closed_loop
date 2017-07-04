package formal_testing.value;

import formal_testing.Language;
import formal_testing.Util;

/**
 * Created by buzhinsky on 7/3/17.
 */
public abstract class Value {
    public abstract String toPromelaString();
    public abstract String toNuSMVString();

    @Override
    public String toString() {
        return Util.LANGUAGE == Language.PROMELA ? toPromelaString() : toNuSMVString();
    }
}
