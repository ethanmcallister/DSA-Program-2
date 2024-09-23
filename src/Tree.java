// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import javax.swing.tree.TreeNode;

import com.sun.source.tree.BinaryTree;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree {
    private BinaryNode root;  // Root of tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create tree from list
     * @param arr   List of elements
     * @param label Name of tree
     * @ordered true if we want an ordered tree
     */
    public Tree(Integer[] arr, String label, boolean ordered) {
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }


    /**
     * Build a NON BST tree by inorder
     * @param arr nodes to be added
     * @return new tree
     */
    private BinaryNode buildUnordered(Integer[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode curr = new BinaryNode(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }


    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString() {
        if (root == null)
            return treeName + " Empty tree";

        return treeName + "\n" + toString(root, 0);
//            return treeName + " Please write the code to print me";
    }

    /**
     * This routine runs in O(??)
     * String representation of tree, with name and
    the keys (in order) of a binary tree, given the root.
     * @param root root node
     * @param level level of the root node
     * @return sideways tree representation
     */
    public String toString(BinaryNode root, int level) {
        if (root == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();

        // right tree
        result.append(toString(root.right, level + 1));

        // add indentation (4 per level)
        for (int i = 0; i < level; i++) {
            result.append("    ");
        }
        result.append(root.element).append("\n");

        // left tree
        result.append(toString(root.left, level + 1));
        return result.toString();
    }
    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     *
     * @param t the node that roots the subtree.
     */
    public String toString2(BinaryNode t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }


    /**
     * The complexity of finding the deepest node is O(???)
     * @return deepest node, if tree is empty, return -1.
     */
    public Integer deepestNode() {
        if (root == null) {  // if empty tree, return -1
            return -1;
        }
        return deepestNode(root, 0)[0];
    }

    // Recursive method to find the deepest node
    private static int[] deepestNode(BinaryNode t, int depth) {
        if (t == null) return new int[] {-1, depth - 1}; // Return -1 for value if node is null

        // Recursively find the deepest node in left and right subtrees
        int[] leftDeepest = deepestNode(t.left, depth + 1);
        int[] rightDeepest = deepestNode(t.right, depth + 1);

        // return the deeper element based on depth
        if (rightDeepest[1] >= leftDeepest[1]) {
            if (t.right != null) {
                return new int[]{rightDeepest[0], rightDeepest[1]};
            } else { 
                return new int[]{t.element, rightDeepest[1]};
            }
        } else {
            if (t.left != null) {
                return new int[]{leftDeepest[0], leftDeepest[1]};
            } else {
                return new int[]{t.element, leftDeepest[1]};
            }
        }   
    }

    /**
     * The complexity of finding the flip is O(???)
     * reverse left and right children recursively
     */
    public void flip() {
        flip(root);
    }
    private void flip(BinaryNode t){
        if (t == null) {
            return;
        }

        BinaryNode temp = t.left;
        t.left = t.right;
        t.right = temp;

        flip(t.left);
        flip(t.right);
    }

    /**
     * Counts number of nodes in specified level
     * The complexity of nodesInLevel is O(???)
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        System.out.println("nodesInLevel needs to return the nodes at " + level);
        
        return nodesInLevel(root, 0, level, 0);
    }

    private static int nodesInLevel(BinaryNode t, int currLevel, int level, int numNodes) {
        if (t == null) { return 0; }
        if (currLevel == level) { return numNodes + 1; }
        return nodesInLevel(t.left, currLevel + 1, level, numNodes) + nodesInLevel(t.right, currLevel + 1, level, numNodes);
    }

    /**
     * Print all paths from root to leaves
     * The complexity of printAllPaths is O(???)
     */
    public void printAllPaths() {
        if (root == null) { System.out.println("printAllPaths does nothing"); }
        else {
            int[] path = new int[1000];  // give myself room
            printAllPaths(root, path, 0);
        }
    }

    private static void printAllPaths(BinaryNode t, int[] path, int pathLength) {
        if (t == null) { return; }  // if the root is null
        
        // append element to the array
        path[pathLength] = t.element;
        pathLength++;

        // if its a leaf node, print the path
        if (t.left == null && t.right == null) {
            printPath(path, pathLength);
        } else {  // otherwise recurse to the left and right
            printAllPaths(t.left, path, pathLength);
            printAllPaths(t.right, path, pathLength);
        }
    }

    private static void printPath(int[] path, int pathLength) {
        for (int i = 0; i < pathLength; i++) {
            System.out.print(path[i] + " ");
        }
        System.out.println();
    }


    /**
     * Counts all non-null binary search trees embedded in tree
     *  The complexity of countBST is O(???)
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        if (root == null) return 0;
        return -1;
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     * The complexity of bstInsert depends on the tree.  If it is balanced the complexity is O(log n)
     * @param x the item to insert.
     */
    public void bstInsert(Integer x) {

        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode bstInsert(Integer x, BinaryNode t) {
        if (t == null)
            return new BinaryNode (x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(Integer item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(Integer x, BinaryNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }
    /**
     * Remove all paths from tree that sum to less than given value
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum) {
        root = pruneK(root, sum, 0);
    }

    private static BinaryNode pruneK(BinaryNode t, int sum, int currSum) {
        if (t == null) { return null; }  // if root is null, return null

        currSum += t.element;
        
        // recurse to left and right
        t.left = pruneK(t.left, sum, currSum);
        t.right = pruneK(t.right, sum, currSum);

        if (t.left == null && t.right == null && currSum < sum) {
            return null;
        }

        // return the node
        return t;
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeFromTraversals(Integer[] inOrder, Integer[] preOrder) {
        root = null;
    }

    public Integer sumAll(){
        BinaryNode  r =   root;
        return sumAll(r);
    }
    public Integer sumAll(BinaryNode  t){
        if (t==null) return 0;
        return t.element + sumAll(t.left) + sumAll(t.right);
    }

    public Integer lca(Integer a, Integer b) {

         BinaryNode l = lca(root,a,b);
         if (l==null) return null;
         return l.element;

    }

        /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public BinaryNode lca(BinaryNode  t,Integer a, Integer b) {
        if (t == null) { return null; }
        if (a < t.element && b < t.element) {  // both a and b in left subtree
            return lca(t.left, a, b); 
        }
        if (a > t.element && b > t.element) {  // both a nd b in right subtree
            return lca(t.right, a, b);
        }
        // we found the lca if both exist... now we check if both exist
        if ((existInTree(t, a)) && (existInTree(t, b))) { return t; }
        return null;  // return null if both don't exist

    }

    private static boolean existInTree(BinaryNode t, int val) {
        if (t == null) { return false; }
        if (t.element == val) { return true; }
        if (val < t.element) { return existInTree(t.left, val); }
        return existInTree(t.right, val);
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        //root = balanceTree(root);
    }

    /**
     * In a BST, keep only nodes between range
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(Integer a, Integer b) {
    }

    // Basic node stored in unbalanced binary  trees
    public static class BinaryNode  {
        Integer element;            // The data in the node
        BinaryNode left;   // Left child
        BinaryNode  right;  // Right child

        // Constructors
        BinaryNode(Integer theElement) {
            this(theElement, null, null);
        }

        BinaryNode(Integer theElement, BinaryNode lt, BinaryNode rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }

    }


}
