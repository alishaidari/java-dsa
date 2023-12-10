/**
 * empty node for BinTree
 * implemented from interface
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTEmptyNode extends BTLeafNode {
    //'E' for empty
    private String nodeType = "E";
    /**
     * flyweight empty leaf node 
     */
    public static final BTEmptyNode EMPTY = new BTEmptyNode();

    /**
     * constructor for the BTEmptyNode
     * it will use the super class for the 
     * empty constructor in BTLeafNode
     */
    public BTEmptyNode() {
        //intentionally blank 
    }
    
    /**
     * getter that will always "E"
     * 
     * @return string of the node type
     */
    public String type() {
        return nodeType;
    }
}
