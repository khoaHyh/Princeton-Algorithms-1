import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.Stack;

public class KdTree {
    private static final double MIN = 0.0;
    private static final double MAX = 1.0;

    // root of KdTree
    private Node root;
    // number of nodes in KdTree
    private int size = 0;

    // represent a node in a 2d-tree
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean orientation = true;     // the orientation of the node
    }

    // utility function to check for null arguments
    private void checkNullArg(Object arg) {
        if (arg == null) throw new IllegalArgumentException();
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNullArg(p);
        if (!contains(p)) {
            // true = x-coordinate (vertical), false = y-coordinate (horizontal)
            root = insert(root, p, MIN, MIN, MAX, MAX);
        }
    }

    // helper function to insert correctly into the kd tree
    private Node insert(Node currRoot, Point2D p, double xmin, double ymin, double xmax, double ymax) {
        if (currRoot == null) {
            size++;
            currRoot = new Node();
            currRoot.p = p;
            currRoot.rect = new RectHV(MIN, MIN, MAX, MAX);
            return currRoot;
        }
        int cmp = p.compareTo(currRoot.p);
        if (cmp < 0) {
            if (currRoot.orientation) xmax = currRoot.p.x();
            else ymax = currRoot.p.y();
            currRoot.lb = insert(currRoot.lb, p, xmin, ymin, xmax, ymax);
        }
        if (cmp > 0) {
            if (currRoot.orientation) xmin = currRoot.p.x();
            else ymin = currRoot.p.y();
            currRoot.rt = insert(currRoot.rt, p, xmin, ymin, xmax, ymax);
        }
        return currRoot;


//        if (currRoot == null) {
//            currRoot = new Node();
//            currRoot.p = p;
//            currRoot.rect = new RectHV(MIN, MIN, MAX, MAX);
//        }
//
//        int cmp = p.compareTo(currRoot.p);
//        if (cmp < 0) {
//            if (currRoot.lb != null) {
//                currRoot.lb.orientation = !currRoot.orientation;
//                currRoot.lb = insert(currRoot.lb, p);
//            } else {
//                Node newNode = new Node();
//                if (currRoot.orientation)
//                    newNode.rect = new RectHV(currRoot.rect.xmin(), currRoot.rect.ymin(),
//                            currRoot.rect.xmax(), currRoot.p.y());
//                else
//                    newNode.rect = new RectHV(currRoot.rect.xmin(), currRoot.rect.ymin(),
//                            currRoot.p.x(), currRoot.rect.ymax());
//
//                newNode.p = p;
//                currRoot.lb = newNode;
//                size++;
//            }
//        } else if (cmp > 0) {
//            if (currRoot.rt != null) {
//                currRoot.rt.orientation = !currRoot.orientation;
//                currRoot.rt = insert(currRoot.rt, p);
//            } else {
//                Node newNode = new Node();
//                if (currRoot.orientation)
//                    newNode.rect = new RectHV(currRoot.rect.xmin(), currRoot.p.y(),
//                            currRoot.rect.xmax(), currRoot.p.y());
//                else
//                    newNode.rect = new RectHV(currRoot.p.x(), currRoot.rect.ymin(),
//                            currRoot.p.x(), currRoot.rect.ymax());
//
//                newNode.p = p;
//                currRoot.rt = newNode;
//                size++;
//            }
//        }
//        return currRoot;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNullArg(p);
        return contains(root, p) != null;
    }

    // helper function to check if the set contains point p
    private Point2D contains(Node x, Point2D p) {
        int cmp = p.compareTo(x.p);
        if (cmp < 0) {
            return contains(x.lb, p);
        } else if (cmp > 0) {
            return contains(x.rt, p);
        } else
            return x.p;
    }

    // draw all points to standard draw
    public void draw() {
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNullArg(rect);
        Stack<Node> nodes = new Stack<>();
        ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
        if (root != null)
            nodes.push(root);
        while (!nodes.isEmpty()) {
            Node curr = nodes.pop();
            if (curr == null || !rect.intersects(curr.rect))
                continue;
            if (rect.contains(curr.p))
                pointsInside.add(curr.p);
            nodes.push(curr.lb);
            nodes.push(curr.rt);
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNullArg(p);
        if (isEmpty()) return null;
        return nearest(root, p, root.p);
    }

    private Point2D nearest(Node node, Point2D p, Point2D champion) {
        if (node == null) return champion;
        if (node.rect.distanceSquaredTo(p) >= p.distanceSquaredTo(champion))
            return champion;
        if ((node.p).distanceSquaredTo(p) < p.distanceSquaredTo(champion))
            champion = node.p;

        Point2D secondClosest;
        if (node.lb != null && isSameSide(p, node.lb, node)) {
            secondClosest = nearest(node.lb, p, champion);
            champion = nearest(node.rt, p, secondClosest);
        } else {
            secondClosest = nearest(node.rt, p, champion);
            champion = nearest(node.lb, p, secondClosest);
        }

        return champion;
    }

    private boolean isSameSide(Point2D p, Node n, Node prev) {
        if (prev.orientation) {
            return Point2D.X_ORDER.compare(p, prev.p)
                    == Point2D.X_ORDER.compare(n.p, prev.p);
        } else {
            return Point2D.Y_ORDER.compare(p, prev.p)
                    == Point2D.Y_ORDER.compare(n.p, prev.p);
        }
    }
}
