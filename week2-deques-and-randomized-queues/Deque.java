/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;

        private Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
   
    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        size++;

        Node oldFirst = first;
        first = new Node(item, null, oldFirst);
        
        if (size() == 1) {
            last = first;
        }
        else {
            oldFirst.prev = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        size++;

        Node oldLast = last;
        last = new Node(item, oldLast, null);
        
        if (size() == 1) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        size--;

        Item item = first.item;
        first = first.next;

        if (size() == 0) {
            last = null;
        }
        else if (size() > 0) {
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        size--;

        Item item = last.item;
        last = last.prev;

        if (size() == 0) {
            first = null;
        }
        else if (size() > 0) {
            last.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to end
    public java.util.Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements java.util.Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        
    }
}