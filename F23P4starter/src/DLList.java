
/**
 * doubly linked list that contains
 * generic data with pointer to previous
 * and next list items
 * 
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 * @param <E> : generic data to store
 */
public class DLList<E> {
    private DLNode<E> head;
    private DLNode<E> tail;
    private DLNode<E> curr;
    private int size;
    
    /**
     * empty constructor that initializes
     * a DLList with a head, current,
     *  and tail pointer DLLNode
     */
    public DLList() {
        //no value at head
        head = new DLNode<E>(null, null);
        //curr points to head
        curr = head;
        //previous of tail points to head
        tail = new DLNode<E>(head, null);
        //next of head points to tail
        head.setNext(tail);
        size = 0;
    }
    /**
     * getter for number of elements in list
     * 
     * @return integer of size
     */
    public int size() {
        return size;
    }
    
    /**
     * getter for current element in list
     * 
     * @return E that is the data of a node
     */
    public E element() {
        if (curr.next() == tail) {
            return null;
        }
        return curr.next().element();
    }
    
    /**
     * calculates the index of current position
     * 
     * @return integer that is index of list
     */
    public int pos() {
        DLNode<E> temp = head;
        int i = 0;
        while (temp != curr) {
            temp = temp.next();
            i++;
        }
        return i;
    }
    
    /**
     * repositions the current index 
     * 
     * @param index : index to be set
     */
    public void repos(int index) {
        //when position  out of bounds
        if (index < 0 || index > size()) {
            return;
        }
        int prevPos = pos();
        //check if index is to the right of current pos
        if (index > prevPos) {
            for (int i = 0; i < index - prevPos; i++) {
                curr = curr.next();
            }
        }
        //check if index is to the left of current pos
        if (index < prevPos) {
            for (int i = 0; i < prevPos - index; i++) {
                curr = curr.prev();
            }
        }
    }
    
    /**
     * inserts data into current position of list
     * 
     * @param inData : data to be inserted
     */
    public void insert(E inData) {
        //new node has prev pointing to curr and next pointing to curr next
        DLNode<E> newNode = new DLNode<E>(inData, curr, curr.next());
        //assign pointers of current to fit in the new node
        curr.next().setPrev(newNode);
        curr.setNext(newNode);
        //increment size
        size++;
    }
    
    /**
     * appends data to tail of the list
     * 
     * @param inData : data to be appended
     */
    public void append(E inData) {
        //new node has prev pointing to tail prev and next pointing to tail
        DLNode<E> newNode = new DLNode<E>(inData, tail.prev(), tail);
        //assign pointers of tail to fit in new node
        tail.prev().setNext(newNode);
        tail.setPrev(newNode);
        //increment size
        size++;
    }
    
    /**
     * checks if the list has a data item
     * 
     * @param inData : data to be searched
     * @return true if found, false otherwise
     */
    public boolean has(E inData) {
        boolean output = false;
        DLNode<E> temp = head.next();
        while (temp != tail) {
            if (temp.element().equals(inData)) {
                output = true;
                break;
            }
            temp = temp.next();
        }
        return output;
        
    }
    
    /**
     * deletes data from any position in list
     * 
     * @param inData : data to be deleted
     * @return true if deleted, false otherwise
     */
    public boolean delete(E inData) {
        boolean output = false;
        DLNode<E> temp = head.next();
        while (temp != tail) {
            if (temp.element().equals(inData)) {
                temp.next().setPrev(temp.prev());
                temp.prev().setNext(temp.next());
                output = true;
                size--;
            }
            temp = temp.next();
        }
        return output;
    }
    
    
    /**
     * remove data from current position
     * 
     * @return data of node removed, null otherwise
     */
    public E remove() {
        //check for item to remove
        E removed = element();
        if (removed == null) {
            return null;
        }
        curr.next().next().setPrev(curr);
        curr.setNext(curr.next().next());
        size--;
        
        return removed;
    }
    
//    /**
//     * truncates data from tail of list
//     * 
//     * @return data of node removed, null otherwise
//     */
//    public E truncate() {
//        //check for item to remove
//        if (tail.prev() == head) {
//            return null;
//        }
//        E removed = tail.prev().element();
//        tail.prev().prev().setNext(tail);
//        tail.setPrev(tail.prev().prev());
//        size--;
//        return removed;
//    }
    
    /**
     * prints out list to console with
     * current position and head and tail pointers
     */
    public void print() {
        System.out.println("");
        StringBuilder out = new StringBuilder();
        out.append("<");
        DLNode<E> temp = head;
        while (temp != tail.next()) {
            out.append(" " + temp.element() + " ");
            if (temp == curr) {
                out.append("|");
            }
            temp = temp.next();
        }
        out.append(">");
        System.out.println(out.toString());
        System.out.println("curr pos: " + pos());
        System.out.println("curr size: " + size());
        System.out.println("");
    }
}
