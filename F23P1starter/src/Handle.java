/**
 * Handle is a pointer to the
 * record within MemManager
 * 
 * @author Ali Haidari (alih)
 * @version 8.24.2022
 */
public class Handle {
    
    private int pos;
    private int length;
    
    /**
     * constructor of a Handle with start position of
     * record in memory and length of record 
     * 
     * @param inPos : pos of the record in memPool
     * @param inLength : length of the record 
     */
    public Handle(int inPos, int inLength) {
        this.pos = inPos;
        this.length = inLength;
    }
    
    
    /**
     * retrieves location of a handle in memPool
     * @return the memory pool offset location
     */
    public int pos() {
        return this.pos;
    }


    /**
     * retrieves length of a record for handle in memPool
     * @return the length of the record
     */
    public int length() {
        return this.length;
    }
    
    /**
     * returns a string representation of a handle 
     * @return string form of a Handle
     */
    public String toString() {
        return "(" + this.pos + ", " + this.length + ")";
    }
}
