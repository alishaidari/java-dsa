/**
 * implementation of LinkedList
 * with head, current, and tail
 * pointers adapted from OpenDSA
 * 
 * @author Ali Haidari (alih)
 * @version 9.3.2023
 * @param <E> generic type that will be stored
 */
public class LinkedList<E> {
    private Link<E> head;
    private Link<E> curr;
    private Link<E> tail;
    private int size;

    /**
     * empty constructor to set up 
     * head, curr, and tail Links
     */
    public LinkedList() {
        head = new Link<E>(null);
        tail = head;
        curr = tail;
        size = 0;
    }
    /**
     * retrieves the data current 
     * @return value within current link
     */
    public E data() {  
        //nothing to retrieve
        if (curr.next() == null) {
            return null;
        }
        return curr.next().element();
    }

    /**
     * current size of the LList
     * @return integer size
     */
    public int size() { 
        return this.size; 
    }

    /**
     * checks if list is empty by size
     * @return true if list is empty, false otherwise
     */
    public boolean isEmpty() {
        return (size() == 0);
    }
    
    /**
     * checks if the current pos is at tail
     * @return true if at end, false otherwise
     */
    public boolean isAtEnd() {
        return (curr == tail);
    }
    /**
     * clears and resets LList to empty
     */
    public void clear() {         
        head.setNext(null);         
        head = new Link<E>(null);
        tail = head;
        curr = tail;
        size = 0;
    }

    /**
     * move curr pointer to next Link in LList
     */
    public void next() { 
        if (curr != tail) {
            curr = curr.next(); 
        }
        else {
            return;
        }
    }

    /**
     * move curr one step back, wont move if at head
     */
    public void prev() {
        if (curr == head) {
            return; 
        }
        Link<E> temp = head;
        while (temp.next() != curr) {
            temp = temp.next();
        }
        curr = temp;
    }

    /**
     * retrieve the current integer index in LList
     * @return integer index
     */
    public int currIndex() {
        Link<E> temp = head;
        int i;
        for (i = 0; temp != curr; i++) {
            temp = temp.next();
        }
        return i;
    }

    /**
     * move current pointer to an index
     * wont move if index is not valid
     * @param index : position to move to 
     */
    public void moveTo(int index) {
        if (index >= 0 && index <= size) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.next();
            }
        }
        else {
            System.out.println("Index: " + index + " out of range");
            return;
        } 
    }
    
    /**
     * moves to an element if it exists in the LList
     * @param it : the element to move to 
     */
    public void moveToElem(E it) {
        Link<E> temp = head;
        while (temp != tail) {
            if (temp.next().element() == it) {
                curr = temp;
            }
            temp = temp.next();
        }
    }

    /**
     * inserts a new link at current position
     * @param it : the element to be inserted
     */
    public void insert(E it) {
        curr.setNext(new Link<E>(it, curr.next()));
        if (tail == curr) {
            tail = curr.next();  
        }
        size++;
    }

    /**
     * adds a new link at end of list without moving current 
     * @param it : the element to be appended
     */
    public void append(E it) {
        tail = tail.setNext(new Link<E>(it, null));
        size++;
    }

    /**
     * removes a link at the current position
     * if the current is at the tail, it will not remove
     * @return removed element
     */
    public E remove() {
        if (curr.next() == null) {
            return null; 
        }
        //store element to return 
        E toRet = curr.next().element();  
        //account for end of list
        if (tail == curr.next()) {
            tail = curr; 
        }
        //remove from list
        curr.setNext(curr.next().next());     
        size--;                    
        return toRet;                            
    }

    /**
     * converts a LList object to a string 
     * @return string representation of LList
     */
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("< ");

        int oldCurr = this.currIndex();
        int length = this.size();

        moveTo(0);

        for (int i = 0; i < oldCurr; i++) {
            out.append(data());
            out.append(" ");
            next();
        }
        out.append("| ");
        for (int i = oldCurr; i < length; i++) {
            out.append(data());
            out.append(" ");
            next();
        }
        out.append(">");
        moveTo(oldCurr);
        return out.toString();
    }
}
