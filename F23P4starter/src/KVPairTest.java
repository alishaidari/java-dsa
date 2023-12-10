import student.TestCase;

/**
 * test class for KVPair and its methods
 * @author Ali Haidari (alih)
 * @version 8.30.2023
 */
public class KVPairTest extends TestCase {
    
    private int testKey;
    private String testVal;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testKey = 1;
        testVal = "payload";
    }
    
    /**
     * tests the initialization (and implicitly the getters) of a KVPair
     */
    public void testKVInit() {
        KVPair<Integer, String> testNull = new KVPair<Integer, String>();
        KVPair<Integer, String> testPair = new KVPair<Integer, 
            String>(testKey, testVal);
        assertNull(testNull.key());
        assertNull(testNull.value());

        
        assertEquals((int)testPair.key(), 1);
        assertEquals(testPair.value(), "payload");
    }
    
    /**
     * tests the setters of KVPair
     */
    public void testSetKVPairs() {
        KVPair<Integer, String> testPair = new KVPair<Integer, 
            String>(testKey, testVal);
        assertEquals((int)testPair.key(), 1);
        assertEquals(testPair.value(), "payload");
        testPair.setKey(3);
        assertEquals((int)testPair.key(), 3);
        testPair.setValue("new payload");
        assertEquals(testPair.value(), "new payload");
    }
    
    
    /**
     * tests the print function of KVPair
     */
    public void testPrintKVPairs() {
        KVPair<Integer, String> testPair = new KVPair<Integer, 
            String>(testKey, testVal);
        assertFuzzyEquals(testPair.toString(), "(1, payload)");
        testPair.setKey(null);
        assertFuzzyEquals(testPair.toString(), "(null, payload)");
        testPair.setValue(null);
        assertFuzzyEquals(testPair.toString(), "(null, null)");
    }
    
    /**
     * tests if two KVPairs are equal
     */
    public void testEqualKVPairs() {
        KVPair<Integer, String> testPair = new KVPair<Integer, 
            String>(testKey, testVal);
        KVPair<Integer, String> other = new KVPair<Integer, 
            String>(testKey, testVal);
        KVPair<Integer, String> emp = new KVPair<Integer, String>();
        assertTrue(testPair.sameAs(other));
        assertTrue(emp.sameAs(new KVPair<Integer, String>()));
        other.setKey(20);
        assertFalse(testPair.sameAs(other));
        other.setKey(testKey);
        other.setValue("200");
        assertFalse(testPair.sameAs(other));
    }
    

}
