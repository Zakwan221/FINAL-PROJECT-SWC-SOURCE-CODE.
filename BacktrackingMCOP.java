
public class BacktrackingMCOP {

    // Stores the best complete route found so far across all branches
    private static int    bestCost;
    private static String bestPath;


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

                path.setLength(savedLength);
                visited[next] = false;
            }
        }

        return minFound;
    }
}
