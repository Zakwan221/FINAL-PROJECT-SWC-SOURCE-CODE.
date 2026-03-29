/**
 * ============================================================
 *  DivideAndConquerMCOP.java
 *  Algorithm : Divide and Conquer
 *
 *  CONCEPT:
 *    Divide and Conquer breaks a large problem into smaller
 *    independent sub-problems, solves each recursively, and
 *    COMBINES the results to get the final answer.
 *
 *  THREE STAGES applied to MCOP:
 *    DIVIDE  — at the current city, treat each unvisited city
 *              as its own independent sub-problem (sub-path).
 *    CONQUER — recursively find the best completion of each
 *              sub-path (visit all remaining cities and return).
 *    COMBINE — select the sub-problem result that gives the
 *              lowest total cost (Math.min across all options).
 *
 *  DIFFERENCE vs BACKTRACKING:
 *    Both explore every possible route.
 *    Backtracking is explicitly framed as CHOOSE/EXPLORE/UN-CHOOSE.
 *    Divide & Conquer is framed as DIVIDE/CONQUER/COMBINE —
 *    same recursion, different conceptual framing.
 *
 *  DIFFERENCE vs DYNAMIC PROGRAMMING:
 *    D&C recomputes overlapping sub-problems from scratch each time.
 *    DP stores those sub-results in a memo table to avoid repetition.
 *    Adding memoization to D&C turns it into DP.
 *
 *  ADVANTAGE:
 *    Always finds the optimal answer.
 *    Clear recursive structure — easy to reason about correctness.
 *
 *  LIMITATION:
 *    Time complexity: O(n!) — same as backtracking, slow for large n.
 *    No memoization — overlapping sub-problems are recomputed.
 * ============================================================
 */
public class DivideAndConquerMCOP {

    // Stores the globally best route found across all recursive branches
    private static int    bestCost;
    private static String bestPath;

    /**
     * Public entry point for the divide-and-conquer algorithm.
     *
     * @param dist      cost matrix (from MCOPData.costMatrix)
     * @param locations city names  (from MCOPData.locations)
     * @return          result string with the optimal route and cost
     */
    public static String divideAndConquerMCOP(int[][] dist, String[] locations) {

        int n = dist.length;
        boolean[] visited = new boolean[n];

        // Reset globals before each run
        bestCost = MCOPData.INF;
        bestPath = "";

        // Start at UPTM (city 0)
        visited[0] = true;
        StringBuilder path = new StringBuilder(locations[0]);

        // Launch the recursive divide-and-conquer from UPTM
        divideAndConquerHelper(0, visited, 0, dist, n, path, locations);

        return "Divide & Conquer Route: " + bestPath
               + " | Total Cost: " + bestCost;
    }

    /**
     * Recursive helper implementing the D&C three stages.
     *
     * DIVIDE  : each unvisited city is an independent sub-problem.
     * CONQUER : recursively solve each sub-problem.
     * COMBINE : keep whichever sub-problem yields the lowest cost.
     *
     * @param pos         city currently at
     * @param visited     which cities have been visited
     * @param currentCost accumulated travel cost so far
     * @param dist        cost matrix
     * @param n           total number of cities
     * @param path        current route string (modified and restored)
     * @param locations   city name array for building path strings
     * @return            minimum cost found from this state onwards
     */
    private static int divideAndConquerHelper(
            int pos, boolean[] visited, int currentCost,
            int[][] dist, int n,
            StringBuilder path, String[] locations) {

        // ── BASE CASE: all cities conquered ───────────────────────
        // COMBINE: check if this complete tour is the best so far.
        if (allVisited(visited)) {
            int totalCost = currentCost + dist[pos][0]; // add return leg

            if (totalCost < bestCost) {
                bestCost = totalCost;
                bestPath = path + " -> " + locations[0];
            }
            return totalCost;
        }

        int minCost = MCOPData.INF;

        // ── DIVIDE: each unvisited city = one sub-problem ─────────
        for (int next = 0; next < n; next++) {
            if (!visited[next]) {

                // ── CONQUER: recursively solve this sub-problem ────
                visited[next]  = true;
                int savedLength = path.length();
                path.append(" -> ").append(locations[next]);

                int result = divideAndConquerHelper(
                        next, visited,
                        currentCost + dist[pos][next],
                        dist, n, path, locations);

                // ── COMBINE: keep the minimum across sub-problems ──
                minCost = Math.min(minCost, result);

                // Restore state for the next sub-problem
                path.setLength(savedLength);
                visited[next] = false;
            }
        }

        return minCost;
    }

    /**
     * Helper — returns true only when every city has been visited.
     * Used by divideAndConquerHelper as the base-case check.
     *
     * @param visited boolean array; true = city already visited
     * @return        true if all entries are true, false otherwise
     */
    private static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) return false; // at least one city still unvisited
        }
        return true;
    }
}
