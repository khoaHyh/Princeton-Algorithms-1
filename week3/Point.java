/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

/*
    Useful tools/tips:
    - Arrays.sort(a, lo, hi)
        - sorts the subarray from a[lo] to a[h-1] according to natural order of a[]
        - We can use a Comparator as the 4th argument to sort according to an alternate order
    - Examples of Comparable and Comparator from lecture
    - Do not divide by 0. With integers, this produces a run-time exception.
        - Utilize the constants Double.POSITIVE_INFINITY and Double.NEGATIVE_INFINITY
    - Return positive zero for horizontal line segments
        - Important for specification regarding positive zero and negative zero
    - To refer to Point instance of the outer class, use Point.this instead of this.
        - With proper design we shouldn't need this awkward notation
    - Consider corner cases for slopeTo()
        - horizontal, vertical, and degenerate line segments
 */

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double slope = (double) (that.y - this.y) / (that.x - this.x);

        // Degenerate line segment case
        if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY;
        // Horizontal line segment case
        else if (that.y == this.y) return +0.0;
        // Vertical line segment case
        else if (that.x == this.x) return Double.POSITIVE_INFINITY;
        else return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        // If this point is less than the argument point (that)
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        // If this point is equal to the argument point
        else if (this.x == that.x && this.y == that.y) return 0;
        // If this point is greater than the argument point
        else return +1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        // Lambda expression:
        //  return (point1, point2) -> Double.compare(slopeTo(point1), slopeTo(point2));
        // Below is a method reference:
        return Comparator.comparingDouble(this::slopeTo);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point invoke, pointA;

        /* Vertical line segments should be +Infinity */
        invoke = new Point(2, 8);
        pointA = new Point(2, 4);
        assert invoke.slopeTo(pointA) == Double.POSITIVE_INFINITY
                : "Vertical line segments should be +Infinity";
        System.out.println(invoke.slopeTo(pointA));

        /* Horizontal line segments should be +0.0 */
        invoke = new Point(4, 7);
        pointA = new Point(6, 7);
        assert invoke.slopeTo(pointA) == +0.0
                : "Horizontal line segments should be +0.0";
        System.out.println(invoke.slopeTo(pointA));

        /* The slope of a point with himself should be -Infinity */
        Point p = new Point(3, 7);
        assert p.slopeTo(p) == Double.NEGATIVE_INFINITY
                : "The slope of a point with himself should be -Infinity";
        System.out.println(p.slopeTo(p));
    }
}