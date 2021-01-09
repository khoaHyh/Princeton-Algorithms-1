import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/*
*   Code adapted from: https://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
*/

public class RandomizedQueue<Item> implements Iterable<Item> {
    // Initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;
    // queue of elements
    private Item[] a;
    // number of elements on RandomizedQueue
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // Resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // Textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        if (n >= 0) System.arraycopy(a, 0, copy, 0, n);
        a = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument provided.");
        if (n == a.length) resize(2 * a.length);
        a[n++] = item;
    }

    private void checkEmptyQueue() {
        if (isEmpty()) throw new NoSuchElementException("Randomized Queue is empty.");
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmptyQueue();
        int randomIndex = StdRandom.uniform(n);
        // Swap random element with the element at the back of the queue
        Item item = a[randomIndex];
        a[randomIndex] = a[n - 1];
        a[n-1] = null;
        n--;
        // Shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkEmptyQueue();
        return a[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private final Item[] copyA = (Item[]) new Object[n];

        public RandomizedQueueIterator() {
            if (n >= 0) System.arraycopy(a, 0, copyA, 0, n);
            StdRandom.shuffle(copyA, 0, n);
            i = n - 1;
        }
        public boolean hasNext() { return i >= 0; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return.");
            return copyA[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                rq.enqueue(item);
            } else if (!rq.isEmpty()) {
                StdOut.print(rq.dequeue() + " ");
            }
        }

//         Test iterator
//        int n = 5;
//        for (int i = 0; i < n; i++)
//            rq.enqueue(String.valueOf(i));
//        for (String a : rq) {
//            for (String b : rq)
//                StdOut.print(a + "-" + b + " ");
//            StdOut.println();
//        }

        StdOut.println("(" + rq.size() + " left on the randomized queue)");
    }
}
