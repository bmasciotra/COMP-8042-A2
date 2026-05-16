package reader;

import tree.nodes.GenericTreeNode;
import trees.GenericTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class DotFileWriter {
    public static void toDotFile(String outputFileName, DirectoryTraverser directoryTree) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.write("digraph FolderStructure {\n");
            writer.write("  node [shape=box];\n");
            writeNodeConnections(directoryTree.getDirectoryTree(), writer);
            writer.write("}\n");
            System.out.println("Graph written to " + outputFileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void writeNodeConnections(GenericTree<File> tree, FileWriter writer) throws IOException {
        String rootValue = tree.getRoot().getValue().getName();
        writer.write(String.format("  \"%s\" [label=\"%s\"];\n", rootValue, rootValue));
        for (GenericTreeNode<File> node : tree.levelOrderTraverse()) {
            for (GenericTreeNode<File> child : node.getChildren()) {
                //Draw an edge from the current node to each of its children
                writer.write(String.format("  \"%s\" -> \"%s\";\n", node.getValue().getName(), child.getValue().getName()));
            }
        }
    }
}
