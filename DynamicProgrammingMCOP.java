import java.util.Arrays;

public class DynamicProgrammingMCOP {

    public static String dynamicProgrammingMCOP(int[][] dist, String[] locations) {

        int n           = dist.length;
        int VISITED_ALL = (1 << n) - 1; // e.g. 1111 = 15 for 4 cities

        int[][]    memo  = new int[n][1 << n];
        String[][] paths = new String[n][1 << n];
        for (int[] row : memo) Arrays.fill(row, -1);

        // Start at UPTM (city 0), only UPTM visited → mask = 0001 = 1
        int startMask = 1;
        int minCost   = dynamicProgrammingMCOPHelper(
                            0, startMask, dist, memo, VISITED_ALL, paths, locations);

        // Append the final return leg to UPTM
        String bestRoute = paths[0][startMask] + " -> " + locations[0];

        return "Dynamic Programming Route: " + bestRoute
               + " | Total Cost: " + minCost;
    }

    private static int dynamicProgrammingMCOPHelper(
            int pos, int mask, int[][] dist,
            int[][] memo, int VISITED_ALL,
            String[][] paths, String[] locations) {

        int n   = dist.length;
        int INF = MCOPData.INF;

        if (mask == VISITED_ALL) {
            memo [pos][mask] = dist[pos][0];   // only option: return home
            paths[pos][mask] = locations[pos]; // record last city
            return dist[pos][0];
        }

        // ── MEMO HIT: sub-problem already solved ──────────────────
        // Like reading a pre-filled knapsack cell — return instantly.
        if (memo[pos][mask] != -1) {
            return memo[pos][mask];
        }

        // ── RECURSIVE CASE: try every unvisited city next ─────────
        int    bestCost = INF;
        String bestPath = "";

        for (int next = 0; next < n; next++) {

            // Skip cities already in this mask
            if ((mask & (1 << next)) != 0) continue;

            // newMask: mark 'next' as visited
            // (equivalent to w - weight[i] in knapsack)
            int newMask = mask | (1 << next);

            // ── THE DP FORMULA ─────────────────────────────────────
            // Knapsack: V[i-1][w - weight[i]]       +  value[i]
            // MCOP:     dp[next][newMask]            +  dist[pos][next]
            int subCost = dynamicProgrammingMCOPHelper(
                              next, newMask, dist, memo,
                              VISITED_ALL, paths, locations);
            int cost = dist[pos][next] + subCost;

            // min() here ↔ max() in knapsack — pick the best option
            if (cost < bestCost) {
                bestCost = cost;
                bestPath = locations[pos] + " -> " + paths[next][newMask];
            }
        }

        // ── SAVE RESULT: fill the memo cell ───────────────────────
        memo [pos][mask] = bestCost;
        paths[pos][mask] = bestPath;

        return bestCost;
    }
}
