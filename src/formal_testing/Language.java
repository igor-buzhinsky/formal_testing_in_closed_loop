package formal_testing;

/**
 * Created by buzhinsky on 7/2/17.
 */
public enum Language {
    PROMELA("//", " && ", " == "), NUSMV("--", " & ", " = ");

    public String commentSymbol;
    public String and;
    public String equals;

    Language(String commentSymbol, String and, String equals) {
        this.commentSymbol = commentSymbol;
        this.and = and;
        this.equals = equals;
    }
}
