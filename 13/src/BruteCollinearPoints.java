import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private static final double epsi = 0.001;
	
	private class MyLineSegment {
				
		public Point p1, p2;
		
		public MyLineSegment(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	}
		
	private LineSegment[] segments;
	
	public BruteCollinearPoints(Point[] points) {
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
		
		ArrayList<MyLineSegment> tmp = new ArrayList<MyLineSegment>();
		
		for (int a = 0; a < points.length; ++a) {
			Point p = points[a];
			
			for (int b = a+1; b < points.length; ++b) {
				Point q = points[b];
				
				for (int c = b+1; c < points.length; ++c) {
					Point r = points[c];
					
					for (int d = c+1; d < points.length; ++d) {
						Point s = points[d];
						
						if ((p != q && p != r && p != s)) {
							double slopePQ = p.slopeTo(q);
							double slopePR = p.slopeTo(r);
							double slopePS = p.slopeTo(s);
							
							if (slopePQ - slopePR < epsi && slopePQ - slopePS < epsi) {
								Point[] four = new Point[4];
								four[0] = p;
								four[1] = q;
								four[2] = r;
								four[3] = s;
								Arrays.sort(four);
								
								MyLineSegment seg = new MyLineSegment(four[0], four[3]);
								
								addLS(tmp, seg);
							}
						}
					}
				}
			}
		}
		
		//--- storage
		
		segments = new LineSegment[tmp.size()];
		for (int i = 0; i < tmp.size(); ++i) {
			segments[i] = new LineSegment(tmp.get(i).p1, tmp.get(i).p2);
		}
	}
	
	private void addLS(ArrayList<MyLineSegment> list, MyLineSegment seg) {
		list.add(seg);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}