import java.io.File;
import java.io.IOException;


//On my honor:
//- I have not used source code obtained from another current or
//former student, or any other unauthorized source, either
//modified or unmodified.
//
//- All source code and documentation used in my program is
//either my original work, or was derived by me from the
//source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
//anyone other than my partner (in the case of a joint
//submission), instructor, ACM/UPE tutors or the TAs assigned
//to this course. I understand that I may discuss the concepts
//of this program with other students, and that another student
//may help me debug my program so long as neither of us writes
//anything during the discussion or modifies any computer file
//during the discussion. I have violated neither the spirit nor
//letter of this restriction.

/**
 * The class containing the method for SeminarManagment
 *
 * @author Ali Haidari (alih)
 * @version 8.20.2023
 */
public class SemManager {
    /**
     * @param args : command line parameters
     * @throws Exception 
     */
    public static void main(String[] args) throws 
        Exception {

        // checks for correct number of arguments
        if (args == null || args.length != 3) {
            System.out.println("Error, usage is as follows: " 
                + "java SemManager {initial-memory-size} " 
                + "{initial-hash-size} {command-file}");
            throw new IOException();
        }

        //required to be declared for try/catch and powers of two
        int memSize = 0;
        int hashSize = 0;
        // checks for correct integer data types being passed in
        try {
            memSize = Integer.parseInt(args[0]);
            hashSize = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Error, {initial-memory-size} or " 
                + "{initial-hash-size} were not integers");
            throw e;
        }

        // checks if the memory size is a power of 2 
        if (!isPowerOfTwo(memSize)) {
            System.out.println("Error, {initial-memory-size}" 
                + " is not a power of 2");
            throw new NumberFormatException();
        }
        // checks if the hash size is a power of 2
        if (!isPowerOfTwo(hashSize)) {
            System.out.println("Error, {initial-hash-size}" 
                + " is not a power of 2");
            throw new NumberFormatException();
        }
        
        //checks if string argument is a file 
        File commandFile = new File(args[2]);
        if (!commandFile.isFile()) {
            System.out.println("Error, the last argument was not a file path");
            throw new IOException();
        }
        
        //checks if the command file contains data
        if (commandFile.length() == 0) {
            System.out.println("Error, the command file is empty");
            throw new IOException();
        }

        // command parser will catch if file is not found
        CommandParser mainParser = new CommandParser(memSize, 
            hashSize, commandFile);
        mainParser.parseCommands();
    }
    /**
     * tests if an input integer is a power of two
     * @param inputN : the integer to be tested
     * @return true if pass, false otherwise
     */
    private static boolean isPowerOfTwo(int inputN) {
        return (inputN != 0 && ((inputN & (inputN - 1)) == 0));
    }
}
