/**
 * BST is a binary search tree class that
 * offers range searches and exact match searches
 * based off a key
 * 
 * @author Ali Haidari (alih)
 * @version 9.15.2023
 * @param <K> : generic comparable key
 * @param <V> : generic value
 */
public class BST<K extends Comparable<K>, V> {
    private BSTNode<K, V> root;
    private int nodeCount;
    private int vistedCount;

    /**
     * empty constructor for a BST
     */
    public BST() {
        root = null;
        nodeCount = 0;
        vistedCount = 0;
    }

    /**
     * retrieves the current root of the BST
     * 
     * @return BSTNode that is the root, null otherwise
     */
    public BSTNode<K, V> root() {
        return this.root;
    }

    /**
     * retrieves the current node count of the BST
     * 
     * @return integer that is the node count
     */
    public int size() {
        return this.nodeCount;
    }
    
    /**
     * retrieves the current visited node count of the BST
     * 
     * @return integer that is the visited node count
     */
    public int visited() {
        return this.vistedCount;
    }

    /**
     * inserts a node into the BST
     * 
     * @param inKey : key to be inserted into tree
     * @param inVal : value to be inserted into tree
     */
    public void insert(K inKey, V inVal) {
        root = insertTraversal(root, inKey, inVal);
        nodeCount++;
    }

    /**
     * removes a node from the BST, finds occurrence
     * matching key and removes that node
     * 
     * @param inKey : key of node to be removed
     * @return value of node removed, null otherwise
     */
    public V delete(K inKey) {
        //find the node with the key first 
        V potentialValue = search(inKey);
        //if the value is found then we remove node
        if (potentialValue != null) {
            root = deleteTraversal(root, inKey);
            nodeCount--;
        }
        return potentialValue;
    }
    

    /**
     * searches for a node in the BST with first
     * matching key
     * 
     * @param inKey : key of the node to be searched
     * @return value of node if found, null otherwise
     */
    public V search(K inKey) {
        //searches for nodes from root
        V toRet = null;
        BSTNode<K, V> temp = searchTraversal(root, inKey);
        //if node is found then we set the value
        if (temp != null) {
            toRet = temp.value();
        }
        return toRet;
    }
    
    /**
     * removes a node from the BST, removes the 
     * node with matching key and value pair
     * 
     * FIXME TEST (searchKVT and deleteKVT)
     * 
     * @param inKey : key of node to be deleted
     * @param inVal : value of node to be deleted
     * @return true if successful deletion, false otherwise
     */
    public boolean deleteKV(K inKey, V inVal) {
        boolean output = false;
        BSTNode<K, V> foundKV = searchKVTraversal(root, inKey, inVal);
        if (foundKV != null) {
            root = deleteKVTraversal(root, inKey, inVal);
            nodeCount--;
            output = true;
        }
        
        return output;
       
    }
    
    /**
     * searches for all the nodes are within the 
     * range of the passed in keys (inclusive)
     * 
     * @param inStart : key that indicates starting of range
     * @param inStop : key that indicates stopping of range
     * @return BST containing all the nodes within the range
     */
    public BST<K, V> rangeSearch(K inStart, K inStop) {

        //create an empty tree that will hold the range values
        BST<K, V> rangeTree = new BST<K, V>();
        
        //traverse within the range with the given range tree
        rangeTraversal(this.root, inStart, inStop, rangeTree);
        
        //pass the node count to the returned tree
        rangeTree.vistedCount = this.visited();
        //reset visit count for new range search 
        this.vistedCount = 0;

        return rangeTree;
    }
    
    
    /**
     * tree format of a BST prints out keys in reverse in order
     */
    public void tree() {
        //base case when there are no nodes
        if (root == null) {
            System.out.println("This tree is empty");
            return;
        }
        //otherwise traverse the in modified in order 
        else {
            treeTraversal(root, 0);
            System.out.println("Number of records: " + size());
        }
    }
    
    /**
     * traverses the BST and prints out the records in order
     */
    public void values() {
        //base case when there are no nodes
        if (root == null) {
            System.out.println("This tree is empty");
            return;
        }
        //traverse in order to print values
        else {
            valuesTraversal(root);
        }
    }
    
    //----------------methods for debugging----------------//
//    /**
//     * prints a diagram of current BST to console
//     */
//    public void diagram() {
//        System.out.println("");
//        if (root == null) {
//            return;
//        }
//        diagramTraversal(root, 0);
//        System.out.println("");
//    }
    
  //----------------helper methods----------------//
    /**
     * helper that modifies the current subtree 
     * to insert new values
     * 
     * @param inRoot : current BSTNode with subtree to insert to 
     * @param inKey : key for new BSTNode
     * @param inVal : value for new BSTNode
     * @return BSTNode that has been inserted
     */
    private BSTNode<K, V> insertTraversal(BSTNode<K, V> inRoot,
            K inKey, V inVal) {
        //if there no root then create one
        if (inRoot == null) {
            return new BSTNode<K, V>(inKey, inVal);
        }
        //if the root key is greater than input key then set left child
        if (inRoot.key().compareTo(inKey) >= 0) {
            inRoot.setLeft(insertTraversal(inRoot.left(), inKey, inVal));
        }
        //otherwise the root key is less than input key then set right child
        else {
            inRoot.setRight(insertTraversal(inRoot.right(), inKey, inVal));
        }
        return inRoot;
    }

