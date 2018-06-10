/*******************************************************************************************
 * Author: Guanting Chen
 * Date: 06/09/2018
 * RandomizedQueue - remove random items uniformly from Queue
 * It DOES NOT have property of FIFO
 *
 * If we dont care about keeping the primitive order of Queue after enqueuing,
 * we would choose a random item and SWITCH it to the dequeue side, waiting to be dequeued.
 *
 * This implementation of dequeue is quite like POP operation of Stack
 * Because we enqueue and dequeue at the same side -- Queue[size]
 * and we dequeue by decreasing size THUS queue[size - 1] will pop off
 *
 *******************************************************************************************/


import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;       //invariant for both enqueuing and dequeuing in this case

    private class RQIterator implements Iterator<Item>{
        private Item[] a;
        private int index;

        public RQIterator() {
            a = (Item[]) new Object[size()];
            this.index = size() - 1;
            for (int i = 0; i < size(); i++) {
                a[i] = queue[i];
            }
            StdRandom.shuffle(a);
        }
        @Override
        public boolean hasNext() {
            if (this.index >= 0) {
                return true;
            } else {
                return false;
            }
        }
        @Override
        public Item next() {
            if (!hasNext() || size() == 0) throw new NoSuchElementException("Queue has no more items to iterate");
            Item item = a[index];
            index -= 1;
            return item;
        }
    }
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    public RandomizedQueue(){
        this.queue = (Item[]) new Object[4];
        this.size = 0;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        int size = this.size;
        return size;
    }
    public void enqueue(Item item) {
        validate(item);
        resize();
        this.queue[this.size] = item;
        this.size += 1;

    }

    /**
     * 1.find a random item uniformly
     * 2.switch it with the item at the dequeue side (dont care about queue order)
     * 3.Set item to be dequeued to null, avoiding loitering
     * 4.shrink the queue and pop off item to be dequeued
     * @return item to be dequeued
     */
    public Item dequeue() {
        validate();
        resize();
        int index = findIndex();
        Item itemToDequeue = this.queue[index];
        Item itemAtDequeueSide = this.queue[this.size - 1];
        this.queue[index] = itemAtDequeueSide;
        this.queue[this.size - 1] = null;      //switch the item at dequeue side with item to be dequeued
        this.size -= 1;
        return itemToDequeue;
    }

    public Item sample() {
        validate();
        int randomIndex = findIndex();
        return queue[randomIndex];
    }

    /**
     * find a index of NOT NULL Item randomly
     */
    private int findIndex() {
        int index = StdRandom.uniform(0, this.size);
        return index;
    }

    private void resize() {
        Item[] newArray;
        if (this.size == this.queue.length) {
            newArray = (Item[]) new Object[2 * queue.length];
            System.arraycopy(queue, 0, newArray, 0, this.size);
            this.queue = newArray;

        } else if (this.queue.length > 4  && this.size <= queue.length / 4) {
            newArray = (Item[]) new Object[queue.length / 2];
            System.arraycopy(queue, 0, newArray, 0, this.size);
            this.queue = newArray;
        }

    }

    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
    }
    private void validate() {
        if (isEmpty()) throw new NoSuchElementException("The Queue is empty!");
    }
    public static void main(String[] args){

    }
}