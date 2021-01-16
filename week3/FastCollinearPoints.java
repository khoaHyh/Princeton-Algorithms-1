import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

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

        int n = points.length;

        // Check for null and repeated points
        for (int i = 0; i < n - 1; i++) {
            checkNull(points[i]);
            for (int j = i + 1; j < n; j++) {
                if (j == n - 1) checkNull(points[j]);
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Argument to constructor contains repeated point.");
            }
        }

        Point[] copyPoints = Arrays.copyOf(points, n);
        // Arrays.sort for Object[] arrays is mergesort. As we learned from lecture/textbook, mergesort
        //  is stable. We will sort by natural order first to make sure we sort from ascending order first.
        Arrays.sort(copyPoints);

        // Iterate through the natural order of the Points array.
        // We use n - 3 because the last 3 points are unable to form a line segment.
        for (int i = 0; i < n - 3; i++) {
            Point p = copyPoints[i];

            Point[] slopeSortP = new Point[n];
            // Copy from the first element of naturally sorted array to the new array, i times.
            System.arraycopy(copyPoints, 0, slopeSortP, 0, i);
            // Copy from i + 1 of naturally sorted array to point i of the new array, n - i - 1 times.
            System.arraycopy(copyPoints, i + 1, slopeSortP, i, n - i - 1);

            // Sort the points according to the slopes they make with p.
            Arrays.sort(slopeSortP, 0, n - 1, p.slopeOrder());
            int currentStart = 0;
            for (int j = 1; j < n; j++) {
                // Last element in slopeSortP array is null
                if (slopeSortP[j] == null ||  slopeSortP[j].slopeTo(p) != slopeSortP[currentStart].slopeTo(p)) {
                    int adjCount = j - currentStart;
                    // Ensure maximal line segment by comparing start of current segment with p
                    if (adjCount >= 3) {
                        assert slopeSortP[currentStart] != null;
                        if (slopeSortP[currentStart].compareTo(p) > 0) {
                            lineSegAL.add(new LineSegment(p, slopeSortP[j - 1]));
                        }
                    }
                    currentStart = j;
                }
            }
        }
    }

    private void checkNull(Point point) {
        if (point == null) throw new IllegalArgumentException("Constructor argument is null.");
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
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // To understand natural sort and stability
//        for (int i = 0; i < points.length; i++) {
//            Arrays.sort(points);
//            Point p = points[i];
//            StdOut.println("i: " + points[i]);
//            Arrays.sort(points, p.slopeOrder());
//            StdOut.println("iSlope: " + points[i]);
//            for (int j = 1; j < points.length; j++) {
//                StdOut.println("slope: " + p.slopeTo(points[j]) + ", j: " + points[j]);
//            }
//        }
//
//        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdOut.println("Number of line segments: " + collinear.numberOfSegments());
    }
}
