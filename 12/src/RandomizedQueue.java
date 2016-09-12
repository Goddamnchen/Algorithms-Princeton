import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {

	private final static int CAPA0 = 10;
	
	private int n;
	private Item[] tab;
	
	public RandomizedQueue() {
		n = 0;
		tab = (Item[]) new Object[CAPA0];
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException();
		
		if (n == tab.length)
			double_array();
		
		tab[n++] = item;
	}
	
	private void double_array() {
		Item[] ntab = (Item[]) new Object[tab.length*2];
		for (int i = 0; i < tab.length; ++i) {
			ntab[i] = tab[i];
		}
		tab = ntab;
	}
	
	private void divide_array() {
		Item[] ntab = (Item[]) new Object[(int) (Math.floor(0.25*tab.length)+1)];
		for (int i = 0; i < n; ++i) {
			ntab[i] = tab[i];
		}
		tab = ntab;
	}
	
	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		
		if (n < 0.25*tab.length && n > 3)
			divide_array();
		
		int rnd = StdRandom.uniform(n);
		Item ans = tab[rnd];
		
		if (rnd < n)
			tab[rnd] = tab[n-1];
		
		tab[--n] = null;
		return ans;
	}
	
	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		
		int rnd = StdRandom.uniform(n);
		return tab[rnd];
	}
	
	private class RQItemIterator implements Iterator<Item> {

		private int cur = n-1;
		private int[] idx = new int[n];
		
		public RQItemIterator() {
			for (int i = 0; i < n; ++i)
				idx[i] = i;
		}
		
		@Override
		public boolean hasNext() {
			return cur >= 0;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
						
			int rnd = StdRandom.uniform(cur+1);
			int ans = idx[rnd];
			
			idx[rnd] = idx[cur--];
			
			return tab[ans];
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}
	
	public Iterator<Item> iterator() {
		return new RQItemIterator();
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
		for (int j = 0; j < 1; ++j) {
			for (int i = 0; i < CAPA0; ++i)
				rq.enqueue(i);
			
			for (int i = 0; i < CAPA0; ++i)
				System.out.print(rq.dequeue() + " ");
			System.out.println();
		}
		
		System.out.println("---");
		
		for (int i = 0; i < CAPA0; ++i)
			rq.enqueue(i);
		
		for (Integer tmp : rq)
			System.out.print(tmp + " ");
		System.out.println();
		
		System.out.println(StdRandom.uniform());
	}
}
