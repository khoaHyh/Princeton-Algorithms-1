import java.util.NoSuchElementException;

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
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
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
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        n++;
        assert check();
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNullArg(item);
        // TODO
    }

    // Prevent item remove... method call when deque is empty
    private void checkDeque() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty. Can't remove any items.");
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkDeque();
        Item item = first.item;
        first = first.next;
        n--;
        assert check();
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkDeque();
        Item item = last.item;
        // TODO
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        checkDeque();
        // implement foreach? private class?
    }

    // unit testing (required)
    public static void main(String[] args)

}
