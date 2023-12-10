
/**
 * FreeBlock is a class that stores the 
 * block size and respective memory positions
 * of a empty block within MemManager
 * 
 * @author Ali Haidari (alih)
 * @version 9.3.2023
 */
public class FreeBlock {
    private int blockSize;
    private LinkedList<Integer> posList;
    
    /**
     * constructor for a freeBlock 
     * 
     * @param inSize : size of the freeBlock
     */
    public FreeBlock(int inSize) {
        this.blockSize = inSize;
        this.posList = new LinkedList<Integer>();
    }
    
    /**
     * retrieves the block size of a FreeBlock 
     * 
     * @return block size of freeBlock
     */
    public int blockSize() {
        return this.blockSize;
    }
    
    /**
     * retrieves the position list of a FreeBlock
     * 
     * @return LList of all starting byte positions for free block
     */
    public LinkedList<Integer> posList() {
        return this.posList;
    }
    
    /**
     * sets the position list of a FreeBlock
     * 
     * @param newPos : the new position LList of integers
     */
    public void setPosList(LinkedList<Integer> newPos) {
        this.posList = newPos;
    }
    
    /**
     * creates a string representation of a FreeBlock
     * 
     * @return string representation of a free block 
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        String blockSizeString = String.valueOf(blockSize());
        out.append(blockSizeString);
        out.append(": ");
        LinkedList<Integer> internalList = posList();
        int oldIndex = internalList.currIndex();
        internalList.moveTo(0);
        while (!internalList.isAtEnd()) {
            out.append(internalList.data());
            out.append(" ");
            internalList.next();
        }
        internalList.moveTo(oldIndex);
        return out.toString();
    }
}
