import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	private final LineSegment[] segments;
	
	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		
		for (Point p : points)
			if (p == null)
				throw new java.lang.NullPointerException();
		
		for (int i = 0; i < points.length; ++i)
			for (int j = i+1; j < points.length; ++j)
				if (points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
		
		//--- checks done
		
		ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();
		
		for (int i = 0; i < points.length; ++i) {
			Point p = points[i];
			Arrays.sort(points, p.slopeOrder());
			
			for (int j = 1; j < points.length; ) {
				Point q = points[j];
				double slopePQ = p.slopeTo(q);
				
				ArrayList<Point> cur = new ArrayList<>();
				cur.add(p);
				cur.add(q);
				
				int k = j+1;
				while (k < points.length && slopePQ == p.slopeTo(points[k])) {
					cur.add(points[k]);
					++k;
				}
				
				j = k;
				
				if (cur.size() > 3) {
					Point[] curarr = new Point[cur.size()];
					for (int a = 0; a < cur.size(); ++a) {
						curarr[a] = cur.get(a);
					}
					
					Arrays.sort(curarr);
					LineSegment seg = new LineSegment(curarr[0], curarr[curarr.length-1]);
					tmp.add(seg);
				}
			}
		}
		
		//--- storage
		
		segments = new LineSegment[tmp.size()];
		for (int i = 0; i < tmp.size(); ++i) {
			segments[i] = tmp.get(i);
		}
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments.clone();
	}
}