import java.io.File;  
import java.io.FileNotFoundException;
import java.util.*;
/**
 * parses the command file and output message
 * 
 * @author Ali Haidari (alih)
 * @version 8.24.2023
 */
public class CommandParser {
    private String currLine; 
    private Scanner input;
    private SemProcessor processor;
    /**
     * constructor for the command parser
     * 
     * @param initMemSize : intial memory size for processor
     * @param initHashSize : initial hash size for processor
     * @param commandFile : potential command file
     * @throws FileNotFoundException 
     */
    public CommandParser(int initMemSize, 
        int initHashSize, File commandFile) throws 
        FileNotFoundException {
        // tries to read a file if it exists
        try {
            input = new Scanner(commandFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, command file arugment not found");
            throw e;
        }
        //creates a processor for handling commands
        processor = new SemProcessor(initMemSize, initHashSize);
    }

    /**
     * TODO WRITE TEST CASE FOR THIS    
     * @throws Exception 
     */
    public void parseCommands() throws Exception {
        currLine = null;
        while (input.hasNextLine()) {
            currLine = input.nextLine().trim();
            if (currLine.equals("")) {
                continue;
            }

            if (currLine.contains("insert")) {
                int insertID = Integer.parseInt(
                    currLine.replaceAll("[^0-9]", ""));
                // parse next four lines of data
                String title = input.nextLine().trim();
                String date = input.next();
                int length = input.nextInt();
                short xLoc = input.nextShort();
                short yLoc = input.nextShort();
                int cost = input.nextInt();

                //adjust lines to move to keyword line
                input.nextLine();
                String keyWordLine = input.nextLine().trim();
                String description = input.nextLine().trim();

                //array list for keyword handling 
                ArrayList<String> keyWordList = new ArrayList<String>(
                    Arrays.asList(keyWordLine.split("\\s+")));
                String[] keyWordArr = keyWordList.toArray(
                    new String[keyWordList.size()]);
                //create seminar object
                Seminar toInsert = new Seminar(insertID, title, 
                    date, length, xLoc, yLoc, 
                    cost, keyWordArr, description);
                
                //if id is not found in database insert to database
                if (processor.semInsert(insertID, toInsert)) {
                    System.out.println("Successfully inserted" + 
                        " record with ID " + insertID);
                    System.out.println(toInsert.toString());
                    byte[] toInsertSerial = toInsert.serialize();
                    System.out.println("Size: " + toInsertSerial.length);
                }
                else {
                    System.out.println("Insert FAILED -- " + 
                        "There is already a record with ID " + insertID);
                }
                continue;
            }
            if (currLine.contains("search")) {
                int searchID = Integer.parseInt(
                    currLine.replaceAll("[^0-9]", ""));
                // if id is found in database get element of search
                if (processor.semSearch(searchID) != null) {
                    byte[] found = processor.semSearch(searchID);
                    Seminar foundSem = Seminar.deserialize(found);
                    
                    System.out.println("Found record with ID " 
                        + searchID + ":");
                    System.out.println(foundSem.toString());
                }
                else {
                    System.out.println("Search FAILED -- " + 
                        "There is no record with ID " + searchID);
                }

                continue;
            }
            if (currLine.contains("delete")) {
                int deleteID = Integer.parseInt(
                    currLine.replaceAll("[^0-9]", ""));
                //if id found in database remove from database
                if (processor.semRemove(deleteID)) {
                    System.out.println("Record with ID " + deleteID +
                        " successfully deleted from the database");
                }
                else {
                    System.out.println("Delete FAILED -- " + 
                        "There is no record with ID " + deleteID);
                }
                continue;
            }
            if (currLine.contains("print hashtable")) {
                processor.getHashTable().print();
                continue;
            }
            if (currLine.contains("print blocks")) {
                processor.getMemManager().dump();
                continue;
            }
        }
    }
}
