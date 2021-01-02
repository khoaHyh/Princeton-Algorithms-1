import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static int n;
    private static int T;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int N, int trials) {
        if (N <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        n = N;
        T = trials;
    }

    // sample mean of percolation threshold
    public double mean() {

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1) {
            return Double.NaN;
        }
        else {

        }
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

    }

    // test client (see below)
    public static void main(String[] args) {
        int n;
        int T;
        if (args.length != 2) {
            throw new NullPointerException("2 command-line arguments are required.");
        } else {
            n = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }

        PercolationStats test = new PercolationStats(n, T);
        Percolation percolationObj = new Percolation(n);


    }
}
