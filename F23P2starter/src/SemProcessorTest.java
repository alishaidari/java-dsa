import student.TestCase;

/**
 * tests SemProcessor and its methods
 * @author Ali Haidari (alih)
 * @version 9.22.2023
 */
public class SemProcessorTest extends TestCase {
    private SemProcessor testProc;
    private Seminar mySem;
    private Seminar altSem;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testProc = new SemProcessor(1024);
        String[] keywords = {"Good", "Bad", "Ugly"};
        mySem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
        altSem = new Seminar(2000, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
    }
    
    /**
     * tests if the SemProcessor inserts into all trees correctly
     */
    public void testProcInsert() {
        assertNotNull(testProc);
        assertTrue(testProc.semInsert(mySem));
        assertFalse(testProc.semInsert(mySem));
    }
    
    /**
     * tests if the SemProcessor searches id correctly
     */
    public void testProcSearchID() {
        assertNull(testProc.semSearchID(3));
        testProc.semInsert(mySem);
        assertNotNull(testProc.semSearchID(1729));
        assertEquals(mySem, testProc.semSearchID(1729));
    }
    
    /**
     * tests if the SemProcessor searches costs correctly
     */
    public void testProcSearchCost() {
        assertEquals(0, testProc.semSearchCost(30, 125).size());
        testProc.semInsert(mySem);
        assertEquals(1, testProc.semSearchCost(30, 125).size());
    }
    
    /**
     * tests if the SemProcessor searches dates correctly
     */
    public void testProcSearchDate() {
        assertEquals(0, testProc.semSearchDate("2", "3").size());
        testProc.semInsert(mySem);
        assertEquals(1, testProc.semSearchDate("2", "3").size());
    }
    
    /**
     * tests if the SemProcessor searches keywords correctly
     */
    public void testProcSearchKeyword() {
        assertEquals(0, testProc.semSearchKeyword("Good").size());
        testProc.semInsert(mySem);
        testProc.semInsert(altSem);
        assertEquals(2, testProc.semSearchKeyword("Good").size());
    }
   
    /**
     * tests if the SemProcessor deletes into all trees correctly
     */
    public void testProcDelete() {
        //insert two seminars with same values for dupes 
        testProc.semInsert(mySem);
        testProc.semInsert(altSem);
        assertFalse(testProc.semDelete(0));
        //remove the seminar with id 1 leaving dupes of id 2
        assertTrue(testProc.semDelete(1729));
        //search that all values remaining still exist
        assertNotNull(testProc.semSearchID(2000));
        assertNotNull(testProc.semSearchCost(30, 125));
        assertNotNull(testProc.semSearchDate("2", "3"));
        assertNotNull(testProc.semSearchKeyword("Good"));
    }
}
