import student.TestCase;

/**
 * test class for HashTable and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 8.20.2023
 */
public class HashTableTest extends TestCase {
    private HashTable<Integer, String> testHash;
    private HashTable<Integer, String> smallHash;
    private HashTable<String, String> stringHash;
    private HashTable<Integer, String> fullHash;
    private KVPair<Integer, String> grave;
    private Exception thrown;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testHash = new HashTable<Integer, String>(16);
        smallHash = new HashTable<Integer, String>(4);
        fullHash = new HashTable<Integer, String>(2);
        stringHash = new HashTable<String, String>(4);
        grave = new KVPair<Integer, String>();
    }
    
    /**
     * tests the initialization (and implicitly the getters)
     * of a HashTable object
     */
    public void testHashInit() {
        assertEquals(smallHash.currCount(), 0);
        assertEquals(smallHash.currSize(), 4);
        assertNotNull(smallHash.table());
    }
    /**
     * testing for the insert function of a HashTable 
     */
    public void testHashInsert() throws NumberFormatException {
        fullHash.table()[0] = new KVPair<Integer, String>(0, "str");
        fullHash.table()[1] = new KVPair<Integer, String>(1, "str");

        testHash.insert(1, "gg");
        assertTrue((Integer)1 == testHash.table()[1].key());
        testHash.insert(55, "xx");
        testHash.insert(39, "zz");
        testHash.insert(92, "aa");
        assertTrue((Integer)55 == testHash.table()[7].key());
        assertTrue((Integer)39 == testHash.table()[12].key());
        assertTrue((Integer)92 == testHash.table()[2].key());
        assertNull(testHash.table()[0]);
        assertFalse(testHash.insert(92, "cc"));
        String output = systemOut().getHistory();
        String dupeError = "Error, duplicates not allowed in hash table";
        assertFuzzyEquals(output, dupeError);
        testHash.insert(2, "bb");
        assertTrue((Integer)2 == testHash.table()[3].key());
    }
    
    /**
     * tests if the insertion key is null or not
     */
    public void testKeyNotNull() {
        try {
            assertFalse(stringHash.insert(null, "gg"));
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String keyError = "Error, the hash table key is not an integer";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(keyError, output);
    }
    
    /**
     * tests if the insertion key is an integer or not
     */
    public void testKeyInt() {
        try {
            assertFalse(stringHash.insert("aa", "gg"));
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String keyError = "Error, the hash table key is not an integer";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(keyError, output);
    }
    
    /**
     * testing for the search function of a HashTable
     */
    public void testHashSearch() {
        testHash.insert(55, "xx");
        testHash.insert(39, "zz");
        testHash.insert(92, "aa");
        assertTrue(testHash.search(55) == testHash.table()[7].value());
        assertTrue(testHash.search(39) == testHash.table()[12].value());
        assertTrue(testHash.search(92) == testHash.table()[2].value());
        assertNull(testHash.search(77));
    }
    
    /**
     * testing for the remove function of a HashTable
     */
    public void testHashRemove() {
        Integer temp = (Integer) 33;
        assertNull(testHash.remove(temp));
        testHash.insert(55, "xx");
        testHash.insert(39, "zz");
        testHash.insert(92, "aa");
        assertNull(testHash.table()[0]);
        assertNotNull(testHash.table()[7]);
        assertEquals(testHash.remove(39), "zz");
        String tomb = testHash.table()[12].toString();
        String testTomb = grave.toString();
        assertTrue(tomb.equals(testTomb));
        assertFalse(testHash.remove(92) == testHash.table()[2].value());
    }
    
    /**
     * testing for the print function of a HashTable
     */
    public void testHashPrint() {
        testHash.insert(55, "xx");
        testHash.insert(39, "zz");
        testHash.insert(92, "aa");
        testHash.print();
        String output = systemOut().getHistory();
        String expected = "Hashtable:\n"
            + "2: 92 \n"
            + "7: 55 \n"
            + "12: 39 \n"
            + "total records: 3\n";
        assertFuzzyEquals(output, expected);
    }
    
    /**
     * testing for the print function of a HashTable with tombStone
     */
    public void testHashTombPrint() {
        testHash.insert(55, "xx");
        testHash.insert(39, "zz");
        testHash.insert(92, "aa");
        testHash.remove(39);
        testHash.print();
        String output = systemOut().getHistory();
        String expected = "Hashtable:\n"
            + "2: 92 \n"
            + "7: 55 \n"
            + "12: TOMBSTONE \n"
            + "total records: 2\n";
        assertFuzzyEquals(output, expected);
    }
    
    /**
     * implicitly testing the expand function by insertion 
     */
    public void testHashExpand() {
        smallHash.insert(1, "aa");
        smallHash.insert(2,  "bb");
        assertEquals(smallHash.currCount(), 2);
        assertEquals(smallHash.currSize(), 4);
        assertNull(smallHash.table()[0]);
        assertNotNull(smallHash.table()[1]);
        smallHash.insert(3, "cc");
//        String output = systemOut().getHistory();
//        String expected = "Hash table expanded to 8 records";
//        assertFuzzyEquals(output, expected);
        assertEquals(smallHash.currCount(), 3);
        assertEquals(smallHash.currSize(), 8);
        assertNull(smallHash.table()[0]);
        assertNotNull(smallHash.table()[1]);
        assertNotNull(smallHash.table()[2]);
        smallHash.insert(0, "zz");
        assertNotNull(smallHash.remove(0));
        smallHash.print();
        assertNull(smallHash.remove(0));
        assertNull(smallHash.remove(5));
        assertNotNull(smallHash.remove(3));
        smallHash.print();
        smallHash.insert(8, "zz");
        smallHash.print();
        smallHash.insert(9, "zz");
        smallHash.print();
        smallHash.insert(10, "++");
        smallHash.print();
        
//        smallHash.insert(11, "&&");
//        smallHash.print();
//        smallHash.insert(12, "++");
//        smallHash.print();
//        smallHash.remove(1);
//        smallHash.print();
//        smallHash.insert(5, "tt"); 
//        smallHash.print();
    }
}
