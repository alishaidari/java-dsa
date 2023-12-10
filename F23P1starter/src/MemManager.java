import java.lang.Math;
/**
 * handles the memory managment and free block list
 * for the seminar processor 
 *
 * @author Ali Haidari (alih)
 * @version 9.11.2023
 */
public class MemManager {

    //TODO CHECK PUBLIC ACCESBILITY MODIFIERS FOR METHODS
    private int poolSize;
    private LinkedList<FreeBlock> freeBlockList;
    private byte[] memPool;
    
    /**
     * constructor for creation of a memory manager
     * @param initPool : the size of the memory pool in bytes
     */
    public MemManager(int initPool) {
        this.poolSize = initPool;
        this.memPool = new byte[poolSize];
        this.freeBlockList = new LinkedList<FreeBlock>();
        //whole pool is one block that is free
        FreeBlock emptyMem = new FreeBlock(initPool);
        emptyMem.posList().insert(0);
        freeBlockList.insert(emptyMem);
    }
    
    /**
     * retrieves the poolSize of MemManager
     * @return poolSize as an integer
     */
    public int getPoolSize() {
        return this.poolSize;
    }
    
    /**
     * retrieves the memPool of MemManager
     * @return byte[] of memPool
     */
    public byte[] getMemPool() {
        return this.memPool;
    }

    /**
     * insert a record and return its position handle
     * 
     * @param record : contains the record to be inserted
     * @return handle that points to record
     */
    public Handle insert(byte[] record) {
        FreeBlock blockLocation = findInsertionBlock(record.length);
        int insertPos = blockLocation.posList().data();
        removeFreeBlock(blockLocation.blockSize(), insertPos);
        
        for (int i = insertPos, j = 0; j < record.length; i++, j++) {
            this.memPool[i] = record[j];
        }
   
        Handle recHandle = new Handle(insertPos, record.length);
        
        return recHandle;
    }
    
    /**
     * gets the record associated with a handle in memPool
     * 
     * @param theHandle : theHandle pointing to the record
     * @return the byte[] that contains the record from handle
     */
    public byte[] get(Handle theHandle) {
        
        int memPos = theHandle.pos();
        int recLength = theHandle.length();
        byte[] record = new byte[recLength];
        
        for (int i = memPos, j = 0; j < recLength; i++, j++) {
            record[j] = memPool[i];
        }
        return record;
    }

    /**
     * free a block at the handle
     * add that block to the free block list
     * merges the adjacent free blocks 
     * @param theHandle : pointer to the record 
     */
    public void remove(Handle theHandle) {
        int removePos = theHandle.pos();
        int recLength = theHandle.length();
        int freedSize = findBlockSize(theHandle.length());
        
        for (int i = removePos, j = 0; j < recLength; i++, j++) {
            memPool[i] = 0;
        }
        addFreeBlock(freedSize, removePos);
        merge();
    }

    /**
     * prints free block list of the MemManager
     */
    public void dump() {
        System.out.println("Freeblock list: ");
        LinkedList<FreeBlock> externalList = freeBlockList;
        if (externalList.isEmpty()) {
            System.out.println("There are no freeblocks in the memory pool");
            return;
        }
        int oldIndex = externalList.currIndex();
        externalList.moveTo(0);
        while (!externalList.isAtEnd()) {
            System.out.println(externalList.data().toString());
            externalList.next();
        }
        externalList.moveTo(oldIndex);
    }

    /*------------------ free block list methods ------------------*/
    
    /**
     * retrieves the freeBlockList in memManager
     * @return linked list of free blocks
     */
    public LinkedList<FreeBlock> getFreeBlockList() {
        return this.freeBlockList;
    }

    /**
     * finds the correct block size given a memory request
     * 
     * @param reqSize : the size of the request
     * @return the size of the block
     */
    public int findBlockSize(int reqSize) {
        if (reqSize <= 0) {
            return 0;
        }
        int i = 0;
        while (Math.pow(2, i) < reqSize) {
            i++;
        }
        return (int)Math.pow(2, i);
    }

    /**
     * retrieves the target block in freeBlockList
     * 
     * @param targetBlockSize : the target block 
     * @return free block if found, null otherwise
     */
    public FreeBlock findBlock(int targetBlockSize) {
        FreeBlock toRet = null;
        LinkedList<FreeBlock> externalList = freeBlockList;
        int oldIndex = externalList.currIndex();
        while (!externalList.isAtEnd()) {
            if (externalList.data().blockSize() == targetBlockSize) {
                toRet = externalList.data();
            }
            externalList.next();
        }
        externalList.moveTo(oldIndex);
        return toRet;
    }

