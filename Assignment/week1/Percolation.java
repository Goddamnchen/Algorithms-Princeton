/***************************************************************************
 *   Author: Guanting Chen
 *   Date: 05/30/2018
 *   Union-Find Assignment
 *   Percolation Version 1.1 : Double WQUUF dealing with backwater
 *   1.fixed bug of n = 1 situation
 *   2.noted worst case percolates runtime
 *
 *   Note: This version uses Top&Bottom Virtual Site
 *   Worst percolates case runtime : ～ (0.59*5)nlogn + 7n (n=n*n grid)
 *
 *   API using by PercolationVisualizer and PercolationStats
 *   Scored 100/100  but memory failed to <= 11 n^2 + 128 n + 1024 bytes
 *   Estimated 17n^2
 **************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF site;
    private int size;
    private int nOS;                                //number of Open Sites
    private boolean[] isOpenSite;
    private WeightedQuickUnionUF connectTop;        //record

    public Percolation(int n) {      // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n can not < 0");
        site = new WeightedQuickUnionUF(n * n + 2);       // implement index [0,n^2 +1]
        connectTop = new WeightedQuickUnionUF(n * n + 2);
        size = n;
        initSiteStatus(n * n + 2);
    }
    /**
     * 1.check indices
     * 2.label a open site (row, col) if it is not open already
     * 3.union surround site
     * Takes 4logn in worst case
     */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            int index = xyTo1d(row, col);
            isOpenSite[index] = true;
            unionSelection(row, col, index);       //1<=index<=size*size 2;
            nOS++;
        }
    }

    /**
     * union site according to the site position
     * dividing by Top or Bottom site conditional branch
     */
    private void unionSelection(int x, int y, int index) {
        if (this.size == 1) {                       //special case: size = 1, already percolated in initSiteStatus()
            site.union(0, 1);
            site.union(2, 1);
            connectTop.union(0, 1);
            connectTop.union(0, 2);
            return;
        }
        if (x == 1) {
            site.union(0, index);
            connectTop.union(0, index);
            if (this.isOpen(x + 1, y)) {          //下
                site.union(xyTo1d(x + 1, y), index);
                connectTop.union(xyTo1d(x + 1, y), index);
            }
            unionSelectionLR(x, y, index);
        } else if (x == size) {
            site.union(size * size + 1, index);
            if (this.isOpen(x - 1, y)) {        //上
                site.union(xyTo1d(x - 1, y), index);
                connectTop.union(xyTo1d(x - 1, y), index);
            }
            unionSelectionLR(x, y, index);
        } else {        //上下
            if (this.isOpen(x - 1, y)) {
                site.union(xyTo1d(x - 1, y), index);
                connectTop.union(xyTo1d(x - 1, y), index);
            }
            if (this.isOpen(x + 1, y)) {
                site.union(xyTo1d(x + 1, y), index);
                connectTop.union(xyTo1d(x + 1, y), index);
            }
            unionSelectionLR(x, y, index);
        }
    }
    /**
     * select Left or Right union according to the situation of Top or Bottom
     * +left; +right; +right+left
     */
    private void unionSelectionLR(int x, int y, int index) {
        if (y == 1) {
            if (this.isOpen(x, y + 1)) {
                site.union(xyTo1d(x, y + 1), index);
                connectTop.union(xyTo1d(x, y + 1), index);
            }
        } else if (y == size) {
            if (this.isOpen(x, y - 1)) {
                site.union(xyTo1d(x, y - 1), index);
                connectTop.union(xyTo1d(x, y - 1), index);
            }
        } else {
            if (this.isOpen(x, y - 1)) {
                site.union(xyTo1d(x, y - 1), index);
                connectTop.union(xyTo1d(x, y - 1), index);
            }
            if (this.isOpen(x, y + 1)) {
                site.union(xyTo1d(x, y + 1), index);
                connectTop.union(xyTo1d(x, y + 1), index);
            }
        }
    }
    public boolean isOpen(int row, int col) {        // is site (row, col) open?
        validate(row, col);
        int index = xyTo1d(row, col);
        return isOpenSite[index];
    }

    public boolean isFull(int row, int col) {        // is site (row, col) full?
        validate(row, col);
        int index = xyTo1d(row, col);
        return (isOpen(row, col) && connectTop.connected(index, 0));          //a backwash problem here
    }
    public int numberOfOpenSites() {      // number of open sites
        return nOS;
    }
    public boolean percolates() {        // does the system percolate?
        return site.connected(0, size * size + 1);
    }

    /**
     * initialize the status array of concrete and virtual sites
     * Takes n + ~
     */
    private void initSiteStatus(int n) {
        this.isOpenSite = new boolean[n];
        this.isOpenSite[0] = true;
        this.isOpenSite[n - 1] = true;
    }

    /**
     * convert 2-dimensional (row, col) to 1-dimensional index
     *  1<=x,y<=size, 1<=return<=size^2
     */
    private int xyTo1d(int x, int y) {
        return (x - 1) * size + y;
    }

    /**
     * validating the range of row & col
     * 1<=x,y<=size
     */
    private boolean validate(int x, int y) {
        if (x < 1 || x > size) throw new IllegalArgumentException("row should be [1,n]");
        else if (y < 1 || y > size) throw new IllegalArgumentException("col should be [1,n]");
        else return true;
    }

}