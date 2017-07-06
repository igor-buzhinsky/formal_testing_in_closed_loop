package formal_testing;

import formal_testing.enums.Language;
import formal_testing.enums.NuSMVMode;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class Util {
    public static String indent(String str) {
        return String.join("\n", Arrays.stream(str.split("\n")).map(s -> "    " + s).collect(Collectors.toList()));
    }

    public static Language LANGUAGE;
    public static NuSMVMode NUSMV_MODE;
}
