import java.io.File;
import java.io.FileNotFoundException;
import student.TestCase;
/**
 * test class for command parser and its methods
 * @author Ali Haidari
 * @version 12.5.2023
 */
public class CommandParserTest extends TestCase {
    private CommandParser testParse;
    private File testFile;
    private File insertFile;
    private File emptyFile;
    private File wrongFile;
    private File removeFile;
    private File printFile;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() throws FileNotFoundException {
        testFile = new File("hashcommands.txt");
        insertFile = new File("insertcommands.txt");
        wrongFile = new File("nocommands.txt");
        emptyFile = new File("empty.txt");
        removeFile = new File("removecommands.txt");
        printFile = new File("printcommands.txt");
    }
    /**
     * tests if command parser catches wrong command
     * 
     * @throws Exception
     */
    public void testWrongCommands() throws Exception {
        testParse = new CommandParser(10, wrongFile);
        testParse.parseCommands();
        String out = systemOut().getHistory();
        String expected = "Error, unrecognized command";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if command parser prints nothing when empty
     * 
     * @throws Exception
     */
    public void testEmptyCommands() throws Exception {
        testParse = new CommandParser(10, emptyFile);
        testParse.parseCommands();
        String out = systemOut().getHistory();
        String expected = "";
        assertFuzzyEquals(expected, out);
    }
    
    /**
     * tests if command parser outputs correctly when
     * there is a remove artist or song command
     * 
     * @throws Exception
     */
    public void testRemoveCommands() throws Exception {
        testParse = new CommandParser(10, removeFile);
        testParse.parseCommands();
        String failArtist = "does not exist in the Artist database.";
        String failSong = "does not exist in the Song database.";
        String passArtist = "removed from the Artist database.";
        String passSong = "removed from the Song database.";
        String out = systemOut().getHistory();
        assertTrue(out.contains(failArtist));
        assertTrue(out.contains(failSong));
        assertTrue(out.contains(passArtist));
        assertTrue(out.contains(passSong));
    }
    
    /**
     * tests if command parser outputs correctly
     * when there is print graph or table command
     * 
     * @throws Exception
     */
    public void testPrintCommands() throws Exception {
        testParse = new CommandParser(10, printFile);
        testParse.parseCommands();
        String passArtist = "total artists:";
        String passSong = "total songs:";
        String out = systemOut().getHistory();
        assertTrue(out.contains(passArtist));
        assertTrue(out.contains(passSong));
    }
    
    /**
     * tests if command parser outputs correctly
     * when there is a insert command
     * 
     * @throws Exception
     */
    public void testInsertCommands() throws Exception {
        testParse = new CommandParser(10, insertFile);
        testParse.parseCommands();
        String passArtist = "added to the Artist database.";
        String passSong = "added to the Song database.";
        String failInsert = "duplicates a record already in the database.";
        String out = systemOut().getHistory();
        assertTrue(out.contains(passArtist));
        assertTrue(out.contains(passSong));
        assertTrue(out.contains(failInsert));
    }
    
    /**
     * tests if command parser outputs last command
     * correctly when sample input is given 
     * 
     * @throws Exception
     */
    public void testParseCommands() throws Exception {
        testParse = new CommandParser(10, testFile);
        testParse.parseCommands();
        String out = systemOut().getHistory();
        assertTrue(out.contains("total artists: 1"));
    }
}
