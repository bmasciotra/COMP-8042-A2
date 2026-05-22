package bst;

import tree.nodes.GenericTreeNode;
import trees.GenericTree;

import java.util.List;

public class BSTChecker {
    final private int LEFT = 0;
    final private int RIGHT = 1;
    final private int MAX_CHILDREN = 2;

    public boolean isBinarySearchTree(GenericTree<Integer> tree) {

        GenericTreeNode<Integer> root = tree.getRoot();

        if (root == null) return true; // empty tree by definition is a bst

        return recurseTree(root, null, null);
    }


    /***
     * Recurse tree recursively traverses the tree based on the root and its children. The algorithm verifies the
     * amount of children and the values in context to the root and returns if that value breaks the rules
     * of a binary search tree
     *
     * @param root the root being examined
     * @return if the root has verified or broken the binary tree rules
     */
    private boolean recurseTree(GenericTreeNode<Integer> root, Integer min, Integer max) {
        int value = root.getValue();

        // detect if there is a value that meets the ruleset for the root but not the tree
        if (min != null && value <= min) return false;
        if (max != null && value >= max) return false;

        if (root.getChildren().size() > MAX_CHILDREN) return false;

        List<GenericTreeNode<Integer>> children = root.getChildren();

        boolean leftIsTree = true;
        boolean rightIsTree = true;

        // recurse the left side
        if (!children.isEmpty()) {
            GenericTreeNode<Integer> leftChild = children.get(LEFT);
            GenericTreeNode<Integer> rightChild = children.size() > 1 ? children.get(RIGHT) : null;


            if (leftChild != null) {
                // if the left child is greater than the pivot we have an invalidation
                if (leftChild.getValue() > root.getValue()) {
                    leftIsTree = false;
                }

                if (!leftChild.getChildren().isEmpty()) {
                    // the left child is now the pivot point
                    leftIsTree = leftIsTree && recurseTree(leftChild, min, root.getValue());
                }
            }

            // recurse the right side
            if (rightChild != null) {
                // If the right child is less than the pivot we have an invalidation
                if (rightChild.getValue() < root.getValue()) {
                    rightIsTree = false;
                }

                if (!rightChild.getChildren().isEmpty()) {
                    // the right child is now the pivot point
                    rightIsTree = rightIsTree && recurseTree(rightChild, root.getValue(), max);
                }
            }
        }

        return leftIsTree && rightIsTree;
    }
}


