/**
 * SemProcessor handles all the seminar data
 * with insertions, searches, and deletions
 * into the respective binary trees
 * 
 * @author Ali Haidari (alih)
 * @version 9.18.2023
 */
public class SemProcessor {
    private BST<Integer, Seminar> idTree;
    private BST<String, Seminar> dateTree;
    private BST<Integer, Seminar> costTree;
    private BST<String, Seminar> keywordTree;
    private BinTree locTree;
   
    /**
     * constructor for a SemProcessor
     * 
     * @param initWorld : the initial worldSize
     */
    public SemProcessor(int initWorld) {
        idTree = new BST<Integer, Seminar>();
        dateTree = new BST<String, Seminar>();
        costTree = new BST<Integer, Seminar>();
        keywordTree = new BST<String, Seminar>();
        locTree = new BinTree(initWorld);
    }
    
    /**
     * handles the insertion of a seminar into 
     * the BSTs and BinTree
     * 
     * @param inSem : seminar object to be inserted
     * @return true if inserted, false otherwise
     */
    public boolean semInsert(Seminar inSem) {
        boolean output = false;
        //check if id is already in idTree
        if (idTree.search(inSem.id()) == null) {
            //insert to all trees
            
            idTree.insert(inSem.id(), inSem);
            dateTree.insert(inSem.date(), inSem);
            costTree.insert(inSem.cost(), inSem);
            String[] currKeywords = inSem.keywords();
            for (String keyword : currKeywords) {
                keywordTree.insert(keyword, inSem);
            }
            locTree.insert(inSem);
            output = true;
        }
        return output;
    }
    
    /**
     * searches for a seminar with an ID key  
     * 
     * @param inID : id of the seminar to search for
     * @return seminar with associated id, null otherwise
     */
    public Seminar semSearchID(int inID) {
        Seminar record = idTree.search(inID);
        return record;
    }
    
    /**
     * searches for a cost range with a low and high cost
     * 
     * @param inLow : low cost for range search 
     * @param inHigh : high cost for range search
     * @return BST with the nodes within cost range, empty BST otherwise
     */
    public BST<Integer, Seminar> semSearchCost(int inLow, int inHigh) {
        BST<Integer, Seminar> costsInRange = 
            costTree.rangeSearch(inLow, inHigh);
        return costsInRange;
    }
    
    /**
     * searches for a date range with a low and high date
     * 
     * @param inLow : low date for range search
     * @param inHigh : high date for range search
     * @return BST with the nodes within date range, empty BST otherwise
     */
    public BST<String, Seminar> semSearchDate(String inLow, String inHigh) {
        BST<String, Seminar> datesInRange = 
            dateTree.rangeSearch(inLow, inHigh);
        return datesInRange;
    }
    
    /**
     * searches for all instances of a keyword using a range search
     * of the same key
     * 
     * @param inKey : keyword to act as the low and high keyword range
     * @return BST with all instances of the keyword, empty BST otherwise
     */
    public BST<String, Seminar> semSearchKeyword(String inKey) {
        BST<String, Seminar> keywordsInRange = 
            keywordTree.rangeSearch(inKey, inKey);
        return keywordsInRange;
    }
    
    //TODO FIX BINTREE SEARCHING
    
    /**
     * deletes a BSTNode with an ID from all trees 
     * 
     * @param inID : id of BSTNode to be deleted from all trees
     * @return true if sucessful delete, false if otherwise
     */
    public boolean semDelete(int inID) {
        boolean output = false;
        //if id is removed then we can remove from rest of trees
        Seminar deleted = idTree.delete(inID);
        if (deleted != null) {
            //find all respective keys associated with ID
            String dateKey = deleted.date();
            int costKey = deleted.cost();
            String[] keywordKeys = deleted.keywords();
            int xLocKey = deleted.x();
            int yLocKey = deleted.y();
            locTree.delete(xLocKey, yLocKey, inID);
            
            //deletion for duplicate trees require KV deletion
            dateTree.deleteKV(dateKey, deleted);
            costTree.deleteKV(costKey, deleted);
            for (String keyword : keywordKeys) {
                keywordTree.deleteKV(keyword, deleted);
            }
            output = true;
        }
        return output;
    }
    
    /**
     * prints the tree of an idTree
     */
    public void semPrintIDTree() {
        idTree.tree();
    }
    
    /**
     * prints the tree of an costTree
     */
    public void semPrintCostTree() {
        costTree.tree();
    }
    
    /**
     * prints the tree of an dateTree
     */
    public void semPrintDateTree() {
        dateTree.tree();
    }
    
    /**
     * prints the tree of an keywordTree
     */
    public void semPrintKeywordTree() {
        keywordTree.tree();
    }
    
    /**
     * prints the pre order of a locTree
     */
    public void semPrintLocTree() {
        locTree.print();
    }
}
