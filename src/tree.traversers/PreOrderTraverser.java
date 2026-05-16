package tree.traversers;

import tree.nodes.BinarySearchTreeNode;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class PreOrderTraverser<T extends Comparable<? super T>, N extends BinarySearchTreeNode<T>> implements Iterator<N> {
    private Stack<N> stack;

    public PreOrderTraverser(N root) {
        stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public N next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        N node = stack.pop();
        if (node.getRightChild() != null) {
            stack.push((N) node.getRightChild());
        }
        if (node.getLeftChild() != null) {
            stack.push((N) node.getLeftChild());
        }
        return node;
    }
}