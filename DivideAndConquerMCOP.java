
public class DivideAndConquerMCOP {

    // Stores the globally best route found across all recursive branches
    private static int    bestCost;
    private static String bestPath;

  
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

  
    private static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) return false; // at least one city still unvisited
        }
        return true;
    }
}
