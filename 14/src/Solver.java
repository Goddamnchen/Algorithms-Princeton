import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private final Board initial;
	private boolean isSolvable = false;
	private List<Board> solution = new LinkedList<>();
	private int moves = 0;

	public Solver(Board _initial) {
		initial = _initial;
		
		//Compute solution here
	}
	
	public boolean isSolvable() {
		return isSolvable;
	}
	
	public int moves() {
		if (!isSolvable())
			return -1;
		
		return moves;
	}
	
	public Iterable<Board> solution() {
		return solution;
	}
	
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
