import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item> {

	@SuppressWarnings("hiding")
	private class Node<Item> {
		
		private Item value;
		private Node<Item> next, prev;
		
		public Node(Item _value, Node<Item> _next, Node<Item> _prev) {
			value = _value;
			next = _next;
			prev = _prev;
		}
	}
	
	private int n;
	private Node<Item> head, queue;
	
	public Deque() {
		n = 0;
		head = null;
		queue = null;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void addFirst(Item item) {
		Node<Item> node = new Node<>(item, head, null);
		
		if (!isEmpty())
			head.prev = node;
		else
			queue = node;
		
		++n;
		head = node;
	}
	
	public void addLast(Item item) {
		Node<Item> node = new Node<>(item, null, queue);
		
		if (!isEmpty())
			queue.next = node;
		else
			head = node;
		
		++n;		
		queue = node;
	}
	
	public Item removeFirst() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		
		Node<Item> ans = head;
		
		if (head != null) {
			head = head.next;
			if (head != null)
				head.prev = null;
			else
				queue = null;
		} else {
			head = null;
		}
		
		--n;
		
		return ans.value;
	}
	
	public Item removeLast() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		
		Node<Item> ans = queue;
		
		if (queue != null) {
			queue = queue.prev;
			if (queue != null)
				queue.next = null;
			else
				head = null;
		} else {
			queue = null;
		}
		
		--n;
		
		return ans.value;
	}
	
	@Override
	public String toString() {
		if (isEmpty())
			return "EMPTY";
		
		StringBuilder b = new StringBuilder();
		
		Node<Item> tmp = head;
		while (tmp != null) {
			b.append(tmp.value + " ");
			tmp = tmp.next;
		}
		
		return b.toString();
	}
	
	private class ItemIterator implements Iterator<Item> {

		private Node<Item> cur = head;
		
		@Override
		public boolean hasNext() {
			return cur != null;
		}

		@Override
		public Item next() {
			Item ans = cur.value;
			cur = cur.next;
			return ans;
		}
		
		//reverse iterator
		/*private Node<Item> cur = queue;
		
		public boolean hasNext() {
			return cur != null;
		}
		
		public Item next() {
			Item ans = cur.value;
			cur = cur.prev;
			return ans;
		}*/
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
	
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}
	
	public static void main(String[] args) {
		Deque<Integer> d = new Deque<>();
		d.addFirst(1);
		d.addFirst(2);
		d.addLast(3);
		d.addLast(4);
		d.addFirst(5);		
		d.removeLast();d.removeLast();d.removeLast();d.removeFirst();d.removeLast();
		
		System.out.println(d.toString());
		
		for (Integer i : d) {
			System.out.println(i);
		}
		
		System.out.println("\n" + StdRandom.uniform());
	}
}
