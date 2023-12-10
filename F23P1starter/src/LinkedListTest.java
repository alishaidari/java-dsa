import student.TestCase;

/**
 * test class for LinkedList and its methods
 * modified version of LinkedList from openDSA
 * @author Ali Haidari (alih)
 * @version 9.3.2023
 */
public class LinkedListTest extends TestCase {
    private LinkedList<Integer> testList;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testList = new LinkedList<Integer>();
    }
    
    /**
     * tests the initialization of a LList
     */
    public void testInit() {
        assertTrue(testList.isEmpty());
    }

    /**
     * tests the correct size of a LList
     */
    public void testSize() {
        assertEquals(0, testList.size());
        testList.insert(4);
        assertEquals(1, testList.size());
        testList.append(5);
        assertEquals(2, testList.size());
        testList.remove();
        assertEquals(1, testList.size());
    }

    /**
     * tests the correct data of a LList
     */
    public void testData() {
        assertNull(testList.data());
        testList.insert(4);
        assertEquals(4, (int)testList.data());
    }
    
    /**
     * tests the correct next Link of a LList
     */
    public void testNext() {
        testList.append(10);
        testList.append(20);
        testList.next();
        assertEquals(20, (int)testList.data());
        testList.next();
        assertTrue(testList.isAtEnd());
        assertNull(testList.data());
        assertEquals(testList.currIndex(), 2);
    }
    
    
    /**
     * tests the correct current index of a LList
     */
    public void testCurrIndex() {
        assertEquals(0, testList.currIndex());
        testList.append(5);
        testList.append(6);
        assertEquals(0, testList.currIndex());
        testList.next();
        assertEquals(1, testList.currIndex());
        testList.next();
        assertEquals(2, testList.currIndex());
        testList.prev();
        assertEquals(1, testList.currIndex());
    }
    
    /**
     * tests the correct previous Link of a LList
     */
    public void testPrev() {
        testList.append(15);
        testList.append(25);
        testList.insert(6);
        testList.prev();
        assertEquals((int)testList.data(), 6);
        testList.next();
        assertEquals((int)testList.data(), 15);
        testList.next();
        assertEquals((int)testList.data(), 25);
    }
    
    /**
     * tests if the current pointer of a 
     * LList moves to index correctly
     */
    public void testMoveTo() {
        testList.moveTo(20);
        assertEquals(0, testList.currIndex());
        testList.append(5);
        testList.insert(6);
        testList.append(4);
        testList.insert(7);
        testList.moveTo(-1);
        assertEquals(0, testList.currIndex());
        testList.moveTo(2);
        assertEquals(testList.currIndex(), 2);
        assertEquals((int)testList.data(), 5);
        testList.moveTo(4);
        assertNull(testList.data());
        String expected = "Index: 20 out of range\n"
            + "Index: -1 out of range";
        String output = systemOut().getHistory();
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests if a current pointer of
     * LList moves to an element correctly
     */
    public void testMoveToElem() {
        testList.moveToElem(10);
        assertTrue(testList.isAtEnd());
        testList.append(5);
        testList.insert(6);
        testList.append(4);
        assertEquals(testList.currIndex(), 0);
        testList.moveToElem(8);
        assertEquals(testList.currIndex(), 0);
        testList.moveToElem(5);
        assertEquals(testList.currIndex(), 1);
    }
    
    /**
     * tests if the LList inserts correctly
     */
    public void testInsert() {
        testList.insert(null);
        assertNull(testList.data());
        testList.insert(5);
        testList.insert(6);
        assertEquals(testList.currIndex(), 0);
        assertEquals(testList.size(), 3);
        testList.moveTo(testList.size());
        assertTrue(testList.isAtEnd());
        testList.insert(88);
        assertEquals((int)testList.data(), 88);
    }
    
    /**
     * tests if the LList appends correctly
     */
    public void testAppend() {
        testList.append(5);
        assertEquals(1, testList.size());
        assertEquals(0, testList.currIndex());
        assertEquals((int)testList.data(), 5);
        assertFalse(testList.isAtEnd());
    }
    
    /**
     * tests if the LList removes correctly
     */
    public void testRemove() {
        assertNull(testList.remove());
        testList.insert(5);
        assertEquals(1, testList.size());
        assertFalse(testList.isAtEnd());
        assertEquals((int)testList.remove(), 5);
        assertEquals(0, testList.size());
        assertTrue(testList.isAtEnd());
        testList.append(1);
        testList.append(2);
        testList.next();
        testList.next();
        assertNull(testList.remove());
        assertEquals(2, testList.size());
    }
    
    /**
     * tests if the LList clears correctly
     */
    public void testClear() {
        testList.append(1);
        testList.append(4);
        testList.insert(5);
        testList.next();
        assertEquals((int)testList.data(), 1);
        assertEquals(1, testList.currIndex());
        testList.clear();
        assertEquals(testList.size(), 0);
        assertEquals(0, testList.currIndex());
        assertNull(testList.data());
    }
    
    /**
     * tests if the LList is empty correctly
     */
    public void testEmpty() {
        assertTrue(testList.isEmpty());
        testList.append(5);
        assertFalse(testList.isEmpty());
        testList.remove();
        assertTrue(testList.isEmpty());
    }
    
    /**
     * tests if the LList is at the end
     */
    public void testIsAtEnd() {
        assertTrue(testList.isAtEnd());
        testList.append(4);
        assertFalse(testList.isAtEnd());
    }
    
    /**
     * tests for correct printing of LList
     */
    public void testToString() {
        String emptyList = "< | >";
        assertTrue(testList.toString().equals(emptyList));
        String inserted = "< | 5 >";
        testList.insert(5);
        assertTrue(testList.toString().equals(inserted));
        testList.remove();
        assertTrue(testList.toString().equals(emptyList));
        testList.append(6);
        testList.append(12);
        testList.next();
        String moveAppended = "< 6 | 12 >";
        assertTrue(testList.toString().equals(moveAppended));
        testList.prev();
        String prevAppended = "< | 6 12 >";
        assertTrue(testList.toString().equals(prevAppended));
    }
}
