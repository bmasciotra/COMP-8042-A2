package trees;

import java.io.File;
import java.io.IOException;

import reader.DirectoryTraverser;
import reader.DotFileReader;
import tree.nodes.GenericTreeNode;

public final class GenericTreeRunner {
    private GenericTreeRunner() {
    }

    public static void main(String[] args) {
        DirectoryTraverser trav = new DirectoryTraverser("..");
        trav.writeTraversalToDotFile("./test.dot");

        for(GenericTreeNode<File> node: trav.getDirectoryTree().levelOrderTraverse()){
            System.out.println(node);
        }

        try {
            GenericTree<Integer> t = DotFileReader.createTreeFromDotFile("./data/tree1.dot", Integer::parseInt);
            t.levelOrderTraverse().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
