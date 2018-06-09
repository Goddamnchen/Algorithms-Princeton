import org.junit.Test;
import static org.junit.Assert.*;
public class TestDequeandQueue {
    @Test
    public void testArrayListDeque(){
        ArrayListDeque<Integer> stringDeque = new ArrayListDeque<>();
        stringDeque.addFirst(1);
        stringDeque.addLast(2);
        stringDeque.addLast(3);
        stringDeque.addLast(4);
        stringDeque.addLast(5);
        stringDeque.addLast(6);
        stringDeque.addLast(7);
        stringDeque.addFirst(0);
        stringDeque.addLast(8);
        for (int a : stringDeque) {
            System.out.println(a);
        }
        int expectSize = 9;
        int actualSize = stringDeque.size();
        assertEquals(expectSize, actualSize);
        stringDeque.removeFirst();
        stringDeque.removeFirst();
        expectSize = 7;
        actualSize = stringDeque.size();
        assertEquals(expectSize, actualSize);
        stringDeque.removeFirst();
        stringDeque.removeFirst();
        stringDeque.removeFirst();
        stringDeque.removeLast();
        expectSize = 3;
        actualSize = stringDeque.size();
        assertEquals(expectSize, actualSize);
        stringDeque.removeFirst();
        stringDeque.removeFirst();
        stringDeque.removeLast();
        expectSize = 0;
        actualSize = stringDeque.size();
        assertEquals(expectSize, actualSize);
    }
    @Test
    public void testLinkedListDeque(){
        Deque<Integer> integersDeque = new Deque<>();
        integersDeque.addFirst(1);
        integersDeque.addLast(2);
        integersDeque.addLast(3);
        integersDeque.addLast(4);
        integersDeque.addFirst(0);
        integersDeque.removeLast();
        integersDeque.removeLast();
        integersDeque.removeFirst();
        integersDeque.removeFirst();
        integersDeque.removeLast();
        for (int a : integersDeque) {
            System.out.println(a);
        }
    }
    @Test
    public void testRandomizedQueue() {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        rQueue.enqueue(0);
        rQueue.enqueue(1);
        rQueue.enqueue(2);
        rQueue.enqueue(3);
        rQueue.enqueue(4);
        rQueue.enqueue(5);
        for (int a : rQueue) {
            for (int b : rQueue) {
                System.out.print(b + " ");
            }
            System.out.println();
        }
        rQueue.dequeue();
        rQueue.dequeue();
        int expectedSize = 4;
        int actualSize = rQueue.size();
        assertEquals(expectedSize, actualSize);
        rQueue.dequeue();
        rQueue.dequeue();

    }
}
