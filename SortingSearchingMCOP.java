import java.util.Arrays;

public class SortingSearchingMCOP {

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
