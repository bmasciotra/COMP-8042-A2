package trees;


import tree.nodes.BinarySearchTreeNode;
import tree.traversers.LevelOrderTraverser;
import tree.traversers.PostOrderTraverser;
import tree.traversers.PreOrderTraverser;

import java.util.Iterator;


public abstract class AbstractBinarySearchTree<T extends Comparable<? super T>, N extends BinarySearchTreeNode<T>> {
    protected N root;

    public AbstractBinarySearchTree() {
        root = null;
    }

    public N getRoot() {
        return root;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public T findMin() {
        if (isEmpty())
            throw new Error();
        return findMin(root).getValue();
    }

    public T findMax() {
        if (isEmpty())
            throw new Error();
        return findMax(root).getValue();
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    protected abstract N insert(T x, N subtreeRoot);

    protected abstract N remove(T x, N subtreeRoot);

    protected N findMin(N t) {
        if (t == null)
            return null;
        else if (t.getLeftChild() == null)
            return t;
        return findMin((N) t.getLeftChild());
    }

    protected N findMax(N t) {
        if (t != null)
            while (t.getRightChild() != null)
                t = (N) t.getRightChild();
        return t;
    }

    protected boolean contains(T x, N t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.getValue());

        if (compareResult < 0)
            return contains(x, (N) t.getLeftChild());
        else if (compareResult > 0)
            return contains(x, (N) t.getRightChild());
        else
            return true; // Match
    }

    protected void printTree(N t) {
        if (t != null) {
            printTree((N) t.getLeftChild());
            System.out.println(t.getValue());
            printTree((N) t.getRightChild());
        }
    }

    public int height() {
        return height(root);
    }

    protected int height(N t) {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height((N) t.getLeftChild()), height((N) t.getRightChild()));
    }

    public Iterable<N> levelOrderTraverse() {
        return new Iterable<N>() {
            @Override
            public Iterator<N> iterator() {
                return new LevelOrderTraverser<T, N>(root);
            }
        };
    }

    public Iterable<N> preOrderTraverse() {
        return new Iterable<N>() {
            @Override
            public Iterator<N> iterator() {
                return new PreOrderTraverser<T, N>(root);
            }
        };
    }

    public Iterable<N> postOrderTraverse() {
        return new Iterable<N>() {
            @Override
            public Iterator<N> iterator() {
                return new PostOrderTraverser<T, N>(root);
            }
        };
    }
}