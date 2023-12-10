import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * sort uses a virtual array mediated
 * by a buffer pool to complete quick
 * sort on a random access file
 * 
 * @author Ali Haidari (alih)
 * @version 10.26.2023
 */
public class Sort {
    //main buffer pool
    private BufferPool mainPool;

    //caches that hold records for quick sort
    private Cache pivotCache; //pivot record
    private Cache rightCache; //left record
    private Cache leftCache; //right record
    private Cache kCache; //k record
    
    //constant total record number
    private final int totalRecords;
    //constant threshold for insertion sort
    private static final int INSERTION_THRESHOLD = 9;

    /**
     * constructor of the sort object, it requires
     * a random access file of length that is multiple
     * of 4096
     * 
     * @param inFile : RAF that is the disk file to be sorted
     * @param numBuffers : maximum amount of buffers for a bufferPool
     * @throws IOException
     */
    public Sort(RandomAccessFile inFile, int numBuffers) throws IOException {
        //buffer pool is called
        mainPool = new BufferPool(inFile, numBuffers);
        totalRecords = (int)(inFile.length() / BufferPool.RECORD_SIZE);
        
        //caches are all initialized to empty
        pivotCache = new Cache();
        leftCache = new Cache();
        rightCache = new Cache();
        kCache = new Cache();
    }

    /**
     * getter for the total amount of records
     * within the RAF (will be immutable)
     * 
     * @return integer record count
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * getter for the buffer pool object 
     * 
     * @return BufferPool within sort
     */
    public BufferPool getBP() {
        return mainPool;
    }

    /**
     * finds the pivot index of two records
     * 
     * @param i : left record
     * @param j : right record
     * @return integer pivot index 
     */
    public int findPivot(int i, int j) {
        return (i + j) >> 1;
    }

    /**
     * performs insertion sort on a virtual array
     * 
     * @param i : start position record
     * @param j : end position record
     * @throws IOException 
     */
    public void insertion(int i, int j) throws IOException {
        for (int m = i + 1; m < j + 1; m++) {
            for (int n = m; (n > 0) 
                && (getKey(n, leftCache) 
                    < getKey(n - 1, leftCache)); n--) {
                swap(n, n - 1, leftCache, rightCache);
            }
        }
    }
    
    /**
     * performs a search on a virtual array to check
     * if all elements are the same
     * 
     * @param i : start record position
     * @param j : end record position
     * @return true if array is the same, false otherwise
     * @throws IOException
     */
    public boolean dupeCheck(int i, int j) throws IOException {
        for (int k = i + 1; k < j; k++) {
            if (getKey(i, leftCache) != getKey(k, rightCache)) {
                return false;
            }
        }
        return true;
    }

    /**
     * performs quicksort on the virtual array
     * switching to insertion sort below threshold
     * reads and writes are mitigated by bufferpool 
     * 
     * @param i : record start position
     * @param j : record end position
     * @throws IOException 
     */
    public void quick(int i, int j) throws IOException {
        //if array small enough use insertion
        if (j - i <= INSERTION_THRESHOLD) {
            insertion(i, j);
            return;
        }
        //if array is already sorted then we return
        if (dupeCheck(i , j)) {
            return;
        }
        int pivotIdx = findPivot(i, j);
        swap(pivotIdx, j, kCache, pivotCache);
        //previous pivot holds j, previous j holds pivot
        int k = partition(i, j - 1, j);
        //put pivot in final place (k holds partition, j holds pivot)
        swap(k, j, rightCache, pivotCache);
        //check if left sub-array can be sorted
        if ((k - i) > 1) { 
             // sort left partition
            quick(i, k - 1);
        }  
        //check if right sub-array can be sorted
        if ((j - k) > 1) { 
            // sort right partition
            quick(k + 1, j); 
        }  
    }
    
