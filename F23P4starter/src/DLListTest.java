import student.TestCase;
/**
 * test class for DLList and its methods
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 */
public class DLListTest extends TestCase {
    private DLList<Integer> testList;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testList = new DLList<Integer>();
    }
    
    /**
     * tests initialization of a DLList
     */
    public void testDLListInit() {
        assertNotNull(testList);
        assertEquals(0, testList.size());
        assertEquals(0, testList.pos());
    }
    
    /**
     * tests getter for element in DLList
     */
    public void testDLListElement() {
        assertNull(testList.element());
        testList.insert(30);
        testList.insert(40);
        assertNotNull(testList.element());
        assertEquals(40, (int)testList.element());
        testList.remove();
        testList.remove();
        assertNull(testList.element());
    }
    
    /**
     * tests if DLList repositions correctly
     */
    public void testDLListRepos() {
        assertEquals(0, (int)testList.pos());
        testList.repos(10);
        assertEquals(0, (int)testList.pos());
        testList.repos(-10);
        assertEquals(0, (int)testList.pos());
        testList.append(0);
        testList.append(1);
        testList.append(2);
        testList.append(3);
        testList.append(4);
        testList.repos(5);
        assertEquals(5, (int)testList.pos());
        assertNull(testList.element());
        testList.repos(3);
        assertEquals(3, (int)testList.pos());
        assertEquals(3, (int)testList.element());
        testList.repos(3);
        assertEquals(3, (int)testList.pos());
        assertEquals(3, (int)testList.element());
        testList.repos(0);
        assertEquals(0, (int)testList.pos());
        assertEquals(0, (int)testList.element());
    }
    
    /**
     * tests if DLList inserts correctly
     */
    public void testDLListInsert() {
        assertNull(testList.element());
        testList.insert(30);
        assertEquals(30, (int)testList.element());
        assertEquals(0, (int)testList.pos());
        assertEquals(1, (int)testList.size());
        testList.insert(40);
        assertEquals(40, (int)testList.element());
        assertEquals(0, (int)testList.pos());
        assertEquals(2, (int)testList.size());
        testList.repos(2);
        testList.insert(4);
        assertEquals(4, (int)testList.element());
        assertEquals(2, (int)testList.pos());
        assertEquals(3, (int)testList.size());
    }
    
    /**
     * tests if DLList appends correctly
     */
    public void testDLListAppend() {
        assertNull(testList.element());
        testList.append(40);
        assertEquals(40, (int)testList.element());
        assertEquals(0, (int)testList.pos());
        assertEquals(1, (int)testList.size());
        testList.append(60);
        assertEquals(0, (int)testList.pos());
        assertEquals(2, (int)testList.size());
        testList.repos(1);
        assertEquals(1, (int)testList.pos());
        assertEquals(60, (int)testList.element());
        assertEquals(2, (int)testList.size());
    }
    
    /**
     * tests if DLList removes correctly
     */
    public void testDLListRemove() {
        assertNull(testList.element());
        assertNull(testList.remove());
        testList.append(50);
        assertEquals(50, (int)testList.remove());
        assertEquals(0, (int)testList.pos());
        assertEquals(0, (int)testList.size());
        testList.insert(60);
        testList.insert(100);
        testList.insert(30);
        testList.repos(1);
        assertEquals(100, (int)testList.remove());
        assertEquals(1, (int)testList.pos());
        assertEquals(2, (int)testList.size());
    }
    
    /**
     * tests if DLList deletes correctly
     */
    public void testDLListDelete() {
        assertNull(testList.element());
        assertFalse(testList.delete(4));
        testList.append(50);
        testList.append(500);
        testList.append(5000);
        testList.print();
        assertTrue(testList.delete(500));
        testList.print();
        
    }
    
//    /**
//     * tests if DLList truncates correctly
//     */
//    public void testDLListTruncate() {
//        assertNull(testList.truncate());
//        testList.append(50);
//        testList.append(300);
//        testList.append(40);
//        assertEquals(40, (int)testList.truncate());
//        assertEquals(0, (int)testList.pos());
//        assertEquals(2, (int)testList.size());
//        assertEquals(300, (int)testList.truncate());
//        assertEquals(0, (int)testList.pos());
//        assertEquals(1, (int)testList.size());
//        testList.repos(1);
//        testList.insert(30);
//        testList.insert(400);
//        assertEquals(30, (int)testList.truncate());
//        assertEquals(1, (int)testList.pos());
//        assertEquals(2, (int)testList.size());
//    }
    
    /**
     * tests if DLList correctly calls has
     */
    public void testDLListHas() {
        assertFalse(testList.has(4));
        testList.append(40);
        assertFalse(testList.has(4));
        testList.append(4000);
        testList.append(3);
        testList.append(4);
        assertTrue(testList.has(4));
    }
    
    /**
     * tests if DLList prints as intended
     */
    public void testDLListEmpPrint() {
        testList.print();
        String out = systemOut().getHistory();
        String expected = "< null | null >";
        assertTrue(out.contains(expected));
    }
}
