/*****************************************************************************************************
 *   Author: Guanting Chen
 *   Date: 05/30/2018
 *   Union-Find Assignment
 *   Percolation Version 2.3 :
 *   1.alter A 2-dimensional status array to TWO SINGLE status array
 *     therefore HALVING the running time!!
 *   2.refactoring percolates() and open()
 *   3.add a instance variable neighboursStatus[][] to RETRIEVE Connect Status of neighbor sites
 *     (this variable needs to be initialize to DEFAULT when each open() being called,
 *      It should be initialized before starting union due to former retrieved status inheritance)
 *   4.refactoring unionConnect not to update every redundant time with each neighbor union operation
 *   5.have similar runtime as version 1.*
 *
 *   Note: this version DO NOT use Top&Bottom Virtual Site
 *   Question: How much time does initialize a 2-dimensional[n][2] cost?
 *   Answer: n * 2
 *   API using by PercolationVisualizer and PercolationStats
 *****************************************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNew {
    private WeightedQuickUnionUF site;
    private int size;
    private int nOS;                        //number of Open Sites
    private boolean[] isOpenSite;
    private boolean[] isConnectTop;        //通过union roots，记录每个union是否connect to top or bottom
    private boolean[] isConnectBottom;
    private boolean percolatedflag;         //using percolated to track status of Top and Bottom
    private boolean[][] neighboursStatus;          //temporarily retrieve ROOT CONNECT of 4 surrounded unions

    public PercolationNew(int n) {          // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n can not < 0");
        this.site = new WeightedQuickUnionUF(n * n + 1);       // implement index [0,n^2], index 0 is not used
        this.size = n;
        this.initSiteStatus(n * n + 1);
    }
    /**
     * 1.check indices
     * 2.label a open site (row, col) if it is not open already
     * 3.Get the existing component identifier status of 4 adjacent open union sets PLUS current newly opened site
     *   and then merge to a integral union and update the  status of this new union
     * Takes 4union() + 5find(); 9logn in worst case
     */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            this.neighboursStatus = new boolean[4][2];
            isOpenSite[xyTo1d(row, col)] = true;
            nOS++;
            selectUnion(row, col);       //1<=index<=size*size 2;

        }
    }
    /**
     * Union site according to whether exists open neighbour
     * Conditional branch differentiate: Top or Bottom line
     * Dependency Principle: 每个union中的所有元素的Connect status 由该union 的ROOT STATUS代表
     */
    private void selectUnion(int x, int y) {
        int curIndex = xyTo1d(x, y);

        if (this.size == 1) {                            //special case, size = 1;
            isConnectTop[curIndex] = true;
            isConnectBottom[curIndex] = true;
            this.percolatedflag = true;
            return;
        }
        if (x == 1) {
            this.isConnectTop[curIndex] = true;          /*initialize top line newly opened element to connect to top */

            if (this.isOpen(x + 1, y)) {            //+down
                unionConnect(xyTo1d(x + 1, y), curIndex, 1);
            }
            selectUnionLR(x, y, curIndex);
        } else if (x == size) {
            this.isConnectBottom[curIndex] = true;          /*initialize bottom line newly opened element to connect to bottom */

            if (this.isOpen(x - 1, y)) {           //+up
                unionConnect(xyTo1d(x - 1, y), curIndex, 0);
            }
            selectUnionLR(x, y, curIndex);
        } else {                                       //+up+down
            if (this.isOpen(x - 1, y)) {
                unionConnect(xyTo1d(x - 1, y), curIndex, 0);
            }
            if (this.isOpen(x + 1, y)) {
                unionConnect(xyTo1d(x + 1, y), curIndex, 1);
            }
            selectUnionLR(x, y, curIndex);
        }
        /* RETRIEVE and COMBInE surrounded and current CONNECT STATUS
           then UPDATE the integral union's CONNECT STATUS */
        int curRoot = site.find(curIndex);          //component identifier after union
        boolean integralTopStatus = this.neighboursStatus[0][0] || this.neighboursStatus[1][0] || this.neighboursStatus[2][0] || this.neighboursStatus[3][0] || isConnectTop[curIndex];
        boolean integralBottomStatus = this.neighboursStatus[0][1] || this.neighboursStatus[1][1] || this.neighboursStatus[2][1] || this.neighboursStatus[3][1] || isConnectBottom[curIndex];
        isConnectTop[curRoot] = integralTopStatus;
        isConnectBottom[curRoot] = integralBottomStatus;
        if (integralTopStatus && integralBottomStatus) {                // judging whether is percolated
            this.percolatedflag = true;
        }

    }
    /**
     * select Left or Right union according to the situation of Top or Bottom
     * +left; +right; +right+left
     */
    private void selectUnionLR(int x, int y, int curIndex) {
        if (y == 1) {                                  //+right
            if (this.isOpen(x, y + 1)) {
                unionConnect(xyTo1d(x, y + 1), curIndex, 3);
            }
        } else if (y == size) {                        //+left
            if (this.isOpen(x, y - 1)) {
                unionConnect(xyTo1d(x, y - 1), curIndex, 2);
            }
        } else {                                       //+left+right
            if (this.isOpen(x, y - 1)) {
                unionConnect(xyTo1d(x, y - 1), curIndex, 2);
            }
            if (this.isOpen(x, y + 1)) {
                unionConnect(xyTo1d(x, y + 1), curIndex, 3);
            }
        }
    }

    /**
     * Union the each(of 4) neighbour with current site(Union)
     * and temporarily Store the CONNECT STATUS of Component Identifier of each
     * @param neighbour representing the surround-site(Union) waited to be union
     * @param cur representing the center-site(Union) going to union others
     * @param neighbourIndex representing up,down,left,right for neighboursStatus[][] to store 4 CONNECT STATUS
     * !!The ROOT's CONNECT STATUS represent the same all status of a whole Set
     * Takes 1 union + 1 find = 2logn each time
     */
    private void unionConnect(int neighbour, int cur, int neighbourIndex) {
        boolean top;
        boolean bottom;
        int neighbourRoot = site.find(neighbour);
        if (isConnectTop[neighbourRoot]) top = true;
        else top = false;
        if (isConnectBottom[neighbourRoot]) bottom = true;
        else bottom = false;

        site.union(neighbour, cur);         //union and then set the CONNECT STATUS of new set
        this.neighboursStatus[neighbourIndex][0] = top;
        this.neighboursStatus[neighbourIndex][1] = bottom;

    }

    public boolean isOpen ( int row, int col){
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
        return (isOpen(row, col) && isConnectTop[site.find(index)]);
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
        this.isConnectTop = new boolean[n];
        this.isConnectBottom = new boolean[n];
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