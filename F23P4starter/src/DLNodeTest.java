import student.TestCase;
/**
 * test class for DLNode and its methods
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 */
public class DLNodeTest extends TestCase {
    private DLNode<Integer> testNode;
    private DLNode<Integer> empNode;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testNode = new DLNode<Integer>(0, null, null);
        empNode = new DLNode<Integer>(null, null);
    }
    
    /**
     * tests if a DLNode is initialized correctly
     */
    public void testDLNodeInit() {
        assertNotNull(testNode);
        assertNull(testNode.next());
        assertNull(testNode.prev());
        assertEquals(0, (int)testNode.element());
        
        assertNull(empNode.next());
        assertNull(empNode.prev());
        assertNull(empNode.element());
    }
    
    /**
     * tests the setters for a DLNode
     */
    public void testDLNodeSetters() {
        testNode.setElement(10);
        assertEquals(10, (int)testNode.element());
        DLNode<Integer> left = new DLNode<Integer>(20, null, null);
        DLNode<Integer> right = new DLNode<Integer>(30, null, null);
        testNode.setNext(right);
        testNode.setPrev(left);
        assertEquals(left, testNode.prev());
        assertEquals(right, testNode.next());
        assertEquals(30, (int)testNode.next().element());
        assertEquals(20, (int)testNode.prev().element());
    }
}
