import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k;
        int n;
        if (args.length != 1) {
            throw new NullPointerException("1 command-line argument required.");
        } else {
            k = Integer.parseInt(args[0]);
        }

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        n = StdIn.readAllStrings().length;
        // Read sequence of strings from standard input
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            // If k elements have been read; with a probability of k/n,
            // swap the element in question with an element already read.
            if (rq.size() == k && StdRandom.uniform(k / n) == k) {
                rq.dequeue();
                rq.enqueue(item);
            } else if (rq.size() < k) {
                rq.enqueue(item);
            }
        }

        // Print the strings 'k' times uniformly at random
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}