package formal_testing.main;

import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.value.Value;
import formal_testing.variable.Variable;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public static void main(String[] args) throws IOException {
        new GenerateRandom().run(args);
    }

    @Override
    protected void launcher() throws IOException {
        final Random random = seed == null ? new Random() : new Random(seed);
        loadData(configurationFilename, headerFilename, plantModelFilename, controllerModelFilename, specFilename);
        final TestSuite ts = new TestSuite(true);
        final List<List<? extends Value>> allValues = data.conf.nondetVars.stream()
                .map(Variable::values).collect(Collectors.toList());
        for (int i = 0; i < number; i++) {
            final TestCase tc = new TestCase(data.conf, false);
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < data.conf.nondetVars.size(); k++) {
                    final Variable<?> var = data.conf.nondetVars.get(k);
                    tc.addValue(var.indexedName(), allValues.get(k).get(random.nextInt(allValues.get(k).size())));
                }
            }
            ts.add(tc);
        }
        ts.print(outputFilename);
        System.out.println("Generated:");
        System.out.println(ts);
    }
}
