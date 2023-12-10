/**
 * DLNode is a doubly linked node
 * that is used for creation of 
 * a doubly linked list
 * 
 * @author Ali Haidari (alih)
 * @version 11.16.2023
 * @param <E> : generic data to store
 */
public class DLNode<E> {
    private E data;
    private DLNode<E> next;
    private DLNode<E> prev;
    
    /**
     * constructor for a DLNode with
     * pointers to previous, next, and data
     * 
     * @param inData : data to be set for node
     * @param inNext : pointer to next node
     * @param inPrev : pointer to prev node
     */
    public DLNode(E inData, DLNode<E> inPrev, DLNode<E> inNext) {
        data = inData;
        prev = inPrev;
        next = inNext;
    }
    
    /**
     * constructor for a DLNode with 
     * pointers to previous and next
     * 
     * @param inPrev : pointer to prev node
     * @param inNext : pointer to next node
     */
    public DLNode(DLNode<E> inPrev, DLNode<E> inNext) {
        prev = inPrev;
        next = inNext;
    }
    
    /**
     * getter for the element
     * contained by a DLNode
     * 
     * @return generic E or null otherwise
     */
    public E element() {
        return this.data;
    }
    
    /**
     * setter for element in a DLNode
     * 
     * @param inData : data to be set
     */
    public void setElement(E inData) {
        this.data = inData;
    }
    
    /**
     * getter for next in a DLNode
     * 
     * @return DLNode that is next, null otherwise
     */
    public DLNode<E> next() {
        return this.next;
    }
    
    /**
     * setter for next in a DLNode
     * 
     * @param inNext : DLNode to be as next
     */
    public void setNext(DLNode<E> inNext) {
        this.next = inNext;
    }
    
    /**
     * getter for prev in a DLNode
     * 
     * @return DLNode that is prev, null otherwise
     */
    public DLNode<E> prev() {
        return this.prev;
    }
    
    /**
     * setter for prev in a DLNode
     * 
     * @param inPrev : DLNode to be set as prev
     */
    public void setPrev(DLNode<E> inPrev) {
        this.prev = inPrev;
    }
}
