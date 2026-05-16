package trees;


import tree.nodes.AvlTreeNode;

/**
 * AVL Tree Implementation
 * This is a self-balancing binary search tree where the height difference
 * between left and right subtrees (balance factor) is at most 1.
 */
public class AvlTree<T extends Comparable<? super T>> extends AbstractBinarySearchTree<T, AvlTreeNode<T>> {

    @Override
    protected AvlTreeNode<T> insert(T x, AvlTreeNode<T> t) {
        if (t == null)
            return new AvlTreeNode<>(x, null, null);

        int compareResult = x.compareTo(t.getValue());

        if (compareResult < 0)
            t.setLeftChild(insert(x, (AvlTreeNode<T>) t.getLeftChild()));
        else if (compareResult > 0)
            t.setRightChild(insert(x, (AvlTreeNode<T>) t.getRightChild()));
        else
            ;  // Duplicate; do nothing
        return balance(t);
    }

    @Override
    protected AvlTreeNode<T> remove(T x, AvlTreeNode<T> t) {
        if (t == null)
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo(t.getValue());

        if (compareResult < 0)
            t.setLeftChild(remove(x, t.getLeftChild()));
        else if (compareResult > 0)
            t.setRightChild(remove(x, t.getRightChild()));
        else if (t.getLeftChild() != null && t.getRightChild() != null) // Two children
        {
            t.setValue(findMin(t.getRightChild()).getValue());
            t.setRightChild(remove(t.getValue(), t.getRightChild()));
        } else
            t = (t.getLeftChild() != null) ? t.getLeftChild() : t.getRightChild();
        return balance(t);
    }

    private AvlTreeNode<T> balance(AvlTreeNode<T> node) {
        if (node == null) return null;

        if (height(node.getLeftChild()) - height(node.getRightChild()) > 1) {
            if (height(node.getLeftChild().getLeftChild()) >= height(node.getLeftChild().getRightChild())) {
                node = rotateRight(node);  // LL case
            } else {
                node = doubleRotateLeftRight(node);  // LR case
            }
        } else if (height(node.getRightChild()) - height(node.getLeftChild()) > 1) {
            if (height(node.getRightChild().getRightChild()) >= height(node.getRightChild().getLeftChild())) {
                node = rotateLeft(node);  // RR case
            } else {
                node = doubleRotateRightLeft(node);  // RL case
            }
        }

        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return node;
    }

    private AvlTreeNode<T> rotateRight(AvlTreeNode<T> node) {
        AvlTreeNode<T> newRoot = node.getLeftChild();
        node.setLeftChild(newRoot.getRightChild());
        newRoot.setRightChild(node);
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private AvlTreeNode<T> rotateLeft(AvlTreeNode<T> node) {
        AvlTreeNode<T> newRoot = node.getRightChild();
        node.setRightChild(newRoot.getLeftChild());
        newRoot.setLeftChild(node);
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private AvlTreeNode<T> doubleRotateLeftRight(AvlTreeNode<T> node) {
        node.setLeftChild(rotateLeft(node.getLeftChild()));
        return rotateRight(node);
    }

    private AvlTreeNode<T> doubleRotateRightLeft(AvlTreeNode<T> node) {
        node.setRightChild(rotateRight(node.getRightChild()));
        return rotateLeft(node);
    }

    private void updateHeight(AvlTreeNode<T> node) {
        if (node != null) {
            node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        }
    }
}