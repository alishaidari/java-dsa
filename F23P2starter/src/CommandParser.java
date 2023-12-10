import java.io.File;  
import java.io.FileNotFoundException;
import java.util.*;
/**
 * parses the command file and output message
 * 
 * @author Ali Haidari (alih)
 * @version 9.18.2023
 */
public class CommandParser {
    private String currLine; 
    private Scanner input;
    private SemProcessor process;
    private int worldSize;

    /**
     * constructor for the command parser
     * 
     * @param inWorldSize : initial world size for searcher
     * @param commandFile : potential command file
     */
    public CommandParser(int inWorldSize, File commandFile) 
            throws FileNotFoundException {
        //tries to read a file if it exists
        try {
            input = new Scanner(commandFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, command file arugment not found");
            throw e;
        }
        input = new Scanner(commandFile);
        worldSize = inWorldSize;
        process = new SemProcessor(worldSize);
    }

    /**
     * parses the command file and manages searcher accordingly
     */
    public void parseCommands() {
        currLine = null;
        while (input.hasNextLine()) {
            currLine = input.nextLine().trim();
            currLine = currLine.replaceAll("\\s+", " ");

            //parses the insert command
            if (currLine.contains("insert")) {
                int insertID = Integer.parseInt(
                    currLine.replaceAll("[^\\d-]", ""));
                // parse seminar data 
                String title = input.nextLine().trim();
                String date = input.next();
                int length = input.nextInt();
                short xLoc = input.nextShort();
                short yLoc = input.nextShort();
                int cost = input.nextInt();
                //check if coordinates are within bounds of world size
                if ((xLoc < 0 || xLoc >= worldSize) ||
                    (yLoc < 0 || yLoc >= worldSize)) {
                    System.out.println("Insert FAILED - Bad x, y coordinates: " 
                        + xLoc + ", " + yLoc);
                    continue;
                }

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
                if (process.semInsert(toInsert)) {
                    System.out.println("Successfully inserted record with ID "
                        + insertID);
                    System.out.println(toInsert.toString());
                }
                else {
                    System.out.println("Insert FAILED - " 
                        + "There is already a record with ID "
                        + insertID);
                }
                continue;
            }
            //parses ID exact match search
            if (currLine.contains("search ID")) {
                int searchID = Integer.parseInt(
                    currLine.replaceAll("[^0-9]", ""));
                Seminar found = process.semSearchID(searchID);
                if (found != null) {
                    System.out.println("Found record with ID " 
                        + searchID + ":");
                    System.out.println(found.toString());
                }
                else {
                    System.out.println("Search FAILED -- " + 
                        "There is no record with ID " + searchID);
                }
                continue;
            }

            //parses the cost range search
            if (currLine.contains("search cost")) {
                String numString = currLine.replaceAll("[^\\d-]", " ").trim();
                String[] costArr = numString.split("\\s+");
                int lowCost = Integer.parseInt(costArr[0]);
                int highCost = Integer.parseInt(costArr[1]);
                BST<Integer, Seminar> foundCosts = 
                    process.semSearchCost(lowCost, highCost);
                System.out.println("Seminars with costs in range " + 
                    lowCost + " to " + highCost + ":");
                int currVisited = foundCosts.visited();
                if (foundCosts.size() != 0) {
                    foundCosts.values();
                }
                System.out.println(currVisited 
                    + " nodes visited in this search");
                continue;
            }

            //parses the date range search
            if (currLine.contains("search date")) {
                String numString = currLine.replaceAll("[^\\d-]", " ").trim();
                String[] dateArr = numString.split("\\s+");
                String lowDate = dateArr[0];
                String highDate = dateArr[1];
                BST<String, Seminar> foundDates = 
                    process.semSearchDate(lowDate, highDate);
                System.out.println("Seminars with dates in range " + 
                    lowDate + " to " + highDate + ":");
                int currVisited = foundDates.visited();
                if (foundDates.size() != 0) {
                    foundDates.values();  
                }
                System.out.println(currVisited 
                    + " nodes visited in this search");
                continue;
            }

            //parses keyword all instances search
            if (currLine.contains("search keyword")) {
                String searchKeyword = 
                    currLine.replaceAll("^.*?(\\w+)\\W*$", "$1");
                BST<String, Seminar> foundKeyword = 
                    process.semSearchKeyword(searchKeyword);
                System.out.println("Seminars matching keyword " + 
                    searchKeyword + ":");
                if (foundKeyword.size() != 0) {
                    foundKeyword.values();
                }
                continue;
            }

            //TODO FIX THE BINTREE FOR SEARCHING
            //handles location radius search
            if (currLine.contains("search location")) {
                String numString = currLine.replaceAll("[^\\d-]", " ").trim();
                String[] locArr = numString.split("\\s+");
                int xLoc = Integer.parseInt(locArr[0]);
                int yLoc = Integer.parseInt(locArr[1]);
                int radius = Integer.parseInt(locArr[2]);
                System.out.println("Seminars within " 
                    + radius + " units of " 
                    + xLoc + ", " 
                    + yLoc + ":");
            }

            //parses the delete command
            if (currLine.contains("delete")) {
                int deleteID = Integer.parseInt(
                    currLine.replaceAll("[^0-9]", ""));
                if (process.semDelete(deleteID)) {
                    System.out.println("Record with ID " 
                        + deleteID 
                        + " successfully deleted from the database");
                }
                else {
                    System.out.println("Delete FAILED -- " + 
                        "There is no record with ID " + deleteID);
                }
                
            }

            //parses the print ID command
            if (currLine.contains("print ID")) {
                System.out.println("ID Tree:");
                process.semPrintIDTree();
                continue;
            }

            //parses the print cost command
            if (currLine.contains("print cost")) {
                System.out.println("Cost Tree:");
                process.semPrintCostTree();
                continue;
            }

            //parses the print date command
            if (currLine.contains("print date")) {
                System.out.println("Date Tree:");
                process.semPrintDateTree();
                continue;
            }

            //parses the print keyword command
            if (currLine.contains("print keyword")) {
                System.out.println("Keyword Tree:");
                process.semPrintKeywordTree();
                continue;
            }

            
            //parses the print location command
            if (currLine.contains("print location")) {
                System.out.println("Location Tree:");
                process.semPrintLocTree();
                continue;
            }
        }
    }
}
