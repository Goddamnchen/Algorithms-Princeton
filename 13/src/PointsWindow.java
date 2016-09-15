import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdDraw;

public class PointsWindow {
	
	public static void main(String[] args) {
		//Point[] points = {new Point(10000, 0), new Point(0, 10000), new Point(3000, 7000), new Point(7000, 3000), new Point(20000, 21000), new Point(3000, 4000), new Point(14000, 15000), new Point(6000, 7000)};
		//Point[] points = {new Point(10000,200), new Point(2000,10000), new Point(2000,20000), new Point(2000,5000), new Point(2000,8000)};
		//Point[] points = {new Point(5000, 100), new Point(5000, 1000), new Point(5000, 2000), new Point(5000, 3000), new Point(5000, 4000), new Point(5000, 5000), new Point(5000, 6000)};
		
		ArrayList<Point> points = new ArrayList<Point>();
		String filename = "input2000.txt";
		Scanner in = null;
		try {
			in = new Scanner(new File(System.getProperty("user.dir") + "\\collinear-testing\\" + filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int nbLines = in.nextInt();
		for (int i = 0; i < nbLines; ++i) {
			int x = in.nextInt();
			int y = in.nextInt();
			points.add(new Point(x, y));
		}
			
		//---
		
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		
		for(Point p : points){
			p.draw();
		}
		StdDraw.show();
		
		Point[] mypoints = points.toArray(new Point[points.size()]);
		
		FastCollinearPoints bruteSegs = new FastCollinearPoints(mypoints);
		//BruteCollinearPoints bruteSegs = new BruteCollinearPoints(mypoints);
		for(LineSegment l : bruteSegs.segments()){
			System.out.println(l);
			l.draw();
		}
		StdDraw.show();
	}
}
