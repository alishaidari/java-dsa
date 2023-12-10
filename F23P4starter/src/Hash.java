/**
 * Hash class holds the functions
 * for hashing and probing within
 * the HashTable
 *
 * @author Ali Haidari (alih)
 * @version 11.3.2023
 */

public class Hash {

    /**
     * compute the sfold hash function
     * 
     * @param s : the string that we are hashing
     * @param length : length of the hash table 
     * @return integer hash value (the home slot)
     */
    public static int h(String s, int length)
    {
//        int intLength = s.length() / 4;
//        long sum = 0;
//        for (int j = 0; j < intLength; j++)
//        {
//            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
//            long mult = 1;
//            for (int k = 0; k < c.length; k++)
//            {
//                sum += c[k] * mult;
//                mult *= 256;
//            }
//        }
//        char[] c = s.substring(intLength * 4).toCharArray();
//        long mult = 1;
//        for (int k = 0; k < c.length; k++)
//        {
//            sum += c[k] * mult;
//            mult *= 256;
//        }
//
//        return (int)(Math.abs(sum) % length);

        //change to hash above with any issues
        long sum = 0;
        long mul = 1;
        for (int i = 0; i < s.length(); i++) {
            mul = (i % 4 == 0) ? 1 : mul * 256;
            sum += s.charAt(i) * mul;
        }
        return (int)(Math.abs(sum) % length);
    }
    
    /**
     * quadratic probing collision resolution
     * 
     * @param home : key hashed to home
     * @param i : collision count
     * @param length : length of the hash table 
     * @return integer index of probed location
     */
    public static int p(int home, int i, int length) {
        return (home + (i * i)) % length;
    }
}
