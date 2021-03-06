import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
*   Code adapted from: "https://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html"
*/

public class Deque<Item> implements Iterable<Item> {
    // Top of stack
    private Node first;
    // Bottom of stack
    private Node last;
    // Size of the stack
    private int n;

    // Helper linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // Checks for null arguments
    private void checkNullArg(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Null argument provided.");
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNullArg(item);
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        if (isEmpty()) {
            last = first;
        } else if (n == 1) {
            last.prev = first;
            first.next = last;
        } else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNullArg(item);
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        } else if (n == 1) {
            first.next = last;
            last.prev = first;
        } else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        n++;
    }

    // Prevent item remove... method call when deque is empty
    private void checkDeque() {
        if (isEmpty())
            throw new NoSuchElementException("Deque is empty. Can't remove any items.");
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkDeque();
        Item item = first.item;
        first = first.next;
        n--;
        // To avoid loitering
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkDeque();
        Item item = last.item;
        last = last.prev;
        n--;
        // To avoid loitering
        if (isEmpty())
            first = null;
        else
            last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return.");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) {
////                deque.addFirst(item);
//                deque.addLast(item);
//            } else if (!deque.isEmpty()) {
//                StdOut.println(deque.removeFirst() + " ");
////                StdOut.println(deque.removeLast() + " ");
//            }
//        }
//        StdOut.println("(" + deque.size() + " left on deque)");

        deque.addLast("1");
        deque.addLast("2");
        StdOut.println(deque.removeLast());
        for (String i : deque)
            StdOut.print(i + " ");
        StdOut.println();
    }
}
