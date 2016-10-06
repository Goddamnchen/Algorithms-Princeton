import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private class Node implements Comparable<Node> {
		private Board board;
		private Node prev;

		private int moves;
		private int manhattan;
		private int priority;
		
		private Node(Board b, Node p) {
			board = b;
			prev = p;
			
			moves = (prev == null) ? 0 : prev.moves+1;
			manhattan = board.manhattan();
			priority = manhattan + moves;
		}
		
		@Override
		public int compareTo(Node that) {
			if (priority > that.priority)
				return 1;
			else if (priority < that.priority)
				return -1;
			else
				return 0;
		}
	}

	private Node sol;
	private Node twinsol;
	
	public Solver(Board initial) {
		sol = new Node(initial, null);
		MinPQ<Node> pq = new MinPQ<>();
		pq.insert(sol);
		
		//---
		
		twinsol = new Node (initial.twin(), null);
		MinPQ<Node> twinpq = new MinPQ<>();
		twinpq.insert(twinsol);
		
		while (!sol.board.isGoal()) {
			sol = pq.delMin();
			Iterable<Board> neighbors = sol.board.neighbors();
			
			for (Board neigh : neighbors) {
				if (!sol.board.equals(neigh))
					pq.insert(new Node(neigh, sol));
			}
			
			//---
			
			twinsol = twinpq.delMin();
			Iterable<Board> twinneighbors = twinsol.board.neighbors();
			
			for (Board neigh : twinneighbors) {
				if (!twinsol.board.equals(neigh))
					twinpq.insert(new Node(neigh, twinsol));
			}
			
			if (twinsol.board.isGoal()) {
				sol = null;
				break;
			}
		}
	}
	
	public boolean isSolvable() {
		return (sol != null);
	}
	
	public int moves() {
		if (!isSolvable())
			return -1;
		
		return sol.moves;
	}
	
	public Iterable<Board> solution() {
		if (!isSolvable())
			return null;
		
		LinkedList<Board> ans = new LinkedList<>();
		Node tmp = sol;
		while (tmp != null) {
			ans.addFirst(tmp.board);
			tmp = tmp.prev;
		}
		return ans;
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
