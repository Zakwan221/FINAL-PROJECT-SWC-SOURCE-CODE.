
public class GreedyMCOP {

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
