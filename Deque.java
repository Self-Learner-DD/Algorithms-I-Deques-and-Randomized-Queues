//each node have 16 bytes of object overhead, 8 bytes each for the references
// to Item and Node obect, and 8 byte for the extra overhead. totally 40
// for each object in the linked list, it needs 16 bytes overhead, usually
// 8 bytes for instance variable, and 4 bytes for padding, totally 28
// so totally 68N if implement using linkedlist with one child..

import java.util.NoSuchElementException;
import java.util.Iterator;
   
public class Deque<Item> implements Iterable<Item> {
    
    private Node first;
    private Node last;
    private int N;
    
    private class Node
    {
        Item item;
        Node prev;
        Node next;
    }
    
    public Deque()                           // construct an empty deque
    { N = 0; }
    
    public boolean isEmpty()                 // is the deque empty?
    { return N == 0 ; }
    
    public int size()                        // return the number of items on the deque
    { return N; }
    
    public void addFirst(Item item)          // add the item to the front
    {
        assertItem(item);
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (last == null) last = first;
        else oldfirst.prev = first;
        N++;
    }
    public void addLast(Item item)           // add the item to the end
    {
        assertItem(item);
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (first == null) first = last;
        else oldlast.next = last;
        N++;
    }
        
    public Item removeFirst()                // remove and return the item from the front
    {
        assertNotEmpty();
        Item item = first.item;
        if (N > 1)
        {
            first = first.next;
            first.prev = null;
        }
        else
        { 
            last = null; //if first == null, last = first will throw Null PointerException
            first = null;
        }
        N--;
        return item;
    }
    
    
    public Item removeLast()                 // remove and return the item from the end
    {
        assertNotEmpty();
        Item item = last.item;
        if (N>1)
        {
            last = last.prev;
            last.next = null;
        }
        else
        {
            first = null;
            last = null;
        }
        N--;
        return item;
    }
    
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    { return new OrderIterator(); }
    
    private class OrderIterator implements Iterator<Item>
    {
        private Node current = first;
        
        public boolean hasNext()
        { return current != null; }
        
        public Item next()
        {
            if (current == null) throw new NoSuchElementException("No more item to iterate");
            //assertCurrent(current); //if current == null, use null as argument will give error?
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove()
        { throw new java.lang.UnsupportedOperationException("Method remove cannot be invoke"); }
    }
    
    private void assertItem(Item item)
    { if (item == null) throw new IllegalArgumentException("Item can not be null"); }
    
    private void assertNotEmpty()
    { if (isEmpty()) throw new NoSuchElementException("Deque is empty"); }
    
//   private void assertCurrent(Node current)
//    { if (current == null) throw new NoSuchElementException("No more item to iterate"); }
    
}