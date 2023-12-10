import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import student.TestCase;
/**
 * test class for BufferPool and its initialization
 * 
 * @author Ali Haidari (alih)
 * @version 10.31.2023
 */
public class BufferPoolTest extends TestCase {
    private BufferPool testPool;
    private RandomAccessFile nonFile;
    private RandomAccessFile twoFile;
    private RandomAccessFile fourFile;
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        nonFile = null;
        try {
            fourFile = new RandomAccessFile("threeBlock.txt", "rwd");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testPool = new BufferPool(fourFile, 1);
    }
    
    /**
     * tests the initialization of a BufferPool
     * 
     * @throws IOException
     */
    public void testBPInit() throws IOException {
        System.out.println(testPool.getRAF().length() 
            + " = " 
            + testPool.getRAF().length() / BufferPool.BLOCK_SIZE 
            + " blocks");
        byte[] temp = new byte[4];
        testPool.request(temp, 4, 4);
        testPool.print();
        String out = new String(temp);
        System.out.println(out);
        testPool.deliver(temp, 12284, 4);
        testPool.print();
        
        
        //source sourcePos, destPos, 4 bytes;
//        //write [0-3] to [8188-8191]
//        testPool.writeToBuffer(0, 8188, 4);
//        //write [8188-8191] to [4-7]
//        testPool.writeToBuffer(8188, 4, 4);
//        //write [12284-12287] to [4096-4099]
//        testPool.writeToBuffer(12284, 4096, 4);
        //write [0-3] to [4092-4095];
//        testPool.writeToBuffer(0, 4092, 4);
        
        
        
//        testPool.print();
//        //req 2
//        testPool.request(10000);
//        testPool.print();
//        //req 0
//        testPool.request(4000);
//        testPool.print();
//        //req 1
//        testPool.request(5000);
//        testPool.print();
//        //req 3
//        testPool.request(14000);
//        testPool.print();
//        //req 1
//        testPool.request(5000);
//        testPool.print();
    }
}
