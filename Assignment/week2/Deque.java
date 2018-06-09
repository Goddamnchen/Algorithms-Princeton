import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node sentinel;
    private int size;

    private class Node {
        private Node prev;
        private Item item;
        private Node next;

        public Node() {
            this.item = null;
            this.item = null;
            this.next = null;
        }
        public Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node p = sentinel;
        private int count = 0;
        @Override
        public boolean hasNext() {
            if (count <= size() ) return true;
            else if (count == 0) return true;
            else return false;
        }
        @Override
        public Item next() {
            if (size() == 0 || count > size()) throw new NoSuchElementException("DequeIterator has no more items to iterate");
            Item item = p.next.item;
            p = p.next;
            count += 1;
            return item;
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("DequeIterator does not support remove() method");
        }
    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public Deque() {
        this.sentinel = new Node();
        this.sentinel.prev = this.sentinel;
        this.sentinel.next = this.sentinel;
        this.size = 0;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }
    public int size() {
        int size = this.size;
        return size;
    }
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
        this.sentinel.next = new Node(item, this.sentinel, this.sentinel.next);
        this.sentinel.next.next.prev = this.sentinel.next;
        this.size += 1;
    }
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
        this.sentinel.prev = new Node(item, this.sentinel.prev, this.sentinel);
        this.sentinel.prev.prev.next = this.sentinel.prev;
        this.size += 1;
    }
    public Item removeFirst() {
        if (this.size == 0) throw new NoSuchElementException("The Deque is empty, can not remove!");
        Node prevFirst = this.sentinel.next;
        this.sentinel.next = this.sentinel.next.next;
        this.sentinel.next.prev = this.sentinel;
        this.size -= 1;
        return prevFirst.item;
    }
    public Item removeLast() {
        if (this.size == 0) throw new NoSuchElementException("The Deque is empty, can not remove!");
        Node prevLast = this.sentinel.prev;
        this.sentinel.prev = this.sentinel.prev.prev;
        this.sentinel.prev.next = this.sentinel;
        this.size -= 1;
        return prevLast.item;
    }

    public static void main(String[] args) {

    }
}
