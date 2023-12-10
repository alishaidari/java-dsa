import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Test the MemManager class and its methods
 *
 *  @author Ali Haidari (alih)
 *  @version 9.8.2023
 */
public class MemManagerTest extends TestCase {
    private MemManager memTest;
    private MemManager empMem;
    private Seminar mySem;

    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        memTest = new MemManager(256);
        empMem = new MemManager(0);
        String[] keywords = {"Good", "Bad", "Ugly"};
        mySem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
    }
    
    /**
     * tests the initialization of a MemManager object
     */
    public void testInit() {
        
        assertNotNull(memTest);
        assertEquals(256, memTest.getPoolSize());
        assertEquals(memTest.getFreeBlockList().size(), 1);
        assertFalse(memTest.getFreeBlockList().isEmpty());
    }
    
    /**
     * tests the empty free block list output method of a MemManager
     */
    public void testEmptyDump() {
        assertFalse(memTest.getFreeBlockList().isEmpty());
        memTest.removeFreeBlock(256, 0);
        assertTrue(memTest.getFreeBlockList().isEmpty());
        memTest.dump();
        String output = systemOut().getHistory();
        String expected = "Freeblock list: \n" 
            + "There are no freeblocks in the memory pool";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests the free block list output method of a MemManager
     */
    
    public void testDump() {
        assertEquals(memTest.getFreeBlockList().currIndex(), 0);
        memTest.splitBlock(memTest.findBlock(256), 64);
        memTest.dump();
        String output = systemOut().getHistory();
        String expected = "Freeblock list: \n" 
            + "64: 0 64 \n"
            + "128: 128";
        assertFuzzyEquals(expected, output);
        assertEquals(memTest.getFreeBlockList().currIndex(), 0);
    }
    
    /**
     * tests finding the correct block size for a memory request
     */
    public void testFindBlockSize() {
        assertEquals(0, memTest.findBlockSize(0));
        assertEquals(4, memTest.findBlockSize(3));
        assertEquals(128, memTest.findBlockSize(100));
        assertEquals(0, memTest.findBlockSize(-12));
    }
    
    /**
     * tests if the correct block is found by MemManager
     */
    public void testFindBlock() {
        FreeBlock firstFree = memTest.getFreeBlockList().data();
        assertEquals(firstFree, memTest.findBlock(256));
        assertNull(memTest.findBlock(32));
    }
    
    /**
     * tests if the correct freeBlock is added to 
     * freeBlockList by MemManager
     */
    public void testAddFreeBlock() {
        assertEquals(memTest.getFreeBlockList().currIndex(), 0);
        assertEquals(1, memTest.findBlock(256).posList().size());
        memTest.addFreeBlock(256, 10);
        assertEquals(memTest.getFreeBlockList().currIndex(), 0);
        assertEquals(2, memTest.findBlock(256).posList().size());
        memTest.addFreeBlock(128, 30);
        assertEquals(2, memTest.getFreeBlockList().size());
        assertEquals(1, memTest.findBlock(128).posList().size());
    }
    
    /**
     * tests if the correct freeBlock is removed from 
     * freeBlockList by MemManager
     */
    public void testRemoveFreeBlock() {
        memTest.splitBlock(memTest.findBlock(256), 64);
        assertEquals(1, memTest.findBlock(128).posList().size());
        assertEquals(2, memTest.getFreeBlockList().size());
        memTest.removeFreeBlock(128, 16);
        assertEquals(128, memTest.findBlock(128).blockSize());
        assertEquals(2, memTest.getFreeBlockList().size());
        assertEquals(1, memTest.findBlock(128).posList().size());
        assertTrue(128 == memTest.findBlock(128).posList().data());
        memTest.removeFreeBlock(128, 128);
        assertNull(memTest.findBlock(128));
        assertEquals(1, memTest.getFreeBlockList().size());
    }
    
    /**
     * tests if smallest block is found by MemManager
     */
    public void testSmallestBlock() {
        FreeBlock smallest = memTest.findBlock(256);
        assertEquals(smallest, memTest.findSmallestBlock());
        memTest.removeFreeBlock(256, 0);
        assertNull(memTest.findSmallestBlock());
        memTest.addFreeBlock(256, 0);
        memTest.splitBlock(memTest.findBlock(256), 2);
        smallest = memTest.findBlock(2);
        assertEquals(smallest, memTest.findSmallestBlock());
    }
    
    /**
     * tests if the biggest block is found by MemManager
     */
    public void testBiggestBlock() {
        FreeBlock biggest = memTest.findBlock(256);
        assertEquals(biggest, memTest.findBiggestBlock());
        memTest.removeFreeBlock(256, 0);
        assertNull(memTest.findBiggestBlock());
        memTest.addFreeBlock(256, 0);
        memTest.splitBlock(memTest.findBlock(256), 2);
        biggest = memTest.findBlock(128);
        memTest.expandMemory();
        biggest = memTest.findBlock(256);
        assertEquals(biggest, memTest.findBiggestBlock());
    }
    
    /**
     * tests if external (and internal posLists)
     * of freeBlockList is sorted in ascending
     * order correctly by MemManager
     */
    public void testExternalSort() {
        //testing for sorting on an empty
        memTest.removeFreeBlock(256, 0);
        assertTrue(memTest.getFreeBlockList().isEmpty());
        memTest.sortExternalList();
        assertTrue(memTest.getFreeBlockList().isEmpty());
        assertNull(memTest.getFreeBlockList().data());
        assertTrue(memTest.getFreeBlockList().isEmpty());
        //manually split blocks out of order 
        memTest.addFreeBlock(128, 128);
        memTest.addFreeBlock(64, 64);
        memTest.addFreeBlock(64, 0);
        //check each value is not in ascending order
        memTest.getFreeBlockList().moveTo(0);
        FreeBlock blockOne = memTest.getFreeBlockList().data();
        memTest.getFreeBlockList().moveTo(1);
        FreeBlock blockTwo = memTest.getFreeBlockList().data();
        blockTwo.posList().moveTo(0);
        int firstPos = blockTwo.posList().data();
        blockTwo.posList().moveTo(1);
        int secondPos = blockTwo.posList().data();
        blockTwo.posList().moveTo(0);
        assertFalse(firstPos < secondPos);
        assertFalse(blockOne.blockSize() < blockTwo.blockSize());
        memTest.getFreeBlockList().moveTo(0);
        //call sort to fix block locations
        memTest.sortExternalList();
        //check each value is in ascending order
        memTest.getFreeBlockList().moveTo(0);
        blockOne = memTest.getFreeBlockList().data();
        blockOne.posList().moveTo(0);
        firstPos = blockTwo.posList().data();
        blockOne.posList().moveTo(1);
        secondPos = blockTwo.posList().data();
        blockOne.posList().moveTo(0);
        memTest.getFreeBlockList().moveTo(1);
        blockTwo = memTest.getFreeBlockList().data();
        memTest.getFreeBlockList().moveTo(0);
        assertTrue(firstPos < secondPos);
        assertTrue(blockOne.blockSize() < blockTwo.blockSize());
    }
    
    /**
     * tests if the MemManager splits block for a null block
     */
    public void testNullSplitBlock() {
        assertEquals(1, memTest.getFreeBlockList().size());
        memTest.splitBlock(memTest.findBlock(26), 2);
        String output = systemOut().getHistory();
        String expected = "Error, cannot split due to block not found";
        assertFuzzyEquals(expected, output);
        assertEquals(1, memTest.getFreeBlockList().size());
    }
    
    /**
     * tests if the MemManager splits block for a invalid size
     */
    public void testSizeSplitBlock() {
        assertEquals(1, memTest.getFreeBlockList().size());
        memTest.splitBlock(memTest.findBlock(256), 0);
        String output = systemOut().getHistory();
        String expected = "Error, cannot split due to target size";
        assertFuzzyEquals(expected, output);
        assertEquals(1, memTest.getFreeBlockList().size());
    }
    
    /**
     * tests if the MemManager splits block correctly
     */
    public void testSplitBlock() {
        assertEquals(1, memTest.getFreeBlockList().size());
        int target = 16;
        memTest.splitBlock(memTest.findBlock(256), target);
        assertEquals(4, memTest.getFreeBlockList().size());
        assertNotNull(memTest.findBlock(target));
        assertEquals(target, memTest.findBlock(target).blockSize());
        assertTrue(memTest.findBlock(target).posList().data() 
            < memTest.findBlock(target * 2).posList().data());
        
    }
    /**
     * tests if the MemManager merges free blocks correctly
     * @throws Exception 
     */
    public void testMerge() throws Exception {
        memTest.splitBlock(memTest.findBlock(256), 2);
        assertEquals(7, memTest.getFreeBlockList().size());
        memTest.merge();
        assertEquals(1, memTest.getFreeBlockList().size());
        memTest.removeFreeBlock(256, 0);
        memTest.merge();
        assertEquals(0, memTest.getFreeBlockList().size());
        memTest.addFreeBlock(256, 0);
        memTest.insert("hello".getBytes());
        assertEquals(5, memTest.getFreeBlockList().size());
        memTest.merge();
        assertEquals(5, memTest.getFreeBlockList().size());
    }
    
    /**
     * tests if the memory pool is doubled
     * correctly by MemManager
     */
    public void testExpandPool() {
        assertEquals(256, memTest.getPoolSize());
        memTest.expandMemory();
        assertEquals(512, memTest.getPoolSize());
        assertEquals(512, memTest.getMemPool().length);
        assertEquals(0, empMem.getPoolSize());
        empMem.expandMemory();
        assertEquals(0, empMem.getPoolSize());
        assertEquals(0, empMem.getMemPool().length);
    }
    
    /**
     * tests if correct insertion block is found 
     * by MemManager
     */
    public void testFindInsertionBlock() {
        FreeBlock expected = new FreeBlock(16);
        LinkedList<Integer> expPosList = new LinkedList<Integer>();
        expPosList.append(0);
        expPosList.append(16);
        expected.setPosList(expPosList);
        assertFuzzyEquals(expected.toString(), 
            memTest.findInsertionBlock(12).toString());
        assertEquals(256, memTest.getPoolSize());
        assertEquals(memTest.findInsertionBlock(300), memTest.findBlock(512));
        assertEquals(1024, memTest.getPoolSize());
        assertEquals(memTest.findInsertionBlock(2), memTest.findBlock(2));
        assertEquals(memTest.findBlock(16), memTest.findInsertionBlock(15));
    }
    
    /**
     * test if a record is inserted correctly by MemManager
     * @throws Exception 
     */
    public void testInsert() throws Exception {
        byte[] rec = mySem.serialize();
        assertNull(memTest.findBlock(128));
        assertFuzzyEquals("(0, 95)", memTest.insert(rec).toString());
        assertNotNull(memTest.findBlock(128));
        memTest.insert(rec);
        memTest.dump();
        String output = systemOut().getHistory();
        String expected = "Freeblock list: \n" + 
            "There are no freeblocks in the memory pool";
        assertFuzzyEquals(expected, output);
        assertEquals(256, memTest.getPoolSize());
        memTest.insert(rec);
        assertEquals(512, memTest.getPoolSize());     
    }
    
    /**
     * tests if a record is retrieved correctly by MemManager
     * @throws Exception
     */
    public void testGet() throws Exception {
        Handle firstHand = memTest.insert(mySem.serialize());
        Handle secondHand = memTest.insert(mySem.serialize());
        Handle thirdHand = memTest.insert(mySem.serialize());
        
        byte[] firstRec = memTest.get(firstHand);
        byte[] secondRec = memTest.get(secondHand);
        byte[] thirdRec = memTest.get(thirdHand);
        assertEquals(mySem.toString(), 
            Seminar.deserialize(firstRec).toString());
        assertEquals(mySem.toString(), 
            Seminar.deserialize(secondRec).toString());
        assertEquals(mySem.toString(), 
            Seminar.deserialize(thirdRec).toString());
    }
    
    /**
     * tests if a record is removed correctly by
     * MemManager
     * @throws Exception
     */
    public void testRemove() throws Exception {
        Handle firstHand = memTest.insert(mySem.serialize());
        Handle secondHand = memTest.insert(mySem.serialize());
        Handle thirdHand = memTest.insert(mySem.serialize());
        assertEquals(1, memTest.findBlock(128).posList().size());
        memTest.remove(secondHand);
        assertEquals(2, memTest.findBlock(128).posList().size());
        assertNull(memTest.findBlock(256));
        memTest.remove(firstHand);
        assertNotNull(memTest.findBlock(256));
        assertNull(memTest.findBlock(512));
        memTest.remove(thirdHand);
        assertNotNull(memTest.findBlock(512));
    }
}
