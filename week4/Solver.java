import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // this private class allows us to store multiple instances of info as a "key"
    private static class Search implements Comparable<Search> {
        private Search previous;
        private final Board board;
        private int moves = 0;
        private int manhattan;

        public Search(Board board, Search previous) {
            this.board = board;
            this.previous = previous;
            this.manhattan = this.board.manhattan();
            if (previous != null)
                this.moves = previous.moves + 1;
        }

        public int compareTo(Search that) {
            int thisPriority = this.manhattan + this.moves;
            int thatPriority = that.manhattan + that.moves;
            return thisPriority - thatPriority;
        }
    }

    // previous search node that was dequeued from the priority queue
    private Search prevSearchNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Constructor argument is null.");

        // insert initial search node
        MinPQ<Search> mpq = new MinPQ<>();
        mpq.insert(new Search(initial, null));

        MinPQ<Search> twinMpq = new MinPQ<>();
        twinMpq.insert(new Search(initial.twin(), null));

        // check if the board is solvable or establish a trail of nodes otherwise
        while (true) {
            prevSearchNode = aStarSearch(mpq);
            if (prevSearchNode != null || aStarSearch(twinMpq) != null) return;
        }
    }

    // A* search using a priority queue
    private Search aStarSearch(MinPQ<Search> pq) {
        if (pq.isEmpty()) return null;
        Search searchNode = pq.delMin();
        if (searchNode.board.isGoal()) return searchNode;
        for (Board x : searchNode.board.neighbors())
            // prevent the initial node and neighbors that equal the previous board
            //  from being inserted into the priority queue
            if (searchNode.previous == null || !x.equals(searchNode.previous.board))
                pq.insert(new Search(x, searchNode));

        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return prevSearchNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? prevSearchNode.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> sequence = new Stack<>();

        // create a copy of the global node as to keep the key immutable
        Search node = prevSearchNode;

        // use previous node and chase the pointers all the wway back
        //  to the initial search node
        while (node != null) {
            sequence.push(node.board);
            node = node.previous;
        }

        return sequence;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}