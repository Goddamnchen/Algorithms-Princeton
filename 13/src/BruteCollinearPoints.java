import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private static final double epsilon = 0.000001;
	
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		
		ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();
		
		for (Point p : points) {
			for (Point q : points) {
				for (Point r : points) {
					for (Point s : points) {
						if ((p != q && p != r && p != s)) {
							double slopePQ = p.slopeTo(q);
							double slopePR = p.slopeTo(r);
							double slopePS = p.slopeTo(s);
							
							if (Math.abs(slopePQ - slopePR) < epsilon && Math.abs(slopePQ - slopePS) < epsilon) {
								Point[] four = new Point[4];
								four[0] = p;
								four[1] = q;
								four[2] = r;
								four[3] = s;
								Arrays.sort(four);
								
								LineSegment seg = new LineSegment(four[0], four[3]);
								tmp.add(seg);
							}
						}
					}
				}
			}
		}
		
		segments = tmp.toArray(new LineSegment[tmp.size()]);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}