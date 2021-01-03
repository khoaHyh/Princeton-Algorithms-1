import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF connectedGrid;
    private boolean[] openSites;
    private int gridSize, virtualTop, virtualBot, totalOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Error: n <= 0");

        gridSize = n;
        connectedGrid = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n];
        totalOpenSites = 0;

        // Initialize whole grid to be blocked, if other libraries were allowed,
        // Array.fill() would be a clean way to write this.
        for (int i = 0; i < openSites.length; i++) {
            openSites[i] = false;
        }

        // Initialize virtual top and bottom sites
        virtualTop = n * n;
        virtualBot = (n * n) + 1;
    }

    // Map 2D pair to a 1D union-find object index
    private int xyTo1D(int row, int col) {
        return ((row - 1) * gridSize) + (col - 1);
    }

    // Throw an exception for invalid indices
    private void validateIndices(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Invalid indices.");
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

            // Connect to virtual top site if the site in question is on the top row
            if (row == 1)
                connectedGrid.union(index1D, virtualTop);
            // Connect to virtual top site if the site in question is on the bottom row
            if (row == gridSize)
                connectedGrid.union(index1D, virtualBot);

            // Link the site in question to its open neighbors
            if (row < gridSize && isOpen(row + 1, col))
                connectedGrid.union(index1D, xyTo1D(row + 1, col));
            if (row > 1 && isOpen(row - 1, col))
                connectedGrid.union(index1D, xyTo1D(row - 1, col));
            if (col < gridSize && isOpen(row, col + 1))
                connectedGrid.union(index1D, xyTo1D(row, col + 1));
            if (col > 1 && isOpen(row, col - 1))
                connectedGrid.union(index1D, xyTo1D(row, col - 1));
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
        return connectedGrid.find(xyTo1D(row, col)) == connectedGrid.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connectedGrid.find(virtualBot) == connectedGrid.find(virtualTop);
    }
}
