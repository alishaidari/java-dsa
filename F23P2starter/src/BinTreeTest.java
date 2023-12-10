import student.TestCase;

/**
 * test class for BinTree and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 10.6.2023
 */
public class BinTreeTest extends TestCase {
    private BinTree bt;
    private Seminar testSem;
    private Seminar altSem;
    private Seminar otherSem;
    private Seminar lastSem;
    private Seminar dupeSem;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        bt = new BinTree(256);
        String[] keywords = {"Good", "Bad", "Ugly"};
        testSem = new Seminar(1, "Left Title", "2405231000", 75,
            (short)130, (short)120, 125, keywords, "great seminar");
        altSem = new Seminar(2, "Left Title", "2405231000", 75,
            (short)90, (short)200, 125, keywords, "great seminar");
        otherSem = new Seminar(3, "Left Title", "2405231000", 75,
            (short)30, (short)154, 125, keywords, "great seminar");
        lastSem = new Seminar(4, "Left Title", "2405231000", 75,
            (short)45, (short)214, 125, keywords, "great seminar");
        dupeSem = new Seminar(5, "Right Title", "2405231000", 75,
            (short)45, (short)214, 125, keywords, "great seminar");
        
    }
    /**
     * tests if binTree inserts properly
     */
    public void testBTInsert() {
        bt.insert(testSem); //A (130, 120)
        assertTrue(bt.root() instanceof BTLeafNode);
        bt.insert(altSem); //C (90, 200)
        assertTrue(bt.root() instanceof BTInternalNode);
        BTInternalNode assertInt = (BTInternalNode)bt.root();
        assertTrue(assertInt.right() instanceof BTLeafNode);
        assertTrue(assertInt.left() instanceof BTLeafNode);
        BTLeafNode leftInt = (BTLeafNode)assertInt.left();
        BTLeafNode rightInt = (BTLeafNode)assertInt.right();
        assertEquals(altSem, leftInt.data().data());
        assertEquals(testSem, rightInt.data().data());
        
        bt.insert(otherSem); //D (30, 154)
        bt.insert(lastSem); //E (45, 214) 
        bt.insert(dupeSem); //E again
        assertEquals(2, bt.search(45, 214).size());
    }
    
    /**
     * tests if binTree searches properly
     */
    public void testBTSearch() {
        assertNull(bt.search(100, 100));
        bt.insert(testSem); //A (130, 120)
        assertNull(bt.search(100, 100));
        bt.insert(altSem); //C (90, 200)
        bt.insert(otherSem); //D (30, 154)
        bt.insert(lastSem); //E (45, 214)
        bt.insert(dupeSem); //E again
        assertNull(bt.search(10, 10));
        assertNotNull(bt.search(130, 120));
        assertFalse(bt.search(130, 120).isEmpty());
        assertEquals(testSem, bt.search(130, 120).data());
        assertEquals(altSem, bt.search(90, 200).data());
        LinkedList<Seminar> dupeTemplate = 
            new LinkedList<Seminar>();
        dupeTemplate.append(lastSem);
        dupeTemplate.append(dupeSem);
        assertEquals(dupeTemplate.toString(), 
            bt.search(45, 214).toString());
    }
    
    /**
     * tests if binTree deletes when the
     * passed in coordinates are not found
     */
    public void testBTDeleteWrongCoord() {
        bt.delete(10, 10, 2);
        String output = systemOut().getHistory();
        String expected = "Error, deletion seminar not found";
        assertFuzzyEquals(expected, output);
        assertTrue(bt.root() instanceof BTEmptyNode);
    }
    
    /**
     * tests if binTree deletes when the 
     * passed in ID is not found
     */
    public void testBTDeleteWrongID() {
        bt.insert(testSem); //A (130, 120)
        bt.delete(130, 120, 0);
        String output = systemOut().getHistory();
        String expected = "Error, deletion ID not found";
        assertFuzzyEquals(expected, output);
        assertTrue(bt.root() instanceof BTLeafNode);
    }
    
    /**
     * tests if binTree deletes when the
     * passed in ID is not found from leaf
     * with duplicates
     */
    public void testBTDeleteDupeWrongID() {
        bt.insert(lastSem);
        bt.insert(dupeSem);
        bt.delete(45, 214, 7);
        String output = systemOut().getHistory();
        String expected = "Error, deletion ID not" 
            + " found within duplicates";
        assertFuzzyEquals(expected, output);
        assertTrue(bt.root() instanceof BTLeafNode);
    }
    
    /**
     * tests if binTree deletes when 
     * the passed in parameters are found
     */
    public void testBTDeletePass() {
        bt.insert(testSem); // A (130, 120);
        bt.delete(130, 120, 1); 
        assertTrue(bt.root() instanceof BTEmptyNode);
        bt.insert(testSem); // A (130, 120);
        bt.insert(altSem); //C (90, 200)
        bt.delete(90, 200, 2);
        assertTrue(bt.root() instanceof BTLeafNode);
        BTLeafNode tempHead = (BTLeafNode)bt.root();
        assertEquals(testSem, tempHead.data().data());
        bt.delete(130, 120, 1);
        bt.insert(testSem); // A (130, 120);
        bt.insert(altSem); //C (90, 200)
        bt.delete(130, 120, 1);
        assertTrue(bt.root() instanceof BTLeafNode);
        BTLeafNode altHead = (BTLeafNode)bt.root();
        assertEquals(altSem, altHead.data().data());
    }
    
    /**
     * tests if binTree prints properly
     */
    public void testBTPrint() {
        bt.print();
        String output = systemOut().getHistory();
        String expected = "E\n";
        assertEquals(expected, output);
    }
}
