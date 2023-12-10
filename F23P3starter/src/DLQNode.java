/**
 * DLQNode is a node with pointers
 * to next and previous, it is used
 * for construction of a doubly linked
 * queue
 * 
 * @author Ali Haidari (alih)
 * @version 10.26.2023
 * @param <E> : the generic data to store
 */
public class DLQNode<E> {
    private DLQNode<E> prev;
    private DLQNode<E> next;
    private E data;
    
    /**
     * constructor for a DLQNode with
     * pointers to previous, next, and data
     * 
     * @param inData : data to be set for node
     * @param inNext : pointer to next node
     * @param inPrev : pointer to prev node
     */
    public DLQNode(E inData, DLQNode<E> inPrev, DLQNode<E> inNext) {
        data = inData;
        prev = inPrev;
        next = inNext;
    }
    
    /**
     * constructor for a DLQNode with 
     * pointers to previous and next
     * 
     * @param inPrev : pointer to prev node
     * @param inNext : pointer to next node
     */
    public DLQNode(DLQNode<E> inPrev, DLQNode<E> inNext) {
        prev = inPrev;
        next = inNext;
    }
    
    /**
     * getter for the element
     * contained by a DLQNode
     * 
     * @return generic E or null otherwise
     */
    public E element() {
        return this.data;
    }
    
    /**
     * setter for element in a DLQNode
     * 
     * @param inData : data to be set
     */
    public void setElement(E inData) {
        this.data = inData;
    }
    
    /**
     * getter for next in a DLQNode
     * 
     * @return DLQNode that is next, null otherwise
     */
    public DLQNode<E> next() {
        return this.next;
    }
    
    /**
     * setter for next in a DLQNode
     * 
     * @param inNext : DLQNode to be as next
     */
    public void setNext(DLQNode<E> inNext) {
        this.next = inNext;
    }
    
    /**
     * getter for prev in a DLQNode
     * 
     * @return DLQNode that is prev, null otherwise
     */
    public DLQNode<E> prev() {
        return this.prev;
    }
    
    /**
     * setter for prev in a DLQNode
     * 
     * @param inPrev : DLQNode to be set as prev
     */
    public void setPrev(DLQNode<E> inPrev) {
        this.prev = inPrev;
    }
}
