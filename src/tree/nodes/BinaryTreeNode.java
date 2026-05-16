package tree.nodes;

public interface BinaryTreeNode<T> {
    public BinaryTreeNode<T> getRightChild();
    public BinaryTreeNode<T> getLeftChild();
    public void setRightChild(T right);
    public void setLeftChild(T left);
    public int getHeight();
    public void setHeight(int height);
}