    /**
     * adds a freeBlock in the freeBlockList
     * 
     * @param inBlockSize : block size to add to free block list
     * @param inMemStart : start position of free memory at blockSize
     */
    public void addFreeBlock(int inBlockSize, int inMemStart) {
        boolean foundBlock = false;
        int oldIndex = freeBlockList.currIndex();
        freeBlockList.moveTo(0);
        while (!freeBlockList.isAtEnd()) {
            //case for when blockSize already exists in freeBlockList
            if (freeBlockList.data().blockSize() == inBlockSize) {
                freeBlockList.data().posList().append(inMemStart);
                foundBlock = true;
            }
            freeBlockList.next();
        }
        freeBlockList.moveTo(oldIndex);

        //case for when blockSize does not exist
        if (!foundBlock) {
            FreeBlock newBlock = new FreeBlock(inBlockSize);
            newBlock.posList().append(inMemStart);
            freeBlockList.append(newBlock);
            foundBlock = true;
        }
    }

    /**
     * removes a freeBlock in the freeBlockList if block 
     * and mem pos exists
     * 
     * @param inBlockSize : block size to remove to free block list
     * @param inMemStart : start position of free memory at blockSize
     */
    public void removeFreeBlock(int inBlockSize, int inMemStart) {
        //case for when blockSize already exists in freeBlockList
        int oldIndex = freeBlockList.currIndex();
        freeBlockList.moveTo(0);
        while (!freeBlockList.isAtEnd()) {
            if (freeBlockList.data().blockSize() == inBlockSize) {
                freeBlockList.data().posList().moveToElem(inMemStart);
                //check if the memory position is found
                if (freeBlockList.data().posList().data() == inMemStart) {
                    freeBlockList.data().posList().remove();
                    freeBlockList.data().posList().moveTo(0);
                }
                //if the free block has no more free positions, remove it 
                if (freeBlockList.data().posList().isEmpty()) {
                    freeBlockList.remove();
                }
            }
            freeBlockList.next();
        }
        freeBlockList.moveTo(oldIndex);
    }

    /**
     * sorts the external freeBlockList in place
     * in ascending order
     * 
     * also sorts the internal posList per each
     * block in ascending order
     */
    public void sortExternalList() {
        LinkedList<FreeBlock> externalList = freeBlockList;
        LinkedList<FreeBlock> sortedExternal = 
            new LinkedList<FreeBlock>();
        int oldIndex = externalList.currIndex();
        //remove from externalList every time min is found
        while (externalList.size() > 0) {
            Integer min = Integer.MAX_VALUE;
            externalList.moveTo(0);
            FreeBlock minBlock = null;
            //find the minimum for every blockSize
            externalList.moveTo(0);
            while (!externalList.isAtEnd()) {
                FreeBlock currBlock = externalList.data();
                //minimum size is replaced
                if (currBlock.blockSize() < min) {
                    min = currBlock.blockSize();
                    minBlock = currBlock;
                    //internal pos list for block is sorted
                    sortInternalList(minBlock);
                }
                externalList.next();
            }
            //remove smallest block from list
            externalList.moveToElem(minBlock);
            externalList.remove();
            //append to new list 
            sortedExternal.append(minBlock);
        }
        //reset to initial block position
        this.freeBlockList = sortedExternal;
        this.freeBlockList.moveTo(oldIndex);
    }

    /**
     * sorts a internal position list within a freeBlock
     * 
     * @param unsortedBlock : block with posList to be sorted
     */
    private void sortInternalList(FreeBlock unsortedBlock) {
        LinkedList<Integer> internalList = unsortedBlock.posList();
        LinkedList<Integer> sortedInternal = new LinkedList<Integer>();
        int oldIndex = internalList.currIndex();

        //terminated by original list size
        while (internalList.size() > 0) {
            Integer min = Integer.MAX_VALUE;
            internalList.moveTo(0);
            //find the minimum for every value in LList
            while (!internalList.isAtEnd()) {
                if (internalList.data() < min) {
                    min = internalList.data();
                }
                internalList.next();
            }
            internalList.moveToElem(min);
            //checks if the internal list moved to minimum element
            if (internalList.currIndex() == internalList.size()) {
                break;
            }
            //remove value from current list
            internalList.remove();
            //add value to sorted list
            sortedInternal.append(min);
        }
        //reset curr position to original
        sortedInternal.moveTo(oldIndex);
        //replace list within the freeBlock
        unsortedBlock.setPosList(sortedInternal);
    }

    /**
     * splits a freeBlock and adds the split blocks to
     * the in place freeBlockList accordingly
     * 
     * @param inBlock : is the block to be split
     * @param targetBlockSize : is the block size requested
     */
    public void splitBlock(FreeBlock inBlock, int targetBlockSize) {
        if (targetBlockSize <= 0) {
            System.out.println("Error, cannot split due to target size");
            return;
        }
        if (inBlock == null) {
            System.out.println("Error, cannot split due to block not found");
            return;
        }
        int currBlockSize = inBlock.blockSize();
        int currFreeLoc = inBlock.posList().data();
        //continue splitting until target block is reached
        while (currBlockSize != targetBlockSize) {
            removeFreeBlock(currBlockSize, currFreeLoc);
            currBlockSize = currBlockSize / 2;
            addFreeBlock(currBlockSize, currFreeLoc);
            addFreeBlock(currBlockSize, currFreeLoc + currBlockSize);
        }
        sortExternalList();
    }

