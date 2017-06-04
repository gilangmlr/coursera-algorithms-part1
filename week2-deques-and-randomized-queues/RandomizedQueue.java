/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;
    private int randomIndex;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[2];
        randomIndex = -1;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (size == queue.length) {
            resize(queue.length * 2);
        }

        size++;
        queue[size - 1] = item;

        randomIndex = StdRandom.uniform(0, size);
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item item = queue[randomIndex];
        queue[randomIndex] = queue[size - 1];
        queue[size - 1] = null;
        size--;

        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }

        if (size() > 0) {
            randomIndex = StdRandom.uniform(0, size);
        }
        else {
            randomIndex = -1;
        }
        
        return item;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        randomIndex = StdRandom.uniform(0, size);

        return queue[randomIndex];
    }
    
    // return an independent iterator over items in random order
    public java.util.Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements java.util.Iterator<Item> {
        private int iterCount;
        private int[] randomIndices;

        public RandomizedQueueIterator() {
            iterCount = 0;

            randomIndices = StdRandom.permutation(size);
        }

        public boolean hasNext() {
            return iterCount < size;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = queue[randomIndices[iterCount]];
            iterCount++;

            return item;
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        
    }
}