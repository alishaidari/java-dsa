import java.nio.ByteBuffer;

/**
 * Cache is a holder for records
 * this minimizes the requests 
 * and deliveries to BufferPool
 * 
 * @author Ali Haidari (alih)
 * @version 10.31.2023
 */
public class Cache {
    //record position
    private int recPos;
    //record of 4 bytes
    private byte[] rec;
    //byte buffer to obtain key
    private ByteBuffer cacheBuffer;
    
    /**
     * empty constructor for a cache,
     * initializes the position to negative
     * the record as empty and wraps the
     * record in a byte buffer
     */
    public Cache() {
        //no record position
        recPos = -1;
        //empty bytes
        rec = new byte[BufferPool.RECORD_SIZE];
        //wrap the bytes in a byte buffer
        cacheBuffer = ByteBuffer.wrap(rec);
    }
    
    /**
     * getter for the byte buffer in a cache
     * 
     * @return byte buffer wrapping record
     */
    public ByteBuffer getBB() {
        return cacheBuffer;
    }
    /**
     * getter for position in a cache
     * 
     * @return integer that is a record position
     */
    public int getPos() {
        return recPos;
    }
    
    /**
     * getter for a record in cache
     * 
     * @return byte array that is a record
     */
    public byte[] getRec() {
        return rec;
    }
    
    /**
     * setter for the record in a cache
     * 
     * @param inRec : record to be set
     */
    public void setRec(byte[] inRec) {
        rec = inRec;
    }
    
    /**
     * setter for the position in cache
     * 
     * @param inPos : position to be set
     */
    public void setPos(int inPos) {
        recPos = inPos;
    }
    
    /**
     * swaps the contents of two caches
     * including records and backing array
     * of byte buffer
     * 
     * @param swapCache : cache to be swapped with
     */
    public void swapCaches(Cache swapCache) {
        //hold onto pointers for records        
        byte[] prevThis = this.getBB().array();
        byte[] prevOther = swapCache.getBB().array();
        byte[] temp = prevOther.clone();
        
        swapCache.getBB().clear();
        this.getBB().clear();
        //set the byte buffers
        swapCache.getBB().put(prevThis);
        this.getBB().put(temp);
        
        //set the records
        swapCache.setRec(prevOther);
        this.setRec(prevThis);
    }
    
    /**
     * creates a string that shows the 
     * cache record position and record
     * 
     * @return string representation of a cache
     */
    public String toString() {
        String out = new String(getRec());
        return String.valueOf(getPos()) + out;
    }
}
