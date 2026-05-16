package tree.nodes;
import java.util.List;

public abstract class TreeNode<T> {
    protected T value;
    protected int height;

    public T getValue() {
        return value;
    }

    public void setValue(T newValue){
        this.value = newValue;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public String toString(){
        return getValue().toString();
    }

    public abstract List<? extends TreeNode<T>> getChildren();
    public abstract boolean isLeaf();
}
