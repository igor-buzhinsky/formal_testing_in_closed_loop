package formal_testing;

/**
 * Created by buzhinsky on 6/27/17.
 */
public class ProblemData {
    public final Configuration conf;
    public final String header;
    public final String plantModel;
    public final String controllerModel;
    public final String spec;

    public ProblemData(Configuration conf, String header, String plantModel, String controllerModel, String spec) {
        this.conf = conf;
        this.header = header;
        this.plantModel = plantModel;
        this.controllerModel = controllerModel;
        this.spec = spec;
    }
}
