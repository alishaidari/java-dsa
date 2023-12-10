import student.TestCase;

/**
 * test class for graph and its methods
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 */
public class GraphTest extends TestCase {
    private Graph testGraph;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testGraph = new Graph(10);
    }
    
    /**
     * tests if a graph initializes correclty
     */
    public void testGraphInit() {
        Graph empGraph = null;
        Exception thrown = null;
        
        try {
            empGraph = new Graph(0);
        }
        catch (Exception e) {
            thrown = e;
        }
        assertNull(empGraph);
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);
        String out = systemOut().getHistory();
        String expected = "Error, cannot initialize graph.";
        assertFuzzyEquals(expected, out);
        assertEquals(0, testGraph.getVertexCount());
        assertNotNull(testGraph.getVertexArray());
        for (int i = 0; i < testGraph.getVertexArray().length; i++) {
            assertNull(testGraph.getAdjList(i));
        }
        assertEquals(0, (int)testGraph.nextFreeVertex());
        assertEquals(10, testGraph.getFreeVertices().size());
    }
    
    /**
     * tests if a graph adds an edge correctly
     */
    public void testGraphAddEdge() {
        assertNull(testGraph.getAdjList(3));
        assertNull(testGraph.getAdjList(4));
        assertNull(testGraph.getAdjList(1));
        testGraph.addEdge(3, 4);
        assertNotNull(testGraph.getAdjList(3));
        assertNotNull(testGraph.getAdjList(4));
        assertEquals(4, (int)testGraph.getAdjList(3).element());
        assertEquals(3, (int)testGraph.getAdjList(4).element());
        assertEquals(2, testGraph.getVertexCount());
        testGraph.addEdge(3,  1);
        testGraph.getAdjList(3).repos(1);
        assertEquals(1, (int)testGraph.getAdjList(3).element());
        assertEquals(3, testGraph.getVertexCount());
        testGraph.getAdjList(3).repos(0);
        assertNotNull(testGraph.getAdjList(1));
        assertEquals(3, (int)testGraph.getAdjList(1).element());
    }
    
    /**
     * tests if a graph can tell it has edge correctly
     */
    public void testGraphHasEdge() {
        testGraph.getVertexArray()[0] = new DLList<Integer>();
        assertFalse(testGraph.hasEdge(2, 0));
        assertFalse(testGraph.hasEdge(0, 2));
        testGraph.getVertexArray()[4] = new DLList<Integer>();
        testGraph.getVertexArray()[5] = new DLList<Integer>();
        testGraph.getAdjList(4).append(5);
        assertFalse(testGraph.hasEdge(4, 5));
        testGraph.getVertexArray()[4] = new DLList<Integer>();
        testGraph.getAdjList(5).append(4);
        assertFalse(testGraph.hasEdge(4, 5));
        testGraph.addEdge(1, 3);
        assertTrue(testGraph.hasEdge(1, 3));
    }
    
    /**
     * tests if graph can remove edge correctly
     */
    public void testGraphRemoveEdge() {
        
        testGraph.addEdge(1, 0);
        testGraph.addEdge(1, 2);
        testGraph.addEdge(1, 3);
        testGraph.removeEdge(5, 6);
        assertNull(testGraph.getAdjList(5));
        assertNull(testGraph.getAdjList(6));
        testGraph.removeEdge(2, 1);
        assertNull(testGraph.getAdjList(2).element());
        assertEquals(0, testGraph.getAdjList(2).size());
        assertEquals(0, (int)testGraph.getAdjList(1).element());
        assertEquals(2, testGraph.getAdjList(1).size());
    }
    
    /**
     * tests if a graph frees up a vertex properly
     */
    public void testGraphFreeVertex() {
        int verOne = testGraph.getFreeVertices().remove();
        int verTwo = testGraph.getFreeVertices().remove();
        assertEquals(0, verOne);
        assertEquals(1, verTwo);
        assertEquals(8, testGraph.getFreeVertices().size());
        testGraph.addEdge(verOne, verTwo);
        assertEquals(2, testGraph.getVertexCount());
        testGraph.freeVertex(verOne);
        assertEquals(1, testGraph.getVertexCount());
        assertNull(testGraph.getAdjList(verOne));
        assertEquals(9, testGraph.getFreeVertices().size());
        assertEquals(verOne, testGraph.nextFreeVertex());
    }
    
    /**
     * tests if a graph expands correctly
     */
    public void testGraphExpand() {
        testGraph.addEdge(1, 4);
        testGraph.addEdge(0, 5);
        testGraph.addEdge(6, 0);
        testGraph.getFreeVertices().delete(1);
        testGraph.getFreeVertices().delete(0);
        testGraph.getFreeVertices().delete(6);
        testGraph.getFreeVertices().delete(5);
        testGraph.getFreeVertices().delete(4);
        testGraph.getFreeVertices().print();
        testGraph.print();
        assertEquals(10, testGraph.getVertexArray().length);
        testGraph.expand();
        assertEquals(20, testGraph.getVertexArray().length);
        testGraph.print();
        testGraph.getFreeVertices().print();
        
    }
    
    /**
     * tests if a graph outputs component and diameter
     * correctly
     * @throws Exception 
     */
    public void testGraphOutput() throws Exception {
        int[] par = {0, 0, 2, 2, 3, 3};
        int[] ver = {0, 1, 3, 5, 6, 9};
        KVPair<int[], int[]> parTab = new KVPair<int[], int[]>(ver, par);
        HashTable<String, Integer> testMap = 
            testGraph.findFrequencies(parTab);
        assertNotNull(testMap);
        KVPair<Integer, Integer> testInfo = 
            testGraph.findComponentInfo(testMap);
        assertNotNull(testInfo);
        DLList<Integer> sP = 
            testGraph.selectLargestParents(testInfo.value(), testMap);
        assertNotNull(sP);
        int[][] mapped = 
            testGraph.mapComponents(sP, parTab, testInfo.value());
        assertEquals(3, mapped.length);
        for (int[] i : mapped) {
            System.out.println("-------");
            testGraph.floyd(i);
            System.out.println("-------");
        }
    }
}
