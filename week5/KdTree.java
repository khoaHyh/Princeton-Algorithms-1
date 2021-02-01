import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final byte VERTICAL = Byte.parseByte("0");
    private static final byte HORIZONTAL = Byte.parseByte("1");
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
        private byte orientation;     // the orientation of the node

        public Node(Point2D p, RectHV rect, byte orientation) {
            this.p = p;
            this.rect = rect;
            this.orientation = orientation;
        }
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
            root = insert(root, p, MIN, MIN, MAX, MAX, HORIZONTAL);
        }
    }

    // helper function to insert correctly into the kd tree
    private Node insert(Node currRoot, Point2D p, double xmin, double ymin, double xmax, double ymax, byte prevO) {
        if (currRoot == null) {
            size++;
            byte newO;
            if (prevO % 2 == 0) newO = HORIZONTAL;
            else newO = VERTICAL;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), newO);
        }

        int cmp = p.compareTo(currRoot.p);
        if (cmp < 0) {
            double copyY = ymax;
            double copyX = xmax;
            if (currRoot.orientation % 2 == 0) {
                copyY = currRoot.p.x();
            } else {
                copyX = currRoot.p.y();
            }

            currRoot.lb = insert(currRoot.lb, p, xmin, ymin, copyX, copyY, currRoot.orientation);
        }
        if (cmp > 0) {
            double copyY = ymax;
            double copyX = xmax;
            if (currRoot.orientation % 2 == 0) {
                copyY = currRoot.p.x();
            } else {
                copyX = currRoot.p.y();
            }
            currRoot.rt = insert(currRoot.rt, p, xmin, ymin, copyX, copyY, currRoot.orientation);
        }
        return currRoot;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNullArg(p);
        return contains(root, p);
    }

    // helper function to check if the set contains point p
    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        int cmp = p.compareTo(x.p);
        if (cmp < 0)
            return contains(x.lb, p);
        if (cmp > 0)
            return contains(x.rt, p);
        return true;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        RectHV canvas = new RectHV(0, 0, 1, 1);
        canvas.draw();
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        if (node.orientation % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.lb);
        draw(node.rt);
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
        if (prev.orientation % 2 == 0) {
            return Point2D.X_ORDER.compare(p, prev.p)
                    == Point2D.X_ORDER.compare(n.p, prev.p);
        } else {
            return Point2D.Y_ORDER.compare(p, prev.p)
                    == Point2D.Y_ORDER.compare(n.p, prev.p);
        }
    }
}
