import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    
    private Item[] a;
    private int N;
    public RandomizedQueue()                 // construct an empty randomized queue
    { a = (Item[]) new Object[2]; }
    
    public boolean isEmpty()                 // is the randomized queue empty?
    { return N == 0; }
    
    public int size()                        // return the number of items on the randomized queue
    { return N; }
    
    public void enqueue(Item item)           // add the item
    {
        assertItem(item);
        if (N == a.length) resize(2*a.length);
        a[N] = item;
        N++;
    }
            
    public Item dequeue()                    // remove and return a random item
    {
        assertNotEmpty();
        int i = StdRandom.uniform(0,N);
        Item item = a[i];
        a[i] = a[N-1];
        a[N-1] = null;
        N--;
        if (N > 0 && N <= a.length/4) resize(a.length/2); // miss N>0
        return item;
    }
    
    public Item sample()                     // return a random item (but do not remove it)
    {
        assertNotEmpty();
        int i = StdRandom.uniform(0,N);
        return a[i];
    }
    
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    { return new RandomOrder(); }
    
    private class RandomOrder implements Iterator<Item>
    {
        private int i;
        private final int[] randomorder = StdRandom.permutation(N);
        
        public boolean hasNext()
        { return i < N; }
        
        public Item next()
        {
            if (i == N) throw new NoSuchElementException("No more item to iterate");
            Item item = a[randomorder[i++]];
            return item;
        }
        
        public void remove()
        { throw new java.lang.UnsupportedOperationException("Method remove cannot be invoke"); }
    }       
    
    private void resize(int length)
    {
        Item[] temp = (Item[]) new Object[length];
        for (int i = 0; i < N; i++)  // should not use a.length here, otherwise when resize down will get outofBound error
            temp[i] = a[i];
        a = temp;
    }
    
    private void assertItem(Item item)
    { if (item == null) throw new IllegalArgumentException("Item can not be null"); }
    
    private void assertNotEmpty()
    { if (isEmpty()) throw new NoSuchElementException("Deque is empty"); }
}