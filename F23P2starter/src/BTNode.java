/**
 * interface for BinTree node and 
 * its subclasses; internal, leaf, 
 * and empty node
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023

 */
public interface BTNode {
    /**
     * getter for the node type
     * 
     * @return string representing node
     */
    public String type();
    
    /**
     * insert a seminar into appropriate node
     * @param level : level of the node 
     * @param inX : x coord of bounding box
     * @param inY : y coord of bounding box
     * @param inBX : x width of bounding box
     * @param inBY : y height of bounding box
     * @param inSem : seminar to insert
     * @return internal, leaf, or empty 
     */
    public BTNode insert(int level, int inX, int inY,
        int inBX, int inBY, Seminar inSem);
    

    /**
     * search for coordinate within appropriate node
     * 
     * @param level : level of the node 
     * @param inSX : x coord of search 
     * @param inSY : y coord of search
     * @param inX : x coord of bounding box
     * @param inY : y coord of bounding box
     * @param inBX : x width of bounding box
     * @param inBY : y height of bounding box
     * @return internal, leaf, or empty
     */
    public BTNode search(int level, int inSX, int inSY,
        int inX, int inY, int inBX, int inBY);
    
    /**
     * delete seminar from appropriate node 
     * 
     * @param level : level of the node 
     * @param inSX : x coord of search 
     * @param inSY : y coord of search
     * @param inX : x coord of bounding box
     * @param inY : y coord of bounding box
     * @param inBX : x width of bounding box
     * @param inBY : y height of bounding box
     * @param inID : id of seminar to delete
     * @return internal, leaf, or empty
     */
    public BTNode delete(int level, int inSX, int inSY,
        int inX, int inY, int inBX, int inBY, int inID);
    

    /**
     * search a radius from within appropriate node
     * 
     * @param level : level of the node
     * @param radius : radius of search circle
     * @param found : list of seminars within range
     * @param inSX : x coord of search 
     * @param inSY : y coord of search
     * @param inX : x coord of bounding box
     * @param inY : y coord of bounding box
     * @param inBX : x width of bounding box
     * @param inBY : y height of bounding box
     * @return visited nodes of internal, leaf, or empty
     */
    
    public int radiusSearch(int level, int radius, 
        LinkedList<Seminar> found, int inSX, int inSY, 
        int inX, int inY, int inBX, int inBY);
    
    /**
     * print the appropriate node to console
     * 
     * @param level : level of the node
     * @param inX : x coord of bounding box
     * @param inY : y coord of bounding box
     * @param inBX : x width of bounding box
     * @param inBY : y height of bounding box
     */
    public void print(int level, int inX, int inY, 
        int inBX, int inBY);
}
