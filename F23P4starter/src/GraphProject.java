import java.io.File;
import java.io.IOException;

/**
 * Main for Graph project (CS3114/CS5040 Fall 2023 Project 4).
 * Usage: java GraphProject <init-hash-size> <command-file>
 *
 * @author Ali Haidari
 * @version 12/5/2023
 *
 */

// On my honor:
// - I have not used source code obtained from another current or
//   former student, or any other unauthorized source, either
//   modified or unmodified.
//
// - All source code and documentation used in my program is
//   either my original work, or was derived by me from the
//   source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
//   anyone other than my partner (in the case of a joint
//   submission), instructor, ACM/UPE tutors or the TAs assigned
//   to this course. I understand that I may discuss the concepts
//   of this program with other students, and that another student
//   may help me debug my program so long as neither of us writes
//   anything during the discussion or modifies any computer file
//   during the discussion. I have violated neither the spirit nor
//   letter of this restriction.

public class GraphProject
{
    /**
     * @param args : command line parameters
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        // checks for correct number of arguments
        if (args == null || args.length != 2) {
            System.out.println("Error, usage is as follows: " 
                + "java GraphProject " 
                + "{init-hash-size} {command-file}");
            throw new IOException();
        }
        
        // checks for integer data being passed in
        int hashSize = 0;
        try {
            hashSize = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("Error, {initial-hash-size}" 
                + " is not an integer");
            throw e;
        }
        
        // checks if the second argument is a file
        File commandFile = new File(args[1]);
        if (!commandFile.isFile()) {
            System.out.println("Error, the last argument was not a file path");
            throw new IOException();
        }
        
        //parse commands
        CommandParser mainParse = new CommandParser(hashSize, commandFile);
        mainParse.parseCommands();
    }
}
