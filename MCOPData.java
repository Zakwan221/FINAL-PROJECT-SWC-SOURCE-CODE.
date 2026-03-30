
public class MCOPData {

    // Travel cost between every pair of locations
    public static final int[][] costMatrix = {
        {0,  15, 25, 35},
        {15,  0, 30, 28},
        {25, 30,  0, 20},
        {35, 28, 20,  0}
    };

    // City names used when printing routes
    public static final String[] locations = {
        "UPTM", "City B", "City C", "City D"
    };

    // Represents "no path found yet" — avoids integer overflow
    // when adding costs together during comparisons
    public static final int INF = Integer.MAX_VALUE / 2;
}