    /**
     * partitions the virtual array between values
     * less than pivot and greater than or equal to pivot
     * 
     * @param left : left record position
     * @param right : right record position
     * @param pivot : pivot record position
     * @return integer record position of end of left sub-array 
     * @throws IOException 
     */
    public int partition(int left, int right, int pivot) throws IOException {
        short pivKey = getKey(pivot, pivotCache);
        // move bounds inward until they meet
        while (left <= right ) {
            if (left - right == 1) {
                break;
            }
            while (getKey(left, leftCache) < pivKey) {
                //push left boundary
                left++; 
            }
            while ((right >= left) && 
                (getKey(right, rightCache) >=  pivKey)) { 
                //pull right boundary
                right--; 
            }
            //swap out-of-place values
            if (right > left) {  
                swap(left, right, leftCache, rightCache);
            } 
        }

        //return the first pos of right partition
        return left;
    }

    /**
     * swaps the position of two records in virtual array
     * updates the caches and the buffer pool
     * 
     * @param i : left record position to be swapped
     * @param j : right record position to be swapped
     * @param iCache : cache to search in for left record
     * @param jCache : cache to search in for right record
     * @throws IOException 
     */
    public void swap(int i, int j, 
        Cache iCache, Cache jCache) throws IOException {
        //calculate the byte positions of the records
        int startByteI = i * BufferPool.RECORD_SIZE;
        int startByteJ = j * BufferPool.RECORD_SIZE;
        byte[] tempI = null;
        byte[] tempJ = null;

        //if we find i rec in cache, hold onto it
        if (iCache.getPos() == i) {
            tempI = iCache.getRec();
        }
        //otherwise we must request for i record
        else {
            mainPool.request(iCache.getRec(), 
                startByteI, BufferPool.RECORD_SIZE);
            iCache.setPos(i);
        }
        //if we find j rec in cache, hold onto it
        if (jCache.getPos() == j) {
            tempJ = jCache.getRec();
        }
        //otherwise we must request for j record
        else {
            mainPool.request(jCache.getRec(), 
                startByteJ, BufferPool.RECORD_SIZE);
            jCache.setPos(j);
        }
        //deliver i to j
        if (tempI != null) {
            mainPool.deliver(tempI, startByteJ, 
                BufferPool.RECORD_SIZE);
        }
        else {
            mainPool.deliver(iCache.getRec(), 
                startByteJ, BufferPool.RECORD_SIZE);
        }

        //deliver j to i
        if (tempJ != null) {
            mainPool.deliver(tempJ, startByteI,
                BufferPool.RECORD_SIZE);
        }
        else {
            mainPool.deliver(jCache.getRec(), 
                startByteI, BufferPool.RECORD_SIZE);
        }
        //swap caches after swapping in buffer
        iCache.swapCaches(jCache);
    }

    /**
     * retrieve the key from a record for virtual array 
     * through bufferpool request
     * 
     * @param pos : position of the record in disk
     * @param inCache : cache to be searched in
     * @return key as a short for the record at position
     * @throws IOException 
     */
    public short getKey(int pos, Cache inCache) throws IOException {
        short toRet = -1;
        //if the cache already holds the record we use the cache
        if (inCache.getPos() == pos) {
            toRet = inCache.getBB().getShort(0);
        }
        //if cache does not hold what we need we overwrite it
        else {
            mainPool.request(inCache.getRec(), 
                pos * BufferPool.RECORD_SIZE, 
                BufferPool.RECORD_SIZE);
            inCache.setPos(pos);
            toRet = inCache.getBB().getShort(0);
        }
        return toRet;
    }
    
    /**
     * main call to the sort, flushes the pool
     * after last call and prints out runtime stats
     * 
     * @throws IOException
     */
    public void sortFile() throws IOException {
        //sort file
        long start = System.currentTimeMillis();
        quick(0, getTotalRecords() - 1);
        //flush the remaining dirty buffers
        getBP().flushPool();
        long end = System.currentTimeMillis();
        //get runtime
        long runtime = end - start;
        //print statistics
        System.out.println("runtime : " + runtime);
        System.out.println(getBP().stats());
    }
}
