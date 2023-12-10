

/**
 * linked list Link with freeList support
 * 
 * @author Ali Haidari (alih)
 * @version 9.1.2023
 * @param <E> : element for Link
 */
public class Link<E> {
    private E element;  // value of Link
    private Link<E> next; // point to next Link
    
    /**
     * constructor for creation of a Link 
     * with element and next Link
     * 
     * @param initElem : initial element of a Link
     * @param initNext : initial next Link
     */
    public Link(E initElem, Link<E> initNext) {
        this.element = initElem;
        this.next = initNext;
    }
    
    /**
     * constructor for a Link with only next Link
     * @param initNext : initial next Link 
     */
    public Link(Link<E> initNext) { 
        this.element = null;
        this.next = initNext; 
    }
    
    /**
     * getter for next Link
     * @return next Link within curr Link
     */
    public Link<E> next() {
        return this.next;
    }
    
    /**
     * setter for next Link and returns Link set
     * @param newNext : input Link to set as next
     * @return Link that is set as next
     */
    public Link<E> setNext(Link<E> newNext) {
        this.next = newNext;
        return this.next;
    }
    
    /**
     * getter for element of Link
     * @return element of a Link
     */
    public E element() {
        return this.element;
    }
    /**
     * setter for element and returns the element set
     * @param inElem : input elem to set as elem
     * @return element that is set
     */
    public E setElement(E inElem) {
        this.element = inElem;
        return this.element;
    }
    
}
    
//    @SuppressWarnings("rawtypes")
//    //extension for freelist support
//    private static Link freelist = null;
//    
//
//    /**
//     * gets a new Link from freelist if possible
//     * 
//     * @param <E> : generic element type
//     * @param inElem : element
//     * @param inNext : next Link
//     * @return a new Link 
//     */
//    @SuppressWarnings("unchecked")
//    public static <E> Link<E> get(E inElem, Link<E> inNext) {
//        if (freelist == null) {
//            return new Link<E>(inElem, inNext);
//        }
//        Link<E> temp = freelist;
//        freelist = freelist.next();
//        temp.setElement(inElem);
//        temp.setNext(inNext);
//        return temp;
//    }
//    
//
//    /**
//     * return a link to the freelist
//     */
//    @SuppressWarnings("unchecked")
//    public void release() {
//        element = null;
//        next = freelist;
//        freelist = this;
//    }

