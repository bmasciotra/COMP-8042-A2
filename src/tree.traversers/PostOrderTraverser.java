package tree.traversers;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import tree.nodes.BinarySearchTreeNode;


public class PostOrderTraverser<T extends Comparable<? super T>, N extends BinarySearchTreeNode<T>> implements Iterator<N> {
    private final Stack<N> stack;
    private final Stack<N> output;

    public PostOrderTraverser(N root) {
        stack = new Stack<>();
        output = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            N current = stack.pop();
            output.push(current);
            if (current.getLeftChild() != null) {
                stack.push((N) current.getLeftChild());
            }
            if (current.getRightChild() != null) {
                stack.push((N) current.getRightChild());
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !output.isEmpty();
    }

    @Override
    public N next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the tree");
        }
        return output.pop();
    }
}