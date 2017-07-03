package formal_testing;

/**
 * Created by buzhinsky on 7/2/17.
 */
public enum Language {
    PROMELA("//"), NUSMV("--");

    public String commentSymbol;

    Language(String commentSymbol) {
        this.commentSymbol = commentSymbol;
    }
}
