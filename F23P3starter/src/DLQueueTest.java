import student.TestCase;
/**
 * test class for DLQueue and its initialization
 * 
 * @author Ali Haidari (alih)
 * @version 10.30.2023
 */
public class DLQueueTest extends TestCase {
    private DLQueue<Integer> testQ;
    /**
     * sets up the tests that follow, used for initialization
     */
    public void setUp() {
        testQ = new DLQueue<Integer>();
    }
    
    /**
     * tests the initialization of a DLQueue
     */
    public void testDLQInit() {
        testQ.dequeue();
        testQ.print();
        testQ.enqueue(5);
        testQ.print();
        testQ.dequeue();
        testQ.print();
        testQ.enqueue(8);
        testQ.print();
        testQ.enqueue(10);
        testQ.enqueue(20);
        testQ.enqueue(40);
        testQ.print();
        testQ.dequeue();
        testQ.print();
        testQ.dequeue();
        testQ.print();
    }
}
