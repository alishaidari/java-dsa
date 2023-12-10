import student.TestCase;
/**
 * test class for BTInternalNode and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTInternalNodeTest extends TestCase {
    private BTInternalNode empInt;
    private BTInternalNode intChildInt;
    private BTInternalNode leafChildInt;
    private BTInternalNode leftInternal;
    private BTInternalNode rightInternal;
    private BTLeafNode leftLeaf;
    private BTLeafNode rightLeaf;
    private Seminar leftSem;
    private Seminar rightSem;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        //internal with empty leaves
        empInt = new BTInternalNode();
        String[] keywords = {"Good", "Bad", "Ugly"};
        leftSem = new Seminar(4, "Left Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "great seminar");
        rightSem =  new Seminar(8, "Right Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "great seminar");
        
        //internal node with two internal node children
        leftInternal = new BTInternalNode();
        rightInternal = new BTInternalNode();
        intChildInt = new BTInternalNode(leftInternal, rightInternal);
        
        //internal node with two leaf children
        leftLeaf = new BTLeafNode(leftSem);
        rightLeaf = new BTLeafNode(rightSem);
        leafChildInt = new BTInternalNode(leftLeaf, rightLeaf);
    }
    
    /**
     * tests initialization of an internal
     * node (and implicitly the getters)
     */
    public void testInternalNodeInit() {
        assertNotNull(empInt);
        assertNotNull(intChildInt);
        assertNotNull(leafChildInt);
        //make sure the children of empInt are empty nodes
        assertEquals("E", empInt.left().type());
        assertEquals("E", empInt.right().type());
        //make sure the children of childInt are internal nodes 
        assertEquals("I", intChildInt.left().type());
        assertEquals("I", intChildInt.right().type());
        //make sure the children of childLeaf are leaf nodes
        assertEquals("L", leafChildInt.left().type());
        assertEquals("L", leafChildInt.right().type());
        //make sure the children of childLeaf hold correct data
        BTLeafNode leftTemp = (BTLeafNode)leafChildInt.left();
        assertEquals(leftSem, leftTemp.data().data());
        BTLeafNode rightTemp = (BTLeafNode)leafChildInt.right();
        assertEquals(rightSem, rightTemp.data().data());
    }
    
    /**
     * tests the setters of an internal node
     */
    public void testInternalSetters() {
        empInt.setLeft(leftInternal);
        empInt.setRight(rightLeaf);
        //assert that the new children are set correctly
        assertEquals("I", empInt.left().type());
        assertEquals("L", empInt.right().type());
        //assert that the leaf set still holds data
        BTLeafNode rightTemp = (BTLeafNode)empInt.right();
        assertEquals(rightSem, rightTemp.data().data());
    }
}
