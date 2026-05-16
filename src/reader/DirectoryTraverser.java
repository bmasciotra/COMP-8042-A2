package reader;
import java.io.File;
import java.util.*;

import tree.nodes.GenericTreeNode;
import trees.GenericTree;

public class DirectoryTraverser {

    private String rootPath;
    private GenericTree<File> directoryTree;

    public DirectoryTraverser(String rootPath){
        this.rootPath = rootPath;
        createTree();
    }

    private void createTree(){
        File root = new File(rootPath);
        
        if (!root.exists()) {
            throw new IllegalArgumentException("Path does not exist: " + root.getAbsolutePath());
        }

        GenericTreeNode<File> rootNode = new GenericTreeNode<File>(root);
        directoryTree = new GenericTree<>(rootNode);
        Queue<GenericTreeNode<File>> queue = new LinkedList<GenericTreeNode<File>>();
        queue.add(rootNode);
        
        while(!queue.isEmpty()){
            GenericTreeNode<File> current = queue.remove();
            if (current.getValue().isDirectory()) {
                File[] files = current.getValue().listFiles();
                if (files != null) {
                    for (File file : files) {
                        GenericTreeNode<File> nextChild = new GenericTreeNode<File>(file);
                        directoryTree.addChild(current, nextChild);
                        queue.add(nextChild);
                    }
                }
            }
        }
    }

    public GenericTree<File> getDirectoryTree(){
        return directoryTree;
    }

    public Iterable<GenericTreeNode<File>> traverse(){
        return directoryTree.levelOrderTraverse();
    }

    public void writeTraversalToDotFile(String outputFileName) {
       DotFileWriter.toDotFile(outputFileName, this);
    }
}
