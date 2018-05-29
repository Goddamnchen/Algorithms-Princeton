
import edu.princeton.cs.algs4.*;

public class PercolationStats {
	
	private double[] percents;

	/**
	 * Performs trials (independent experiments) on an n-by-n grid
	 * @param n size of the grid
	 * @param trials number of experiments
	 */
	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1)
			throw new java.lang.IllegalArgumentException();
		
		percents = new double[trials];
		final int n_sq = n*n;
		
		for (int i = 0; i < trials; ++i) {
			Percolation p = new Percolation(n);
			int j = 0;
			
			while (!p.percolates()) {
				int r, c;
				
				do {
					r = StdRandom.uniform(n)+1;
					c = StdRandom.uniform(n)+1;
				} while (p.isOpen(r, c));
				
				p.open(r, c);
				++j;
			}
			
			percents[i] = (double) j/n_sq;
		}
	}
	
	/**
	 * sample mean of percolation threshold
	 * @return mean of percolation threshold
	 */
	public double mean() {
		return StdStats.mean(percents);
	}
	
	/**
	 * sample std deviation of percolation threshold
	 * @return std dev
	 */
	public double stddev() {
		return StdStats.stddev(percents);
	}
	
	/**
	 * low endpoint of 95% confidence interval
	 * @return low endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(percents.length);
	}
	
	/**
	 * high endpoint of 95% confidence interval
	 * @return high endpoint of 95% confidence interval
	 */
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(percents.length);
	}
	
	/**
	 * test client
	 * @param args [n size of the grid, trials number of experiments]
	 */
	public static void main(String[] args) {
		if (args.length < 2)
			throw new IllegalArgumentException();
		
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		
		System.out.println("mean                    = " + stats.mean());
		System.out.println("stddev                  = " + stats.stddev());
		System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
