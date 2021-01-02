import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static WeightedQuickUnionUF connectedGrid;
    private static boolean[] openSites;
    private static int gridSize, virtualTop, virtualBot, totalOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        gridSize = n;
        connectedGrid = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n];

        // Initialize whole grid to be blocked, if other libraries were allowed,
        // Array.fill() would be a clean way to write this.
        for (int i = 0; i < openSites.length; i ++) {
            openSites[i] = false;
        }

        // Initialize virtual top and bottom sites
        virtualTop = (n * n);
        virtualBot = (n * n) + 1;

        // Connect all top sites of N by N grid to virtual top site
        for (int i = 0; i < n; i++)
            connectedGrid.union(virtualTop, i);

        // Connect all bottom sites of N b N grid to virtual bottom site
        for (int i = openSites.length - 1; i >= openSites.length - n; i--)
            connectedGrid.union(virtualBot, i);
    }

    // Map 2D pair to a 1D union-find object index
    private static int xyTo1D(int row, int col) {
        return ((row - 1) * gridSize) + (col - 1);
    }

    // Throw an exception for invalid indices
    private static void validateIndices(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        int index1D = xyTo1D(row, col);

        // Mark the site as open if isn't already
        if (!openSites[index1D]) {
            totalOpenSites++;
            openSites[index1D] = true;

            // Link the site in question to its open neighbors
            if (row + 1 <= gridSize)
                connectedGrid.union(index1D, index1D + gridSize);
            if (row - 1 > 0)
                connectedGrid.union(index1D, index1D - gridSize);
            if (col + 1 <= gridSize)
                connectedGrid.union(index1D, index1D + 1);
            if (col - 1 > 0)
                connectedGrid.union(index1D, index1D - 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return openSites[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return connectedGrid.find(xyTo1D(row, col)) == virtualTop;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connectedGrid.find(virtualBot) == virtualTop;
    }
}