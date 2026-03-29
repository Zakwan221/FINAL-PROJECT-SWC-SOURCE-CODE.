/**
 * ============================================================
 *  MinHeap.java
 *  Data Structure : Min-Heap  (Individual Task)
 *
 *  CONCEPT:
 *    A Min-Heap is a complete binary tree where the SMALLEST
 *    element is always at the root (index 0).
 *    HEAP PROPERTY: every parent node is smaller than or equal
 *    to both its children at every level of the tree.
 *
 *  Stored as an ARRAY — no pointers needed. The tree structure
 *  is implicit using index arithmetic:
 *    parent(i)     = (i - 1) / 2
 *    leftChild(i)  = 2 * i + 1
 *    rightChild(i) = 2 * i + 2
 *
 *  KEY OPERATIONS:
 *    insert()     — add a value at the end, SIFT UP to fix order
 *    extractMin() — remove the root, move last to root, SIFT DOWN
 *
 *  MARKETING USE CASE:
 *    A Min-Heap can manage a priority queue of marketing leads
 *    sorted by cost or urgency — extractMin() always returns the
 *    most urgent/cheapest lead in O(log n) time.
 *
 *  ADVANTAGE:
 *    Fast insert and extract: O(log n).
 *    Always gives the minimum element in O(1) — just read root.
 *
 *  LIMITATION:
 *    Does not support fast search for arbitrary values — O(n).
 *    Fixed capacity unless dynamic resizing is added.
 *
 *  Time complexity:
 *    insert()     : O(log n)
 *    extractMin() : O(log n)
 *    peek root    : O(1)
 *  Space complexity: O(n)
 * ============================================================
 */
public class MinHeap {

    private int[] heap;     // array storing all heap elements
    private int   size;     // current number of elements
    private int   capacity; // maximum elements the heap can hold

    // ── Constructor: initialise an empty min-heap ─────────────────
    public MinHeap() {
        capacity = 100;
        heap     = new int[capacity];
        size     = 0;
    }

    // ── Index navigation helpers ──────────────────────────────────
    private int parent(int i)     { return (i - 1) / 2; }
    private int leftChild(int i)  { return 2 * i + 1;   }
    private int rightChild(int i) { return 2 * i + 2;   }

    // ── Swap two elements in the heap array ───────────────────────
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i]  = heap[j];
        heap[j]  = temp;
    }

    // ─────────────────────────────────────────────────────────────
    //  INSERT: add a new value and restore the heap property.
    //
    //  Steps:
    //    1. Place the new value at the LAST position (end of array).
    //       This keeps the tree complete (no gaps in the array).
    //    2. SIFT UP: repeatedly swap the new element with its parent
    //       while the new element is SMALLER than its parent.
    //       Stops when: new element >= parent, or it reaches root.
    //
    //  Why end first?  Placing at the end is O(1) and maintains
    //  the complete-tree shape. Sifting up then fixes the ordering.
    // ─────────────────────────────────────────────────────────────
    public void insert(int value) {

        if (size >= capacity) {
            System.out.println("Heap is full! Cannot insert " + value);
            return;
        }

        // Step 1: add at the last position
        heap[size] = value;
        size++;

        // Step 2: sift UP — bubble toward root while smaller than parent
        int i = size - 1;
        while (i > 0 && heap[i] < heap[parent(i)]) {
            swap(i, parent(i));
            i = parent(i); // move one level up
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  EXTRACT MIN: remove and return the smallest element (root).
    //
    //  Steps:
    //    1. Save the root value (index 0) — that IS the minimum.
    //    2. Move the LAST element to the root position.
    //    3. Reduce size by 1 (discard the old last position).
    //    4. SIFT DOWN from the root to restore the heap property.
    //
    //  Why move the last element?  Removing the root leaves a gap
    //  at index 0. The last element fills it without breaking the
    //  complete-tree shape; sifting down then fixes the ordering.
    // ─────────────────────────────────────────────────────────────
    public int extractMin() {

        if (size == 0) {
            System.out.println("Heap is empty! Nothing to extract.");
            return -1;
        }

        int min    = heap[0];           // Step 1: save the minimum
        heap[0]    = heap[size - 1];    // Step 2: move last to root
        size--;                         // Step 3: shrink the heap
        siftDown(0);                    // Step 4: restore heap property

        return min;
    }

    // ─────────────────────────────────────────────────────────────
    //  SIFT DOWN: push element at index i downward until the heap
    //  property (parent <= children) is satisfied again.
    //
    //  At each level:
    //    Find the SMALLEST of { current node, left child, right child }.
    //    If the smallest is NOT the current node, swap and continue.
    //    If current node IS the smallest, stop — heap property holds.
    // ─────────────────────────────────────────────────────────────
    private void siftDown(int i) {

        int smallest = i;            // assume current is the smallest
        int left     = leftChild(i);
        int right    = rightChild(i);

        // Check left child
        if (left < size && heap[left] < heap[smallest])
            smallest = left;

        // Check right child
        if (right < size && heap[right] < heap[smallest])
            smallest = right;

        // If a child is smaller, swap and continue sifting down
        if (smallest != i) {
            swap(i, smallest);
            siftDown(smallest); // recurse at the swapped position
        }
        // If smallest == i, the heap property is already satisfied
    }
}
