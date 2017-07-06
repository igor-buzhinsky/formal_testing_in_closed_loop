package formal_testing.enums;

/**
 * Created by buzhinsky on 7/2/17.
 */
public enum Language {
    PROMELA("//", " && ", " == "), NUSMV("--", " & ", " = "), NUXMV("--", " & ", " = ");

    public final String commentSymbol;
    public final String and;
    public final String equals;

    Language(String commentSymbol, String and, String equals) {
        this.commentSymbol = commentSymbol;
        this.and = and;
        this.equals = equals;
    }
}
