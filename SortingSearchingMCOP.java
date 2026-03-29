import java.util.Arrays;

/**
 * ============================================================
 *  SortingSearchingMCOP.java
 *  Algorithms : Insertion Sort  +  Binary Search
 *
 *  PURPOSE IN MCOP CONTEXT:
 *    Sorting and searching algorithms are essential for managing
 *    student leads databases and optimizing campaign scheduling.
 *    For example:
 *      - Sort city travel costs to prioritize cheaper routes.
 *      - Sort student lead scores to identify top prospects fast.
 *      - Binary search a sorted schedule to locate a city entry.
 *
 * ──────────────────────────────────────────────────────────────
 *  INSERTION SORT
 *
 *  CONCEPT:
 *    Works like sorting playing cards in your hand.
 *    The array is divided into a sorted left portion and an
 *    unsorted right portion. Each pass takes the first unsorted
 *    element (the "key") and inserts it into the correct
 *    position among the already-sorted elements by shifting
 *    larger elements one step to the right.
 *
 *  Time complexity:
 *    Best case  : O(n)   — already sorted
 *    Worst case : O(n²)  — reverse sorted
 *  Space complexity: O(1) — sorts in place, no extra array needed
 *
 * ──────────────────────────────────────────────────────────────
 *  BINARY SEARCH
 *
 *  CONCEPT:
 *    Efficiently finds a target value in a SORTED array.
 *    Each step compares the target with the MIDDLE element:
 *      - If equal   → found, return index.
 *      - If smaller → discard the right half, search left half.
 *      - If larger  → discard the left half, search right half.
 *    The search range halves every step — very fast.
 *
 *  IMPORTANT: the array MUST be sorted before calling this method.
 *
 *  Time complexity : O(log n)
 *  Space complexity: O(1)
 * ============================================================
 */
public class SortingSearchingMCOP {

    /**
     * INSERTION SORT — sorts the array in ascending order in place.
     *
     * Walkthrough on {8, 3, 5, 1, 9, 2}:
     *   Pass 1: key=3 → shift 8 right → [3, 8, 5, 1, 9, 2]
     *   Pass 2: key=5 → shift 8 right → [3, 5, 8, 1, 9, 2]
     *   Pass 3: key=1 → shift 8,5,3   → [1, 3, 5, 8, 9, 2]
     *   Pass 4: key=9 → already OK    → [1, 3, 5, 8, 9, 2]
     *   Pass 5: key=2 → shift 9,8,5,3 → [1, 2, 3, 5, 8, 9]
     *
     * @param arr the integer array to sort (modified in place)
     * @return    the sorted array as a readable string
     */
    public static String insertionSort(int[] arr) {

        int n = arr.length;

        // Outer loop: grow the sorted portion one element at a time
        for (int i = 1; i < n; i++) {

            int key = arr[i]; // the element to be placed correctly
            int j   = i - 1; // start comparing with the element before key

            // Shift all sorted elements that are GREATER than key
            // one position to the right, making room for key
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; // shift right
                j--;
            }

            // Place key in the gap left by shifting
            arr[j + 1] = key;
        }

        return Arrays.toString(arr); // return result as readable string
    }

    /**
     * BINARY SEARCH — finds the index of target in a sorted array.
     *
     * Walkthrough searching for 5 in [1, 2, 3, 5, 8, 9]:
     *   Step 1: left=0, right=5, mid=2 → arr[2]=3 < 5 → left=3
     *   Step 2: left=3, right=5, mid=4 → arr[4]=8 > 5 → right=3
     *   Step 3: left=3, right=3, mid=3 → arr[3]=5 == 5 → return 3
     *
     * @param arr    sorted integer array to search in
     * @param target value to search for
     * @return       index of target if found, -1 if not found
     */
    public static int binarySearch(int[] arr, int target) {

        int left  = 0;
        int right = arr.length - 1;

        while (left <= right) {

            // Calculate mid without risking integer overflow
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;           // target found — return its index

            } else if (arr[mid] < target) {
                left = mid + 1;       // target is in the RIGHT half

            } else {
                right = mid - 1;      // target is in the LEFT half
            }
        }

        return -1; // target not found in the array
    }
}
