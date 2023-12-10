import student.TestCase;
/**
 * test class for seminar processor and its methods
 * @author Ali Haidari (alih)
 * @version 9.4.2023
 */
public class SemProcessorTest extends TestCase {
    private SemProcessor testProc; 
    private HashTable<Integer, Handle> testTable;
    private Seminar mySem;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testProc = new SemProcessor(256, 4);
        testTable = new HashTable<Integer, Handle>(4);
        String[] keywords = {"Good", "Bad", "Ugly"};
        mySem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
    }
    
    /**
     * tests the initialization of a sem processor 
     */
    public void testInit() {
        assertNotNull(testProc.getHashTable());
        assertTrue(testProc.getHashTable().getClass()
            == testTable.getClass());
    }
    /**
     * tests the search method of a sem processor
     * @throws Exception 
     */
    public void testSemSearch() throws Exception {
        assertNull(testProc.semSearch(0));
        testProc.semInsert(1, mySem);
        assertNotNull(testProc.semSearch(1));
        
    }
    /**
     * tests the insert method of a sem processor
     * @throws Exception 
     */
    public void testSemInsert() throws Exception {
        assertTrue(testProc.semInsert(2, mySem));
        assertFalse(testProc.semInsert(2, mySem));
        assertTrue(testProc.semInsert(1, mySem));
        assertFalse(testProc.semInsert(1, mySem));
    }
    /**
     * tests the remove method of a sem processor
     * @throws Exception 
     */
    public void testSemRemove() throws Exception {
        assertFalse(testProc.semRemove(0));
        testProc.semInsert(1, mySem);
        assertTrue(testProc.semRemove(1));
        
    }
}
