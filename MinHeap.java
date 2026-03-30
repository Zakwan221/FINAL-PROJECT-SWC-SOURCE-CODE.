
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
