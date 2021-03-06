package formal_testing.value;

import formal_testing.Settings;
import formal_testing.enums.Language;

import java.io.Serializable;

/**
 * Created by buzhinsky on 7/3/17.
 */
public abstract class Value implements Serializable {
    public abstract String toPromelaString();
    public abstract String toNuSMVString();

    @Override
    public String toString() {
        return Settings.LANGUAGE == Language.PROMELA ? toPromelaString() : toNuSMVString();
    }
}
