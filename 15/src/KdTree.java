import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

	private final TreeSet<Point2D> set = new TreeSet<>();

	public KdTree() {

	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		set.add(p);
	}

	public boolean contains(Point2D p) {
		return set.contains(p);
	}

	public void draw() {
		for (Point2D p : set) {
			p.draw();
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		java.util.List<Point2D> ans = new java.util.LinkedList<>();

		for (Point2D p : set) {
			if (rect.contains(p))
				ans.add(p);
		}

		return ans;
	}

	public Point2D nearest(Point2D p) {
		double nn_dist = Double.MAX_VALUE;
		Point2D nn = null;

		for (Point2D q : set) {
			if (q != p) {
				if (nn == null) {
					nn = q;
				} else {
					double tmp = p.distanceSquaredTo(q);
					if (tmp < nn_dist) {
						nn = q;
						nn_dist = tmp;
					}
				}
			}
		}

		return nn;
	}

	public static void main(String[] args) {

	}
}
