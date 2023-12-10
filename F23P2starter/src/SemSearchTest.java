import java.io.IOException;
import student.TestCase;

/**
 * tests semSearch and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 9.18.2023
 */
public class SemSearchTest extends TestCase {
    private SemSearch testSearch;
    private String worldCorrect;
    private String worldIncorrect;

    private String realFile;
    private String nonFile;
    private String worldDecimal;
    private Exception thrown;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testSearch = new SemSearch();
        worldCorrect = "128";
        worldIncorrect = "34";
        worldDecimal = "32.1";
        nonFile = "not_file";
        realFile = ".//delete_all.txt";
        thrown = null;
    }
    
    /**
     * tests for null argument passed in main 
     */
    public void testNullMain() {
        assertNotNull(testSearch);
        try {
            SemSearch.main(null);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, usage is as follows: " 
            + "java SemSearch {world-size} " 
            + "{command-file}";
        assertFuzzyEquals(expected, output);
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
    }
    
   /**
    * tests for not enough arguments passed in main
    */
    public void testArgsCountMain() {
        String[] oneArg = new String[1];
        oneArg[0] = realFile;
        try {
            SemSearch.main(oneArg);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, usage is as follows: " 
            + "java SemSearch {world-size} " 
            + "{command-file}";
        assertFuzzyEquals(expected, output);
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
    }
    
    /**
     * tests if the command file argument is a filepath
     */
    public void testFilePathMain() {
        String[] nonFileArg = new String[2];
        nonFileArg[0] = worldCorrect;
        nonFileArg[1] = nonFile;
        try {
            SemSearch.main(nonFileArg);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, the last argument was not a file path";
        assertFuzzyEquals(expected, output);
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
    }
    
    /**
     * tests if the command file argument is empty
     */
    public void testEmptyFileMain() {
        String[] emptyArg = new String[2];
        emptyArg[0] = worldCorrect;
        emptyArg[1] = ".//emptyFile.txt";
        try {
            SemSearch.main(emptyArg);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, the command file is empty";
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests if the power of two helper correctly returns boolean
     */
    public void testIsPowerOfTwo() {
        //TODO
        assertFalse(SemSearch.isPowerOfTwo(0));
        assertFalse(SemSearch.isPowerOfTwo(-2));
        assertTrue(SemSearch.isPowerOfTwo(32));
    }
    /**
     * tests if the world size argument is an integer
     */
    public void testWorldInteger() {
        String[] nonIntArg = new String[2];
        nonIntArg[0] = worldDecimal;
        nonIntArg[1] = realFile;
        
        try {
            SemSearch.main(nonIntArg);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        
        String output = systemOut().getHistory();
        String expected = "Error, world size " 
            + "was not an integer";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(expected, output);
    }
    /**
     * tests if the world size argument is power of two
     */
    public void testWorldMain() {
        String[] worldArg = new String[2];
        worldArg[0] = worldIncorrect;
        worldArg[1] = realFile;
        try {
            SemSearch.main(worldArg);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, world size" 
            + " is not a power of 2";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests if the correct arguments pass checks
     * @throws Exception 
     */
    public void testArgsMain() throws Exception {
        String[] correctArg = new String[2];
        correctArg[0] = worldCorrect;
        correctArg[1] = realFile;
        SemSearch.main(correctArg);
        assertNull(thrown);
    }
}

