package tree.nodes;

import java.util.ArrayList;
import java.util.List;

public class AvlTreeNode<T extends Comparable<? super T>> extends BinarySearchTreeNode<T> {
    private AvlTreeNode<T> left;   
    private AvlTreeNode<T> right;  
    private int height;

    public AvlTreeNode(T value) {
        this(value, null, null);
    }

    public AvlTreeNode(T value, AvlTreeNode<T> left, AvlTreeNode<T> right) {
        super(value);
        setLeftChild(left);
        setRightChild(right);

    }

    public AvlTreeNode<T> getLeftChild() {
        return left;
    }

    public AvlTreeNode<T> getRightChild() {
        return right;
    }

    public void setLeftChild(AvlTreeNode<T> left) {
        if (left == null) {
            this.left = null;
            return;
        }

        int comp = left.getValue().compareTo(this.getValue());

        if (comp < 0) {
            this.left = left;
            return;
        } else {
            throw new RuntimeException("Left child must be less than parent");
        }
    }

    public void setRightChild(AvlTreeNode<T> right) {
        if (right == null) {
            this.right = null;
            return;
        }

        int comp = right.getValue().compareTo(this.getValue());

        if (comp > 0) {
            this.right = right;
            return;
        } else {
            throw new RuntimeException("Right child must be greater than parent");
        }
    }
    
    public boolean isLeaf() {
        return left == null && right == null;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
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
}
