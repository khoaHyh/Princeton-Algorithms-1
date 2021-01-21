import java.util.Arrays;

public class Board {
    private final int n;
    private final int[][] tilesArr;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        tilesArr = Arrays.copyOf(tiles, n);
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tilesArr[i][j]));
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

        if (tilesArr[n-1][n-1] != 0) count++;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                int prevRowTile = col + 1;
                if (col == n - 1 && row == n - 1) return count;
                if (tilesArr[row][col] != prevRowTile + (row * n))
                    count ++;
            }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++) {
                int prevRowTile = col + 1;
                int tile = tilesArr[row][col];
                int correctRow = Math.floorDiv(tile, n);
                int correctCol = tile - (correctRow * n)  - 1;

                // If tile is out of place, calculate the Manhattan distances
                //  from the tiles to their goal positions
                if (tile != prevRowTile + (row * n)) {
                    // Calculate vertical distance
                    if (col > correctCol) sum += col - correctCol;
                    else if (col < correctCol) sum += correctCol - col;

                    // Calculate horizontal distance
                    if (row > correctRow) sum += row - correctRow;
                    else if (row < correctRow) sum += correctRow - row;
                }
            }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // To save time, check if the last tile on the board isn't equal to 0
        if (tilesArr[n-1][n-1] != 0) return false;
        return hamming() == 0;
    }

    // checks if an array is 2D
    private boolean isArray2D(Object[] array) {
        return array.getClass().getComponentType().isArray();
    }

//    // does this board equal y?
//    public boolean equals(Object y) {
//
//    }
//
//    // all neighboring boards
//    public Iterable<Board> neighbors()
//
//    // a board that is obtained by exchanging any pair of tiles
//    public Board twin()

    // unit testing (not graded)
    public static void main(String[] args) {
        // Test isGoal()
        int n = 3;
        int[][] a = new int[n][n];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int prevRowTile = j + 1;

                if (i == 2 && j == 2) a[i][j] = 0;
                else a[i][j] = prevRowTile + (i * 3);
            }

//        int count = 8;
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++) {
//                int prevRowTile = j + 1;
//
//                if (i == n - 1 && j == n - 1) a[i][j] = 0;
//                else {
//                    a[i][j] = count;
//                    count--;
//                }
//            }

        Board test = new Board(a);

        System.out.println(test.toString());
        System.out.println(test.isGoal());
        System.out.println("dimension: " + test.dimension());
        System.out.println("hamming: " + test.hamming());
    }
}
