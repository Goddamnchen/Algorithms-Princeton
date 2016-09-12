import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> {

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
		if (n < 0.25*tab.length && n > 3)
			divide_array();
		
		int rnd = StdRandom.uniform(n);
		Item ans = tab[rnd];
		
		if (rnd < n)
			tab[rnd] = tab[n-1];
		
		tab[n--] = null;
		return ans;
	}
	
	public Item sample() {
		int rnd = StdRandom.uniform(n);
		return tab[rnd];
	}
	
	private class ItemIterator implements Iterator<Item> {

		private int cur = 0;
		private int[] idx = new int[n];
		
		public ItemIterator() {
			for (int i = 0; i < n; ++i)
				idx[i] = i;
			
			StdRandom.shuffle(idx);
		}
		
		@Override
		public boolean hasNext() {
			return cur < idx.length;
		}

		@Override
		public Item next() {
			return tab[idx[cur]];
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}
	
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();
		for (int i = 0; i < CAPA0; ++i)
			rq.enqueue(i);
		
		rq.enqueue(11);
		
		for (int i = 0; i < CAPA0+1; ++i)
			System.out.println(rq.dequeue());
		
		System.out.println(StdRandom.uniform());
	}
}
