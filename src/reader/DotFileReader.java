package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;

import tree.nodes.GenericTreeNode;
import trees.GenericTree;

public class DotFileReader {

    public static <T extends Comparable<? super T>> GenericTree<T> createTreeFromDotFile(
            String filePath, Function<String, T> converter) throws IOException {
        GenericTree<T> tree = new GenericTree<T>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isTreeContent = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip "digraph {" or "graph {" and "}" lines
                if (line.startsWith("digraph") || line.startsWith("graph")) {
                    isTreeContent = true;
                    continue;
                }
                if (line.equals("}")) {
                    isTreeContent = false;
                    break;
                }

                if (isTreeContent && line.contains("->")) {
                    String[] parts = line.split("->");
                    if (parts.length == 2) {
                        T parentValue = converter.apply(parts[0].trim());
                        T childValue = converter.apply(parts[1].replace(";", "").trim());

                        GenericTreeNode<T> parent = tree.searchNode(parentValue);
                        GenericTreeNode<T> child = new GenericTreeNode<>(childValue);

                        if (parent == null) {
                            GenericTreeNode<T> rootNode = new GenericTreeNode<T>(parentValue);
                            tree = new GenericTree<>(rootNode);
                            rootNode.addChild(child);
                        } else {
                            parent.addChild(child);
                        }
                    }
                }
            }
        }
        return tree;
    }
}
