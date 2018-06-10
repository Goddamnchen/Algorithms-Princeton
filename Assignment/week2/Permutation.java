/***************************************************************************************
 * Author: Guanting Chen
 * Date: 06/09/2018
 *
 * Permutation:
 * 1.Takes an integer k as a command-line argument;
 * 2.Reads in a sequence of strings from standard input using StdIn.readString();
 * 3.Prints exactly k of them, uniformly at random.
 *
 * Implementation:
 * 1.Standard input: java Permutation [number] < [redirected text file of standard input]
 *  * "<" is the notation to redirect standard input to certain file
 * 2.Using RandmoizedQueue and dequeue() to print k items, uniformly at random
 *
 * Note: This program is executed by console using command
 * Scored 100/100, Passed all Memory and Timing tests
 ****************************************************************************************/


import edu.princeton.cs.algs4.StdIn;
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            System.out.println(q.dequeue());
        }
    }

}
