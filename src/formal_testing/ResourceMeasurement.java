package formal_testing;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by buzhinsky on 7/1/17.
 */
public class ResourceMeasurement {
    private final double userTime;
    private final double systemTime;
    private final double elapsedTime;
    private final int maxResident;
    private final Set<String> comments = new LinkedHashSet<>();

    public ResourceMeasurement() {
        this(0, 0, 0, 0);
    }

    public ResourceMeasurement(double userTime, double systemTime, double elapsedTime, int maxResident,
                                String... comments) {
        this.userTime = userTime;
        this.systemTime = systemTime;
        this.elapsedTime = elapsedTime;
        this.maxResident = maxResident;
        this.comments.addAll(Arrays.asList(comments));
    }

    /*
     * according to FORMAT
      */
    public ResourceMeasurement(String measurement, String... comments) {
        final String[] tokens = measurement.split(" ");
        userTime = Double.parseDouble(tokens[2]);
        systemTime = Double.parseDouble(tokens[3]);
        elapsedTime = Double.parseDouble(tokens[4]);
        maxResident = Integer.parseInt(tokens[5]);
        this.comments.addAll(Arrays.asList(comments));
    }

    public static boolean isMeasurement(String measurement) {
        if (!measurement.startsWith("*** RESOURCES ") || !measurement.endsWith(" ***")) {
            return false;
        }
        try {
            new ResourceMeasurement(measurement);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public ResourceMeasurement add(ResourceMeasurement other) {
        final ResourceMeasurement result = new ResourceMeasurement(userTime + other.userTime,
                systemTime + other.systemTime, elapsedTime + other.elapsedTime,
                Math.max(maxResident, other.maxResident));
        result.comments.addAll(comments);
        result.comments.addAll(other.comments);
        return result;
    }

    public static final String FORMAT = "*** RESOURCES %U %S %e %M ***\\n";

    @Override
    public String toString() {
        return String.format("Measurement [user=%.2f, system=%.2f, elapsed=%.2f, maxresident=%dk] - ",
                userTime, systemTime, elapsedTime, maxResident) + String.join(", ", comments);
    }
}
