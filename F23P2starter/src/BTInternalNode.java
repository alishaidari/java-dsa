/**
 * internal node for a BinTree
 * implemented from interface
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTInternalNode implements BTNode {
    //internal nodes only store two children
    private BTNode left;
    private BTNode right;
    //'I' for internal
    private String nodeType = "I";

    /**
     * empty constructor for an internal
     * node sets the children to empty
     * leaves initially
     */
    public BTInternalNode() {
        left = BTEmptyNode.EMPTY;
        right = BTEmptyNode.EMPTY;
    }

    /**
     * constructor for an internal
     * node that sets the children 
     * BTNodes (either internal,
     * leaf, or empty leaf)
     * 
     * @param inLeft : the BTNode to be set as left child
     * @param inRight : the BTNode to be set as right child
     */
    public BTInternalNode(BTNode inLeft, BTNode inRight) {
        left = inLeft;
        right = inRight;
    }

    /**
     * getter that will always "I"
     * 
     * @return string of the node type
     */
    @Override
    public String type() {
        return nodeType;
    }

    /**
     * getter for left child of internal
     * 
     * @return BTNode that leaf, empty, or internal
     */
    public BTNode left() {
        return left;
    }

    /**
     * getter for right child of internal
     * 
     * @return BTNode that is leaf, empty, or internal
     */
    public BTNode right() {
        return right;
    }

    /**
     * setter for left child of internal
     * 
     * @param inLeft : BTNode to be set as left child
     */
    public void setLeft(BTNode inLeft) {
        left = inLeft;
    }

    /**
     * setter for right child of internal
     * 
     * @param inRight : BTNode to be set as right child
     */
    public void setRight(BTNode inRight) {
        right = inRight;
    }

    /**
     * internal node insert will pass the seminar
     * data to the correct child for the leaf to
     * insert it
     */
    @Override
    public BTNode insert(int level, int inX, int inY, 
        int inBX, int inBY, Seminar inSem) {

        //even level means we split by x
        if (level % 2 == 0) {
            int xMid = inX + inBX / 2;
            int xSplit = inBX / 2;
            /**
             * if the seminar's x coord is less than the midpoint x
             * coord of the current node, then we traverse left to insert
             */
            if (inSem.x() < xMid) {
                this.setLeft(this.left().insert(++level, inX, inY, 
                    xSplit, inBY, inSem));
            }
            /**
             * otherwise, traverse to right subtree to insert
             */
            else {
                this.setRight(this.right().insert(++level, 
                    xMid, inY, xSplit, inBY, inSem));
            }
        }
        //odd level means we split by y
        else {
            int yMid = inY + inBY / 2;
            int ySplit = inBY / 2;
            /**
             * if the seminar's y coord is less than the midpoint y
             * coord of the current node, then we traverse left to insert
             */
            if (inSem.y() < yMid) {
                this.setLeft(this.left().insert(++level, inX, inY, 
                    inBX, ySplit, inSem));
            }
            /**
             * otherwise, traverse to right subtree to insert
             */
            else {
                this.setRight(this.right().insert(++level, 
                    inX, yMid, inBX, ySplit, inSem));
            }
        }
        return this;
    }

    /**
     * internal node search will traverse the
     * children until the bounding box with leaf
     * is found
     */
    @Override
    public BTNode search(int level, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY) {
        //even level means we split by x
        if (level % 2 == 0) {
            int xMid = inX + inBX / 2;
            int xSplit = inBX / 2;
            /**
             * if the x coord of seminar to search for 
             * is less than midpoint x coord of current node
             * we search left
             */
            if (inSX < xMid) {
                return this.left().search(++level, inSX, inSY, 
                    inX, inY, xSplit, inBY);
            }
            /**
             * otherwise we search right subtree
             */
            else {
                return this.right().search(++level, inSX, inSY,
                    xMid, inY, xSplit, inBY);
            }

        }
        //odd level means we split by y
        else {
            int yMid = inY + inBY / 2;
            int ySplit = inBY / 2;
            /**
             * if the y coord of seminar to search for 
             * is less than midpoint y coord of current node
             * we search left
             */
            if (inSY < yMid) {
                return this.left().search(++level, inSX, inSY, 
                    inX, inY, inBX, ySplit);
            }
            /**
             * otherwise, we search right subtree
             */
            else {
                return this.right().search(++level, inSX, inSY,
                    inX, yMid, inBX, ySplit);
            }
        }
    }

    /**
     * internal node delete will traverse the children
     * until the bounding box with leaf is found, if there
     * it will also merge internals that only contain one
     * leaf and one empty node to preserve BinTree
     */
    @Override
    public BTNode delete(int level, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY, int inID) {
        
        //even level means we split by x
        if (level % 2 == 0) {
            int xMid = inX + inBX / 2;
            int xSplit = inBX / 2;
            /**
             * if the seminar's x coord is less than the midpoint x
             * coord of the current node, then we traverse left to remove
             */
            if (inSX < xMid) {
                this.setLeft(this.left().delete(++level, inSX, inSY,
                    inX, inY, xSplit, inBY, inID));
            }
            /**
             * otherwise, traverse to the right subtree to remove
             */
            else {
                this.setRight(this.right().delete(++level, inSX, inSY,
                    xMid, inY, xSplit, inBY, inID));
            }
        }
        //odd level means we split by y
        else {
            int yMid = inY + inBY / 2;
            int ySplit = inBY / 2;
            /**
             * if the seminar's y coord is less than the midpoint y
             * coordinate of the current node, then we traverse left to remove
             */
            if (inSY < yMid) {
                this.setLeft(this.left().delete(++level, inSX, inSY, 
                    inX, inY, inBX, ySplit, inID));
            }
            /**
             * otherwise, traverse to the right subtree to remove
             */
            else {
                this.setRight(this.right().delete(++level, inSX, inSY,
                    inX, yMid, inBX, ySplit, inID));
            }
        }
        
        //merge left child to root when the right is empty
        if (this.right() instanceof BTEmptyNode 
            && this.left() instanceof BTLeafNode) {
            return this.left();
        }
        
        //merge right child to root when the left is empty
        if (this.left() instanceof BTEmptyNode 
            && this.right() instanceof BTLeafNode) {
            return this.right();
        }
        
        return this;
    }

    /**
     * radius search for internal node 
     * find the bounding box for the search
     * circle and if it intersects then we
     * visit it
     */
    @Override
    public int radiusSearch(int level, int radius, 
        LinkedList<Seminar> found, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY) {
        //TODO
        //keep track of node count
        int visitCount = 1;
        // calculate the bounding box of the node
//        int nodeSX = inX - radius;
//        int nodeSY = inY - radius;
//        int nodeBX = inBX + 2 * radius + 1;
//        int nodeBY = inBY + 2 * radius + 1;
        return 0;
    }

    /**
     * 
     * internal node print, traverses the children
     * of the internal node in pre order and prints
     * 
     */
    @Override
    public void print(int level, int inX, int inY, 
        int inBX, int inBY) {
        //calculate the spaces based on level
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < level; i++) {
            padding.append("  ");
        }
        //print the root internal with the spacing 
        System.out.println(padding + this.type());
        
        /**
         * preorder traversal visiting left and 
         * right children depending on the discriminator
         */

        //even split by x coord, call left then right
        if (level % 2 == 0) {
            int xMid = inX + inBX / 2;
            int xSplit = inBX / 2;
            
            this.left().print(level + 1, inX, inY,
                xSplit, inBY);
            this.right().print(level + 1, 
                xMid, inY, xSplit, inBY);
        }
        //odd split by y coord, call left then right
        else {
            int yMid = inY + inBY / 2;
            int ySplit = inBY / 2;
            this.left().print(level + 1, inX, inY,
                inBX, ySplit);
            this.right().print(level + 1,
                inX, yMid, inBX, ySplit);
        }
    }
}
