import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * BufferPool holds buffers
 * and mitigates reads and writes
 * to disk file for optimization
 * 
 * @author Ali Haidari (alih)
 * @version 10.31. 2023
 */
public class BufferPool {
    /**
     * each block will hold size capacity bytes
     */
    public static final int BLOCK_SIZE = 4096;
    /**
     * each record will be record length bytes
     */
    public static final int RECORD_SIZE = 4;
    //input file
    private RandomAccessFile disk;
    //LRU queue of buffers
    private DLQueue<Buffer> pool;
    //maximum size of queue
    private int maxBuffers;
    //runtime stats
    private int hits;
    private int reads;
    private int writes;
    
    /**
     * constructor for a BufferPool
     * that accepts the file and max buffers
     * 
     * @param inFile : RAF that will be sorted upon
     * @param inNumBuffers : number of buffers allowed
     */
    public BufferPool(RandomAccessFile inFile, int inNumBuffers) {
        disk = inFile;
        pool = new DLQueue<Buffer>();
        maxBuffers = inNumBuffers;
        hits = 0;
        reads = 0;
        writes = 0;
    }
    
    /**
     * getter for the DLQueue within a BufferPool
     * 
     * @return DLQueue that holds buffers
     */
    public DLQueue<Buffer> getPool() {
        return pool;
    }
    
    /**
     * getter for the maximum amount of buffers
     * 
     * @return integer of the max buffers allowed
     */
    public int maxBuffers() {
        return maxBuffers;
    }
    
    /**
     * getter for the RAF within a BufferPool
     * 
     * @return RAF that will be read from and write to
     */
    public RandomAccessFile getRAF() {
        return disk;
    }
    
    /**
     * copy entire block of source into the position in disk
     * 
     * @param source : source block of bytes to copy into disk
     * @param pos : starting position in disk to copy to
     * @throws IOException 
     */
    public void giveToDisk(byte[] source, int pos) throws IOException {
        //seek to position to write
        disk.seek(pos);
        //write into position the entire block of space
        disk.write(source);
        writes++;
    }
    
    /**
     * copy the entire block of bytes starting at position in disk
     * into space
     * 
     * @param dest : destination block of bytes to copy from disk
     * @param pos : starting position of bytes in disk 
     * @throws IOException 
     */
    public void getFromDisk(byte[] dest, int pos) throws IOException {
        //calculate which buffer index it is
        int buffIdx = pos / BLOCK_SIZE;
        //use the buffer index to determine start point of block
        int blockIdx = BLOCK_SIZE * buffIdx;
        //seek to the start point of block
        disk.seek(blockIdx);
        //read the entire block from disk into space
        disk.read(dest);
        reads++;
    }
    
    /**
     * get the buffer that is the most recently used 
     * within the LRU queue
     * 
     * @return Buffer if one exists, null otherwise
     */
    public Buffer getMRU() {
        if (pool.getTail().element() == null) {
            return null;
        }
        return pool.getTail().element();
    }
    
    /**
     * will search and delete a buffer in the LRU queue
     * based on an index of the buffer
     * 
     * @param pos : index of buffer to search for
     * 
     * @return Buffer if found and deleted, null otherwise
     */
    public Buffer deleteBuffer(int pos) {
        Buffer deleted = null;
        //case when there are no buffers to search for
        if (pool.size() == 0) {
            return deleted;
        }
        //set pointers to current and end of search
        DLQNode<Buffer> curr = pool.getHead().next();
        DLQNode<Buffer> end = pool.getTail().next();
        
        //search until we find the buffer
        while (curr != end) {
            if (curr.element().index() == pos) {
                //found the buffer and break
                deleted = curr.element();
                break;
            }
            curr = curr.next();
        }
        //if buffer is found then we delete update pointers
        if (deleted != null) {
            DLQNode<Buffer> prev = curr.prev();
            DLQNode<Buffer> next = curr.next();
            
            prev.setNext(next);
            next.setPrev(prev);
            //decrement the size
            pool.setSize(pool.size() - 1);
            //increment the hits
            hits++;
        }
        return deleted;
    }
    
    /**
     * read size bytes from position in pool (zero-indexed)
     * to the destination. LRU eviction policy
     * 
     * @param dest : destination that the MRU will copy to
     * @param pos : starting position of bytes from disk
     * @param size : size of bytes to copy from MRU
     * 
     * @throws IOException 
     */
    public void request(byte[] dest, int pos, int size) throws IOException {
//        //check if request position is within disk bytes
//        if (pos > getRAF().length()) {
//            System.out.println("Error, bufferpool tried to"
//                + " request out of index");
//            return;
//        }
//        //check if destination length can copy request
//        if (dest.length < size) {
//            System.out.println("Error, bufferpool"
//                + "tried to read out of bounds");
//            return;
//        }
        //request buffer is already MRU we read and break
        if (getMRU() != null && pos / BLOCK_SIZE == getMRU().index()) {
            System.arraycopy(getMRU().block(), 
                pos % BLOCK_SIZE, dest, 0, size);
            hits++;
            return;
        }
        //otherwise find the buffer or read from disk
        else {
            //try to find and delete buffer in pool
            Buffer reqBuffer = deleteBuffer(pos / BLOCK_SIZE);
            //case when buffer is found in pool
            if (reqBuffer != null) {
                //make found buffer the MRU
                pool.enqueue(reqBuffer);
                System.arraycopy(getMRU().block(), 
                    pos % BLOCK_SIZE, dest, 0, size);
            }
            //case when buffer is not found in pool
            else {
                //check if we create new buffer or reuse
                if (pool.size() < maxBuffers) {
                    Buffer newBuffer = new Buffer(pos / BLOCK_SIZE);
                    getFromDisk(newBuffer.block(), pos);
                    pool.enqueue(newBuffer);
                    System.arraycopy(getMRU().block(), 
                        pos % BLOCK_SIZE, dest, 0, size);
                }
                //otherwise we dequeue and reuse the LRU buffer
                else {
                    //remove LRU from head
                    Buffer lru = pool.dequeue();
                    //if LRU is dirty then we write buffer to disk
                    if (lru.isDirty()) {
                        flushBuffer(lru);
                        lru.setClean();
                    }
                    //update bytes in LRU to request
                    getFromDisk(lru.block(), pos);
                    //update index
                    lru.setIndex(pos / BLOCK_SIZE);
                    //reuse dequeued buffer for next MRU
                    pool.enqueue(lru);
                    System.arraycopy(getMRU().block(), 
                        pos % BLOCK_SIZE, dest, 0, size);
                }
            }
        }  
    }
    
