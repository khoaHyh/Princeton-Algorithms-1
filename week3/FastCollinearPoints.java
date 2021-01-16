import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
 *   Notes:
 *   - Only include maximal line segments containing 4 (or more) points
 *       exactly once (if p->t, don't include p->s or q->t)
 *
 *   Lessons learned:
 *   - Stability of sorting algorithms
 * */

public class FastCollinearPoints {
    // ArrayList of line segments
    private final ArrayList<LineSegment> lineSegAL = new ArrayList<>();

    // Finds all lines segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument is null.");

        Point[] copyPoints = Arrays.copyOf(points, points.length);

        // Check for null and repeated points
        for (int i = 0; i < copyPoints.length - 1; i++) {
            if (copyPoints[i] == null) throw new IllegalArgumentException("Constructor argument is null.");
            for (int j = i + 1; j < copyPoints.length; j++)
                if (copyPoints[i].compareTo(copyPoints[j]) == 0)
                    throw new IllegalArgumentException("Argument to constructor contains repeated point.");
        }

        // Preserve natural stable order
        Arrays.sort(copyPoints);

        for (int i = 0; i < copyPoints.length; i++) {
            Arrays.sort(copyPoints);
            Point p = copyPoints[i];
            // Sort the points according to the slopes they make with p)
            Arrays.sort(copyPoints, p.slopeOrder());
            int currentStart = 0;
            for (int j = 1; j < copyPoints.length; j++) {
                if (copyPoints[j].slopeTo(p) != copyPoints[currentStart].slopeTo(p)) {
                    int lineSegLen = j - currentStart;
                    // Ensure maximal line segment by comparing start of current segment with p
                    if (lineSegLen >= 3 && copyPoints[currentStart].compareTo(p) > 0) {
                        lineSegAL.add(new LineSegment(p, copyPoints[j - 1]));
                    }
                    currentStart = j;
                }
            }
        }
    }

    // The number of line segments
    public int numberOfSegments() {
        return lineSegAL.size();
    }

    // The line segments
    public LineSegment[] segments() {
        return lineSegAL.toArray(new LineSegment[lineSegAL.size()]);
    }

    // For testing
//    public static void main(String[] args) {
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
////        // To understand slopeOrder()
////        Arrays.sort(points);
////        for (int i = 0; i < points.length; i++) {
////            Arrays.sort(points);
////            Arrays.sort(points, points[i].slopeOrder());
////            if (i == 0 || i == 2 || i == 4) StdOut.println("i: " + points[i]);
////            for (int j = 1; j < points.length; j++) {
////                if (i == 0 || i == 2 || i == 4)
////                    StdOut.println("slope: " + points[i].slopeTo(points[j]) + ", j: " + points[j]);
////            }
////        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}
