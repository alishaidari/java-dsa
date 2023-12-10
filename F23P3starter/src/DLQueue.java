/**
 * DLQueue is a doubly linked queue
 * that is used for the LRU Queue
 * within BufferPool
 * 
 * @param <E> : generic contained by queue
 * @author Ali Haidari (alih)
 * @version 10.26.2023
 */
public class DLQueue<E> {
    private DLQNode<E> head;
    private DLQNode<E> tail;
    private int size;
    
    /**
     * constructor for DLQueue with
     * head, tail and size of zero
     */
    public DLQueue() {
        head = new DLQNode<E>(null, null);
        tail = new DLQNode<E>(head, null);
        head.setNext(tail);
        size = 0;
    }
    
    /**
     * getter for the size of the DLQueue
     * 
     * @return integer size 
     */
    public int size() {
        return this.size;
    }
    
    /**
     * setter for the size of the DLQueue
     * 
     * @param inSize : size to be set
     */
    public void setSize(int inSize) {
        size = inSize;
    }
    
    /**
     * getter for the head of the DLQueue
     * 
     * @return DLQNode that is the head (rear)
     */
    public DLQNode<E> getHead() {
        return head;
    }
    
    /**
     * getter for the tail of the DLQueue
     * 
     * @return DLQNode that is the tail (front)
     */
    public DLQNode<E> getTail() {
        return tail;
    }
    
    /**
     * queue the data to the front 
     * 
     * @param inData : data to be queued
     */
    public void enqueue(E inData) {
        //case when tail is empty
        if (tail.element() == null) {
            tail.setElement(inData);
        } 
        //case when tail is full
        else {
            DLQNode<E> newNode = new DLQNode<E>(inData, tail, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        //increment size
        size++;
    }
    
    /**
     * dequeue the data from the rear
     * 
     * @return generic data E that was dequeued, null otherwise
     */
    public E dequeue() {
        E toRet = null;
        //case when queue is empty
        if (size == 0) {
            return toRet;
        }
        //case when queue is non empty
        else {
            DLQNode<E> outHead = head.next();
            //case when the only node is tail
            if (outHead == tail) {
                toRet = tail.element();
                tail.setElement(null);
            }
            //otherwise we shift pointers
            else {
                toRet = outHead.element();
                head.setNext(outHead.next());
                outHead.next().setPrev(head);
            }        
            
            size--;
            return toRet;
        }
    }
    
    /**
     * print out the contents of the queue 
     * starting at rear and ending at front
     */
    public void print() {
        StringBuilder out = new StringBuilder();
        System.out.println("curr size: " + size);
        DLQNode<E> curr = head.next();
        out.append("<");
        if (size == 0) {
            out.append(">");
            System.out.println(out.toString());
            return;
        }
        while (curr != tail.next()) {
            out.append("[");
            out.append(curr.element().toString());
            out.append("]");
            curr = curr.next();
        }
        out.append(">");
        System.out.println(out.toString());
    }
}
