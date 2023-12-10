import student.TestCase;

/**
 * test class for hash table and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 11.3.2023
 */
public class HashTableTest extends TestCase {
    private HashTable<String, String> testHash;
    private HashTable<Integer, String> wrongHash;
    private HashTable<String, String> smallHash;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        //initial size of 10
        testHash = new HashTable<String, String>(10);
        wrongHash = new HashTable<Integer, String>(10);
        //initial size of 2
        smallHash = new HashTable<String, String>(2);
    }
    
    /**
     * tests if a hash table initializes correctly
     */
    public void testHashInit() {
        assertNotNull(testHash);
        assertEquals(0, testHash.currCount());
        assertEquals(10, testHash.currSize());
    }
    
    /**
     * tests if a hash table can correctly catch 
     * an incorrect hash attempt
     */
    public void testWrongHashKey() {
        Exception thrown = null;
        //try to hash null key
        try {
            testHash.insert(null, "false");
        }
        catch (Exception e) {
            thrown = e;
        }
        assertNotNull(thrown);
        String nullOut = thrown.getLocalizedMessage();
        String keyError = "Error, wrong key type";
        assertEquals(keyError, nullOut);
        
        //reset exception to null
        thrown = null;
        
        //try to hash an integer key
        try {
            wrongHash.insert(1, "false");
        }
        catch (Exception e) {
            thrown = e;
        }
        assertNotNull(thrown);
        String intOut = thrown.getLocalizedMessage();
        assertEquals(keyError, intOut);
    }
    
    /**
     * tests if hash insert will probe a simple sequence
     * 
     * @throws Exception 
     */
    public void testInsertSimple() throws Exception {
        //first home slot at 5
        assertTrue(testHash.insert("i", "load"));
        //probe into 6
        assertTrue(testHash.insert("ag", "load"));
        //probe into 9
        assertTrue(testHash.insert("az3", "load"));
        //probe into 14 (goes to 4)
        assertTrue(testHash.insert("AZ", "load"));
        assertEquals("i", testHash.table()[5].key());
        assertEquals("ag", testHash.table()[6].key());
        assertEquals("az3", testHash.table()[9].key());
        assertEquals("AZ", testHash.table()[4].key());
        
        //make sure no duplicates are allowed
        assertFalse(testHash.insert("az3", "other"));
    }
    
    /**
     * tests if hash table removes correctly
     * 
     * @throws Exception
     */
    public void testSimpleRemove() throws Exception {
        //first home slot at 5
        assertTrue(testHash.insert("i", "a"));
        //probe into 6
        assertTrue(testHash.insert("ag", "b"));
        //probe into 9
        assertTrue(testHash.insert("az3", "c"));
        //probe into 14 (goes to 4)
        assertTrue(testHash.insert("AZ", "d"));
        //assert that a key that does not exist is null
        assertNull(testHash.remove("emp"));
        //remove pos 5
        assertEquals("a", testHash.remove("i"));
        assertEquals(3, testHash.currCount());
        //try to remove the same key
        assertNull(testHash.remove("i"));
        //remove pos 4
        assertEquals("d", testHash.remove("AZ"));
        assertEquals(2, testHash.currCount());
        //insert to pos 5
        testHash.insert("AZ", "x");
        assertEquals("x", testHash.table()[5].value());
        //insert to pos 4
        testHash.insert("i", "y");
        assertEquals("y", testHash.table()[4].value());
    }
    
    /**
     * tests if hash search will find a value correctly
     * 
     * @throws Exception
     */
    public void testSearchSimple() throws Exception {
        //first home slot at 5
        testHash.insert("i", "red");
        //probe into 6
        testHash.insert("ag", "blu");
        //probe into 9
        testHash.insert("az3", "gre");
        //probe into 14 (goes to 4)
        testHash.insert("AZ", "yel");
        
        //non existent key
        assertNull(testHash.search("z"));
        //keys that exist
        assertEquals("red", testHash.search("i"));
        assertEquals("yel", testHash.search("AZ"));
    }
    
    /**
     * tests if hash insert with expand and rehash correctly
     * 
     * @throws Exception
     */
    public void testInsertExpand() throws Exception {
        //first home at 1 (will get rehashed to 3)
        smallHash.insert("clap", "1");
        //next insert will exceed half full
        smallHash.insert("blue", "dk");
        //check for expanded size
        assertEquals(4, smallHash.currSize());
        assertEquals(2, smallHash.currCount());
        //check for rehash at 1
        assertNull(smallHash.table()[1]);
        assertEquals("blue", smallHash.table()[2].key());
        assertEquals("clap", smallHash.table()[3].key());
    }
    
    /**
     * tests if hash insert will overwrite a tombstone
     * 
     * @throws Exception
     */
    public void testInsertTomb() throws Exception {
        //first home slot at 5
        testHash.insert("i", "red");
        //probe into 6
        testHash.insert("ag", "blu");
        //probe into 9
        testHash.insert("az3", "gre");
        //probe into 14 (goes to 4)
        testHash.insert("AZ", "yel");
        
        //remove KV at 4
        testHash.remove("AZ");
        //remove KV at 5
        testHash.remove("i");
        //reinsert to 4
        assertTrue(testHash.insert("tea", "purp"));
        assertEquals("purp", testHash.table()[4].value());
        //try to insert 4 go to 5
        assertTrue(testHash.insert("baaa", "oran"));
        assertEquals("oran", testHash.table()[5].value());
    }
    
    /**
     * tests if hash insert will expand and rehash tombstones
     * 
     * @throws Exception
     */
    public void testInsertExpandTomb() throws Exception {
        //first home slot at 5
        testHash.insert("i", "red");
        
        //probe into 6
        testHash.insert("ag", "blu");
        //probe into 9
        testHash.insert("az3", "gre");
        //probe into 14 (goes to 4)
        testHash.insert("AZ", "yel");
        
        //probe into 21 (goes to 1)
        testHash.insert("ga", "gray");

        //remove at 5
        testHash.remove("i");
        //remove at 1
        testHash.remove("ga");
        //home at 8
        testHash.insert("b", "temp");
        //home at 7
        testHash.insert("ad", "gray");
        //next insert will expand
        testHash.insert("ea", "ya");
        //make sure previous tombstone is deleted
        assertNull(testHash.table()[1]);
        //make sure previous values rehashed
        assertEquals("AZ", testHash.table()[5].key());
        assertEquals("ag", testHash.table()[6].key());
        assertEquals("az3", testHash.table()[9].key());
        assertEquals("ea", testHash.table()[13].key());
        assertEquals("ad", testHash.table()[17].key());
        assertEquals("b", testHash.table()[18].key());
    }
    
    
    /**
     * tests if a hash search will skip tombstones
     * 
     * @throws Exception
     */
    public void testSearchTomb() throws Exception {

        //first home slot at 5
        testHash.insert("i", "red");
        //probe into 6
        testHash.insert("ag", "blu");
        //probe into 9
        testHash.insert("az3", "gre");
        //probe into 14 (goes to 4)
        testHash.insert("AZ", "yel");
        
        //remove KV at 5
        testHash.remove("i");
        //make sure the search returns null
        assertNull(testHash.search("i"));
        assertEquals("blu", testHash.search("ag"));
        
    }
    
    /**
     * tests if hash table removes correctly
     * with a tombstone
     * 
     * @throws Exception
     */
    public void testTombRemove() throws Exception {
        //manually insert tombstones
        KVPair<String, String> tomb = new KVPair<String, String>();
        smallHash.table()[0] = tomb;
        assertNull(smallHash.remove("temp"));
        
//        System.out.println(Hash.h("aa", 10));
//        System.out.println(Hash.h("aa", 10));
//        System.out.println(Hash.h("Blind Lemon Jefferson", 10));
//        System.out.println(Hash.h("long Lonesome Blues", 10));
//        System.out.println(Hash.h("Ma Rainey", 10));
//        System.out.println(Hash.h("Ma Rainey's Black Bottom", 10));
    }
    
}