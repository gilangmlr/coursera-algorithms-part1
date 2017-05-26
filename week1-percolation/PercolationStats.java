/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private int numberOfTrials;
    private double[] listOfMeans;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.numberOfTrials = trials;
        this.listOfMeans = new double[trials];

        for (int i = 0; i < trials; i++) {
            this.doTrial(n, i);
        }
    }

    private void doTrial(int n, int index) {
        Percolation percolation = new Percolation(n);
        
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(col, row);
        }

        double numberOfOpenSites = percolation.numberOfOpenSites();
        double mean = numberOfOpenSites / (n * n);

        listOfMeans[index] = mean;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.listOfMeans);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.listOfMeans);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.numberOfTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.numberOfTrials));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        String stats = ""; 
        stats += "mean                    = " + percolationStats.mean() + "\n";
        stats += "stddev                  = " + percolationStats.stddev() + "\n";
        stats += "95% confidence interval = [" + percolationStats.confidenceLo();
        stats += ", " + percolationStats.confidenceHi() + "]";

        System.out.println(stats);
    }
}