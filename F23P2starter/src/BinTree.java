/**
 * BT is a binary spatial trie that is a 
 * natural extension of a BST for multi-
 * dimensional keys (two key implemntation)
 * 
 * @author Ali Haidari (alih)
 * @version 9.29.2023
 */
public class BinTree {
    private BTNode root;
    private int worldSize;
        
    /**
     * constructor for a bin tree 
     * with a starting world size
     * 
     * @param initWorld : initial world size
     */
    public BinTree(int initWorld) {
        root = BTEmptyNode.EMPTY;
        worldSize = initWorld;
    }
    
    /**
     * getter for the root node of a BinTree
     * 
     * @return internal, leaf, or empty node
     */
    public BTNode root() {
        return root;
    }
    
    /**
     * inserts a seminar into the BinTree
     * 
     * @param inSem : seminar to be inserted
     */
    public void insert(Seminar inSem) {
        root = root.insert(0, 0, 0, worldSize, worldSize, inSem);
    }
    
    /**
     * searches for an coordinate within binTree
     * 
     * @param inSX : x coord to search
     * @param inSY : y coord to search
     * @return list of seminars at point, null otherwise
     */
    public LinkedList<Seminar> search(int inSX, int inSY) {
        BTNode found = root.search(0, inSX, inSY, 0, 0, worldSize, worldSize);
        //if the search returns nothing we return null
        if (found == null) {
            return null;
        }
        //otherwise we check the coordinates of the found leaf
        BTLeafNode foundLeaf = (BTLeafNode)found;
        return foundLeaf.data();
       
    }
    
    /**
     * deletes a seminar within a leaf from binTree
     * 
     * @param inSX : x coord of seminar
     * @param inSY : y coord of seminar
     * @param inID : id of seminar to delete
     */
    public void delete(int inSX, int inSY, int inID) {
        //first search for the coordinates 
        LinkedList<Seminar> found = search(inSX, inSY);
        //if not found stop the deletion
        if (found == null) {
            System.out.println("Error, deletion seminar not found");
            return;
        }
        //otherwise we can delete
        root = root.delete(0, inSX, inSY, 0, 0, worldSize, worldSize, inID);
    }
    
    /**
     * prints a binTree in preorder traversal
     */
    public void print() {
        root.print(0, 0, 0, worldSize, worldSize);
    }
    
    
//    /**
//     * helper that diagrams a binTree in the console
//     */
//    public void diagram() {
//        System.out.println();
//        diagramTraversal(root, 0);
//        System.out.println();
//    }
//    
//    /**
//     * helper that decides what type of node the binTree
//     * contains and prints it accordingly to console
//     * 
//     * @param inRoot : the root of the subtree to diagram
//     * @param depth : current depth of the node
//     */
//    private void diagramTraversal(BTNode inRoot, int depth) {
//        if (inRoot.type().equals("I")) {
//            // root is an internal node, so get children
//            BTInternalNode internalRoot = (BTInternalNode)inRoot;
//
//            // Print the right subtree
//            diagramTraversal(internalRoot.right(), depth + 1);
//
//            // Print the current node, indented by the depth
//            for (int i = 0; i < depth; i++) {
//                System.out.print("    ");
//            }
//            System.out.println("I");
//
//            // Print the left subtree
//            diagramTraversal(internalRoot.left(), depth + 1);
//        } 
//        // otherwise, inRoot is a leaf node or an empty node
//        else {
//            // Simply print the node type
//            for (int i = 0; i < depth; i++) {
//                System.out.print("    ");
//            }
//            //root is a leaf and we print the data within
//            if (inRoot.type().equals("L")) {
//                
//                BTLeafNode leafRoot = (BTLeafNode)inRoot;
//                LinkedList<Seminar> datList = leafRoot.data();
//                Seminar dat = datList.data();
//                System.out.println("(" + dat.x() 
//                    + ", " + dat.y() + ") " + datList.size());
//                
//            }
//            //root is empty and we simply print type
//            else {
//                System.out.println(inRoot.type());
//            }
//        }
//    }
}

