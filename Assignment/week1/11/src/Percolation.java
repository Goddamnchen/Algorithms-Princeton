
import edu.princeton.cs.algs4.*;

public class Percolation {
	
	private boolean[][] grid;
	private WeightedQuickUnionUF uf; //only to manage percolation
	private WeightedQuickUnionUF uf_con; //only to manage full/connection
	private final int size_sq;
	
	/**
	 * Create n-by-n grid with all sites blocked
	 * @param n size of the grid
	 */
	public Percolation(int n) {
		if (n < 1)
			throw new java.lang.IllegalArgumentException();
		
		grid = new boolean[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				grid[i][j] = false;
			}
		}
		
		size_sq = n*n;
		
		uf = new WeightedQuickUnionUF(size_sq+2);
		uf_con = new WeightedQuickUnionUF(size_sq+1);
	}
	
	/**
	 * open site [i, j] if not already open
	 * [row, column]
	 * @param i #row in [1..n]
	 * @param j #column in [1..n]
	 */
	public void open(int i, int j) {
		if (i > grid.length || j > grid.length || i < 1 || j < 1)
			throw new java.lang.IndexOutOfBoundsException();
		
		int is = i-1;
		int js = j-1;
		
		if (!grid[is][js]) {
			grid[is][js] = true;
			
			if (is == 0) {
				uf.union(size_sq, gridToArray(is, js)); //first virtual site: top
				uf_con.union(size_sq, gridToArray(is, js));
			}
			
			if (is == grid.length-1) {
				uf.union(size_sq+1, gridToArray(is, js)); //second virtual site: bot
			}
			
			if (is > 0 && grid[is-1][js]) {
				uf.union(gridToArray(is, js), gridToArray(is-1, js));
				uf_con.union(gridToArray(is, js), gridToArray(is-1, js));
			}
			
			if (is < grid.length-1 && grid[is+1][js]) {
				uf.union(gridToArray(is, js), gridToArray(is+1, js));
				uf_con.union(gridToArray(is, js), gridToArray(is+1, js));
			}
			
			if (js > 0 && grid[is][js-1]) {
				uf.union(gridToArray(is, js), gridToArray(is, js-1));
				uf_con.union(gridToArray(is, js), gridToArray(is, js-1));
			}
			
			if (js < grid.length-1 && grid[is][js+1]) {
				uf.union(gridToArray(is, js), gridToArray(is, js+1));
				uf_con.union(gridToArray(is, js), gridToArray(is, js+1));
			}
		}
	}
	
	/**
	 * is the site [i, j] open?
	 * @param i #row in [1..n]
	 * @param j #column in [1..n]
	 * @return true if [i, j] is open, false otherwise
	 */
	public boolean isOpen(int i, int j) {
		if (i > grid.length || j > grid.length || i < 1 || j < 1)
			throw new java.lang.IndexOutOfBoundsException();
		
		int is = i-1;
		int js = j-1;
		
		return grid[is][js];
	}
	
	/**
	 * is the site [i, j] connected to the line #n-1?
	 * @param i #row in [1..n]
	 * @param j #column in [1..n]
	 * @return true if [i, j] is full, meaning that [i, j] is connected to the line #n-1
	 */
	public boolean isFull(int i, int j) {
		if (i > grid.length || j > grid.length || i < 1 || j < 1)
			throw new java.lang.IndexOutOfBoundsException();
		
		int is = i-1;
		int js = j-1;
		
		return uf_con.connected(size_sq, gridToArray(is, js));
	}
	
	/**
	 * does the system percolate?
	 * @return true if system percolates, meaning one 0-line case connected with one n-1-case
	 */
	public boolean percolates() {
		return uf.connected(size_sq, size_sq+1);
	}
	
	/**
	 * converts coordinates from the grid perspective to the array perspective
	 * @param i #row in [0..n-1]
	 * @param j #column in [0..n-1]
	 * @return coordinate [i, j] into single-dimension array equivalent
	 */
	private int gridToArray(int i, int j) {
		return j + i*grid.length;
	}
}
