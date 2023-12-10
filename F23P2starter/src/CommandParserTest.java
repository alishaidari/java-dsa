import java.io.File;
import java.io.FileNotFoundException;
import student.TestCase;

/**
 * test for CommandParser and its methods
 * @author Ali Haidari (alih)
 * @version 9.20.2023
 */
public class CommandParserTest extends TestCase {

    private File emptyFile;
    private File notFoundFile;
    private File insertSyntaxFile;
    private File searchSyntaxFile;
    private File deleteSyntaxFile;
    private File printSyntaxFile;
    private File testFile;
    private CommandParser testParse;
    private Exception thrown;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        emptyFile = new File(".//emptyFile.txt");
        notFoundFile = new File("foo.txt");
        insertSyntaxFile = new File(".//P2syntaxInsert_input.txt");
        searchSyntaxFile = new File(".//P2syntaxSearch_input.txt");
        deleteSyntaxFile = new File(".//P2syntaxDelete_input.txt");
        printSyntaxFile = new File(".//P2syntaxPrint_input.txt");
        testFile = new File(".//P2Sample_input.txt");
        thrown = null;
    }
    
    /**
     * tests if a command parser correctly outputs for file not found
     */
    public void testFileNotFound() {
        CommandParser nada = null;
        //testing for when a file not is not found 
        try {
            nada = new CommandParser(16, notFoundFile);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String expected = "Error, command file arugment not found";
        assertNull(nada);
        assertNotNull(thrown);
        assertFuzzyEquals(output, expected);
        assertTrue(thrown instanceof FileNotFoundException);
    }
    /**
     * tests if a command parser correctly outputs 
     * nothing for an empty file
     * 
     * @throws FileNotFoundException
     */
    public void testEmptyFileParse() throws FileNotFoundException {
        CommandParser emp = null;
        emp = new CommandParser(16, emptyFile);
        emp.parseCommands();
        String output = systemOut().getHistory();
        String expected = "";
        assertEquals(expected, output);
    }
    
    /**
     * tests if a command parser correctly parses insert command
     * @throws FileNotFoundException
     */
    public void testSyntaxInsert() throws FileNotFoundException {
        testParse = new CommandParser(128, insertSyntaxFile);
        testParse.parseCommands();
        String output = systemOut().getHistory();
        String expectedPass = "Successfully inserted record with ID 1\nID: 1";
        String expectedDupe = "Insert FAILED - There is already a record with";
        String expectedFail = "Insert FAILED - " 
            + "Bad x, y coordinates: -1, 0\n"
            + "Insert FAILED - Bad x, y coordinates: 0, -1\n"
            + "Insert FAILED - Bad x, y coordinates: 2000, 0\n"
            + "Insert FAILED - Bad x, y coordinates: 0, 2000";
        assertTrue(output.contains(expectedPass));
        assertTrue(output.contains(expectedFail));
        assertTrue(output.contains(expectedDupe));
    }
    
    /**
     * tests if a command parser correctly parses search command
     * @throws FileNotFoundException
     */
    public void testSyntaxSearch() throws FileNotFoundException {
        testParse = new CommandParser(128, searchSyntaxFile);
        testParse.parseCommands();
        String output = systemOut().getHistory();
        String expectedIDPass = "Found record with ID 1";
        String expectedIDFail = "Search FAILED -- "
            + "There is no record with ID 20";
        assertTrue(output.contains(expectedIDPass));
        assertTrue(output.contains(expectedIDFail));
        String expectedCostPass = "Seminars with costs in range 30 to 45:\nID:";
        String expectedCostFail = "Seminars with costs in range 0 to 1:\n4";
        assertTrue(output.contains(expectedCostPass));
        assertTrue(output.contains(expectedCostFail));
        String expectedDatePass = "Seminars with dates in range 0 to 1:\nID:";
        String expectedDateFail = "Seminars with dates in range 3 to 4:\n5";
        assertTrue(output.contains(expectedDatePass));
        assertTrue(output.contains(expectedDateFail));
        String expectedKeyPass = "Seminars matching keyword HCI:\nID: 1";
        String expectedKeyFail = "Seminars matching keyword blah:\nSeminars";
        assertTrue(output.contains(expectedKeyPass));
        assertTrue(output.contains(expectedKeyFail));
        
        //FIXME BIN TREE SEARCHING EXPECTED PASS AND FAIL
        String expectedLocPass = "Seminars within 1 units of 0, 1:";
        String expectedLocFail = "Seminars within 1 units of 250, 300:\nFound";
        assertTrue(output.contains(expectedLocPass));
        assertFalse(output.contains(expectedLocFail));
    }
    
    /**
     * tests if a command parser correctly outputs delete command
     * @throws FileNotFoundException
     */
    public void testSyntaxDelete() throws FileNotFoundException {
        testParse = new CommandParser(16, deleteSyntaxFile);
        testParse.parseCommands();
        String output = systemOut().getHistory();
        String expectedPass = "Record with ID 1 successfully deleted";
        String expectedFail = "There is no record with ID 20";
        assertTrue(output.contains(expectedPass));
        assertTrue(output.contains(expectedFail));
    }
    
    /**
     * tests if a command parser correctly outputs on print command
     * @throws FileNotFoundException
     */
    public void testSyntaxPrint() throws FileNotFoundException {
        testParse = new CommandParser(16, printSyntaxFile);
        testParse.parseCommands();
        String output = systemOut().getHistory();
        String expectedIDPass = "ID Tree:";
        String expectedCostPass = "Cost Tree:";
        String expectedDatePass = "Date Tree:";
        String expectedKeyPass = "Keyword Tree:";
        String expectedLocPass = "Location Tree";
        assertTrue(output.contains(expectedIDPass));
        assertTrue(output.contains(expectedCostPass));
        assertTrue(output.contains(expectedDatePass));
        assertTrue(output.contains(expectedKeyPass));
        assertTrue(output.contains(expectedLocPass));
    }
}
