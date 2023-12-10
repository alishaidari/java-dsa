import java.io.IOException;
import java.io.RandomAccessFile;
import student.TestCase;
/**
 * test class for Sort and its initialization
 * 
 * @author Ali Haidari (alih)
 * @version 10.31.2023
 */
public class SortTest extends TestCase {
    private RandomAccessFile blocks;
    private Sort mainSort;
    private String fileName = "thouBlock.txt";
    
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() throws IOException {
        blocks = new RandomAccessFile(fileName, "rw");
        mainSort = new Sort(blocks, 10);
    }
    
    /**
     * tests the initialization of a Sort
     * 
     * @throws IOException
     */
    public void testSortInit() throws Exception {
        System.out.println("num recs: " + mainSort.getTotalRecords());
        
//      mainSort.sortFile();
//      CheckFile temp = new CheckFile();
//      assertTrue(temp.checkFile(fileName));
        
//        Cache aCache = new Cache();
//        Cache bCache = new Cache();
//        Cache cCache = new Cache();
        
        
//        short zKey = mainSort.getKey(0, cCache);
//        short oKey = mainSort.getKey(1, aCache);
//        System.out.println(cCache + ", " + zKey);
//        System.out.println(aCache + ", " + oKey);
//        System.out.println(new String(cCache.getBB().array()));
//        System.out.println(new String(aCache.getBB().array()));
//        System.out.println("____");
//        cCache.swapCaches(aCache);
//        System.out.println(cCache + ", " + mainSort.getKey(0, cCache));
//        System.out.println(aCache + ", " + mainSort.getKey(1, aCache));
//        System.out.println(new String (cCache.getRec()));
//        System.out.println(new String (aCache.getRec()));
        
        

//        System.out.println(new String(cCache.getBB().array()));
//        System.out.println(new String(aCache.getBB().array()));

        
        

        
//        
//        mainSort.getKey(0, leftCache);
//        mainSort.getKey(1, rightCache);
//        System.out.println(leftCache);
//        System.out.println(rightCache);
//        leftCache.swapCaches(rightCache);
//        System.out.println(leftCache);
//        System.out.println(rightCache);
        
        

        

//        mainSort.getBP().print();
//        mainSort.swap(1023, 2047, pivotCache, rightCache);
        
//        mainSort.getKey(3, pivotCache);
//        mainSort.getKey(2047, otherCache);
//        mainSort.getBP().print();
//        String out = new String(pivotCache.getRec());
//        System.out.println(out);
//        System.out.println(pivotCache.getPos());
//        System.out.println(pivotCache.isEmpty());
        
        
        
//        mainSort.quick(0, mainSort.getTotalRecords()-1);
//        System.out.println(mainSort.getBP().stats());
        
//        mainSort.getBP().print();
//        mainSort.getKey(2047);
//        mainSort.getBP().print();

//    
//
//        mainSort.getBP().getRAF().read(temp);
//
//        String ascii = new String(temp);
//        System.out.println(ascii.length());
//        System.out.println(ascii);
//
    }
}
