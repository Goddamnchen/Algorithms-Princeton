
public class Test {

	public static void main(String[] args) {
		Board board_solved = new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});
		System.out.println(board_solved);
		System.out.println("Man: " + board_solved.manhattan());
		System.out.println("Ham: " + board_solved.hamming());
		System.out.println("isGoal: " + board_solved.isGoal());
		
		System.out.println("\n1---\n");
		
		Board board1 = new Board(new int[][] {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
		System.out.println(board1);
		System.out.println("Man: " + board1.manhattan());
		System.out.println("Ham: " + board1.hamming());
		System.out.println("isGoal: " + board1.isGoal());
		
		System.out.println("\n2---\n");

		for(Board neigh : board_solved.neighbors()) {
			System.out.println(neigh + "\n");
		}
		
		System.out.println("\n3---\n");

		for(Board neigh : board1.neighbors()) {
			System.out.println(neigh + "\n");
		}
		
		System.out.println("\n4---\n");

		Solver solver_solved = new Solver(board_solved);
		for (Board b : solver_solved.solution())
			System.out.println(b + "\n");
		
		System.out.println("\n5---\n");

		Solver solver1 = new Solver(board1);
		for (Board b : solver1.solution())
			System.out.println(b + "\n");
		
		System.out.println("\n5---\n");

		Solver solver = new Solver(new Board(new int[][] {{1, 3}, {2, 0}}));
		for (Board b : solver.solution())
			System.out.println(b + "\n");
	}
}
