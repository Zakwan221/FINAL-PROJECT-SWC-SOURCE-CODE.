/**
 * ============================================================
 *  GreedyMCOP.java
 *  Algorithm : Greedy (Nearest-Neighbour Heuristic)
 *
 *  CONCEPT:
 *    A greedy algorithm makes the LOCALLY OPTIMAL choice at
 *    every step, hoping it leads to a globally optimal result.
 *    For MCOP, "locally optimal" means always travelling to the
 *    NEAREST unvisited city next.
 *
 *  HOW IT WORKS (step by step):
 *    1. Start at UPTM (city 0).
 *    2. Look at all unvisited cities — pick the one with the
 *       lowest travel cost from the current position.
 *    3. Move to that city and mark it visited.
 *    4. Repeat steps 2–3 until all cities are visited.
 *    5. Return home to UPTM.
 *
 *  ADVANTAGE:
 *    Very fast — O(n²) time. Easy to understand and implement.
 *
 *  LIMITATION:
 *    Does NOT guarantee the globally shortest route.
 *    An early greedy choice can block a cheaper overall path.
 *
 *  For this dataset the greedy and optimal routes happen to
 *  match (both = 88), but this is not always the case.
 * ============================================================
 */
public class GreedyMCOP {

    /**
     * Solves the MCOP using the greedy nearest-neighbour heuristic.
     *
     * @param dist      cost matrix (from MCOPData.costMatrix)
     * @param locations city names  (from MCOPData.locations)
     * @return          result string with the route and total cost
     */
    public static String greedyMCOP(int[][] dist, String[] locations) {

        int n         = dist.length;
        int INF       = MCOPData.INF;
        boolean[] visited = new boolean[n]; // false = city not yet visited

        StringBuilder route     = new StringBuilder();
        int           totalCost = 0;
        int           current   = 0; // start at UPTM (index 0)

        // Mark UPTM as the starting point
        visited[current] = true;
        route.append(locations[current]);

        // Repeat n-1 times — one new city is chosen each iteration
        for (int step = 0; step < n - 1; step++) {

            int nearest  = -1;  // index of the closest unvisited city
            int minCost  = INF; // cost to reach that city

            // ── GREEDY CHOICE: scan all cities, pick the nearest ──
            for (int next = 0; next < n; next++) {
                if (!visited[next] && dist[current][next] < minCost) {
                    minCost = dist[current][next];
                    nearest = next;
                }
            }

            // Move to the nearest unvisited city
            visited[nearest] = true;
            totalCost       += minCost;
            route.append(" -> ").append(locations[nearest]);
            current = nearest;
        }

        // All cities visited — add the return leg back to UPTM
        totalCost += dist[current][0];
        route.append(" -> ").append(locations[0]);

        return "Greedy Route: " + route + " | Total Cost: " + totalCost;
    }
}
