import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Percolation assignment without virtual sites and 1 WQUUF instance.
// Uses bit manipulation and a byte array to handle state of each site.

public class Percolation {
    private WeightedQuickUnionUF connGrid;
    private byte[] percState;
    private int gridSize, totalOpenSites;
    // Binary Representation: 00000001
    private byte OPEN = Byte.valueOf("1");
    // Binary Representation: 00000010
    private byte FULL = Byte.valueOf("2");
    // Binary Representation: 00000100
    private byte CONNTOBOT = Byte.valueOf("4");
    // Binary Representation: 00000111
    private byte PERCOLATES = Byte.valueOf("7");
    private boolean systemPercolates = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Error: n <= 0");

        gridSize = n;
        connGrid = new WeightedQuickUnionUF(n * n );
        totalOpenSites = 0;
        // Initial value of Byte array is 0
        percState = new byte[n * n];
    }

    // Map 2D pair to a 1D union-find object index
    private int xyTo1D(int row, int col) {
        return (row - 1) * gridSize + col - 1;
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
        if ((percState[index1D] & OPEN) == 0) {
            totalOpenSites++;
            percState[index1D] = (byte)(percState[index1D] | OPEN);

            // Set bits for sites in the top and bottom row respectively
            if (row == 1)
                percState[index1D] = (byte)(percState[index1D] | FULL);
            if (row == gridSize)
                percState[index1D] = (byte)(percState[index1D] | CONNTOBOT);

            // Indices for adjacent sites
            int bottomAdj = xyTo1D(row + 1, col);
            int topAdj = xyTo1D(row - 1, col);
            int leftAdj = xyTo1D(row, col - 1);
            int rightAdj = xyTo1D(row, col + 1);

            // Link the site in question to its open neighbors and update new root with the
            // Bitwise OR of the previous roots
            // It is imperative to handle the state of the site being opened before union
            // with the adjacent site because the root site will change after the union.
            if (row < gridSize && (percState[bottomAdj] & OPEN) == OPEN) {
                percState[index1D] = (byte)(percState[index1D] | percState[connGrid.find(bottomAdj)]);
                connGrid.union(index1D, bottomAdj);
            }
            if (row > 1 && (percState[topAdj] & OPEN) == OPEN) {
                percState[index1D] = (byte)(percState[index1D] | percState[connGrid.find(topAdj)]);
                connGrid.union(index1D, topAdj);
            }
            if (col < gridSize && (percState[rightAdj] & OPEN) == OPEN) {
                percState[index1D] = (byte)(percState[index1D] | percState[connGrid.find(rightAdj)]);
                connGrid.union(index1D, rightAdj);
            }
            if (col > 1 && (percState[leftAdj] & OPEN) == OPEN) {
                percState[index1D] = (byte)(percState[index1D] | percState[connGrid.find(leftAdj)]);
                connGrid.union(index1D, leftAdj);
            }

            // Update state of site in question with state of root index
            int rootIndex = connGrid.find(index1D);
            percState[rootIndex] = (byte)(percState[rootIndex] | percState[index1D]);
            if ((byte)(percState[rootIndex] & PERCOLATES) == PERCOLATES) {
                systemPercolates = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return (percState[xyTo1D(row, col)] & OPEN) == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        // Check state of root index of site in question
        return isOpen(row, col) && (percState[connGrid.find(xyTo1D(row, col))] & FULL) == FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totalOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return systemPercolates;
    }
}
