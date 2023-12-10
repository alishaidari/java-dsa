import student.TestCase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * test class for GraphProject and initialization
 *
 * @author Ali Haidari (alih)
 * @version 11.13.2023
 */
public class GraphProjectTest extends TestCase {
    private Exception thrown;

    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        thrown = null;

    }

    /**
     * tests initialization of a graph
     */
    public void testQInit()
    {
        GraphProject it = new GraphProject();
        assertNotNull(it);
    }
    
    /**
     * tests if GraphProject catches null usage
     * 
     * @throws Exception
     */
    public void testNullArg() throws Exception {
        try {
            GraphProject.main(null);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        String out = systemOut().getHistory();
        String expected = "Error, usage is as follows: java "
            + "GraphProject {init-hash-size} {command-file}";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if GraphProject catches one arg usage
     * @throws Exception
     */
    public void testOneArgs() throws Exception {
        String[] args = {"10"};
        try {
            GraphProject.main(args);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        String out = systemOut().getHistory();
        String expected = "Error, usage is as follows: java "
            + "GraphProject {init-hash-size} {command-file}";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if GraphProject catches wrong size usage
     * @throws Exception
     */
    public void testWrongSize() throws Exception {
        String[] args = {"a", "foo.txt"};
        try {
            GraphProject.main(args);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof NumberFormatException);
        String out = systemOut().getHistory();
        String expected = "Error, {initial-hash-size}" 
            + " is not an integer";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if GraphProject catches no file usage
     * @throws Exception
     */
    public void testNoFile() throws Exception {
        String[] args = {"10", "foo.txt"};
        try {
            GraphProject.main(args);
        }
        catch (Exception exception) {
            thrown = exception;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IOException);
        String out = systemOut().getHistory();
        String expected = "Error, the last argument was not a file path";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if GraphProject works with correct usage
     * @throws Exception
     */
    public void testSuccessArgs() throws Exception {
        String[] args = {"10", "P4sampleInput.txt"};
        GraphProject.main(args);
        String out = systemOut().getHistory();
        String expected = "database";
        assertTrue(out.contains(expected));
    }
    
    // ----------------------------------------------------------
    /**
     * read contents of a file into a string
     * 
     * @param path : File name
     * @return string representation of file
     * @throws IOException
     */
    static String readFile(String path)
        throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }
}