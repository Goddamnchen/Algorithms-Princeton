/***********************************************************************
 *   Author: Guanting Chen
 *   Date: 05/30/2018
 *   Union-Find Assignment
 *   Percolation Version 2.1 :
 *   1.fixed bug of n = 1 situation
 *   2.refactoring percolates() and open() to not use virtual site
 *   3.open() cost a little bit time more than Version2.0
 *
 *   Note: this version DO NOT use Top&Bottom Virtual Site
 *   Worst percolates case runtime = ～ (0.59*20)nlogn + 6n (n=n*n grid)
 *   API using by PercolationVisualizer and PercolationStats
 ***********************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNew {
    private WeightedQuickUnionUF site;
    private int size;
    private int nOS;                        //number of Open Sites
    private boolean[] isOpenSite;
    private boolean[][] isConnectTB;        //通过union roots，记录每个union是否connect to top or bottom
    private boolean percolatedflag;         //using percolated to track status of Top and Bottom

    public PercolationNew(int n) {          // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n can not < 0");
        this.site = new WeightedQuickUnionUF(n * n + 1);       // implement index [0,n^2], index 0 is not used
        this.size = n;
        this.initSiteStatus(n * n + 1);
    }
    /**
     * 1.check indices
     * 2.label a open site (row, col) if it is not open already
     * 3.Get the existing component identifier status of 2 open union sets
     *   and then merge to a integra-l union and update the  status of this new union
     * Takes 20logn time in worst case
     */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            unionSelection(row, col);       //1<=index<=size*size 2;
            isOpenSite[xyTo1d(row, col)] = true;
            nOS++;
        }
    }

    /**
     * Union site according to whether exists open neighbour
     * Conditional branch differentiate: Top or Bottom line
     * Dependency Principle: 每个union中的所有元素的Connect status 由该union 的ROOT STATUS代表
     */
    private void unionSelection(int x, int y) {
        int index = xyTo1d(x, y);
        if (this.size == 1) {                       //special case, size = 1;
            isConnectTB[index][0] = true;
            isConnectTB[index][1] = true;
            this.percolatedflag = true;
            return;
        }
        if (x == 1) {
            this.isConnectTB[index][0] = true;          /*initialize top line element to connect to top */

            if (this.isOpen(x + 1, y)) {            //+down
                checkUnion(xyTo1d(x + 1, y), index);
            }
            unionSelectionLR(x, y, index);
        } else if (x == size) {
            this.isConnectTB[index][1] = true;          /*initialize bottom line element to connect to bottom */

            if (this.isOpen(x - 1, y)) {           //+up
                checkUnion(xyTo1d(x - 1, y), index);
            }
            unionSelectionLR(x, y, index);
        } else {                                       //+up+down
            if (this.isOpen(x - 1, y)) {
                checkUnion(xyTo1d(x - 1, y), index);
            }
            if (this.isOpen(x + 1, y)) {
                checkUnion(xyTo1d(x + 1, y), index);
            }
            unionSelectionLR(x, y, index);
        }
    }
    /**
     * select Left or Right union according to the situation of Top or Bottom
     * +left; +right; +right+left
     */
    private void unionSelectionLR(int x, int y, int curIndex) {
        if (y == 1) {                                  //+right
            if (this.isOpen(x, y + 1)) {
                checkUnion(xyTo1d(x, y + 1), curIndex);
            }
        } else if (y == size) {                        //+left
            if (this.isOpen(x, y - 1)) {
                checkUnion(xyTo1d(x, y - 1), curIndex);
            }
        } else {                                       //+left+right
            if (this.isOpen(x, y - 1)) {
                checkUnion(xyTo1d(x, y - 1), curIndex);
            }
            if (this.isOpen(x, y + 1)) {
                checkUnion(xyTo1d(x, y + 1), curIndex);
            }
        }
    }

    /**
     * check the CONNECT STATUS of Component Identifier of two input site
     * @param neighbour representing the surround-site(Set) waited to be union
     * @param cur representing the center-site(Set) going to union other
     * !!The ROOT's CONNECT STATUS represent the same all status of a whole Set
     * Takes 5logn time in worst case
     */
    private void checkUnion(int neighbour, int cur) {
        boolean top;
        boolean bottom;
        int neighbourRoot = site.find(neighbour);
        int curRoot = site.find(cur);
        if (isConnectTB[neighbourRoot][0] || isConnectTB[curRoot][0]) top = true;
        else top = false;
        if ((isConnectTB[neighbourRoot][1] || isConnectTB[curRoot][1])) bottom = true;
        else bottom = false;

        site.union(neighbour, cur);         //union and then set the CONNECT STATUS of new set
        isConnectTB[site.find(cur)][0] = top;
        isConnectTB[site.find(cur)][1] = bottom;
        if (top && bottom) {                // judging whether is percolated
            this.percolatedflag = true;
        }
    }
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = xyTo1d(row, col);
        return isOpenSite[index];
    }

    /**
     * Judging whether a site is a Full open site
     * @return by checking the CONNECT STATUS of a whole set
     * Takes logn time in worst case because of .find()
     */
    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = xyTo1d(row, col);
        return (isOpen(row, col) && isConnectTB[site.find(index)][0]);
    }
    public int numberOfOpenSites() {      // number of open sites
        return nOS;
    }

    /**
     * Judging whether is percolates
     * Get rid of costly find() of top virtual site to check CONNECT STATUS
     * @return percolatedflag which tracks CONNECT STATUS during Open()
     * Takes constant time
     */
    public boolean percolates() {
        return this.percolatedflag;
    }

    /**
     * initialize the status array of concrete sites and Top virtual sites
     */
    private void initSiteStatus(int n) {
        this.isOpenSite = new boolean[n];
        this.isConnectTB = new boolean[n][2];
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
        if (x < 1 || x > this.size) throw new IllegalArgumentException("row should be [1,n]");
        else if (y < 1 || y > this.size) throw new IllegalArgumentException("col should be [1,n]");
        else return true;
    }

}
