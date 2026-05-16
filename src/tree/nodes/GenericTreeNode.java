package tree.nodes;

import java.util.*;

public class GenericTreeNode<T> extends TreeNode<T> {
    private final List<GenericTreeNode<T>> children;

    public GenericTreeNode(T value) {
        this.value = value;

        // ToDo for students: Is using an arraylist a good idea here?
        this.children = new ArrayList<>();
    }

    public List<GenericTreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(GenericTreeNode<T> child) {
        /*
         * Adds child from right to left order
         */
        children.add(child);
    }

    public boolean removeChild(GenericTreeNode<T> child) {
        return children.remove(child);
    }


    public boolean isLeaf() {
        return children.isEmpty();
    }
}
