import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

public class Board {
    // Board dimension
    private final int n;
    // Independent copy of tiles array
    private final char[] copyArr;
    // length of 1D tiles array (n^2)
    private final int nSquared;
    // Stores position of the blank square
    private int blankIndex;
    // hamming instance variable
    private int hamming = 0;
    // manhattan instance variable
    private int manhattan = 0;

    // check if the tile is in the correct place
    private boolean inWrongPlace(int index) {
        return this.copyArr[index] != index + 1;
    }

    // returns a new array with swapped elements
    private int[][] swap(int a, int b) {
        int[][] copy = charToIntArr(this.copyArr);
        int temp = copy[getRow(a)][getCol(a)];
        copy[getRow(a)][getCol(a)] = copy[getRow(b)][getCol(b)];
        copy[getRow(b)][getCol(b)] = temp;

        return copy;
    }

    // convert a 1D char array to a 2D int array
    private int[][] charToIntArr(char[] arr) {
        int[][] newArr = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                newArr[row][col] = arr[xyTo1D(row, col)];

        return newArr;
    }

    private int xyTo1D(int x, int y) {
        return x * n + y;
    }

    // get the row from a 1D array index
    private int getRow(int index) {
        return (index - getCol(index)) / n;
    }

    // get the column from a 1D array index
    private int getCol(int index) {
        return index % n;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        nSquared = n*n;
        this.copyArr = new char[n*n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                int tile = tiles[row][col];
                this.copyArr[xyTo1D(row, col)] = (char) tile;

                if (tiles[row][col] == 0) {
                    blankIndex = xyTo1D(row, col);
                // calculate the hamming and manhattan
                } else if (inWrongPlace(xyTo1D(row, col))) {
                    hamming++;
                    int goalRow = getRow(tile - 1);
                    int goalCol = getCol(tile - 1);
                    // add the combined vertical and horizontal distances to the sum
                    manhattan += Math.abs(col - goalCol) + Math.abs(row - goalRow);
                }
            }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) this.copyArr[xyTo1D(i, j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < nSquared; i++)
            if (this.copyArr[i] != that.copyArr[i])
                    return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        if (getRow(blankIndex) > 0) neighbors.push(new Board(swap(blankIndex, blankIndex - n)));
        if (getRow(blankIndex) < n - 1) neighbors.push(new Board(swap(blankIndex, blankIndex + n)));
        if (getCol(blankIndex) > 0) neighbors.push(new Board(swap(blankIndex - 1, blankIndex)));
        if (getCol(blankIndex) < n - 1) neighbors.push(new Board(swap(blankIndex + 1, blankIndex)));

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < nSquared - 1; i++)
            if (this.copyArr[i] != 0 && this.copyArr[i + 1] != 0)
                return new Board(swap(i, i+1));

        throw new RuntimeException("Invalid board, too many zeroes.");
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Test isGoal()
        int n = 3;
        int[][] a = new int[n][n];
        int[][] b = new int[n][n];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int prevRowTile = j + 1;

                if (i == 2 && j == 2) a[i][j] = 0;
                else a[i][j] = prevRowTile + (i * 3);
            }

//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++) {
//                int prevRowTile = j + 1;
//
//                if (i == 2 && j == 2) b[i][j] = 0;
//                else b[i][j] = prevRowTile + (i * 3);
//            }

        int count = 8;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1) b[i][j] = 0;
                else {
                    b[i][j] = count;
                    count--;
                }
            }

        Board test = new Board(a);
        Board test2 = new Board(b);

        System.out.println(test2.toString());
        System.out.println("isGoal: " + test2.isGoal());
        System.out.println("dimension: " + test2.dimension());
        System.out.println("hamming: " + test2.hamming());
        System.out.println("manhattan: " + test2.manhattan());
        System.out.println("equals: " + test.equals(test2));
        System.out.println("twin: " + test2.twin().toString());

        for (Board x : test2.neighbors())
            System.out.println(x.toString());
    }
}