    /**
     * helper that searches the current subtree for 
     * a BSTNode to delete
     * 
     * @param inRoot : current BSTNode with subtree to delete from
     * @param inKey : key to search for deletion node
     * @return BSTNode to be deleted, null if not found
     */
    private BSTNode<K, V> deleteTraversal(BSTNode<K, V> inRoot, K inKey) {
        //base case if root is null and key is not found
        if (inRoot == null) {
            return null;
        }
        //if the root is greater than key then traverse to left child
        if (inRoot.key().compareTo(inKey) > 0) {
            inRoot.setLeft(deleteTraversal(inRoot.left(), inKey));
        }
        //if root is less than key then traverse to right child
        else if (inRoot.key().compareTo(inKey) < 0) {
            inRoot.setRight(deleteTraversal(inRoot.right(), inKey));
        }
        //when key is found
        else {
            //if left child does not exist get the right
            if (inRoot.left() == null) {
                return inRoot.right();
            }
            //if right child does not exist get the left
            else if (inRoot.right() == null) {
                return inRoot.left();
            }
            //if it is has children then we find the max of left subtree 
            else {
                BSTNode<K, V> currMax = getMax(inRoot.left());
                inRoot.setValue(currMax.value());
                inRoot.setKey(currMax.key());
                inRoot.setLeft(deleteMax(inRoot.left()));
            }
        }
        return inRoot;
    }
    
    /**
     * helper that searches the current subtree for a
     * BSTNode with a matching a key
     * 
     * @param inRoot : current BSTNode with subtree to search in
     * @param inKey : key to search for matching node
     * @return BSTNode with the matching key, null otherwise
     */
    private BSTNode<K, V> searchTraversal(BSTNode<K, V> inRoot, K inKey) {
        //base case if root is null and key is not found
        if (inRoot == null) {
            return null;
        }
        //if root is greater than key we traverse left 
        if (inRoot.key().compareTo(inKey) > 0) {
            return searchTraversal(inRoot.left(), inKey);
        }
        //if root is the key then we return the root
        else if (inRoot.key().compareTo(inKey) == 0) {
            return inRoot;
        }
        //otherwise the root is less than key and we traverse right
        else {
            return searchTraversal(inRoot.right(), inKey);
        }
    }
    
    /**
     * helper that searches the current subtree for a
     * BSTNode with a matching a key and value
     * 
     * @param inRoot : current BSTNode with subtree to search in
     * @param inKey : key to search for matching node
     * @param inVal : value to search for matching node
     * @return BSTNode with the matching key and value, null otherwise
     */
    private BSTNode<K, V> searchKVTraversal(BSTNode<K, V> inRoot, 
        K inKey, V inVal) {
        if (inRoot == null) {
            return null;
        }
        //if root is greater than key we traverse left 
        if (inRoot.key().compareTo(inKey) > 0) {
            return searchKVTraversal(inRoot.left(), inKey, inVal);
        }
        //if root is less than key then we traverse right
        else if (inRoot.key().compareTo(inKey) < 0) {
            return searchKVTraversal(inRoot.right(), inKey, inVal);
        }
        //the key is found 
        else {
            //return root if the values match
            if (inRoot.value() == inVal) {
                return inRoot;
            }
            //keep traversing left until a dupe matches value
            return searchKVTraversal(inRoot.left(), inKey, inVal);
        }
    }
    
    /**
     * helper that searches the current subtree for 
     * a BSTNode to delete only if the value and key match
     * 
     * @param inRoot : current BSTNode with subtree to delete from
     * @param inKey : key of node to be deleted
     * @param inVal : value of node to be deleted
     * @return modified subtree when possible, null for no root
     */
    private BSTNode<K, V> deleteKVTraversal(BSTNode<K, V> inRoot, 
        K inKey, V inVal) {
        //base case when root is null
        if (inRoot == null) {
            return null;
        }
        //if root is greater than key we traverse left 
        if (inRoot.key().compareTo(inKey) > 0) {
            inRoot.setLeft(deleteKVTraversal(inRoot.left(), inKey, inVal));
        }
        //if root is less than key then we traverse right
        else if (inRoot.key().compareTo(inKey) < 0) {
            inRoot.setRight(deleteKVTraversal(inRoot.right(), inKey, inVal));
        }
        //the key is found 
        else {
            //if values match we can delete the node
            if (inRoot.value() == inVal) {
                //right child case
                if (inRoot.left() == null) {
                    return inRoot.right();
                }
                //left child case
                else if (inRoot.right() == null) {
                    return inRoot.left();
                }
                //two children case
                else {
                    BSTNode<K, V> temp = getMax(inRoot.left());
                    inRoot.setValue(temp.value());
                    inRoot.setKey(temp.key());
                    inRoot.setLeft(deleteMax(inRoot.left()));
                }
            }
            //keep traversing left until a matched value
            else {
                inRoot.setLeft(deleteKVTraversal(inRoot.left(), inKey, inVal));
            }
        }
        return inRoot;
    }
    
