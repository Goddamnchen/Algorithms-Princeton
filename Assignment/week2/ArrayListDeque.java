import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayListDeque<Item> implements Iterable<Item> {
    private int size;
    private int first;
    private int last;
    private Item[] arrayDeque;
    public ArrayListDeque() {
        this.arrayDeque = (Item[]) new Object[8];
        this.size = 0;
        this.first = 0;
        this.last = 0;
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
        resize();
        if (this.size != 0) this.first = toIndex(this.first - 1);
        this.arrayDeque[this.first] = item;
        this.size += 1;
    }
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add null");
        resize();
        if (this.size != 0) this.last = toIndex(this.last + 1);
        this.arrayDeque[last] = item;
        this.size += 1;
    }

    public Item removeFirst() {
        if (this.size == 0) throw new NoSuchElementException("The Deque is empty, can not remove!");
        resize();
        Item returnValue = this.arrayDeque[this.first];
        this.arrayDeque[this.first] = null;
        if (this.size != 1) this.first = toIndex(this.first + 1);
        this.size -= 1;
        return returnValue;
    }
    public Item removeLast() {
        if (this.size == 0) throw new NoSuchElementException("The Deque is empty, can not remove!");
        resize();
        Item returnvalue = this.arrayDeque[last];
        this.arrayDeque[this.last] = null;
        if (this.size != 1) this.last = toIndex(this.last - 1);
        this.size -= 1;
        return returnvalue;
    }

    /* return an iterator over items in order from front to end */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public class DequeIterator implements Iterator<Item>{
        private int p = first;
        private int count = 0;

        public boolean hasNext() {
            if (count < size() ) return true;
            else return false;
        }
        public Item next() {
            Item item = arrayDeque[p];
            if(count > size()) throw new NoSuchElementException("DequeIterator has no more items to iterate");
            p = toIndex(p + 1);
            count += 1;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException("DequeIterator does not support remove() method");
        }
    }

    private void resize(){
        Item[] newArray;
        if (this.size == this.arrayDeque.length) {
            newArray = (Item[]) new Object[2 * arrayDeque.length];
            if (this.last < this.first) {
                System.arraycopy(this.arrayDeque, this.first, newArray, 0, this.arrayDeque.length - this.first);
                System.arraycopy(this.arrayDeque, 0, newArray, this.arrayDeque.length - this.first, this.last + 1 );
            } else {
                System.arraycopy(this.arrayDeque, this.first, newArray, 0, this.last - this.first + 1);
            }
            this.arrayDeque = newArray;
            this.first = 0;
            this.last = this.size - 1;

        } else if (this.arrayDeque.length > 8  && this.size <= arrayDeque.length / 4) {
            newArray = (Item[]) new Object[arrayDeque.length / 2];
            if (this.last < this.first) {
                System.arraycopy(this.arrayDeque, this.first, newArray, 0, this.arrayDeque.length - this.first);
                System.arraycopy(this.arrayDeque, 0, newArray, this.arrayDeque.length - this.first, this.last + 1 );
            } else {
                System.arraycopy(this.arrayDeque, this.first, newArray, 0, this.last - this.first + 1);
            }
            this.arrayDeque = newArray;
            this.first = 0;
            this.last = this.size - 1;

        }

    }
    private int toIndex(int index){
        int newIndex;
        if (index < 0) {
            newIndex = arrayDeque.length - 1;
        } else if (index >= arrayDeque.length) {
            newIndex = 0;
        } else {
            newIndex = index;
        }
        return newIndex;
    }

    public static void main(String[] args) {

    }
}

