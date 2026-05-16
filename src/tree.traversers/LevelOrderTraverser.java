package tree.traversers;

import tree.nodes.TreeNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class LevelOrderTraverser<T extends Comparable<? super T>, N extends TreeNode<T>> implements Iterator<N> {
    private Queue<N> q = new LinkedList<N>();

    public LevelOrderTraverser(N root) {
        q = new LinkedList<>();
        if (root != null) {
            q.add(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !q.isEmpty();
    }

    @Override
    public N next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the tree");
        }

        N current = q.poll();
        
        for(N child: (List<? extends N>) current.getChildren()){
            q.add(child);
        }
        return current;
    }
}
