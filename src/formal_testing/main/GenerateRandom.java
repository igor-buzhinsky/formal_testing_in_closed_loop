package formal_testing.main;

import formal_testing.TestSuite;
import org.kohsuke.args4j.Option;

import java.io.IOException;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandom extends MainArgs {
    @Option(name = "--length", usage = "test case length, default = 10", metaVar = "<length>")
    private int length = 10;

    @Option(name = "--number", usage = "test case number, default = 10", metaVar = "<number>")
    private int number = 10;

    @Option(name = "--seed", usage = "random seed", metaVar = "<seed>")
    private Long seed;

    @Option(name = "--output", usage = "output filename", metaVar = "<filename>")
    private String outputFilename = "test.bin";

    public static void main(String[] args) {
        new GenerateRandom().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);
        final TestSuite ts = new TestSuite(true);
        fillRandom(ts, data, seed, length, number);
        ts.print(outputFilename);
        System.out.println("Generated:");
        System.out.println(ts);
    }
}
