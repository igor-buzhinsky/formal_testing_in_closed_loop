package formal_testing.command;

import formal_testing.ProblemData;
import formal_testing.TestCase;
import formal_testing.variable.Variable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by buzhinsky on 6/28/17.
 */
public class GenerateRandomTest extends Command {
    private final int length;
    private final String filename;
    private final Random random;

    public GenerateRandomTest(ProblemData data, int length, String filename, Random random) {
        super(data);
        this.length = length;
        this.filename = filename;
        this.random = random;
    }

    @Override
    public void execute() throws IOException {
        final TestCase tc = new TestCase(data.conf);
        final List<List<String>> allValues = data.conf.nondetVars.stream()
                .map(Variable::promelaValues).collect(Collectors.toList());
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < data.conf.nondetVars.size(); j++) {
                Variable var = data.conf.nondetVars.get(j);
                tc.addValue(var.indexedName(), allValues.get(j).get(random.nextInt(allValues.get(j).size())));
            }
        }
        try (PrintWriter pw = new PrintWriter(filename + ".header")) {
            pw.print(tc.promelaHeader());
        }
        try (PrintWriter pw = new PrintWriter(filename + ".body")) {
            pw.print(tc.promelaBody(true));
        }
    }
}
