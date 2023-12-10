import student.TestCase;
/**
 * test class for BTLeafNode and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTLeafNodeTest extends TestCase {
    private BTLeafNode leafFly;
    private Seminar mySem;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        String[] keywords = {"Good", "Bad", "Ugly"};
        mySem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
        leafFly = new BTLeafNode(mySem);
    }
    
    /**
     * tests the initialization of a leaf
     */
    public void testLeafNodeInit() {
        assertNotNull(leafFly);
        assertEquals(mySem, leafFly.data().data());
        //make sure the leaf is correct node type
        assertEquals("L", leafFly.type());
        //FIXME?
        //make sure empty node is instance of empty
        assertTrue(BTEmptyNode.EMPTY instanceof BTEmptyNode);
        //make sure empty node data is null
        assertNull(BTEmptyNode.EMPTY.data());
        //make sure empty is correct node type
        assertEquals("E", BTEmptyNode.EMPTY.type());
    }
}
