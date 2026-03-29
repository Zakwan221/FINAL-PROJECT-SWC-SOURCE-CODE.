import java.util.Arrays;

/**
 * ============================================================
 *  UPTMMarketingOptimization.java
 *  MAIN RUNNER — calls all algorithm classes.
 *
 *  Course  : SWC3524 / SWC4423
 *  Project : Marketing Campaign Optimization Problem (MCOP)
 *
 *  HOW TO RUN IN BLUEJ:
 *    1. Place ALL .java files in the same BlueJ project folder:
 *         MCOPData.java
 *         GreedyMCOP.java
 *         DynamicProgrammingMCOP.java
 *         BacktrackingMCOP.java
 *         DivideAndConquerMCOP.java
 *         SortingSearchingMCOP.java
 *         MinHeap.java
 *         SplayTree.java
 *         UPTMMarketingOptimization.java  ← this file
 *    2. Click Compile (or press Ctrl + K).
 *    3. Right-click UPTMMarketingOptimization → main → OK.
 *    4. View output in the BlueJ terminal window.
 *
 *  EXPECTED OUTPUT:
 *    Greedy Route: UPTM -> City B -> City D -> City C -> UPTM | Total Cost: 88
 *    Dynamic Programming Route: UPTM -> City B -> City D -> City C -> UPTM | Total Cost: 88
 *    Backtracking Route: UPTM -> City B -> City D -> City C -> UPTM | Total Cost: 88
 *    Divide & Conquer Route: UPTM -> City B -> City D -> City C -> UPTM | Total Cost: 88
 *
 *    Sorted Array: [1, 2, 3, 5, 8, 9]
 *    Binary Search (5 found at index): 3
 *
 *    Min-Heap Extract Min: 3
 *    Splay Tree Search (10 found): true
 * ============================================================
 */
public class UPTMMarketingOptimization {

    public static void main(String[] args) {

        // Load shared cost matrix and city names from MCOPData
        int[][]  dist      = MCOPData.costMatrix;
        String[] locations = MCOPData.locations;

        System.out.println("============================================");
        System.out.println(" UPTM Marketing Campaign Optimization (MCOP)");
        System.out.println("============================================\n");

        // ── 1. Greedy Algorithm ───────────────────────────────────
        System.out.println(GreedyMCOP.greedyMCOP(dist, locations));

        // ── 2. Dynamic Programming ────────────────────────────────
        System.out.println(DynamicProgrammingMCOP.dynamicProgrammingMCOP(dist, locations));

        // ── 3. Backtracking ───────────────────────────────────────
        System.out.println(BacktrackingMCOP.backtrackingMCOP(dist, locations));

        // ── 4. Divide and Conquer ─────────────────────────────────
        System.out.println(DivideAndConquerMCOP.divideAndConquerMCOP(dist, locations));

        System.out.println();

        // ── 5. Insertion Sort ─────────────────────────────────────
        int[] arr = {8, 3, 5, 1, 9, 2};
        SortingSearchingMCOP.insertionSort(arr); // sorts in place
        System.out.println("Sorted Array: " + Arrays.toString(arr));

        // ── 6. Binary Search ──────────────────────────────────────
        // NOTE: array must be sorted before calling binary search
        int idx = SortingSearchingMCOP.binarySearch(arr, 5);
        System.out.println("Binary Search (5 found at index): " + idx);

        System.out.println();

        // ── 7. Min-Heap ───────────────────────────────────────────
        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);
        // extractMin() should always return the smallest = 3
        System.out.println("Min-Heap Extract Min: " + heap.extractMin());

        // ── 8. Splay Tree ─────────────────────────────────────────
        SplayTree tree = new SplayTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        // search() splays the found node to the root and returns true
        System.out.println("Splay Tree Search (10 found): " + tree.search(10));

        System.out.println("\n============================================");
        System.out.println(" End of MCOP Results");
        System.out.println("============================================");
    }
}
