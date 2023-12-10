import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Test the Seminar class
 *
 *  @author CS3114/CS5040 staff
 *  @version July 2023, last updated September 2023
 */
public class SeminarTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }
    
    /**
     * checks the initialization process of a seminar
     * @throws Exception
     */
    public void testSeminarInit()
        throws Exception
    {
        String[] keywords = {"Good", "Bad", "Ugly"};

        Seminar mysem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
        String semPrint = mysem.toString();
        System.out.println(semPrint);
        assertTrue(semPrint.equals("ID: 1729, Title: Seminar Title\n" +
            "Date: 2405231000, Length: 75, X: 15, Y: 33, Cost: 125\n" +
            "Description: This is a great seminar\n" +
            "Keywords: Good, Bad, Ugly"));
    }

    /**
     * check the toString method of a seminar
     */
    public void testToString()
    {
        String[] keywords = {"Good", "Bad", "Ugly"};
        String expected = "ID: 1729, Title: Seminar Title\n"
            + "Date: 2405231000, Length: 75, X: 15, Y: 33, Cost: 125\n"
            + "Description: This is a great seminar\n"
            + "Keywords: Good, Bad, Ugly";
        Seminar mysem = new Seminar(1729, "Seminar Title", "2405231000", 75,
            (short)15, (short)33, 125, keywords, "This is a great seminar");
        String semPrint = mysem.toString();
        System.out.println("testtoString");
        System.out.println(semPrint);
        assertTrue(semPrint.equals(expected));
        assertTrue(mysem.id() == 1729);
        assertTrue(mysem.cost() == 125);
        assertTrue(mysem.x() == 15);
        assertTrue(mysem.y() == 33);
        assertTrue(mysem.date().equals("2405231000"));
        String[] tempKeywords = mysem.keywords();
        for (int i = 0; i < keywords.length; i++) {
            assertTrue(tempKeywords[i].equals(keywords[i]));
        }
    }
}
