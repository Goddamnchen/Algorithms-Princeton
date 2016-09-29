
public class Test {

	public static void main(String[] args) {
		Board board_solved = new Board(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
		System.out.println(board_solved);
		System.out.println("Man: " + board_solved.manhattan());
		System.out.println("Ham: " + board_solved.hamming());
		System.out.println("isGoal: " + board_solved.isGoal());
		
		System.out.println("\n---\n");
		
		Board board1 = new Board(new int[][] {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
		System.out.println(board1);
		System.out.println("Man: " + board1.manhattan());
		System.out.println("Ham: " + board1.hamming());
		System.out.println("isGoal: " + board1.isGoal());

	}
}
