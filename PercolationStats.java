
import edu.princeton.cs.algs4.StdRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Krishna Kadiyala
 */
public class PercolationStats {

    private double[] numberofopensites;
    private double num = 0.0;
    private double dev;
    private int tr;
    private int gridsize;
    private Percolation p;

    public PercolationStats(int n, int trials) // perform trials independent experiments on an n-by-n grid
    {
        if ((n < 0) || (n == 0)) {
            throw new IllegalArgumentException(" Grid size n can't be null");
        }
        if ((trials < 0) || (trials == 0)) {
            throw new IllegalArgumentException(" Number of trials can't be null");
        }
        for (int t = 1; t <= trials; t++) {
            System.out.println("trial number: " + t);

            gridsize = n;
            int num1, num2;

            p = new Percolation(gridsize);
            tr = trials;
            boolean percolate = false;
            numberofopensites = new double[trials + 1];

            while (!percolate) {
                num1 = StdRandom.uniform(1, gridsize + 1);
                num2 = StdRandom.uniform(1, gridsize + 1);
                p.open(num1, num2);
                percolate = p.percolates();
            }

            double d = ((double) p.numberOfOpenSites() / (gridsize * gridsize));
            numberofopensites[t] = d;

        }
    }

    public double mean() // sample mean of percolation threshold
    {
        for (int i = 1; i <= tr; i++) {
            num = num + numberofopensites[i];
        }
        //System.out.println(num + "," + tr);
        return (num / tr);
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        for (int i = 1; i <= tr; i++) {
            dev = dev + ((numberofopensites[i] - num) * (numberofopensites[i] - num));
        }
        return dev / ((double) (tr * tr) - 1);
    }

    public double confidenceLo() // low  endpoint of 95% confidence interval
    {
        double me = mean();
        double s = stddev();

        return (me - (1.96 * Math.sqrt(s)) / Math.sqrt(tr));

    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        double me = mean();
        double s = stddev();

        return (me + (1.96 * Math.sqrt(s)) / Math.sqrt(tr));
    }

    public static void main(String[] args) // test client (described below)
    {
        int number = 0;
        int numoftrials = 0;
        // number = StdIn.readInt();
        if (args.length != 0) {
            number = Integer.parseInt(args[0]);
            numoftrials = Integer.parseInt(args[1]);
        }

        if ((number < 0) || (number == 0)) {
            throw new IllegalArgumentException(" Grid size n can't be null");
        }
        if ((numoftrials < 0) || (numoftrials == 0)) {
            throw new IllegalArgumentException(" Number of trials can't be null");
        } else {
            PercolationStats p = new PercolationStats(number, numoftrials);
        }
    }
}
