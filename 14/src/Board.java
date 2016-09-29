
public class Board {

	private int[][] blocks;
	private final int n;
	
	public Board(int[][] _blocks) {
		n = _blocks.length;
		
		blocks = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				blocks[i][j] = _blocks[i][j];
			}
		}
	}
	
	public int dimension() {
		return n;
	}
	
	public int hamming() {
		int k = 1;
		int dist = 0;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != 0 && blocks[i][j] != k)
					++dist;
				++k;
			}
		}
		
		return dist;
	}
	
	public int manhattan() {
		int k = 1;
		int dist = 0;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != 0) {
					int tmp = Math.abs(k - blocks[i][j]);
					dist += tmp/3 + tmp%3;
				}
				++k;
			}
		}
		
		return dist;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n-1; ++j) {
				str += blocks[i][j] + " ";
			}
			str += blocks[i][n-1];
			if (i < n-1) str += "\n";
		}
		return str;
	}
	
	public boolean isGoal() {
		int k = 1;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != k && blocks[i][j] != 0) {
					return false;
				}
				++k;
			}
		}
		
		return true;
	}
}
