/**
 * leaf node for BinTree 
 * implemented from interface
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTLeafNode implements BTNode {
    //leaf nodes store only list of seminar data 
    private LinkedList<Seminar> data;
    //'L' for leaf
    private String nodeType = "L";

    /**
     * superclass constructor 
     * required for creating the
     * empty leaf subclass and flyweight
     */
    public BTLeafNode() {
        //set data to null for empty node
        data = null;
    }
    
    /**
     * constructor for a leaf node that
     * holds in a data object 
     * 
     * @param inSem : data contained
     */
    public BTLeafNode(Seminar inSem) {
        data = new LinkedList<Seminar>();
        data.append(inSem);
    }
    
    /**
     * getter for the data of a leaf node
     * 
     * @return linked list with all data
     */
    public LinkedList<Seminar> data() {
        return data;
    }

    /**
     * getter will always return "L"
     * 
     * @return string of the node type
     */
    @Override
    public String type() {
        return nodeType;
    }

    /**
     * leaf node insert the seminar depending
     * on internal, leaf, or empty node
     */
    @Override
    public BTNode insert(int level, int inX, int inY, 
        int inBX, int inBY, Seminar inSem) {
        //base case if node is empty
        if (this instanceof BTEmptyNode) {
            return new BTLeafNode(inSem);
        }
        //check if the current leaf node allows a duplicate
        else if (this.data().data().x() == inSem.x() &&
                this.data().data().y() == inSem.y()) {
            //append the new data to the leaf and return
            this.data().append(inSem);
            return this;
        }
        //otherwise we split the leaf into an internal node
        else {
            //create a base node that initially points to leaf
            BTNode currNode = this;
            //hold onto leaf data
            BTLeafNode savedLeaf = (BTLeafNode)currNode;
            LinkedList<Seminar> savedList = savedLeaf.data();
            //change base node to point to an internal
            currNode = new BTInternalNode();
            
            //get all data items to reinsert
            while (!savedList.isAtEnd()) {
                Seminar curr = savedList.data();
                //insert saved leaf into internal node
                currNode = currNode.insert(level, inX, inY, 
                    inBX, inBY, curr); 
                savedList.next();
            }
            //reset data position
            savedList.moveTo(0);
            
            //insert requested seminar into internal node
            currNode = currNode.insert(level, inX, inY, 
                inBX, inBY, inSem);
            return currNode;
        }
    }

    /**
     * search returns either an empty node or leaf 
     * with data found
     */
    @Override
    public BTNode search(int level, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY) {
        //if the coordinates found are empty we return null
        if (this instanceof BTEmptyNode) {
            return null;
        }
        //otherwise we return the search result leaf
        if (this.data().data().x() != inSX 
            || this.data().data().y() != inSY) {
            return null;
        }
        return this;
    }
    
    /**
     * leaf node delete will remove the correct seminar
     * from a leaf or replace it with an empty node when
     * there is only one seminar within the leaf
     */
    @Override
    public BTNode delete(int level, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY, int inID) {

        //leaf with duplicates, we must remove correct seminar
        if (this.data().size() > 1) {
            LinkedList<Seminar> copyData = this.data();
            int semIndex = 0;
            boolean found = false;
            //find the seminar with the id of interest
            while (!copyData.isAtEnd()) {
                //break when we find
                if (copyData.data().id() == inID) {
                    found = true;
                    break;
                }
                semIndex++;
                copyData.next();
            }

            if (found) {
                //move to seminar
                this.data().moveTo(semIndex);
                //remove it
                this.data().remove();
                //reset the list position
                this.data().moveTo(0);
            }
            else {
                this.data().moveTo(0);
                System.out.println("Error, deletion ID not" 
                    + " found within duplicates");
            }
            //return the node after removal
            return this;
        }
        //otherwise we replace the leaf with an empty node
        else if (this.data().data().id() != inID) {
            System.out.println("Error, deletion ID not found");
            return this;
        }
        else {
            return BTEmptyNode.EMPTY;
        }
    }

    /**
     * radius search for leaf will check the point
     * within the leaf against the circle and add
     * it to the found seminar list
     */
    @Override
    public int radiusSearch(int level, int radius, 
        LinkedList<Seminar> found, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY) {
        // TODO 
        return 0;
    }
    /**
     * leaf node print, prints out leaf object
     * or empty leaf object
     */
    @Override
    public void print(int level, int inX, int inY, 
        int inBX, int inBY) {
        //calculate the spaces based on level
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < level; i++) {
            padding.append("  ");
        }
        //if its an empty node print it
        if (this instanceof BTEmptyNode) {
            System.out.println(padding + this.type());
        }
        //otherwise we handle the leaf print
        else {
            StringBuilder semIDs = new StringBuilder();
            BTLeafNode leaf = (BTLeafNode)this;
            //gather all the IDs within the leaf
            while (!leaf.data().isAtEnd()) {
                semIDs.append(" ");
                semIDs.append(leaf.data().data().id());
                leaf.data().next();
            }
            //reset the list position within leaf
            leaf.data().moveTo(0);
            //print out leaf with size and IDs
            System.out.println(padding + "Leaf with " 
                + leaf.data().size() + " objects:" + semIDs);
        }
    }
}
