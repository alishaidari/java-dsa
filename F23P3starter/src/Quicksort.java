import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * the class containing the main method
 *
 * @author Ali Haidari (alih)
 * @version 10.17.2023
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class Quicksort {

    /**
     * This method is used to generate a file of a certain size, containing a
     * specified number of records.
     *
     * @param filename the name of the file to create/write to
     * @param blockSize the size of the file to generate
     * @param format the format of file to create
     * @throws IOException throw if the file is not open and proper
     */
    public static void generateFile(String filename, String blockSize,
        char format) throws IOException {
        FileGenerator generator = new FileGenerator();
        String[] inputs = new String[3];
        inputs[0] = "-" + format;
        inputs[1] = filename;
        inputs[2] = blockSize;
        generator.generateFile(inputs);
    }


    /**
     * @param args
     *      Command line parameters.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        //check for correct amount of arguments passed
        if (args == null || args.length != 3) {
            System.out.println("Error, usage is as follows:"
                + " java Quicksort <data-file-name>"
                + " <numb-buffers> <stat-file-name>");
            throw new IOException();
        }
        //check if the file exists
        RandomAccessFile binFile = null;
        try {
            binFile = new RandomAccessFile(args[0], "rw");
        }
        catch (FileNotFoundException e) {
            System.out.println("Error, the first argument"
                + " was not a filepath");
        }
        
        //check if file length is multiple of block size
        if (binFile.length() % BufferPool.BLOCK_SIZE != 0) {
            System.out.println("Error, the file length"
                + " was not a multiple of 4096");
            throw new IOException();
        }
        
        //check if second argument is a number
        int numOfBuffers = 0;
        try {
            numOfBuffers = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Error, the second argument"
                + " was not an integer");
        }
        
        //check the number of buffers in range
        if (numOfBuffers < 1 || numOfBuffers > 20) {
            System.out.println("Error, the number of buffers"
                + " must be in range of 1 - 20");
            throw new NumberFormatException();
        }
        
        //FIXME RUNTIME STATS CHECK
        
        try {
            generateFile("oneBlock.txt", "1", 'a');
            generateFile("twoBlock.txt", "2", 'a');
            generateFile("threeBlock.txt", "3", 'a');
            generateFile("fiveBlock.txt", "5", 'a');
            generateFile("tenBlock.txt", "10", 'a');
            generateFile("thouBlock.txt", "1000", 'a');
            generateFile("hunBlock.txt", "100", 'b');
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //initialize the sort
        Sort mainSort = new Sort(binFile, numOfBuffers);
        //sort the file
//        mainSort.sortFile();  
    }
}
