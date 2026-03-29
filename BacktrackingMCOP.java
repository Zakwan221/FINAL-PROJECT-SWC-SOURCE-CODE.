/**
 * ============================================================
 *  BacktrackingMCOP.java
 *  Algorithm : Backtracking
 *
 *  CONCEPT:
 *    Backtracking is a systematic brute-force technique that
 *    explores ALL possible routes using recursion. When it
 *    reaches a dead end or finishes a route, it BACKTRACKS
 *    (undoes the last choice) and tries the next option.
 *    This guarantees finding the globally optimal route.
 *
 *  PATTERN — CHOOSE → EXPLORE → UN-CHOOSE:
 *    CHOOSE   : mark the next city as visited, add to path
 *    EXPLORE  : recurse to visit remaining cities
 *    UN-CHOOSE: un-visit the city, remove from path
 *               so other branches can use that slot
 *
 *  HOW IT DIFFERS FROM GREEDY:
 *    Greedy commits to the nearest city and never looks back.
 *    Backtracking tries EVERY city at each step and keeps only
 *    the route with the lowest total cost.
 *
 *  HOW IT DIFFERS FROM DYNAMIC PROGRAMMING:
 *    DP stores sub-problem results to avoid recomputation.
 *    Backtracking recomputes from scratch for every branch —
 *    no memoization — but is simpler to implement.
 *
 *  ADVANTAGE:
 *    Always finds the optimal answer.
 *    Simple recursive logic — easy to trace and explain.
 *
 *  LIMITATION:
 *    Time complexity: O(n!) — very slow for large n.
 *    No pruning is applied here; adding a bound check
 *    (branch & bound) would speed it up significantly.
 * ============================================================
 */
public class BacktrackingMCOP {

    // Stores the best complete route found so far across all branches
    private static int    bestCost;
    private static String bestPath;

    /**
     * Public entry point for the backtracking algorithm.
     *
     * @param dist      cost matrix (from MCOPData.costMatrix)
     * @param locations city names  (from MCOPData.locations)
     * @return          result string with the optimal route and cost
     */
    public static String backtrackingMCOP(int[][] dist, String[] locations) {

        int n = dist.length;
        boolean[] visited = new boolean[n];

        // Reset globals before each run (important for repeated calls)
        bestCost = MCOPData.INF;
        bestPath = "";

        // Mark UPTM as the starting city
        visited[0] = true;
        StringBuilder path = new StringBuilder(locations[0]);

        // Launch the recursive search from UPTM
        mcopBacktracking(0, dist, visited, n, 1, 0, path, locations);

        return "Backtracking Route: " + bestPath
               + " | Total Cost: " + bestCost;
    }

    /**
     * Recursive backtracking helper.
     *
     * At each call: CHOOSE a next city → EXPLORE from it → UN-CHOOSE it.
     *
     * @param pos       city we are currently at
     * @param dist      cost matrix
     * @param visited   which cities have been visited
     * @param n         total number of cities
     * @param count     how many cities visited so far (including start)
     * @param cost      accumulated travel cost so far
     * @param path      current route string (modified & restored in place)
     * @param locations city name array for building path strings
     * @return          minimum cost found from this recursive state
     */
    private static int mcopBacktracking(
            int pos, int[][] dist, boolean[] visited,
            int n, int count, int cost,
            StringBuilder path, String[] locations) {

        // ── BASE CASE: all cities visited ─────────────────────────
        // Add the return leg cost and check if this is the best tour.
        if (count == n) {
            int totalCost = cost + dist[pos][0];

            if (totalCost < bestCost) {
                bestCost = totalCost;
                bestPath = path + " -> " + locations[0];
            }
            return totalCost;
        }

        int minFound = MCOPData.INF;

        // Try every unvisited city as the next destination
        for (int next = 0; next < n; next++) {
            if (!visited[next]) {

                // ── CHOOSE ────────────────────────────────────────
                visited[next]  = true;
                int savedLength = path.length();
                path.append(" -> ").append(locations[next]);

                // ── EXPLORE ───────────────────────────────────────
                int result = mcopBacktracking(
                        next, dist, visited, n,
                        count + 1,
                        cost + dist[pos][next],
                        path, locations);

                minFound = Math.min(minFound, result);

                // ── UN-CHOOSE (backtrack) ─────────────────────────
                // Restore path string and visited flag so the next
                // iteration of the loop can try a different city here.
                path.setLength(savedLength);
                visited[next] = false;
            }
        }

        return minFound;
    }
}
