import student.TestCase;

/**
 * test file for quick sort and its methods
 * 
 * @author Ali Haidari (alih)
 * @version 10.17.2023
 */
public class QuicksortTest extends TestCase {
    private CheckFile fileChecker;

    /**
     * Sets up the tests that follow. In general, used for initialization.
     */
    public void setUp() {
        fileChecker = new CheckFile();
    }

    /**
     * This method is a demonstration of the file generator and file checker
     * functionality. It calles generateFile to create a small "ascii" file.
     * It then calls the file checker to see if it is sorted (presumably not
     * since we don't call a sort method in this test, so we assertFalse).
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testFileGenerator()
        throws Exception
    {
        String[] args = new String[3];
        args[0] = "input.txt";
        args[1] = "1";
        args[2] = "statFile.txt";
        Quicksort.generateFile("input.txt", "1", 'a');
        Quicksort.main(args);
//        assertTrue(fileChecker.checkFile("input.txt"));
        //TODO
        // In a real test we would call the sort
        // Quicksort.main(args);
        // In a real test, the following would be assertTrue()
        
    }

    /**
     * Get code coverage of the class declaration.
     * @throws Exception 
     */
    public void testQInit() throws Exception {
        Quicksort tree = new Quicksort();
        assertNotNull(tree);
//        String[] args = new String[3];
//        args[0] = "threeBlock.txt";
//        args[1] = "1";
//        args[2] = "statFile.txt";
//        Quicksort.main(args);
//        assertTrue(fileChecker.checkFile(args[0]));
    }
}
