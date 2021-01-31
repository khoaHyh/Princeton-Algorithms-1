import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

public class PointSET {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    private void checkNullArg(Point2D arg) {
        if (arg == null) throw new IllegalArgumentException();
    }

    // construct an empty set of points
    public PointSET() {

    }

    // is the set empty?
    public boolean isEmpty() {

    }

    // number of points in the set
    public int size() {

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNullArg(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNullArg(p);
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNullArg(p);
    }

    // unit testing of the methods (optional)
//    public static void main(String[] args) {
//
//    }
}