    /**
     * merges all adjacent buddies in a freeBlockList in place
     */
    public void merge() {
        boolean mergeFlag = true;
        int blockIndex = 0;
        int oldIndex = freeBlockList.currIndex();
        while (!freeBlockList.isEmpty() && mergeFlag) {
            //sort list every time a new block indexed
            sortExternalList();
            freeBlockList.moveTo(blockIndex);
            FreeBlock currBlock = freeBlockList.data();
            LinkedList<Integer> currPosList = currBlock.posList();
            int posIndex = 0;
            //search internal lists for buddies
            while (currPosList.size() > 1 &&
                posIndex <= currPosList.size() - 1) {
                currPosList.moveTo(posIndex);
                int currBlockSize = currBlock.blockSize();
                int buddyOne = currPosList.data();
                currPosList.moveTo(posIndex + 1);
                //if buddy has a value
                if (currPosList.data() != null) {
                    int buddyTwo = currPosList.data();
                    int firstMask = buddyOne | currBlockSize;
                    int secondMask = buddyTwo | currBlockSize;
                    //check if buddy is merge-able
                    if (firstMask == secondMask) {
                        //add initial pos to merged block
                        addFreeBlock(currBlockSize * 2, buddyOne);
                        //remove positions from previous block
                        removeFreeBlock(currBlockSize, buddyTwo);
                        removeFreeBlock(currBlockSize, buddyOne);
                        //reset external list to search merges
                        blockIndex = -1;
                    }
                }
                posIndex++;
            }
            blockIndex++;
            //terminates the merging of blocks
            if (blockIndex == freeBlockList.size()) {
                mergeFlag = false;
            }
        }
        freeBlockList.moveTo(oldIndex);
    }
    
    /**
     * expands the memory pool and copies used bytes
     * into new expanded memoryPool, updates freeBlockList
     * to reflect new memory pool
     */
    public void expandMemory() {
        addFreeBlock(poolSize, poolSize);
        this.poolSize = poolSize * 2;
        byte[] newPool = new byte[poolSize];
        for (int i = 0; i < memPool.length; i++) {
            newPool[i] = memPool[i];
        }
        memPool = newPool;
        System.out.println("Memory pool expanded to " + poolSize
            + " bytes");
    }
    
    /**
     * finds a location to insert a record of a required size
     * if smallest insertion block is too large, then it is split
     * if there is no suitable block found then the pool is expanded
     * 
     * @param reqSize : the size of the record 
     * @return FreeBlock that is insertion
     */
    public FreeBlock findInsertionBlock(int reqSize) {
        int targetBlockSize = findBlockSize(reqSize);
        FreeBlock insertBlock = findBlock(targetBlockSize);
        FreeBlock smallestBlock = findSmallestBlock();
        FreeBlock biggestBlock = findBiggestBlock();
        FreeBlock toRet = null;
        
        //checks if target block already exists in freeBlockList
        if (insertBlock == null) {
            //case where we have to expand
            while (biggestBlock == null || 
                    biggestBlock.blockSize() < targetBlockSize) {
                expandMemory();
                biggestBlock = findBiggestBlock();
                smallestBlock = findSmallestBlock();
                toRet = biggestBlock;
            }
            
            //case where we have to split
            if (smallestBlock.blockSize() > targetBlockSize) {
                splitBlock(smallestBlock, targetBlockSize);
                smallestBlock = findSmallestBlock();
                toRet = smallestBlock;
            }
        }
        else {
            toRet = insertBlock;
        }
        
        return toRet;
    }
    
    /**
     * finds the smallest block in the current 
     * freeBlockList
     * @return smallest freeBlock, or null if none exist
     */
    public FreeBlock findSmallestBlock() {
        FreeBlock smallest = null;
        int oldIndex = freeBlockList.currIndex();
        int min = Integer.MAX_VALUE;
        while (!freeBlockList.isAtEnd()) {
            FreeBlock currBlock = freeBlockList.data();
            if (currBlock.blockSize() < min) {
                min = currBlock.blockSize();
                smallest = findBlock(min);
            }
            freeBlockList.next();
        }
        freeBlockList.moveTo(oldIndex);
        return smallest;
    }
    
    /**
     * finds the largest block in the current 
     * freeBlockList
     * @return largest freeBlock, or null if none exist
     */
    public FreeBlock findBiggestBlock() {
        FreeBlock biggest = null;
        int oldIndex = freeBlockList.currIndex();
        int max = Integer.MIN_VALUE;
        while (!freeBlockList.isAtEnd()) {
            FreeBlock currBlock = freeBlockList.data();
            if (currBlock.blockSize() > max) {
                max = currBlock.blockSize();
                biggest = findBlock(max);
            }
            freeBlockList.next();
        }
        freeBlockList.moveTo(oldIndex);
        return biggest;
    }
}

