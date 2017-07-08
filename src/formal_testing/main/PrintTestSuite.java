package formal_testing.main;

import formal_testing.Settings;
import formal_testing.TestSuite;
import formal_testing.enums.Language;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class PrintTestSuite extends MainArgs {
    @Option(name = "--input", usage = "input filename", metaVar = "<filename>", required = true)
    private String filename;

    @Option(name = "--outputHeader", usage = "output test header file", metaVar = "<filename>")
    private String outputHeader;

    @Option(name = "--outputBody", usage = "output test body file", metaVar = "<filename>")
    private String outputBody;

    public static void main(String[] args) throws IOException {
        new PrintTestSuite().run(args);
    }

    private static String extension() {
        return Settings.LANGUAGE == Language.PROMELA ? "pml" : "smv";
    }

    @Override
    protected void launcher() throws IOException {
        final TestSuite ts = TestSuite.read(filename);
        if (outputHeader == null) {
            outputHeader = "test.header." + extension();
        }
        if (outputBody == null) {
            outputBody = "test.body." + extension();
        }
        try (PrintWriter pw = new PrintWriter(outputHeader)) {
            pw.println(ts.header());
        }
        try (PrintWriter pw = new PrintWriter(outputBody)) {
            pw.println(ts.body(data.conf));
        }
    }
}
