/**
 * Buffer is used to construct BufferPools
 * and holds an index along with a block
 * of 4069 bytes
 * 
 * @author Ali Haidari (alih)
 * @version 10.18.2023
 */
public class Buffer {
    private byte[] block;
    private boolean dirty;
    private int index;
    
    /**
     * constructor for a new Buffer
     * 
     * @param inIndex : index to be set for buffer
     */
    public Buffer(int inIndex) {
        block = new byte[BufferPool.BLOCK_SIZE];
        dirty = false;
        index = inIndex;
    }
    
    /**
     * getter for the block of bytes within buffer
     * 
     * @return byte array contained by buffer
     */
    public byte[] block() {
        return block;
    }
    
    
    /**
     * getter for the index of the buffer
     * 
     * @return integer of the buffer
     */
    public int index() {
        return index;
    }
    
    /**
     * setter for the index of the buffer
     * 
     * @param inIndex : index to be set
     */
    public void setIndex(int inIndex) {
        index = inIndex;
    }
    
    /**
     * getter to check if a buffer is dirty
     * 
     * @return true if dirty, false otherwise
     */
    public boolean isDirty() {
        return dirty;
    }
    
    /**
     * setter for the buffer to dirty
     */
    public void setDirty() {
        dirty = true;
    }
    
    /**
     * setter for the buffer to clean
     */
    public void setClean() {
        dirty = false;
    }
    
    /**
     * represents the buffer as the index 
     * in string format
     * 
     * @return string of the index
     */
    public String toString() {
        return String.valueOf(this.index());    
    }
}