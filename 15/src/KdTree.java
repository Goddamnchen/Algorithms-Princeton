import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

	private static class Node {
		private Node lb, rt;
		private RectHV zone;
		private Point2D p;

		public Node(RectHV _zone, Point2D _p) {
			zone = _zone;
			p = _p;
			lb = null;
			rt = null;
		}
	}

	// ---

	private Node root = null;
	private int n = 0;

	public KdTree() {

	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return n;
	}

	public void insert(Point2D p) {
		if (isEmpty()) {
			root = new Node(new RectHV(0, 0, 1, 1), p);
			++n;
			return;
		}

		Node tmp = root;
		while (true) {
			if (p.x() < tmp.p.x()) { // vertical division
				if (tmp.lb == null) { // left
					tmp.lb = new Node(new RectHV(tmp.zone.xmin(), tmp.zone.ymin(), tmp.p.x(), tmp.zone.ymax()), p);
					++n;
					return;
				} else {
					tmp = tmp.lb;
				}
			} else {
				if (p.x() == tmp.p.x() && p.y() == tmp.p.y())
					return;
				
				if (tmp.rt == null) { // right
					tmp.rt = new Node(new RectHV(tmp.p.x(), tmp.zone.ymin(), tmp.zone.xmax(), tmp.zone.ymax()), p);
					++n;
					return;
				} else {
					tmp = tmp.rt;
				}
			}

			if (p.y() < tmp.p.y()) { // horizontal division
				if (tmp.lb == null) { // bottom
					tmp.lb = new Node(new RectHV(tmp.zone.xmin(), tmp.zone.ymin(), tmp.zone.xmax(), tmp.p.y()), p);
					++n;
					return;
				} else {
					tmp = tmp.lb;
				}
			} else {
				if (p.x() == tmp.p.x() && p.y() == tmp.p.y())
					return;
				
				if (tmp.rt == null) { // top
					tmp.rt = new Node(new RectHV(tmp.zone.xmin(), tmp.p.y(), tmp.zone.xmax(), tmp.zone.ymax()), p);
					++n;
					return;
				} else {
					tmp = tmp.rt;
				}
			}
		}
	}

	public boolean contains(Point2D p) {
		if (isEmpty())
			return false;

		Node tmp = root;
		while (true) {
			if (p.equals(tmp.p)) { // right
				return true;
			}
			
			if (p.x() < tmp.p.x()) { // vertical division
				if (tmp.lb == null) { // left
					return false;
				} else {
					tmp = tmp.lb;
				}
			} else {
				if (tmp.rt == null) {
					return false;
				} else {
					tmp = tmp.rt;
				}
			}

			
			
			if (p.equals(tmp.p)) { // top
				return true;
			}
			
			if (p.y() < tmp.p.y()) { // horizontal division
				if (tmp.lb == null) { // bottom
					return false;
				} else {
					tmp = tmp.lb;
				}
			} else {
				if (tmp.rt == null) {
					return false;
				} else {
					tmp = tmp.rt;
				}
			}
		}
	}

	private void draw_rec(Node tmp, boolean vertical) {
		if (tmp == null)
			return;

		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.circle(tmp.p.x(), tmp.p.y(), 0.005);

		if (vertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(tmp.p.x(), tmp.zone.ymin(), tmp.p.x(), tmp.zone.ymax());
			draw_rec(tmp.lb, false);
			draw_rec(tmp.rt, false);
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(tmp.zone.xmin(), tmp.p.y(), tmp.zone.xmax(), tmp.p.y());
			draw_rec(tmp.lb, true);
			draw_rec(tmp.rt, true);
		}
	}

	public void draw() {
		draw_rec(root, true);
		StdDraw.setPenColor(StdDraw.BLACK);
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> queue = new Queue<Point2D>();

		if (!isEmpty())
			range_rec(queue, rect, root);

		return queue;
	}

	private void range_rec(Queue<Point2D> queue, RectHV rect, Node x) {
		if (!rect.intersects(x.zone))
			return;

		if (rect.contains(x.p))
			queue.enqueue(x.p);

		if (x.lb != null)
			range_rec(queue, rect, x.lb);

		if (x.rt != null)
			range_rec(queue, rect, x.rt);
	}

	public Point2D nearest(Point2D p) {
		//TODO
		if (isEmpty())
			return null;

		Queue<Point2D> todo = new Queue<>();
		nearest_rec(todo, p, root);
		
		double ndist = Double.MAX_VALUE;
		Point2D nn = null;
		for (Point2D q : todo) {
			double tmpdist = q.distanceSquaredTo(p);
			if (ndist > tmpdist) {
				nn = q;
				ndist = tmpdist;
			}
		}
		
		return nn;
	}
	
	private void nearest_rec(Queue<Point2D> queue, Point2D p, Node x) {
		//TODO
		queue.enqueue(x.p);
		
		if (x.lb != null) {
			queue.enqueue(x.lb.p);
			
			if (x.lb.zone.contains(p))
				nearest_rec(queue, p, x.lb);
		}

		if (x.rt != null && x.rt.zone.contains(p))
			nearest_rec(queue, p, x.rt);
	}

	public static void main(String[] args) {
		KdTree t = new KdTree();

		Point2D p1 = new Point2D(0, 0);
		Point2D p2 = new Point2D(0, 0);
		Point2D p3 = new Point2D(0.5, 1);
		Point2D p4 = new Point2D(0.5, 1);

		t.insert(p1);
		t.insert(p2);
		t.insert(p3);

		System.out.println(t.contains(p4));

		System.out.println(t.size());
	}
}
