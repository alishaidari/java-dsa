import student.TestCase;
/**
 * test class for BSTNode and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 9.15.2023
 */
public class BSTNodeTest extends TestCase {
    private BSTNode<Integer, String> empNode;
    private BSTNode<Integer, String> testNode;
    private BSTNode<Integer, String> leftChild;
    private BSTNode<Integer, String> rightChild;
    private BSTNode<Integer, String> leafNode;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        empNode = new BSTNode<Integer, String>();
        leafNode = new BSTNode<Integer, String>(0, "leaf");
        leftChild = new BSTNode<Integer, String>(4, "left", null, null);
        rightChild = new BSTNode<Integer, String>(8, "right", null, null);
        testNode = 
            new BSTNode<Integer, String>(1, "root", leftChild, rightChild);
    }
    
    /**
     * tests the initialization (and getters) of a BSTNode
     */
    public void testBSTNInit() {
        assertNotNull(empNode);
        assertNotNull(leafNode);
        assertNotNull(leftChild);
        assertNotNull(rightChild);
        assertNotNull(testNode);
        assertNull(empNode.key());
        assertNull(empNode.value());
        assertNull(empNode.left());
        assertNull(empNode.right());
        assertNull(leftChild.left());
        assertNull(leftChild.right());
        assertNull(rightChild.left());
        assertNull(rightChild.right());
        assertNull(leafNode.left());
        assertNull(leafNode.right());
        assertEquals(0, (int)leafNode.key());
        assertEquals(4, (int)leftChild.key());
        assertEquals(8, (int)rightChild.key());
        assertEquals(1, (int)testNode.key());
        assertFuzzyEquals("root", testNode.value());
        assertEquals(leftChild, testNode.left());
        assertEquals(rightChild, testNode.right());
    }
    
    /**
     * tests the setters of a BSTNode
     */
    public void testBSTNSet() {
        assertNull(empNode.left());
        assertNull(empNode.right());
        empNode.setLeft(leftChild);
        empNode.setRight(rightChild);
        assertEquals(leftChild, empNode.left());
        assertEquals(rightChild, empNode.right());
        empNode.setKey(100);
        assertEquals(100, (int)empNode.key());
        empNode.setValue("value");
        assertFuzzyEquals("value", empNode.value());
    }
    
    /**
     * tests if BSTNode isLeaf correctly detects leaf nodes
     */
    public void testBSTNLeaf() {
        assertFalse(testNode.isLeaf());
        testNode.setLeft(null);
        assertFalse(testNode.isLeaf());
        testNode.setLeft(leftChild);
        testNode.setRight(null);
        assertFalse(testNode.isLeaf());
        testNode.setRight(rightChild);
        assertTrue(leftChild.isLeaf());
        assertTrue(rightChild.isLeaf());
        assertTrue(leafNode.isLeaf());
    }
    
    /**
     * tests if BSTNode toString provides correct string
     */
    public void testBSTNKVString() {
        assertFuzzyEquals("(null, null)", empNode.toKVString());
        assertFuzzyEquals("(1, root)", testNode.toKVString());
        assertFuzzyEquals("(4, left)", leftChild.toKVString());
        assertFuzzyEquals("(8, right)", rightChild.toKVString());
        assertFuzzyEquals("(0, leaf)", leafNode.toKVString());
    }
    
//    /**
//     * tests if the BSTNode inOrder provides correct string
//     */
//    public void testBSTNInOrder() {
//        assertFuzzyEquals("left, root, right", testNode.inOrderString());
//        assertFuzzyEquals("null", empNode.inOrderString());
//        assertFuzzyEquals("left", leftChild.inOrderString());
//    }
}
