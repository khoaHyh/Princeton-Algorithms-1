import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

/*
*   Utils/Tools/Tips:
*   - Order of growth of the running time should be n^4 (O(n^4)).
*   - Our solution should use space proportional to 'n' plus number of line segments returned.
*   - Arrays.sort(a, lo, hi)
*       - sorts the subarray from a[lo] to a[h-1] according to natural order of a[]
*       - We can use a Comparator as the 4th argument to sort according to an alternate order.
*   - ArrayList to mutate the array size
*
*   Lessons learned:
*   - ArrayLists are powerful structures because its not a fixed size
*       - Comes with methods to add, remove, etc.
*   - Mini-optimizations by adding conditionals (situational) before the last loop
*       in nested for loops.
*
*   Improvements:
*   - Implementing N choose k (N choose 4)
*       - Iterating through all combinations of 4 points instead of all 4 tuples
*       - Saving a factor of 4!
* */

public class BruteCollinearPoints {
    // Array of line segments
    private final ArrayList<LineSegment> lineSegAL = new ArrayList<>();

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Constructor argument is null.");

        // Check for null and repeated points
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Point in array is null.");
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Argument to constructor contains repeated point.");

        }

        // Sort by natural order
        Arrays.sort(points);

        // Iterate through cWorst case n^4
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double slope2 = points[j].slopeTo(points[k]);
                    // If the first slope is not the same value as the
                    // second slope there won't be a line segment.
                    if (Double.compare(slope1, slope2) != 0) continue;
                    for (int l = k + 1; l < points.length; l++) {
                        double slope3 = points[k].slopeTo(points[l]);
                        if (Double.compare(slope1, slope2) == 0 && Double.compare(slope2, slope3) == 0) {
                            lineSegAL.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    // The number of line segments
    public int numberOfSegments() {
        return lineSegAL.size();
    }

    // The line segment
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        // Print number of line segments
        StdOut.println("Number of line segments: " + collinear.numberOfSegments());
    }
}
