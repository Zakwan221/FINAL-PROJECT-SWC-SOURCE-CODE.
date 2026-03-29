import java.util.Arrays;

/**
 * ============================================================
 *  DynamicProgrammingMCOP.java
 *  Algorithm : Dynamic Programming (Top-Down Memoization)
 *
 *  CONCEPT:
 *    Dynamic Programming solves a complex problem by breaking
 *    it into overlapping sub-problems, solving each ONCE, and
 *    storing the result in a table (memoization) so it is never
 *    recomputed.
 *
 *  KNAPSACK FORMULA (from lecture slide):
 *    V[i, w] = max{ V[i-1, w],  V[i-1, w - weight[i]] + value[i] }
 *
 *  MCOP FORMULA (same structure, different problem):
 *    dp[pos][mask] = min{ dist[pos][next]
 *                       + dp[next][mask | (1<<next)] }
 *                   for every unvisited city 'next'
 *
 *  KNAPSACK vs MCOP PARALLEL:
 *  ┌──────────────────────────┬──────────────────────────────┐
 *  │ KNAPSACK                 │ MCOP                         │
 *  ├──────────────────────────┼──────────────────────────────┤
 *  │ V[i][w]                  │ memo[pos][mask]              │
 *  │ i = which item           │ pos = current city           │
 *  │ w = bag capacity         │ mask = cities visited so far │
 *  │ V[i-1][w-weight[i]]      │ dp[next][mask|(1<<next)]     │
 *  │ + value[i]               │ + dist[pos][next]            │
 *  │ max(options)             │ min(options)                 │
 *  │ Base: no items → 0       │ Base: all visited → go home  │
 *  └──────────────────────────┴──────────────────────────────┘
 *
 *  ADVANTAGE:
 *    Guarantees the optimal route. Much faster than brute-force
 *    because each (pos, mask) state is solved only once.
 *    Time complexity: O(n² × 2ⁿ)
 *
 *  LIMITATION:
 *    Uses O(n × 2ⁿ) memory for the memo table. Impractical for
 *    very large n due to exponential memory and time growth.
 * ============================================================
 */
public class DynamicProgrammingMCOP {

    /**
     * Public entry point — sets up memo table then calls the helper.
     *
     * @param dist      cost matrix (from MCOPData.costMatrix)
     * @param locations city names  (from MCOPData.locations)
     * @return          result string with the optimal route and cost
     */
    public static String dynamicProgrammingMCOP(int[][] dist, String[] locations) {

        int n           = dist.length;
        int VISITED_ALL = (1 << n) - 1; // e.g. 1111 = 15 for 4 cities

        // ── MEMO TABLE ────────────────────────────────────────────
        // memo[pos][mask] = min cost to finish the tour when:
        //   - currently AT city 'pos'
        //   - 'mask' encodes which cities have been visited so far
        // -1 means "not yet computed" (like an unfilled knapsack cell)
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

    /**
     * Private recursive helper — computes and memoises dp[pos][mask].
     *
     * Knapsack parallel:
     *   memo[pos][mask]    ≡  V[i][w]           (cell being filled)
     *   mask == VISITED_ALL ≡ base case          (nothing left to decide)
     *   memo[pos][mask]!=-1 ≡ cell already filled (skip recomputation)
     *   dist[pos][next]    ≡  value[i]           (cost added this step)
     *   dp[next][newMask]  ≡  V[i-1][w-weight]  (previously solved sub-problem)
     *   min(all options)   ≡  max(take/skip)     (pick best option)
     *
     * @param pos         city we are currently at
     * @param mask        bitmask of cities visited so far
     * @param dist        cost matrix
     * @param memo        memoisation table  (-1 = not yet computed)
     * @param VISITED_ALL bitmask with every city marked
     * @param paths       stores route string for each (pos, mask) state
     * @param locations   city name array for building path strings
     * @return            minimum cost to complete the tour from this state
     */
    private static int dynamicProgrammingMCOPHelper(
            int pos, int mask, int[][] dist,
            int[][] memo, int VISITED_ALL,
            String[][] paths, String[] locations) {

        int n   = dist.length;
        int INF = MCOPData.INF;

        // ── BASE CASE: all cities visited ─────────────────────────
        // Equivalent to knapsack base V[0][w] = 0.
        // Nothing left to decide — just go straight back to UPTM.
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
