
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

    private Node rotateRight(Node x) {
        Node y  = x.left;   // y is x's left child
        x.left  = y.right;  // y's right subtree moves to x's left
        y.right = x;        // x drops to become y's right child
        return y;           // y is the new subtree root
    }


    private Node rotateLeft(Node x) {
        Node y  = x.right;  // y is x's right child
        x.right = y.left;   // y's left subtree moves to x's right
        y.left  = x;        // x drops to become y's left child
        return y;           // y is the new subtree root
    }


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


    public boolean search(int key) {
        root = splay(root, key); // move closest key to root
        return root != null && root.key == key;
    }
}
