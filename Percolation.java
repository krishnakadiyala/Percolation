/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *
 * @author Krishna Kadiyala
 */
public class Percolation {

    private int[][] grid = null;
    private final int num;

    private WeightedQuickUnionUF uf;

    private boolean[] status;
    private boolean[] full;
    private int upneighbor;
    private int downneighbor;
    private int rightneighbor;
    private int leftneighbor;
    private int id;
    private final int source = 0;

    public Percolation(int n) // create n-by-n grid, with all sites blocked
    {
        num = n;
        if ((num < 0) || (num == 0)) {
            throw new IllegalArgumentException("n can't be null");
        }
        grid = new int[n + 1][n + 1]; //allocate memory. Arrays won't work without this statement.

        id = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = id;
                id++;
            }
        }

        uf = new WeightedQuickUnionUF(id);
        status = new boolean[id];
        full = new boolean[id];

        for (int i = 0; i <= num; i++) {
            for (int j = 0; j <= num; j++) {
                int f = grid[i][j];
                status[f] = false;
                full[f] = false;
            }
        }
        int i = 1;
        for (int j = 1; j <= n; j++) {
            int elem = grid[i][j];
            uf.union(source, elem);
        }

    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        if (row < 1 || row > num) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        } else if (col < 1 || col > num) {
            throw new IndexOutOfBoundsException("column out of bounds");
        } else {
            int element1 = grid[row][col];
            // System.out.println("Opening element: " + element1);

            if (status[element1] == false) {
                status[element1] = true; //open site id
            }

            if (status[element1]) {
                if (col < num) {
                    rightneighbor = grid[row][col + 1];
                } else {
                    rightneighbor = element1;
                }
                if (col > 1) {
                    leftneighbor = grid[row][col - 1];
                } else {
                    leftneighbor = element1;
                }
                if ((row > 1)) {
                    upneighbor = grid[row - 1][col];
                } else {
                    upneighbor = element1;
                }
                if ((row < num)) {
                    downneighbor = grid[row + 1][col];
                } else {
                    downneighbor = element1;
                }

                if (status[upneighbor]) {
                    uf.union(element1, upneighbor);
                }

                if (status[downneighbor]) {
                    uf.union(element1, downneighbor);
                }

                if (status[leftneighbor]) {
                    uf.union(element1, leftneighbor);
                }

                if (status[rightneighbor]) {
                    uf.union(element1, rightneighbor);
                }

                /*once opened, fill the current site*/
                if ((row == 1) || (full[upneighbor]) || (full[downneighbor]) || (full[rightneighbor]) || (full[leftneighbor])) {
                    full[element1] = true;
                }
                
                if( full[element1])
                {
                    if (status[upneighbor]) {
                        if ((!full[upneighbor])) {
                            open(row - 1, col);
                        } else {
                            //return;
                            full[upneighbor] = true;
                        }
                    }

                    if (status[downneighbor]) {
                        if ((!full[downneighbor])) {
                            open(row + 1, col);
                        } else {
                            full[downneighbor] = true;
                        }
                    }

                    if (status[leftneighbor]) {
                        if ((!full[leftneighbor])) {
                            open(row, col - 1);
                        } else {
                            full[leftneighbor] = true;
                            //return;
                        }
                    }

                    if (status[rightneighbor]) {
                        if ((!full[rightneighbor])) {
                            open(row, col + 1);
                        } else {
                           // return;
                            full[rightneighbor] = true;
                        }
                    }
                }
            }
            
            
        }
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        if (row < 1 || row > num) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        } else if (col < 1 || col > num) {
            throw new IndexOutOfBoundsException("column out of bounds");
        } else {
            int site = grid[row][col];
            return status[site];
        }
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        if (row < 1 || row > num) {
            throw new IndexOutOfBoundsException("Row out of bounds");
        } else if (col < 1 || col > num) {
            throw new IndexOutOfBoundsException("column out of bounds");
        } else {
            int site = grid[row][col];
            return (uf.connected(source,site) && full[site]);
        }
    }

    public int numberOfOpenSites() // number of open sites
    {
        int numopen = 0;
        for (int i = 0; i < id; i++) {
            if (status[i] == true) {
                numopen++;
            }
        }
        return numopen;

    }

    public boolean percolates() // does the system percolate?
    {
        boolean systempercolates = false;
        int i = num;

        for (int j = 1; j <= num; j++) {
            int element = grid[i][j];
            if (uf.connected(source, element) && full[element]) {
                systempercolates = true;
                break;
            }
        }
        return systempercolates;

    }
}
