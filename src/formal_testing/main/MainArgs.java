package formal_testing.main;

/**
 * Created by buzhinsky on 7/7/17.
 */

import org.kohsuke.args4j.Argument;

abstract class MainArgs extends MainBase{
    @Argument(usage = "configuration filename", metaVar = "<filename>", required = true, index = 0)
    String configurationFilename;

    @Argument(usage = "header filename", metaVar = "<filename>", required = true, index = 1)
    String headerFilename;

    @Argument(usage = "plant model filename", metaVar = "<filename>", required = true, index = 2)
    String plantModelFilename;

    @Argument(usage = "controller model filename", metaVar = "<filename>", required = true, index = 3)
    String controllerModelFilename;

    @Argument(usage = "specification filename", metaVar = "<filename>", required = true, index = 4)
    String specFilename;
}
