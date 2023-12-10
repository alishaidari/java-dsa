import student.TestCase;
/**
 * test class for BST and its methods
 * @author Ali Haidari (alih)
 * @version 9.20.2023
 */
public class BSTTest extends TestCase {
    private BST<Integer, String> testBST;
    private BST<Integer, String> empBST;
    private BST<String, String> altBST;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testBST = new BST<Integer, String>();
        empBST = new BST<Integer, String>();
        altBST = new BST<String, String>();
    }
    
    /**
     * tests if the BST is initialized correctly
     */
    public void testBSTInit() {
        assertNotNull(testBST);
        assertNotNull(empBST);
        assertNotNull(altBST);
        assertNull(testBST.root());
        assertNull(empBST.root());
        assertNull(altBST.root());
        assertEquals(0, testBST.size());
        assertEquals(0, empBST.size());
        assertEquals(0, altBST.size());
        assertEquals(0, testBST.visited());
        assertEquals(0, empBST.visited());
        assertEquals(0, altBST.visited());
    }
    
    /**
     * tests if the BST inserts correctly
     */
    public void testBSTInsert() {
        assertNull(testBST.root());
        testBST.insert(4, "root");
        assertNotNull(testBST.root());
        testBST.insert(4, "f left");
        testBST.insert(3, "s left");
        testBST.insert(5, "right");
        assertFuzzyEquals("root", 
            testBST.root().value());
        assertFuzzyEquals("right", 
            testBST.root().right().value());
        assertFuzzyEquals("f left", 
            testBST.root().left().value());
        assertFuzzyEquals("s left", 
            testBST.root().left().left().value());
        assertNull(testBST.root().right().right());
        assertNull(testBST.root().left().left().left());
        assertEquals(4, testBST.size());
    }
    
    /**
     * tests if a BST deletes correctly
     */
    public void testBSTDelete() {
        assertNull(testBST.root());
        assertEquals(0, testBST.size());
        assertNull(testBST.delete(9));
        assertNull(testBST.root());
        assertEquals(0, testBST.size());
        testBST.insert(4, "root");
        testBST.insert(4, "f left");
        testBST.insert(3, "s left");
        testBST.insert(5, "right");
        assertEquals(4, testBST.size());
        assertNull(testBST.delete(10));
        assertEquals(4, testBST.size());
        assertEquals(5, (int)testBST.root().right().key());
        assertNotNull(testBST.root());
        assertNotNull(testBST.root().right());
        assertNotNull(testBST.delete(5));
        assertNotNull(testBST.root());
        assertEquals(3, testBST.size());
        assertNull(testBST.root().right());
        
        empBST.insert(1, "root");
        empBST.insert(2, "l");
        empBST.insert(10, "r r");
        empBST.insert(3, "r  r l");
        assertEquals("root", empBST.root().value());
        assertEquals("root",  empBST.delete(1));
        
        altBST.insert("root", "r");
        altBST.insert("a", "a");
        altBST.insert("b", "a");
        altBST.insert("z", "z");
        altBST.insert("s", "s");
        assertEquals("a", altBST.root().left().key());
        assertEquals("a", altBST.delete("a"));
        assertEquals("b", altBST.root().left().key());
        assertEquals("z", altBST.delete("z"));
    }
    /**
     * tests for the rest of the branches in
     * a BST delete
     */
    public void testBSTDeleteAlt() {
        testBST.insert(10, "root");
        testBST.insert(20, "right");
        testBST.delete(10);
        assertEquals("right", testBST.root().value());
        
        empBST.insert(10, "root");
        empBST.insert(5, "left");
        empBST.delete(10);
        assertEquals("left", empBST.root().value());
    }
    
    /**
     * tests if a search is completed by a BST correctly
     */
    public void testBSTSearch() {
        testBST.insert(1, "one");
        testBST.insert(0, "zero");
        testBST.insert(2, "two");
        testBST.insert(10, "ten");
        testBST.insert(3, "three");
        assertEquals(testBST.root().left().value(), 
                testBST.search(0));
    }
    
    /**
     * tests if a range search is completed by a BST correctly
     */
    public void testBSTRangeSearch() {
        assertNull(testBST.rangeSearch(2, 3).root());
        testBST.insert(1, "1");
        testBST.insert(2, "2");
        testBST.insert(10, "10");
        testBST.insert(3, "3");
        assertNotNull(testBST.rangeSearch(2, 10).root());
        assertEquals(3, testBST.rangeSearch(2, 10).size());
        assertNull(empBST.root());
        assertNull(empBST.rangeSearch(1, 100).root());
    }
    
    /**
     * tests if a range search with same keys is completed 
     * by a BST correctly
     */
    public void testBSTSearchAll() {
        testBST.insert(1, "a");
        testBST.insert(2, "b");
        testBST.insert(10, "c");
        testBST.insert(3, "d");
        testBST.insert(3, "e");
        testBST.insert(1, "f");
        testBST.insert(30, "g");
        assertNotNull(testBST.rangeSearch(3, 3).root());
        assertNull(empBST.rangeSearch(1, 1).root());
    }
    
    /**
     * tests if a BST can find the correct node 
     * when searching for key and value
     */
    public void testBSTDeleteKV() {
        System.out.println("----");
        testBST.insert(10, "a");
        testBST.insert(5, "s8k");
        testBST.insert(8, "j");
        testBST.insert(20, "bb");
        testBST.insert(20, "b");
        testBST.insert(15, "c");
        testBST.insert(20, "bbb");
        testBST.insert(20, "z");
        testBST.insert(100, "c");
        testBST.insert(30, "d");
//        testBST.diagram();
        System.out.println(testBST.size());
        //TODO
        assertFalse(testBST.deleteKV(1, "a"));
        assertTrue(testBST.deleteKV(20, "bbb"));
//        testBST.diagram();
        System.out.println(testBST.size());
        System.out.println("----");
    }
    
    /**
     * tests if an empty BST outputs the correct print output
     * when tree method is called
     */
    public void testBSTEmptyTree() {
        empBST.tree();
        String output = systemOut().getHistory();
        String expected = "This tree is empty";
        assertFuzzyEquals(expected, output);
    }
    /**
     * tests if a BST outputs the correct print keys
     * when tree method is called
     */
    public void testBSTTree() {
        testBST.insert(1, "1");
        testBST.insert(2, "2");
        testBST.insert(10, "10");
        testBST.insert(3, "3");
        testBST.tree();
        String output = systemOut().getHistory();
        String expected = "      null\n"
            + "    10\n"
            + "        null\n"
            + "      3\n"
            + "        null\n"
            + "  2\n"
            + "    null\n"
            + "1\n"
            + "  null\n"
            + "Number of records: 4";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests if an empty BST outputs correct print values
     * when the values method is called
     */
    public void testBSTEmptyValues() {
        empBST.values();
        String output = systemOut().getHistory();
        String expected = "This tree is empty";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests if a BST outputs correct print values
     * when the values method is called
     */
    public void testBSTValues() {
        testBST.insert(1, "1");
        testBST.insert(2, "2");
        testBST.insert(10, "10");
        testBST.insert(3, "3");
        testBST.values();
        String output = systemOut().getHistory();
        String expected = "1\n"
            + "2\n"
            + "3\n"
            + "10";
        assertFuzzyEquals(expected, output);
    }
    
//    /**
//     * tests if an empty BST can diagram itself
//     * to console correctly
//     */
//    public void testBSTEmptyDiagram() {
//        empBST.diagram();
//        String output = systemOut().getHistory();
//        assertEquals("\n", output);
//    }
//    
//    /**
//     * tests if a BST can diagram itself
//     * to console correctly
//     */
//    public void testBSTDiagram() {
//        testBST.insert(8, "root");
//        testBST.insert(3, "l");
//        testBST.insert(1, "l l");
//        testBST.insert(6, "l r");
//        testBST.insert(10, "r");
//        testBST.insert(14, "r r");
//        testBST.insert(13, "r r l");
//        testBST.insert(4, "l r l");
//        testBST.insert(7, "l r r");
//        testBST.diagram();
//        String output = systemOut().getHistory();
//        System.out.println(output);
//        String expected = "\n"
//            + "|       |-------14\n"
//            + "|       |       |-------13\n"
//            + "|-------10\n"
//            + "8\n"
//            + "|       |       |-------7\n"
//            + "|       |-------6\n"
//            + "|       |       |-------4\n"
//            + "|-------3\n"
//            + "|       |-------1\n"
//            + "\n";
//        assertFuzzyEquals(expected, output);
//    }
}
