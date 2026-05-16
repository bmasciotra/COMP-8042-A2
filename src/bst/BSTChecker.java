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
        return iterateTree(root);
    }

    // recursively iterate
    private boolean iterateTree(GenericTreeNode<Integer> root) {

        List<GenericTreeNode<Integer>> children = root.getChildren();

        boolean leftIsTree = true;
        boolean rightIsTree = true;

        // recurse the left side
        if (!children.isEmpty()) {
            GenericTreeNode<Integer> leftChild = children.get(_left);
            GenericTreeNode<Integer> rightChild = children.get(_right);

            if (leftChild != null) {

                // if the left child is greater than the pivot we have an invalidation
                if (leftChild.getValue() > root.getValue() || leftChild.getChildren().size() > _maxChildren) {
                    leftIsTree = false;
                }

                if (!leftChild.getChildren().isEmpty()) {
                    // the left child is now the pivot point
                    leftIsTree = leftIsTree && iterateTree(leftChild);
                }
            }

            // recurse the right side
            if (rightChild != null) {

                // If the right child is less than the pivot we have an invalidation
                if (rightChild.getValue() < root.getValue() || rightChild.getChildren().size() > _maxChildren) {
                    rightIsTree = false;
                }

                if (!rightChild.getChildren().isEmpty()) {
                    // the right child is now the pivot point
                    rightIsTree = rightIsTree && iterateTree(rightChild);
                }
            }
        }

        return leftIsTree && rightIsTree;
    }
}


