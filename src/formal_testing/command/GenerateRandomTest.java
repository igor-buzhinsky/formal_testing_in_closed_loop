package formal_testing.command;

import formal_testing.ProblemData;
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
        super("generate-random-test", data);
        this.length = length;
        this.filename = filename;
        this.random = random;
    }

    @Override
    public void execute() throws IOException {
        // looping scenario

        try (PrintWriter pw = new PrintWriter(filename + ".header")) {
            pw.print("int _test_step;");
        }

        try (PrintWriter pw = new PrintWriter(filename + ".body")) {
            final List<List<String>> allValues = data.conf.nondetVars.stream()
                    .map(Variable::promelaValues).collect(Collectors.toList());
            pw.println("d_step {");
            pw.println("    if");
            for (int i = 0; i < length; i++) {
                pw.print("    :: _test_step == " + i + " -> ");
                for (int j = 0; j < data.conf.nondetVars.size(); j++) {
                    Variable var = data.conf.nondetVars.get(j);
                    pw.print(var.indexedName() + " = " + allValues.get(j).get(random.nextInt(allValues.get(j).size()))
                            + "; ");
                }
                pw.println();
            }
            pw.println("    fi");
            pw.println("    _test_step = (_test_step + 1) % " + length + ";");
            pw.println("    _test_passed = (_test_step == 0 -> true : _test_passed);");
            pw.println("}");
        }
    }
}
