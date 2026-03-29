/**
 * ============================================================
 *  SplayTree.java
 *  Data Structure : Splay Tree  (Individual Task)
 *
 *  CONCEPT:
 *    A Splay Tree is a self-adjusting Binary Search Tree (BST).
 *    Every time a node is ACCESSED (inserted or searched), it is
 *    moved to the ROOT using a sequence of rotations called
 *    SPLAYING. This means frequently accessed nodes stay near
 *    the top of the tree — making repeated lookups of the same
 *    node very fast.
 *
 *  HOW IT DIFFERS FROM A NORMAL BST:
 *    Normal BST: nodes stay in their original positions forever.
 *                Worst case O(n) if the tree becomes unbalanced.
 *    Splay Tree: the accessed node is always rotated to the root.
 *                Amortised O(log n) per operation even if the tree
 *                temporarily becomes unbalanced.
 *
 *  THREE SPLAY ROTATION CASES:
 *    1. Zig     — target is a DIRECT child of the root.
 *                 One single rotation brings it to the root.
 *    2. Zig-Zig — target and its parent are both LEFT children
 *                 (or both RIGHT children).
 *                 Two same-direction rotations (grandparent first,
 *                 then parent) bring target to the root.
 *    3. Zig-Zag — target is a LEFT child but parent is a RIGHT
 *                 child (or vice versa).
 *                 Two opposite-direction rotations bring target up.
 *
 *  MARKETING USE CASE:
 *    A Splay Tree can store frequently queried student records
 *    (e.g., top applicants accessed daily). Repeated lookups of
 *    the same record become O(1) after the first access because
 *    the record moves to the root and stays near the top.
 *
 *  ADVANTAGE:
 *    Amortised O(log n) per operation.
 *    No extra balance information needed (simpler than AVL/RB trees).
 *    Excellent for workloads with repeated access patterns.
 *
 *  LIMITATION:
 *    Individual operations can be O(n) in the worst case
 *    (amortised cost is good, but single calls may be slow).
 *    Not suitable when all operations must have guaranteed O(log n).
 * ============================================================
 */
public class SplayTree {

    // ── Inner node class ──────────────────────────────────────────
    private static class Node {
        int  key;
        Node left, right;

        Node(int key) {
            this.key   = key;
            this.left  = null;
            this.right = null;
        }
    }

    private Node root; // root of the splay tree

    // ─────────────────────────────────────────────────────────────
    //  ROTATE RIGHT
    //  Brings the LEFT child (y) up to the current position (x).
    //
    //       x                y
    //      / \              / \
    //     y   C    →       A   x
    //    / \                  / \
    //   A   B                B   C
    //
    //  y.right becomes x.left; x becomes y.right.
    // ─────────────────────────────────────────────────────────────
    private Node rotateRight(Node x) {
        Node y  = x.left;   // y is x's left child
        x.left  = y.right;  // y's right subtree moves to x's left
        y.right = x;        // x drops to become y's right child
        return y;           // y is the new subtree root
    }

    // ─────────────────────────────────────────────────────────────
    //  ROTATE LEFT
    //  Brings the RIGHT child (y) up to the current position (x).
    //
    //     x                  y
    //    / \                / \
    //   A   y      →       x   C
    //      / \            / \
    //     B   C          A   B
    //
    //  y.left becomes x.right; x becomes y.left.
    // ─────────────────────────────────────────────────────────────
    private Node rotateLeft(Node x) {
        Node y  = x.right;  // y is x's right child
        x.right = y.left;   // y's left subtree moves to x's right
        y.left  = x;        // x drops to become y's left child
        return y;           // y is the new subtree root
    }

    // ─────────────────────────────────────────────────────────────
    //  SPLAY: move the node with 'key' to the root of the subtree.
    //
    //  Applies the correct rotation case at each recursive level:
    //    Zig     — one rotation (direct child of root)
    //    Zig-Zig — two same-direction rotations
    //    Zig-Zag — two opposite-direction rotations
    //
    //  If the key is not found, the closest node ends up at root.
    //
    // @param root current subtree root
    // @param key  the key to splay to the top
    // @return     the new root after splaying
    // ─────────────────────────────────────────────────────────────
    private Node splay(Node root, int key) {

        // Base case: root is null or root IS the target
        if (root == null || root.key == key) return root;

        if (key < root.key) {
            // ── Key is in the LEFT subtree ────────────────────────
            if (root.left == null) return root; // not found

            if (key < root.left.key) {
                // ZIG-ZIG (left-left):
                // Recursively splay in the left-left grandchild,
                // then rotate right TWICE (grandparent then parent).
                root.left.left = splay(root.left.left, key);
                root = rotateRight(root);              // 1st rotation

            } else if (key > root.left.key) {
                // ZIG-ZAG (left-right):
                // Splay from the left-right grandchild,
                // rotate left (inner) then right (outer).
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = rotateLeft(root.left); // 1st rotation
            }

            // ZIG (final single rotation) or return if left is null
            return (root.left == null) ? root : rotateRight(root);

        } else {
            // ── Key is in the RIGHT subtree ───────────────────────
            if (root.right == null) return root; // not found

            if (key > root.right.key) {
                // ZIG-ZIG (right-right):
                root.right.right = splay(root.right.right, key);
                root = rotateLeft(root);               // 1st rotation

            } else if (key < root.right.key) {
                // ZIG-ZAG (right-left):
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = rotateRight(root.right); // 1st rotation
            }

            // ZIG (final single rotation) or return if right is null
            return (root.right == null) ? root : rotateLeft(root);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  INSERT: add a new key and splay it to become the new root.
    //
    //  Steps:
    //    1. Splay the closest existing key to the root.
    //    2. If that key equals the new key → already exists, stop.
    //    3. Otherwise, split the tree around the new key and
    //       attach the two halves as children of the new node.
    //    4. The new node becomes the root.
    //
    //  Duplicate keys are ignored (standard BST behaviour).
    // ─────────────────────────────────────────────────────────────
    public void insert(int key) {

        if (root == null) {
            root = new Node(key); // first node — becomes root directly
            return;
        }

        // Splay the closest key; if key already exists, do nothing
        root = splay(root, key);
        if (root.key == key) return;

        Node newNode = new Node(key);

        if (key < root.key) {
            // New node fits BETWEEN root's left subtree and root
            newNode.right = root;       // root drops to right child
            newNode.left  = root.left;  // inherit root's left subtree
            root.left     = null;       // sever old connection
        } else {
            // New node fits BETWEEN root and root's right subtree
            newNode.left  = root;       // root drops to left child
            newNode.right = root.right; // inherit root's right subtree
            root.right    = null;
        }

        root = newNode; // new node is now the root
    }

    // ─────────────────────────────────────────────────────────────
    //  SEARCH: find a key and splay it to the root if found.
    //
    //  After this call, the found key will be AT the root —
    //  meaning a second search for the same key is O(1).
    //  This is the key advantage of a splay tree over a plain BST.
    //
    // @param key value to search for
    // @return    true if the key exists in the tree, false otherwise
    // ─────────────────────────────────────────────────────────────
    public boolean search(int key) {
        root = splay(root, key); // move closest key to root
        return root != null && root.key == key;
    }
}
