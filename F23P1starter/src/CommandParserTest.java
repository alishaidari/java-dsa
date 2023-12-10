import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import student.TestCase;

/**
 *  test class for CommandParser and its methods
 *  @author Ali Haidari (alih)
 *  @version 8.30.2023
 */
public class CommandParserTest extends TestCase {

    private File nullFile;
    private File testFile;
    private File insertFile;
    private File searchFile;
    private File deleteFile;
    private File printFile;
    private File emptyFile;
    private Exception thrown;

    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        nullFile = new File("foo.txt");
        testFile = new File(".//P1Sample_input.txt");
        insertFile = new File(".//insert_input.txt");
        searchFile = new File(".//search_input.txt");
        deleteFile = new File(".//delete_input.txt");
        printFile = new File(".//print_input.txt");
        emptyFile = new File(".//space_input.txt");
        thrown = null;
    }

    /**
     * tests the initialization of a CommandParser object
     * @throws IOException 
     */
    public void testInit() throws FileNotFoundException {
        CommandParser nada = null;
        //testing for when a file not is not found 
        try {
            nada = new CommandParser(8, 16, nullFile);
        }
        catch (Exception exception)
        {
            thrown = exception;
        }
        String output = systemOut().getHistory();
        String fileError = "Error, command file arugment not found";
        assertNull(nada);
        assertNotNull(thrown);
        assertFuzzyEquals(output, fileError);
        assertTrue(thrown instanceof FileNotFoundException);
        
        //testing for when a file is found in local directory system
        CommandParser test = new CommandParser(8, 16, testFile);
        assertNotNull(test);
    }
    
    /**
     * tests the parsing of the insert command 
     * @throws Exception 
     */
    public void testParseInsert() throws Exception {
        CommandParser insertParse = new CommandParser(256, 4, insertFile);
        insertParse.parseCommands();
        String output = systemOut().getHistory();
        String expected = "Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of " 
            + "HCI research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "Size: 173\n"
            + "Insert FAILED - There is already a record with ID 1";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests the parsing of the search command 
     * @throws Exception 
     */
    public void testParseSearch() throws Exception {
        CommandParser searchParse = new CommandParser(256, 4, searchFile);
        searchParse.parseCommands();
        String output = systemOut().getHistory();
        String expected = "Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of " 
            + "HCI research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "Size: 173\n"
            + "Found record with ID 1:\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of " 
            + "HCI research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "Search FAILED -- There is no record with ID 2";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests the parsing of the remove command 
     * @throws Exception 
     */
    public void testParseRemove() throws Exception {
        CommandParser removeParse = new CommandParser(256, 4, deleteFile);
        removeParse.parseCommands();
        String output = systemOut().getHistory();
        String expected = "Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of " 
            + "HCI research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "Size: 173\n"
            + "Record with ID 1 successfully deleted from the database\n"
            + "Delete FAILED -- There is no record with ID 2";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests the parsing of the print command
     * @throws Exception
     */
    public void testParsePrint() throws Exception {
        CommandParser printParse = new CommandParser(256, 4, printFile);
        printParse.parseCommands();
        String output = systemOut().getHistory();
        String expected = "Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of " 
            + "HCI research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "Size: 173\n"
            + "Hashtable: \n"
            + "1: 1 \n"
            + "total records: 1\n"
            + "Freeblock list: \n"
            + "There are no freeblocks in the memory pool";
        assertFuzzyEquals(expected, output);
    }
    
    /**
     * tests the parsing output is blank when no 
     * commands are provided
     * 
     * @throws Exception
     */
    public void testParseSpace() throws Exception {
        CommandParser spaceParse = new CommandParser(256, 4, emptyFile);
        spaceParse.parseCommands();
        String output = systemOut().getHistory();
        String expected = "";
        assertFuzzyEquals(expected, output);
    }

}
