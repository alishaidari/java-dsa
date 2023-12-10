import java.io.File;
import java.io.IOException;
import student.TestCase;

/**
 * test class for SemManager and its methods
 * @author Ali Haidari (alih)
 * @version 8.20.2023
 */
public class SemManagerTest extends TestCase {
    private String memCorrect;
    private String memIncorrect;
    private String hashCorrect;
    private String hashIncorrect;
    private String tempFile;
    private String nonFile;
    private Exception thrown;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        memCorrect = "256";
        memIncorrect = "-255";
        hashCorrect = "64";
        hashIncorrect = "63";
        tempFile = "test.txt";
        nonFile = "test";
        thrown = null;
    }

    /**
     * tests for null argument passed in main
     * @throws IOException 
     */
    public void testNullMain() throws IOException {
        SemManager sem = new SemManager();
        try {
            SemManager.main(null);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String nullError = "Error, usage is as follows: " 
            + "java SemManager {initial-memory-size} " 
            + "{initial-hash-size} {command-file}";
        
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        assertFuzzyEquals(nullError, output);
        assertNotNull(sem);
    }
    
    /**
     * tests for not enough arguments passed in main
     * @throws IOException
     */
    public void testMainArgs() throws IOException {
        SemManager sem = new SemManager();
        String[] twoArgs = {"2", tempFile};
        try {
            SemManager.main(twoArgs);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        
        String output = systemOut().getHistory();
        String nullError = "Error, usage is as follows: " 
            + "java SemManager {initial-memory-size} " 
            + "{initial-hash-size} {command-file}";
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        assertFuzzyEquals(nullError, output);
        assertNotNull(sem);
    }
    
    /**
     * testing if the argument in main is a file path
     * @throws IOException
     */
    public void testFilePathMain() throws IOException {
        SemManager sem = new SemManager();
        String[] threeArgs = {memCorrect, hashCorrect, nonFile};
        try {
            SemManager.main(threeArgs);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        
        String output = systemOut().getHistory();
        String nonFileError = "Error, the last argument was not a file path";
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        assertFuzzyEquals(nonFileError, output);
        assertNotNull(sem);
    }
    /**
     * testing for empty file passed into main
     * @throws IOException
     */
    public void testEmptyFileMain() throws IOException {
        SemManager sem = new SemManager();

        String[] threeArgs = {memCorrect, hashCorrect, ".//emptyFile.txt"};
        try {
            SemManager.main(threeArgs);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String emptyError = "Error, the command file is empty";
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        assertFuzzyEquals(emptyError, output);
        assertNotNull(sem);
    }
    
    /**
     * testing for power of two mem size in main
     * @throws NumberFormatException
     */
    public void testMemMain() throws NumberFormatException {
        SemManager sem = new SemManager();
        String[] wrongMem = {memIncorrect, hashCorrect, tempFile};
        try {
            SemManager.main(wrongMem);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String memError = "Error, {initial-memory-size}" 
            + " is not a power of 2";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(memError, output);
        assertNotNull(sem);
    }
    
    /**
     * testing for power of two general cases
     * @throws NumberFormatException
     */
    public void testPowerOfTwo() throws NumberFormatException {
        SemManager sem = new SemManager();
        String[] wrongMem = {"0", hashCorrect, tempFile};
        try {
            SemManager.main(wrongMem);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String memError = "Error, {initial-memory-size}" 
            + " is not a power of 2";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(memError, output);
        assertNotNull(sem);
    }
    
    /**
     * testing for power of two hash size
     * @throws NumberFormatException
     */
    public void testHashMain() throws NumberFormatException {
        SemManager sem = new SemManager();
        String[] wrongHash = {memCorrect, hashIncorrect, tempFile};
        try {
            SemManager.main(wrongHash);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String hashError = "Error, {initial-hash-size}" 
            + " is not a power of 2";
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        assertFuzzyEquals(hashError, output);
        assertNotNull(sem);
    }
    
    /**
     * testing for correct arguments in main
     * 
     * @throws Exception 
     */
    public void testArgsMain() throws Exception {
        SemManager sem = new SemManager();
        String[] correctArgs = {memCorrect, 
            hashCorrect, ".//P1Sample_input.txt"};
        SemManager.main(correctArgs);
        assertNotNull(sem);
        assertNull(thrown);
    }
}

