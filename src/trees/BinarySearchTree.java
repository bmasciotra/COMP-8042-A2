package trees;

import tree.nodes.BinarySearchTreeNode;

public class BinarySearchTree<T extends Comparable<? super T>> extends AbstractBinarySearchTree<T, BinarySearchTreeNode<T>> {

    @Override
    protected BinarySearchTreeNode<T> insert(T x, BinarySearchTreeNode<T> subtreeRoot) {
        if (subtreeRoot == null) {
            return new BinarySearchTreeNode<>(x);
        }

        int compareResult = x.compareTo(subtreeRoot.getValue());

        if (compareResult < 0) {
            subtreeRoot.setLeftChild(insert(x, subtreeRoot.getLeftChild()));
        } else if (compareResult > 0) {
            subtreeRoot.setRightChild(insert(x, subtreeRoot.getRightChild()));
        } else {
            // Duplicate; do nothing
        }
        return subtreeRoot;
    }

    @Override
    protected BinarySearchTreeNode<T> remove(T x, BinarySearchTreeNode<T> root) {
        if (root == null) {
            return null; // Item not found; do nothing
        }

        int compareResult = x.compareTo(root.getValue());

        if (compareResult < 0)
            root.setLeftChild(remove(x, root.getLeftChild()));
        else if (compareResult > 0)
            root.setRightChild(remove(x, root.getRightChild()));
        else {
            if (root.isLeaf()) {
                return null;
            }

            if (root.getLeftChild() == null) {
                return root.getRightChild();
            }

            if (root.getRightChild() == null) {
                return root.getLeftChild();
            }
            // When both children are present
            BinarySearchTreeNode<T> successor = findMin(root.getRightChild());
            root.setValue(successor.getValue());
            root.setRightChild(remove(successor.getValue(), root.getRightChild()));
        }
        return root;
    }
}