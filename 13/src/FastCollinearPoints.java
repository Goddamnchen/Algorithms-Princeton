import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
	
	private static final double epsilon = 0.000001;
	
	private LineSegment[] segments;
	
	public FastCollinearPoints(Point[] points) {
		//TODO
	}
	
	public int numberOfSegments() {
		return segments.length;
	}
	
	public LineSegment[] segments() {
		return segments;
	}
}