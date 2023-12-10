import student.TestCase;
/**
 * test class for BTEmptyNode and its method
 * 
 * @author Ali Haidari (alih)
 * @version 9.28.2023
 */
public class BTEmptyNodeTest extends TestCase {
    private BTEmptyNode empTest;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        empTest = new BTEmptyNode();
    }
    
    /**
     * tests the initialization of empty node
     */
    public void testEmptyNodeInit() {
        assertNotNull(empTest);
        assertNull(empTest.data());
    }
    
    /**
     * tests the node type of empty node
     */
    public void testEmptyNodeType() {
        assertEquals("E", empTest.type());
    }
}
