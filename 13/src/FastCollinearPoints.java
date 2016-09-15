import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class FastCollinearPoints {
	
	private class Line {
		public double slope;
		public double oo; //y when x=0
		
		public Line(double _slope, double _oo) {
			slope = _slope;
			oo = _oo;
		}
		
		@Override
		public int hashCode() {
			return (int) (slope+oo);
		}
		
		@Override
		public boolean equals(Object l) {
			return slope == ((Line) l).slope && oo == ((Line) l).oo;
		}
	}
	
	private LineSegment[] segments;
	
	//---
	
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
		
		version1(points);
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments.clone();
	}
	
	private void version3(Point[] points) {
		HashMap<Point, ArrayList<Double>> tmp = new HashMap<>();
		
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
					for (Point point : cur) {
						if (!tmp.keySet().contains(point))
							tmp.put(point, new ArrayList<>());
						
						if (!tmp.get(point).contains(slopePQ)) //maybe remove this check
							tmp.get(point).add(slopePQ);
					}
				}
			}
		}
		
		//--- storage
		
		segments = new LineSegment[tmp.size()];
		//need to transform tmp into LineSegment[]
		
		/*int i = 0;
		
		for (Line l : tmp.keySet()) {
			Collections.sort(tmp.get(l));
			segments[i] = new LineSegment(tmp.get(l).get(0), tmp.get(l).get(tmp.get(l).size()-1));
			if (segments[i] == null)
				System.out.println("oktamer");
		}*/
	}
	
	private double computeOO(Point p1, Point p2) {
		String tmp = p1.toString();
		
		String[] p1str = tmp.substring(1, tmp.length()-1).split(", ");
		int x1 = Integer.parseInt(p1str[0]);
		int y1 = Integer.parseInt(p1str[1]);
		
		tmp = p2.toString();
		
		String[] p2str = tmp.substring(1, tmp.length()-1).split(", ");
		int x2 = Integer.parseInt(p2str[0]);
		int y2 = Integer.parseInt(p2str[1]);
		
		return (y1*x2 - y2*x1) / (x2-x1);
	}
	
	private void version2(Point[] points) {
		HashMap<Line, ArrayList<Point>> tmp = new HashMap<>();
		
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
					double ooPQ = computeOO(p, q);
					Line pq = new Line(slopePQ, ooPQ);
					
					if (!tmp.keySet().contains(pq))
						tmp.put(pq, new ArrayList<>());
					
					for (Point point : cur) {
						if (!tmp.get(pq).contains(point)) //maybe remove this check
							tmp.get(pq).add(point);
					}
				}
			}
		}
		
		//--- storage
		
		segments = new LineSegment[tmp.size()];
		int i = 0;
		for (Line l : tmp.keySet()) {
			Collections.sort(tmp.get(l));
			segments[i] = new LineSegment(tmp.get(l).get(0), tmp.get(l).get(tmp.get(l).size()-1));
			if (segments[i] == null)
				System.out.println("oktamer");
		}
	}
	
	private void version1(Point[] points) {
		ArrayList<LineSegment> tmp = new ArrayList<LineSegment>();
		
		Point[] clone = points.clone();
		
		for (int i = 0; i < points.length; ++i) {
			Point p = points[i];
			Arrays.sort(clone, p.slopeOrder());
			
			for (int j = 1; j < clone.length; ) {
				Point q = clone[j];
				
				double slopePQ = p.slopeTo(q);
				
				ArrayList<Point> cur = new ArrayList<>();
				cur.add(p);
				cur.add(q);
				
				int k = j+1;
				while (k < clone.length && slopePQ == p.slopeTo(clone[k])) {
					cur.add(clone[k]);
					++k;
				}
				
				j = k;
				
				if (cur.size() > 3) {
					Point[] curarr = new Point[cur.size()];
					for (int a = 0; a < cur.size(); ++a) {
						curarr[a] = cur.get(a);
					}
					
					Arrays.sort(curarr);
					if (curarr[0] == p) {
						LineSegment seg = new LineSegment(p, curarr[curarr.length-1]);
						tmp.add(seg);
					}
				}
			}
		}
		
		//--- storage

		segments = new LineSegment[tmp.size()];
		for (int i = 0; i < tmp.size(); ++i) {
			segments[i] = tmp.get(i);
		}
	}
}