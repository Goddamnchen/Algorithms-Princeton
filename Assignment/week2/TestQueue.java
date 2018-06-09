import org.junit.Test;
import static org.junit.Assert.*;
public class TestQueue {
    @Test
    public void testDeque(){
        Deque<Integer> stringDeque = new Deque<>();
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
}