    /**
     * write size bytes from source (zero indexed)
     * to a buffer starting at the destination position
     * 
     * @param source : source of bytes to write from
     * @param pos : starting position of destination
     * @param size : number of bytes to write
     * @throws IOException
     */
    public void deliver(byte[] source, int pos, int size) throws IOException {
        //check if deliver position is within disk bytes
//        if (pos > getRAF().length()) {
//            System.out.println("Error, bufferpool tried to"
//                + " deliver out of index");
//            return;
//        }
//        //check if source length can copy deliver
//        if (source.length < size) {
//            System.out.println("Error, bufferpool"
//                + "tried to write out of bounds");
//            return;
//        }
        //deliver buffer is already at MRU we write and break
        if (getMRU() != null && pos / BLOCK_SIZE == getMRU().index()) {
            hits++;
            System.arraycopy(source, 0, 
                getMRU().block(), pos % BLOCK_SIZE, size);
            //make dirty after copy
            getMRU().setDirty();
            return;
        }
        //otherwise find the buffer or read from disk
        else {
            //try to find and delete buffer in pool
            Buffer delBuffer = deleteBuffer(pos / BLOCK_SIZE);
            //case when buffer is found in pool
            if (delBuffer != null) {
                //make found buffer the MRU
                pool.enqueue(delBuffer);
                System.arraycopy(source, 0, 
                    getMRU().block(), pos % BLOCK_SIZE, size);
                //make dirty after copy
                getMRU().setDirty();
            }
            //case when buffer is not found in pool
            else {
                //check if we create new buffer or reuse
                if (pool.size() < maxBuffers) {
                    Buffer newBuffer = new Buffer(pos / BLOCK_SIZE);
                    getFromDisk(newBuffer.block(), pos);
                    pool.enqueue(newBuffer);
                    System.arraycopy(source, 0,
                        getMRU().block(), pos % BLOCK_SIZE, size);
                    //make dirty after copy
                    getMRU().setDirty();
                }
                //otherwise we dequeue and reuse the LRU buffer
                else {
                    //remove LRU from head
                    Buffer lru = pool.dequeue();
                    //if LRU is dirty then we write buffer to disk
                    if (lru.isDirty()) {
                        flushBuffer(lru);
                        lru.setClean();
                    }
                    //update bytes in LRU to request
                    getFromDisk(lru.block(), pos);
                    //update index
                    lru.setIndex(pos / BLOCK_SIZE);
                    //reuse dequeued buffer for next MRU
                    pool.enqueue(lru);
                    System.arraycopy(source, 0,
                        getMRU().block(), pos % BLOCK_SIZE, size);
                    //make dirty after copy
                    getMRU().setDirty();
                }
            } 
        }
    }
    
    /**
     * writes a dirty buffer back to disk based on index
     * 
     * @param inBuffer : buffer with block to be written to disk
     * @throws IOException 
     */
    public void flushBuffer(Buffer inBuffer) throws IOException {
        //check if buffer is within bounds
//        if (inBuffer.index() * BLOCK_SIZE > getRAF().length()) {
//            System.out.println("Error, flushed buffer" 
//                + " tried to write out of index");
//            return;
//        }
        //write buffer back to disk
        giveToDisk(inBuffer.block(), 
            inBuffer.index() * BLOCK_SIZE);
    }
    
    /**
     * writes out all the dirty buffers currently 
     * in a pool to disk
     * 
     * @throws IOException 
     */
    public void flushPool() throws IOException {
        //case when there is no buffers 
        if (pool.size() == 0) {
            System.out.println("Error, can't empty flush pool");
            return;
        }
        //set pointers to current and end of search
        DLQNode<Buffer> curr = pool.getHead().next();
        DLQNode<Buffer> end = pool.getTail().next();
        
        //flush all dirty buffers in queue
        while (curr != end) {
            if (curr.element().isDirty()) {
                flushBuffer(curr.element());
            }
            curr = curr.next();
        }
    }
    
    /**
     * prints out a buffer pool to console
     * 
     * @throws IOException 
     */
    public void print() throws IOException {
        System.out.println("");
        pool.print();
        DLQNode<Buffer> curr = getPool().getHead().next();
        while (curr != getPool().getTail().next()) {
            if (curr.element() != null) {
                String out = new String(curr.element().block());
                System.out.println("dirty[" + curr.element().isDirty() 
                    + "]\t" +  curr.element().index() + ": " + out);
            }
            curr = curr.next();
        }
        System.out.println("MRU: " + getMRU());
        System.out.println("");
    }
    
    /**
     * generates string representation of runtime statistics
     * 
     * @return string containing reads, writes, hits
     */
    public String stats() {
        StringBuilder out = new StringBuilder();
        out.append("cache hits: " + hits + "\n");
        out.append("disk reads: " + reads + "\n");
        out.append("disk writes: " + writes + "\n");
        return out.toString();
    }
}
