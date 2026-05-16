package tree.nodes;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeNode<T extends Comparable<? super T>> extends TreeNode<T> implements BinaryTreeNode<BinarySearchTreeNode<T>> {
    private BinarySearchTreeNode<T> left;   
    private BinarySearchTreeNode<T> right;

    public BinarySearchTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public BinarySearchTreeNode<T> getLeftChild() {
        return left;
    }

    public BinarySearchTreeNode<T> getRightChild() {
        return right;
    }

    public void setRightChild(BinarySearchTreeNode<T> right) {
        if(right == null){
            this.right = null;
            return;
        }

        int comp = right.getValue().compareTo(this.getValue());

        if (comp > 0) {
            this.right = right;
        } else {
            throw new RuntimeException("Right child must be greater than parent");
        }
    }

    public void setLeftChild(BinarySearchTreeNode<T> left) {
        if (left == null) {
            this.left = null;
            return;
        }

        int comp = left.getValue().compareTo(this.getValue());

        if (comp < 0) {
            this.left = left;
        } else {
            throw new RuntimeException("Left child must be less than parent");
        }
    }
    
    @Override
    public List<TreeNode<T>> getChildren() {
        List<TreeNode<T>> children = new ArrayList<>();
        if (left != null) {
            children.add(left);
        }
        if (right != null) {
            children.add(right);
        }
        return children;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
