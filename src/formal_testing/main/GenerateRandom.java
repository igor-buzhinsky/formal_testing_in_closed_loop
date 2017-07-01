package formal_testing.main;

import formal_testing.ProblemData;
import formal_testing.TestCase;
import formal_testing.TestSuite;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandom extends Command {
    private final int length;
    private final int number;
    private final String outputFilename;
    private final Random random;

    public GenerateRandom(ProblemData data, int length, int number, String outputFilename, Random random) {
        super(data);
        this.length = length;
        this.number = number;
        this.outputFilename = outputFilename;
        this.random = random;
    }

    @Override
    public void execute() throws IOException {
        final TestSuite ts = new TestSuite();
        final List<List<String>> allValues = data.conf.nondetVars.stream()
                .map(Variable::promelaValues).collect(Collectors.toList());
        for (int i = 0; i < number; i++) {
            final TestCase tc = new TestCase(data.conf);
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < data.conf.nondetVars.size(); k++) {
                    final Variable var = data.conf.nondetVars.get(k);
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