    /**
     * helper that searches the current subtree for 
     * BSTNodes within a key range, if node is found it is
     * added to inserted to a BST recursively
     * 
     * @param inRoot : current BSTNode with subtree to search in
     * @param inStart : start key to search for within range node
     * @param inStop : stop key to search for within range node
     * @param rangeTree : BST to insert nodes that fall in range
     * @return BST that contains the nodes within key range
     */
    private void rangeTraversal(BSTNode<K, V> inRoot, 
            K inStart, K inStop, BST<K, V> inTree) {
        //increase the visit count for each new traversal call
        this.vistedCount++;
        if (inRoot == null) {
            return;
        }

        //if current node is less than start key then we traverse right
        if (inRoot.key().compareTo(inStart) < 0) {
            rangeTraversal(inRoot.right(), inStart, inStop, inTree);
        }
        //if current node is greater than end key we traverse left
        else if (inRoot.key().compareTo(inStop) > 0) {
            rangeTraversal(inRoot.left(), inStart, inStop, inTree);
        }
        else {
            //otherwise it is within range, and we insert it into the tree
            inTree.insert(inRoot.key(), inRoot.value());
            rangeTraversal(inRoot.left(), inStart, inStop, inTree);
            rangeTraversal(inRoot.right(), inStart, inStop, inTree);
        }
    }
    

    /**
     * prints the spacing and values for each BSTNode in a BST
     * in reverse in order format
     * 
     * @param inRoot : the BSTNode with a subtree to traverse in
     * @param depth : the current depth of the BST
     */
    private void treeTraversal(BSTNode<K, V> inRoot, int depth) {
        //base case if root is null
        if (inRoot == null) {
            //print null with correct depth
            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }
            System.out.println(inRoot + "");
            return;
        }

        //move to right
        treeTraversal(inRoot.right(), depth + 1);
        //print the root with correct depth
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(inRoot.key());
        //move to left
        treeTraversal(inRoot.left(), depth + 1);
    }
    
    /**
     * helper that traverses a BST in order 
     * and prints out values
     * 
     * @param inRoot : BSTNode of the current subtree to traverse
     */
    private void valuesTraversal(BSTNode<K, V> inRoot) {
        //base case where root is null
        if (inRoot == null) {
            return;
        }
        //move to left root 
        valuesTraversal(inRoot.left());
        //print out value
        System.out.println(inRoot.value());
        //move to right root
        valuesTraversal(inRoot.right());
    }


    /**
     * helper that continues to traverse the BST
     * to the right to find the maximum node
     * 
     * @param inRoot : BSTNode with subtree to traverse right
     * @return BSTNode with the highest key value
     */
    private BSTNode<K, V> getMax(BSTNode<K, V> inRoot) {
        if (inRoot.right() == null) {
            return inRoot;
        }
        return getMax(inRoot.right());
    }

    /**
     * helper that finds the the maximum of the current
     * subtree and deletes it
     * 
     * @param inRoot : BSTNode with subtree to traverse right
     * @return BSTNode of the subtree with deleted maximum
     */
    private BSTNode<K, V> deleteMax(BSTNode<K, V> inRoot) {
        //if no right child exists then we move to left
        if (inRoot.right() == null) {
            return inRoot.left();
        }
        //otherwise we set the right child to new subtree
        inRoot.setRight(deleteMax(inRoot.right()));
        return inRoot;
    }
    
    /**
     * helper function that traverses and prints BST nodes
     * 
     * adapted from (aleom) and (anurug agarwal) on stackoverflow
     * 
     * @param inRoot : BSTNode with subtree to diagram
     * @param depth : depth of the current subtree
     */
//    private void diagramTraversal(BSTNode<K, V> inRoot, int depth) {
//        if (inRoot == null) {
//            return;
//        }
//        //move to right to print the right subtree
//        diagramTraversal(inRoot.right(), depth + 1);
//        //adjust for depth if its not the root node
//        if (depth != 0) {
//            for (int i = 0; i < depth - 1; i++) {
//                System.out.print("|\t");
//            }
//            System.out.println("|-------" + inRoot.key()
//            + "("+ inRoot.value() + ")");
//        }
//        //otherwise print the root node
//        else {
//            System.out.println(inRoot.key() + "("+ inRoot.value() + ")");
//        }
//        //move left to print the left subtree
//        diagramTraversal(inRoot.left(), depth + 1);
//    }

}
