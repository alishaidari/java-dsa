/**
 * implements closed hash table with double
 * hashing and expansion of table
 * 
 * @author Ali Haidari (alih)
 * @version 8.24.2023
 * @param <K> : generic comparable key
 * @param <V> : generic value
 */
public class HashTable<K extends Comparable<K>, V> {
    
    private int entryCount;
    private int size;
    private KVPair<K, V>[] hashTable;
    private KVPair<K, V> tombStone = new KVPair<K, V>();
    
    /**
     * constructor for the HashTable object 
     * 
     * @param initSize : initial size of array of KVPairs
     */
    @SuppressWarnings("unchecked") // allow for generic array allocation
    public HashTable(int initSize) {
        this.entryCount = 0;
        this.size = initSize;
        this.hashTable = (KVPair<K, V>[]) new KVPair[size];
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
     * gets the table of KVPairs
     * @return table in HashTable class
     */
    public KVPair<K, V>[] table() {
        return hashTable;
    }
    /**
     * gets the current entries of table
     * @return entry count of table
     */
    public int currCount() {
        return entryCount;
    }
    
    /**
     * insert a K key, V value pair into the hashTable
     * 
     * @precondition assumes key is integer 
     * @param key : key to be inserted
     * @param value : value to be inserted
     * @return true if inserted, false otherwise
     */
    public boolean insert(K key, V value) {
        
        //TODO check for tombstones
        if (this.currCount() >= this.currSize() / 2) {
            this.expand();
        }
       
        //will throw number format exception if key is not int
        int home = h(key);
        int pos = home;
        for (int i = 1; hashTable[pos] != null && 
                !hashTable[pos].sameAs(tombStone); i++) {
            if (key == hashTable[pos].key()) {
                System.out.println("Error, duplicates not " 
                    +  "allowed in hash table");
                return false;
            }
            pos = (home + p((Integer)key, i)) % this.currSize(); // probe
        }
 
        hashTable[pos] = new KVPair<K, V>(key, value);  
        entryCount++;
        return true;
    }
    
    /**
     * retrieves a value based off an integer key
     * 
     * @param key : key to be searched
     * @return value if found in hashTable, null otherwise
     */
    public V search(K key) {
        //will throw number format exception if key is not int
        int home = h(key);
        int pos = home;
        //TODO check for tombstones
        for (int i = 1; hashTable[pos] != null &&
                key != hashTable[pos].key(); i++) {
            pos = (home + p((Integer)key, i)) % this.currSize(); // probe
        }
        if (hashTable[pos] == null) {
            return null; // key not in hash table
        }
        V valueFound = hashTable[pos].value();
        return valueFound;
    }
    
    /**
     * removes a KVPair from hashTable if found
     * 
     * @param key : key to be removed 
     * @return true if removed from hashTable, false otherwise
     */
    public V remove(K key) {
        //will throw number format exception if key is not int
        int home = h(key);
        int pos = home;
        //TODO check for tombstones
        for (int i = 1; hashTable[pos] != null &&
            (key != hashTable[pos].key() || 
            hashTable[pos].sameAs(tombStone)); i++) {
            pos = (home + p((Integer)key, i)) % this.currSize(); // probe
        }
        //key not found in hashTable
        if (hashTable[pos] == null) {
            return null;
        }
        //key found in hashTable
        else {
            V valueOfRemoved = hashTable[pos].value();
            hashTable[pos] = tombStone;
            entryCount--;
            return valueOfRemoved;
        }
    }
       
    /**
     * prints out the hashTable
     */
    public void print() {
        System.out.println("Hashtable: ");
        for (int i = 0; i < this.currSize(); i++) {
            if (hashTable[i] != null) {
                if (hashTable[i].sameAs(tombStone)) {
                    System.out.print(i + ": TOMBSTONE\n");
//                    System.out.println(hashTable[i].toString());
                }
                else {
                    System.out.print(i + ": ");
                    System.out.printf("%s \n", hashTable[i].key());
                }
            }
        }
        System.out.println("total records: " + this.currCount());
    }
    
    /**
     * helper function that doubles hashTable 
     */
    @SuppressWarnings("unchecked") // allow for generic array allocation
    private void expand() {
        int home;
        int pos;
        int newSize = currSize() * 2;
        this.size = newSize;
        KVPair<K, V>[] temp =  (KVPair<K, V>[])new KVPair[newSize];
       
        for (int i = 0; i < currSize() / 2; i++) {
            if (hashTable[i] != null && !hashTable[i].sameAs(tombStone)) {
              //will throw number format exception if key is not int
                home = h(hashTable[i].key());
                pos = home;
                
                //probe to rehash into expanded table
                for (int j = 1; temp[pos] != null; j++) {
                    pos = (home + p((Integer)hashTable[i].key(), j)) % newSize;
                }
                
                temp[pos] = hashTable[i];
 
  
            }
        }
        this.hashTable = temp;
        System.out.println("Hash table expanded to " + 
            this.currSize() + " records");
    }
    
    /**
     * helper simple mod hash function 
     * 
     * will hash an integer object
     * 
     * @param inKey : integer to be hashed
     * @return singly hashed integer 
     * @throws NumberFormatException
     */
    private int h(K inKey) throws NumberFormatException {
        Object keyO = (Object)inKey;
        if (keyO == null || keyO.getClass() != Integer.class) {
            System.out.println("Error, the hash table key is not an integer");
            throw new NumberFormatException();
        }
        return (Integer)keyO % this.currSize();
    }
    
    /**
     * helper probe function that uses double hashing
     * 
     * @param inKey : original key used for double hashing
     * @param i : current index of hashtable
     * @return return the proper index after probing
     */
    private int p(int inKey, int i) {
        return i * h2(inKey);
    }
    
    /**
     * helper double hash method 
     * 
     * @param hashedKey : hashed integer
     * @return rehashed integer
     */
    private int h2(int hashedKey) {
        int rehashed = (((hashedKey / this.currSize()) 
            % (this.currSize() / 2)) * 2) + 1;
        return rehashed;
    }
}
