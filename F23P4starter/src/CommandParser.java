import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * parses the commands from the input
 * file with the music database class
 * 
 * @author Ali Haidari (alih)
 * @version 11.3.2023
 */
public class CommandParser {
    private Scanner input;
    
    private MusicDatabase mainDatabase;
    
    
    /**
     * constructor for the command parser
     * 
     * @param initSize : initial size of hash table
     * @param inFile : file to be parsed
     * @throws FileNotFoundException 
     */
    public CommandParser(int initSize, File inFile) 
        throws FileNotFoundException {
        //try to scan file if it exists
        try {
            input = new Scanner(inFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, command file arugment not found");
            throw e;
        }
        mainDatabase = new MusicDatabase(initSize);
    }
    
    /**
     * parses the input and makes
     * calls to music database 
     * 
     * @throws Exception 
     */
    public void parseCommands() throws Exception {
        String[] lineSplit;
        String[] musicSplit;
        String command;
        String data;
        String currLine;
        while (input.hasNextLine()) {
            //read in current line
            currLine = input.nextLine().trim();
            //skip blank lines
            if (currLine.equals("")) {
                continue;
            }
            //split the command from the data
            lineSplit = currLine.split(" ", 2);
            command = lineSplit[0];
            data = lineSplit[1];
            switch(command) {
                case "remove":
                    //we must decide whether it is artist or song
                    musicSplit = data.split(" " , 2);
                    if (musicSplit[0].equals("song")) {
                        mainDatabase.removeSong(musicSplit[1]);
                    }
                    else {
                        mainDatabase.removeArtist(musicSplit[1]);
                    }
                    break;
                case "insert":
                    //we can just split data and insert 
                    musicSplit = data.split("<SEP>");
                    mainDatabase.insert(musicSplit[0], musicSplit[1]);
                    break;
                case "print":
                    //must decide to print tables or graph
                    if (data.equals("artist")) {
                        mainDatabase.printArtist();
                    }
                    else if (data.equals("song")) {
                        mainDatabase.printSong();
                    }
                    else {
                        mainDatabase.printGraph();
                    }
                    break;
                default:
                    System.out.println("Error, unrecognized command"); 
                    break;
            }
        }
    }
}