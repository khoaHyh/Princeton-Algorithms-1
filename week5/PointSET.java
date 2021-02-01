import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;

public class PointSET {
    TreeSet<Point2D> set;

    // utility function to check for null arguments
    private void checkNullArg(Object arg) {
        if (arg == null) throw new IllegalArgumentException();
    }

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNullArg(p);
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNullArg(p);
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNullArg(rect);
        Stack<Point2D> pointsInside = new Stack<>();
        for (Point2D point : set)
            if (rect.contains(point))
                pointsInside.push(point);

        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNullArg(p);
        if (isEmpty()) return null;
        Point2D champion = null;
        double champDistance = Math.sqrt(2);

        for (Point2D point : set) {
            double distance = point.distanceTo(p);
            if (distance < champDistance) {
                champion = point;
                champDistance = distance;
            }
        }

        return champion;
    }
}
