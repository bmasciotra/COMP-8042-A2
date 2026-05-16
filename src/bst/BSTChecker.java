package bst;

import tree.nodes.GenericTreeNode;
import trees.GenericTree;

import java.util.List;

public class BSTChecker {
    final private int _left = 0;
    final private int _right = 1;
    final private int _maxChildren = 2;

    public boolean isBinarySearchTree(GenericTree<Integer> tree) {

        GenericTreeNode<Integer> root = tree.getRoot();
        return recurseTree(root);
    }


    /***
     * Recurse tree recursively traverses the tree based on the root and its children. The algorithm verifies the
     * amount of children and the values in context to the root and returns if that value breaks the rules
     * of a binary search tree
     *
     * @param root the root being examined
     * @return if the root has verified or broken the binary tree rules
     */
    private boolean recurseTree(GenericTreeNode<Integer> root) {

        if (root.getChildren().size() > _maxChildren) return false;

        List<GenericTreeNode<Integer>> children = root.getChildren();

        boolean leftIsTree = true;
        boolean rightIsTree = true;

        // recurse the left side
        if (!children.isEmpty()) {
            GenericTreeNode<Integer> leftChild = children.get(_left);
            GenericTreeNode<Integer> rightChild = children.get(_right);

            if (leftChild != null) {
                // if the left child is greater than the pivot we have an invalidation
                if (leftChild.getValue() > root.getValue()) {
                    leftIsTree = false;
                }

                if (!leftChild.getChildren().isEmpty()) {
                    // the left child is now the pivot point
                    leftIsTree = leftIsTree && recurseTree(leftChild);
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
                    rightIsTree = rightIsTree && recurseTree(rightChild);
                }
            }
        }

        return leftIsTree && rightIsTree;
    }
}


