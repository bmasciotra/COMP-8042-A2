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

    // recursively iterate
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


