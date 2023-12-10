/**
 * TODO
 * 
 * @author Ali Haidari (alih)
 * @version 9.15.2023
 * @param <K> : the generic key 
 * @param <V> : the generic value 
 */
public class BSTNode<K, V> {
    private K key;
    private V value;
    private BSTNode<K, V> left;
    private BSTNode<K, V> right;
    
    /**
     * empty constructor for BSTNode
     */
    public BSTNode() {
        this.left = null;
        this.right = null;
    }
    
    /**
     * constructor for BSTNode with intial values
     * 
     * @param inKey : key to bet set for BSTNode
     * @param inVal : value to be set for BSTNode
     */
    public BSTNode(K inKey, V inVal) { 
        this.key = inKey;
        this.value = inVal;
        this.left = null;
        this.right = null;
    }
    
    /**
     * constructor for a BSTNode with key, value 
     * and right and left children
     * 
     * @param inKey : key for node
     * @param inVal : value for node
     * @param inLeft : pointer to left child node
     * @param inRight : pointer to right child node
     */
    public BSTNode(K inKey, V inVal, 
        BSTNode<K, V> inLeft, BSTNode<K, V> inRight) {
        this.key = inKey;
        this.value = inVal;
        this.left = inLeft;
        this.right = inRight;
    }
    
    /**
     * getter for the key in BSTNode
     * 
     * @return key of generic type
     */
    public K key() {
        return this.key;
    }
    
    /**
     * getter for the value in BSTNode
     * 
     * @return value of generic type
     */
    public V value() {
        return this.value;
    }
    
    /**
     * getter for the left child of BSTNode
     * 
     * @return BSTNode if it exists, null otherwise
     */
    public BSTNode<K, V> left() {
        return this.left;
    }
    
    /**
     * getter for the right child of BSTNode
     * 
     * @return BSTNode if it exists, null otherwise
     */
    public BSTNode<K, V> right() {
        return this.right;
    }
    
    /**
     * setter for the key of BSTNode
     * 
     * @param inKey : key to be set in BSTNode
     */
    public void setKey(K inKey) {
        this.key = inKey;
    }
    
    /**
     * setter for the value of BSTNode
     * 
     * @param inValue : value to be set in BSTNode
     */
    public void setValue(V inValue) {
        this.value = inValue;
    }
    
    /**
     * setter for the left child of a BSTNode
     * 
     * @param inLeft : BSTNode to be set as left child 
     */
    public void setLeft(BSTNode<K, V> inLeft) {
        this.left = inLeft;
    }
    
    /**
     * setter for the right child of a BSTNode
     * 
     * @param inRight : BSTNode to be set as right child 
     */
    public void setRight(BSTNode<K, V> inRight) {
        this.right = inRight;
    }
    
    /**
     * checks if the current BSTNode is a leaf node
     * @return true if leaf, false otherwise
     */
    public boolean isLeaf() {
        return (left() == null && right() == null);
    }
    
    /**
     * creates the KV string form of the BSTNode 
     * 
     * @return the string form of BSTNode 
     * or 'null' if either key or value is null
     */
    public String toKVString() {
        String retKey;
        String retVal;
        if (key() == null) {
            retKey = "null";
        }
        else {
            retKey = key.toString();
        }

        if (value() == null) {
            retVal = "null";
        }
        else {
            retVal = value.toString();
        }
        
        return "(" + retKey + ", " + retVal + ")";
    }
    
//    /**
//     * creates an in order representation of a BSTNode
//     * 
//     * @return the string form of the BSTNode in order
//     */
//    public String inOrderString() {
//        StringBuilder builder = new StringBuilder();
//        if (this.isLeaf()) {
//            return this.value() + "";
//        }
//        if (left != null) {
//            builder.append(left.inOrderString() + ", ");
//        }
//        builder.append(value.toString());
//        if (right != null) {
//            builder.append(", " + right.inOrderString());
//        }
//        return builder.toString();
//    }
}
