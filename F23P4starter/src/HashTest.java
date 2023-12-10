import student.TestCase;

/**
 * test class for hash and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 11.3.2023
 */
public class HashTest extends TestCase {
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        //intentionally blank, Hash is public static
    }

    /**
     * test out the sfold method
     */
    public void testSfold() throws Exception {
        Hash blah = new Hash();
        assertNotNull(blah);
        assertEquals(Hash.h("a", 10000), 97);
        assertEquals(Hash.h("b", 10000), 98);
        assertEquals(Hash.h("aaaa", 10000), 1873);
        assertEquals(Hash.h("aaab", 10000), 9089);
        assertEquals(Hash.h("baaa", 10000), 1874);
        assertEquals(Hash.h("aaaaaaa", 10000), 3794);
        assertEquals(Hash.h("Long Lonesome Blues", 10000), 4635);
        assertEquals(Hash.h("Long   Lonesome Blues", 10000), 4159);
        assertEquals(Hash.h("long Lonesome Blues", 10000), 4667);
    }
    
    /**
     * tests if Hash probes correctly
     */
    public void testProbe() {
        //home slots
        int homeFive = 5;
        int homeSix = 6;
        //resolved indices
        int[] resolvedFive = {6, 9, 4};
        int[] resolvedSix = {7, 0, 5};
        
        //hash length
        int tabLen = 10;
        //probing for i : 1 - 3
        for (int i = 1; i < 4; i++) {
            assertEquals(resolvedFive[i - 1], Hash.p(homeFive, i, tabLen));
            assertEquals(resolvedSix[i - 1], Hash.p(homeSix, i, tabLen));
        }
    }
}
