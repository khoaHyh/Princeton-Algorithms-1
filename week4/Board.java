import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    // Board dimension
    private final int n;
    // Independent copy of tiles array
    private final byte[][] copyArr;
    // Stores position of the blank square
    private int rowBs = 0;
    private int colBs = 0;

    // check if the tile is in the correct place
    private boolean inWrongPlace(int row, int col) {
        return this.copyArr[row][col] != col + 1 + (row * n);
    }

    // utility function to calculate horizontal or
    //  vertical distance to goal position
    private int addToSum(int current, int goal) {
        if (current > goal) return current - goal;
        else if (current < goal) return goal - current;
        else return 0;
    }

    // convert int to byte
    private byte intToByte(int x) {
        return Byte.parseByte(Integer.toString(x));
    }

    // returns a new array with swapped elements
    private int[][] swap(int a, int b, int x, int y) {
        int[][] copy = copy2D(this.copyArr);
        int temp = copy[a][b];
        copy[a][b] = copy[x][y];
        copy[x][y] = temp;

        return copy;
    }

    // copy2D array
    private int[][] copy2D(byte[][] arr) {
        int[][] newArr = new int[n][n];

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                newArr[row][col] = arr[row][col];
            }

        return newArr;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;

        this.copyArr = new byte[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                if (copyArr[row][col] == 0) {
//                    blankSquare[0] = row;
//                    blankSquare[1] = col;
                    rowBs = row;
                    colBs = col;
                }
                this.copyArr[row][col] = intToByte(tiles[row][col]);
            }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", this.copyArr[i][j]));
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
        int count = 0;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                if (inWrongPlace(row, col) && this.copyArr[row][col] != 0)
                    count ++;
            }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                int tile = this.copyArr[row][col];
                int goalRow;

                if (tile % n != 0) goalRow = Math.floorDiv(tile, n);
                else goalRow = Math.floorDiv(tile, n) - 1;

                int goalCol = tile - (goalRow * n)  - 1;
                if (inWrongPlace(row, col) && tile != 0) {
                    // Calculate vertical + horizontal distance
                    sum += addToSum(col, goalCol) + addToSum(row, goalRow);
                }
            }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // To save time, check if the last tile on the board isn't equal to 0
        if (this.copyArr[n-1][n-1] != 0) return false;
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.copyArr, that.copyArr);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        if (rowBs > 0) neighbors.push(new Board(swap(rowBs, colBs, rowBs - 1, colBs)));
        if (rowBs < n - 1) neighbors.push(new Board(swap(rowBs, colBs, rowBs + 1, colBs)));
        if (colBs > 0) neighbors.push(new Board(swap(rowBs, colBs, rowBs, colBs - 1)));
        if (colBs < n - 1) neighbors.push(new Board(swap(rowBs, colBs, rowBs, colBs + 1)));

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n - 1; col++) {
                if (this.copyArr[row][col] != 0 && this.copyArr[row][col + 1] != 0) {
                        return new Board(swap(row, col, row, col + 1));
                    }
                }
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

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int prevRowTile = j + 1;

                if (i == 2 && j == 2) b[i][j] = 0;
                else b[i][j] = prevRowTile + (i * 3);
            }

//        int count = 8;
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++) {
//                if (i == n - 1 && j == n - 1) b[i][j] = 0;
//                else {
//                    b[i][j] = count;
//                    count--;
//                }
//            }

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
