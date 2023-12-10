/**
 * table class holds an 
 * extensible open addressing  
 * array with s-fold hashing and
 * quadratic probing collision 
 * resolution
 * 
 * @author Ali Haidari (alih)
 * @version 8.24.2023
 * @param <K> : generic comparable key
 * @param <V> : generic value
 */
public class HashTable<K extends Comparable<K>, V> {
    private int entryCount;
    private int size;
    private boolean expandFlag;
    private KVPair<K, V>[] table;
    private KVPair<K, V> tombStone = new KVPair<K, V>();
    
    /**
     * constructor for the table object 
     * 
     * @param initSize : initial size of array of KVPairs
     */
    @SuppressWarnings("unchecked") // allow for generic array allocation
    public HashTable(int initSize) {
        this.entryCount = 0;
        this.size = initSize;
        this.table = (KVPair<K, V>[]) new KVPair[size];
        this.expandFlag = false;
    }
    
    /**
     * gets the current size of the table
     * 
     * @return size of table
     */
    public int currSize() {
        return size;
    }
    
    /**
     * gets the current entries of table
     * 
     * @return entry count of table
     */
    public int currCount() {
        return entryCount;
    }
    
    /**
     * gets the table of KVPairs
     * 
     * @return table in table class
     */
    public KVPair<K, V>[] table() {
        return table;
    }
    
    /**
     * getter for a tombstone
     * 
     * @return KVPair that holds nulls
     */
    public KVPair<K, V> tomb() {
        return tombStone;
    }
    
    /**
     * flag that tells if table has expanded
     * 
     * @return true if expanded, false otherwise
     */
    public boolean isExpanded() {
        return expandFlag;
    }
    
    /**
     * setter for expand flag
     * 
     * @param isExpanded : boolean to be set
     */
    public void setExpanded(boolean isExpanded) {
        expandFlag = isExpanded;
    }

    /**
     * insert a K key, V value pair into the table
     * 
     * @precondition assumes key is string 
     * @param key : key to be inserted
     * @param value : value to be inserted
     * @return true if inserted, false otherwise
     * @throws Exception 
     */
    public boolean insert(K key, V value) throws Exception {
        
        //expand and rehash before trying to insert
        if (this.currCount() >= this.currSize() / 2) {
            this.expand();
        }
       
        //will throw exception if key is not string
        int home = h(key);
        int pos = home;
        for (int i = 1; table[pos] != null && 
                !table[pos].sameAs(tombStone); i++) {
            if (key.equals(table[pos].key())) {
                return false;
            }
            pos = p(home, i); // probe
        }
 
        table[pos] = new KVPair<K, V>(key, value);  
        entryCount++;
        return true;
    }
    
    /**
     * retrieves a value based off an integer key
     * 
     * @param key : key to be searched
     * @return value if found in table, null otherwise
     * @throws Exception 
     */
    public V search(K key) throws Exception {
        //will throw exception if key is not string
        int home = h(key);
        int pos = home;
        for (int i = 1; table[pos] != null &&
                !key.equals(table[pos].key()); i++) {
            pos = p(home, i);
        }
        // key not in hash table
        if (table[pos] == null) {
            return null; 
        }
        //value is found at position
        V valueFound = table[pos].value();
        return valueFound;
    }
    
    /**
     * removes a KVPair from table if found
     * 
     * @param key : key to be removed 
     * @return true if removed from table, false otherwise
     * @throws Exception 
     */
    public V remove(K key) throws Exception {
        //will throw exception if key is not string
        int home = h(key);
        int pos = home;
        for (int i = 1; table[pos] != null &&
            (!key.equals(table[pos].key()) || 
                table[pos].sameAs(tombStone)); i++) {
            pos = p(home, i); // probe
        }
        //key not found in table
        if (table[pos] == null) {
            return null;
        }
        //key found in table
        else {
            V valueOfRemoved = table[pos].value();
            table[pos] = tombStone;
            entryCount--;
            return valueOfRemoved;
        }
    }
    
    /**
     * prints out occupied hash table slots and their keys
     */
    public void print() {
        System.out.println("table: ");
        for (int i = 0; i < this.currSize(); i++) {
            if (table[i] != null) {
                if (table[i].sameAs(tombStone)) {
                    System.out.print(i + ": TOMBSTONE\n");
//                    System.out.println(table[i].toString());
                }
                else {
                    System.out.print(i + ": ");
                    System.out.printf("%s \n", table[i].key());
                }
            }
        }
        System.out.println("total records: " + this.currCount());
        System.out.println("total size: " + this.currSize());
    }
    
    /**
     * helper function that doubles hash table 
     * size and rehashes key values, when next 
     * insert will cause table to be over half full
     * 
     * @throws Exception 
     */
    @SuppressWarnings("unchecked") // allow for generic array allocation
    public void expand() throws Exception {        
        //hold onto current size
        int previous = this.currSize();
        //double the current size
        int expanded = this.currSize() * 2;
        //set the size to expanded
        this.size = expanded;
        //declare pointers for home and pos
        int home;
        int pos;
        //create a new table of expanded size
        KVPair<K, V>[] expandTable = new KVPair[expanded];
        for (int i = 0; i < previous; i++) {

            if (table[i] != null && 
                !table[i].sameAs(tombStone)) {
                home = h(table[i].key());
                pos = home;
                //probe if required
                for (int j = 1;  expandTable[pos] != null; j++) {
                    pos = p(home, j);
                }
                //expanded table holds same as hash
                expandTable[pos] = table[i];
            }
        }
        this.table = expandTable;
        this.setExpanded(true);
    }
    
    /**
     * helper s-fold hash function
     * 
     * will hash a string object
     * 
     * @precondition : key should be string
     * @param inKey : string to be hashed
     * @return home slot of key, negative one otherwise
     * @throws Exception 
     */
    private int h(K inKey) throws Exception {
        Object keyO = (Object)inKey;
        if (keyO == null || keyO.getClass() != String.class) {
            throw new Exception("Error, wrong key type");
        }
        return Hash.h((String)inKey, this.currSize());
    }
    
    /**
     * helper probe function that uses quadratic probe
     * 
     * @param hashedKey : home slot for hashed key
     * @param i : current index of collision
     * @return return the proper index after probing
     */
    private int p(int hashedKey, int i) {
        return Hash.p(hashedKey, i, this.currSize());
    }
}
