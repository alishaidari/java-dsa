/**
 * SeminarProcessor handles the requests from 
 * CommandParser and adjusts HashTable and 
 * MemManager accordingly
 * 
 * @author Ali Haidari (alih)
 * @version 8.28.2023
 */
public class SemProcessor {
    private HashTable<Integer, Handle> mainTable;
    private MemManager mainMem;
    
    /**
     * constructor for the SeminarProcessor
     * 
     * @param initMemSize : initial memory size
     * @param initHashSize : initial hash size
     */
    public SemProcessor(int initMemSize, int initHashSize) {
        mainTable = new HashTable<Integer, Handle>(initHashSize);
        mainMem = new MemManager(initMemSize);
    }
    
    /**
     * gets HashTable object for seminar processor
     * 
     * @return HashTable object 
     */
    public HashTable<Integer, Handle> getHashTable() {
        return mainTable;
    }
    
    /**
     * gets the MemManager object for seminar processor
     * 
     * @return MemManager object
     */
    public MemManager getMemManager() {
        return mainMem;
    }
    
    /**
     * inserts the parsed in seminar into the 
     * the HashTable and MemManager
     * 
     * @param keyID : the id to be inserted into hashTable
     * @param semVal : the seminar to be inserted into hashTable
     * @return true if inserted, false otherwise
     * @throws Exception 
     */
    public boolean semInsert(int keyID, Seminar semVal) throws Exception {
        boolean output = false;
        
        if (mainTable.search(keyID) == null) {
            Handle insertHandle = mainMem.insert(semVal.serialize());
            output = mainTable.insert(keyID, insertHandle);
        }
        return output;
    }
    
    /**
     * wrapper method that checks if key is within
     * HashTable or not and then retrieves it from
     * MemManger
     * 
     * @param keyID : the id to be search by hashTable
     * @return record if found, null otherwise
     */
    public byte[] semSearch(int keyID) {
        byte[] record = null;
        Handle searchHandle = mainTable.search(keyID);
        if (searchHandle != null) {
            record = mainMem.get(searchHandle);
        }
        return record;
    }
    
    /**
     * removes the parsed in seminar from the 
     * the HashTable and MemManager
     * 
     * @param keyID : the id to be removed by HashTable
     * @return true if removed, false otherwise
     */
    public boolean semRemove(int keyID) {
        boolean output = false;
        if (mainTable.search(keyID) != null) {
            Handle removeHandle = mainTable.remove(keyID);
            mainMem.remove(removeHandle);
            output = true;
        }
        return output;
    }

}
