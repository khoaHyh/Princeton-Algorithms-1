import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// useful WeightedQuickUnionUF methods
//  count
//  find
//  connected
//  validate
//  union


public class Percolation {
    private static int[] parent;
    private static int[] gridSize;
    private static int totalSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        totalSites = n;
        parent = new int[n];
        gridSize = new int[n];

    }

    // Map 2D pair to a 1D union-find object index
    private static int xyTo1D(int row, int col) {
        return gridSize[row] = col;
    }

    // Throw an exception for invalid indices
    private static void validateIndices(int row, int col) {
        if (row < 1 || row > totalSites || col < 1 || col > totalSites   ) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return true;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        // TODO
        return 1;
    }

    // does the system percolate?
    public boolean percolates() {
        // TODO
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        // Test command-line arguments
        if (args.length != 2) {
            throw new NullPointerException("2 command-line arguments are required.");
        } else {
            System.out.println(args[0]);
            System.out.println(args[1]);
        }

        // Test private method
        Percolation test = new Percolation(40);
        System.out.println(xyTo1D(12,  23));
    }
}
