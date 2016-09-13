import edu.princeton.cs.algs4.StdDraw;

public class PointsWindow {
	
	public static void main(String[] args) {
		Point[] points = {new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000), new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000), new Point(14000, 15000), new Point(6000, 7000)};
		
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		for(Point p : points){
			p.draw();
		}
		StdDraw.show();
		
		BruteCollinearPoints bruteSegs = new BruteCollinearPoints(points);
		for(LineSegment l : bruteSegs.segments()){
			System.out.println(l);
			l.draw();
		}
		StdDraw.show();
	}
}
