import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k;
        if (args.length != 1) {
            throw new NullPointerException("1 command-line argument required.");
        } else {
            k = Integer.parseInt(args[0]);
        }

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        // Read sequence of strings from standard input
        for (int i = 0; i < k; i++) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        // Print the strings 'k' times uniformly at random
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}