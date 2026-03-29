/**
 * ============================================================
 *  MCOPData.java
 *  Shared data used by ALL algorithm classes.
 *
 *  Purpose:
 *    Define the cost matrix and location names in ONE place so
 *    every class reads from the same source — no duplication,
 *    no risk of mismatched data between team members' files.
 *
 *  Cost Matrix (Table 1 from project brief):
 *             UPTM   City B   City C   City D
 *    UPTM  [   0,     15,      25,      35  ]
 *    City B [  15,     0,      30,      28  ]
 *    City C [  25,    30,       0,      20  ]
 *    City D [  35,    28,      20,       0  ]
 *
 *  Index mapping:
 *    0 = UPTM Headquarters
 *    1 = City B Education Fair
 *    2 = City C Recruitment Hub
 *    3 = City D Partner Institution
 * ============================================================
 */
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
