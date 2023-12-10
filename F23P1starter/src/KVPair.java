/**
 * KVPair class that holds a key of comparable type T and a
 * value object
 * 
 * @author Ali Haidari
 * @version 8/30/2023
 * @param <K> : the generic key comparable
 * @param <V> : the generic value 
 */
public class KVPair<K, V> {
    private K key;
    private V value;
    
    /**
     * empty constructor for KVPair
     */
    public KVPair() { 
        this.key = null; 
        this.value = null; 
    }

    /**
     * 
     * constructor for KVPair
     * initializes the key and value
     * 
     * @param initKey : key generic
     * @param initVal : value generic
     */
    public KVPair(K initKey, V initVal) {
        this.key = initKey;
        this.value = initVal;
    }

    /**
     * gets the key in KVPair
     * 
     * @return the key
     */
    public K key() {
        return key;
    }

    /**
     * sets the key in KVPair
     * 
     * @param newKey : the key to set
     */
    public void setKey(K newKey) {
        this.key = newKey;
    }

    /**
     * gets the value in KVPair
     * 
     * @return the value
     */
    public V value() {
        return value;
    }

    /**
     * sets the value in KVPair
     * 
     * @param newValue : the value to set
     */
    public void setValue(V newValue) {
        this.value = newValue;
    }
    
    /**
     * checks if two KVPairs are contain same data
     * 
     * @param other : second KVPair to be compared
     * @return true if keys and values are equal, false otherwise
     */
    public boolean sameAs(KVPair<K, V> other) {
        boolean output = false;
        if (this.key() == other.key() && this.value() == other.value()) {
            output = true;
        }
        return output;
    }
    

    /**
     * creates the string form of the KVPair 
     * 
     * @return the string form of KVPair or 'null' if pair is null
     */
    public String toString() {
        String retKey;
        String retVal;
        if (key == null) {
            retKey = "null";
        }
        else {
            retKey = key.toString();
        }

        if (value == null) {
            retVal = "null";
        }
        else {
            retVal = value.toString();
        }
        
        return "(" + retKey + ", " + retVal + ")";
    }

}
