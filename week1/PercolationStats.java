import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static int totalComputations;
    private static double[] pThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Error: n or trials <=0");

        totalComputations = trials;
        pThresholds = new double[trials];

        // Repeat computation experiment by T(trials) times
        for (int i = 0; i < trials; i++) {
            // Initialize n-by-n whole grid to be blocked
            Percolation percolationObj = new Percolation(n);

            // Open sites uniformly at random until system percolates
            while (!percolationObj.percolates()) {
                // Generate 2 integers at random
                int uniformRand1 = StdRandom.uniform(1, n + 1);
                int uniformRand2 = StdRandom.uniform(1, n + 1);

                // Open site uniformly at random if it is blocked
                if (!percolationObj.isOpen(uniformRand1, uniformRand2)) {
                    percolationObj.open(uniformRand1, uniformRand2);
                }
            }
            // Estimate of percolation threshold for this iteration of the computation
            pThresholds[i] = (double) percolationObj.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (totalComputations == 1) {
            return Double.NaN;
        }
        else {
            return StdStats.stddev(pThresholds);
        }
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(totalComputations));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(totalComputations));
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

        PercolationStats psObj = new PercolationStats(n, T);

        System.out.printf("%s = %f\n", "mean", psObj.mean());
        System.out.printf("%s = %f\n", "stddev()", psObj.stddev());
        System.out.printf("%s = [%f, %f]\n", "95% confidence interval", psObj.confidenceLo(), psObj.confidenceHi());
    }
}
