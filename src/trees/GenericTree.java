package trees;

import tree.nodes.GenericTreeNode;
import tree.traversers.LevelOrderTraverser;

import java.util.*;


public class GenericTree<T extends Comparable<? super T>> {
    private GenericTreeNode<T> root;
    private int size;

    public GenericTree(GenericTreeNode<T> root) {
        this.root = root;
        this.size = 1;
    }

    public GenericTree() {
        this.size = 0;
    }

    /*
     * A very inefficient way to search - sequentially by level.
     */
    public GenericTreeNode<T> searchNode(T element) {
        for (GenericTreeNode<T> node : levelOrderTraverse()) {
            if (node.getValue().equals(element)) {
                return node;
            }
        }
        return null;
    }

    public GenericTreeNode<T> getRoot() {
        return root;
    }

    public int size() {
        return size;
    }

    public int height() {
        // ToDo: Students should implement this
        return 0;
    }

    public void addChild(GenericTreeNode<T> parent, GenericTreeNode<T> child) {
        /*
         * Adds children from right to left order
         */
        if (parent == null) {
            if (root == null) {
                root = child;
                size++;
            } else {
                throw new Error("Cannot add child to null parent in a non-empty tree.");
            }
        } else {
            parent.addChild(child);
            size++;
        }
    }

    public boolean removeLeaf(GenericTreeNode<T> parent, GenericTreeNode<T> child) {
        if (parent == null) {
            throw new Error("Cannot remove child from a null parent.");
        }
        if (!child.isLeaf()) {
            throw new Error("Can only remove leaves from a tree.");
        }

        boolean child_removed = parent.removeChild(child);

        if (child_removed) {
            size--;
        }
        return child_removed;
    }

    public Iterable<GenericTreeNode<T>> levelOrderTraverse() {
        return new Iterable<GenericTreeNode<T>>() {
            @Override
            public Iterator<GenericTreeNode<T>> iterator() {
                return new LevelOrderTraverser<T, GenericTreeNode<T>>(root);
            }
        };
    }

    @Override
    public String toString() {
        if (root == null) return "(empty tree)";
        Block b = render(root);
        return String.join("\n", b.lines);
    }

    private static class Block {
        final List<String> lines;
        final int rootCol;  // column where this subtree's root sits

        Block(List<String> lines, int rootCol) {
            this.lines = lines;
            this.rootCol = rootCol;
        }

        int width() {
            return lines.isEmpty() ? 0 : lines.get(0).length();
        }
    }

    private Block render(GenericTreeNode<T> node) {
        String label = String.valueOf(node.getValue());
        List<GenericTreeNode<T>> children = node.getChildren();

        if (children.isEmpty()) {
            return new Block(new ArrayList<>(List.of(label)), label.length() / 2);
        }

        int gap = 2;
        List<Block> kids = new ArrayList<>();
        for (GenericTreeNode<T> c : children) kids.add(render(c));

        // Lay children out side-by-side and remember where each child's root sits
        List<Integer> kidRootCols = new ArrayList<>();
        int totalWidth = 0;
        for (int i = 0; i < kids.size(); i++) {
            if (i > 0) totalWidth += gap;
            kidRootCols.add(totalWidth + kids.get(i).rootCol);
            totalWidth += kids.get(i).width();
        }

        int childrenCenter = (kidRootCols.get(0) + kidRootCols.get(kidRootCols.size() - 1)) / 2;
        int labelLeft = childrenCenter - label.length() / 2;
        int labelRight = labelLeft + label.length();
        int leftPad = Math.max(0, -labelLeft);
        int rightPad = Math.max(0, labelRight - totalWidth);
        int width = totalWidth + leftPad + rightPad;
        int rootCenter = childrenCenter + leftPad;

        char[] labelRow = blank(width);
        char[] connectRow = blank(width);

        int labelStart = rootCenter - label.length() / 2;
        for (int i = 0; i < label.length(); i++) labelRow[labelStart + i] = label.charAt(i);

        for (int col : kidRootCols) {
            int c = col + leftPad;
            if (c < rootCenter) connectRow[c] = '/';
            else if (c > rootCenter) connectRow[c] = '\\';
            else connectRow[c] = '|';
        }

        List<String> out = new ArrayList<>();
        out.add(new String(labelRow));
        out.add(new String(connectRow));

        int maxH = 0;
        for (Block b : kids) maxH = Math.max(maxH, b.lines.size());
        for (int row = 0; row < maxH; row++) {
            StringBuilder sb = new StringBuilder();
            sb.append(" ".repeat(leftPad));
            for (int i = 0; i < kids.size(); i++) {
                if (i > 0) sb.append(" ".repeat(gap));
                Block b = kids.get(i);
                sb.append(row < b.lines.size() ? b.lines.get(row) : " ".repeat(b.width()));
            }
            sb.append(" ".repeat(rightPad));
            out.add(sb.toString());
        }
        return new Block(out, rootCenter);
    }

    private static char[] blank(int n) {
        char[] a = new char[n];
        java.util.Arrays.fill(a, ' ');
        return a;
    }
}
