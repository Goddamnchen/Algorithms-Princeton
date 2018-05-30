/*****************************************************************
 *   Author: Guanting Chen
 *   Date: 05/29/2018
 *   Union-Find Assignment
 *   Percolation Version 2.0 : Dealing backwater with status Array
 *   Note: this version do not use Top&Bottom Virtual Site
 *   API using by PercolationVisualizer
 *****************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationNew {
    private WeightedQuickUnionUF site;
    private int size;
    private int nOS;        //number of Open Sites
    private boolean[] isOpenSite;
    private boolean[][] isConnectTB;        //通过union roots，记录每个union是否connect to top or bottom
    public PercolationNew(int n) {      // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n can not < 0");
        this.site = new WeightedQuickUnionUF(n * n + 1);       // implement index [0,n^2], with only Top Virtual site
        this.size = n;
        this.initSiteStatus(n * n + 1);
    }
    /**
     * 1.check indices
     * 2.label a open site (row, col) if it is not open already
     * 3.Get the existing component identifier status of 2 open union sets
     *   and then merge to a integral union and update the  status of this new union
     */
    public void open(int row, int col){
        validate(row, col);
        if (!isOpen(row, col)){
            unionSelection(row, col);       //1<=index<=size*size 2;
            isOpenSite[xyTo1d(row,col)] = true;
            nOS ++;
        }
    }

    /**
     * Union site according to whether exists open neighbour
     * Conditional branch differentiate: Top or Bottom line
     * Dependency Principle: 每个union中的所有元素的Connect status 由该union 的ROOT STATUS代表
     */
    private void unionSelection(int x, int y) {
        int index = xyTo1d(x,y);
        if (x == 1) {                                   //on the top line
            CheckandUnion(0, index);           /*top line element --initialize union--> top virtual element
                                                        isConnectTB[top_line_index][0] --> isConnectTB[top_virtual_index][0] = true; */
            if (this.isOpen(x + 1, y)) {            //+down
                CheckandUnion(xyTo1d(x + 1,y), index);
            }
            unionSelectionLR(x,y,index);
        } else if (x == size) {                         //on the bottom line
            isConnectTB[index][1] = true;               //bottom line element --initialize status--> isConnectTB[bottom_line_index][1]

            if (this.isOpen(x - 1, y)) {           //+up
                CheckandUnion(xyTo1d(x - 1,y), index);
            }
            unionSelectionLR(x,y,index);
        } else {                                       //+up+down
            if (this.isOpen(x - 1, y)) {
                CheckandUnion(xyTo1d(x - 1,y), index);
            }
            if (this.isOpen(x + 1, y)) {
                CheckandUnion(xyTo1d(x + 1,y), index);
            }
            unionSelectionLR(x,y,index);
        }
    }
    /**
     * select Left or Right union according to the situation of Top or Bottom
     * +left; +right; +right+left
     */
    private void unionSelectionLR(int x, int y, int cur_index){
        if (y == 1) {           //+right
            if (this.isOpen(x, y + 1)) {
                CheckandUnion(xyTo1d(x, y + 1), cur_index);
            }
        } else if (y == size) {         //+left
            if (this.isOpen(x, y - 1)) {
                CheckandUnion(xyTo1d(x, y - 1), cur_index);
            }
        } else {            //+left+right
            if (this.isOpen(x, y - 1)) {
                CheckandUnion(xyTo1d(x, y - 1), cur_index);
            }
            if (this.isOpen(x, y + 1)) {
                CheckandUnion(xyTo1d(x, y + 1), cur_index);
            }
        }
    }

    /**
     * check the CONNECT STATUS of Component Identifier of two input site
     * @param neighbour representing the surround-site(Set) waited to be union
     * @param cur representing the center-site(Set) going to union other
     * !!The ROOT's CONNECT STATUS represent the same all status of a whole Set
     */
    private void CheckandUnion(int neighbour, int cur){
        boolean top;
        boolean bottom;
        if (isConnectTB[site.find(neighbour)][0] || isConnectTB[site.find(cur)][0]) top = true;
        else top = false;
        if ((isConnectTB[site.find(neighbour)][1] || isConnectTB[site.find(cur)][1])) bottom = true;
        else bottom = false;

        site.union(neighbour, cur);         //union and then set the CONNECT STATUS of new set
        isConnectTB[site.find(cur)][0] = top;
        isConnectTB[site.find(cur)][1] = bottom;
    }
    public boolean isOpen(int row, int col) {        // is site (row, col) open?
        validate(row, col);
        int index = xyTo1d(row, col);
        return isOpenSite[index];
    }

    /**
     * Judging whether a site is a Full open site
     * @return by checking the CONNECT STATUS of a whole set
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
     * @return by checking the CONNECT STATUS of the root of a set containing Top Virtual Site;
     * which represents a set must have bottom connected to the top
     */
    public boolean percolates() {        // does the system percolate?
        return (isConnectTB[site.find(0)][0] && isConnectTB[site.find(0)][1]);
    }

    /**
     * initialize the status array of concrete sites and Top virtual sites
     */
    private void initSiteStatus(int n) {
        this.isOpenSite = new boolean[n];
        this.isOpenSite[0] = true;
        this.isConnectTB = new boolean[n][2];
        this.isConnectTB[0][0] = true;          //top virtual site is connected to top
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
    private boolean validate(int x, int y){
        if (x < 1 || x > size) throw new IllegalArgumentException("row should be [1,n]");
        else if (y < 1 || y > size) throw new IllegalArgumentException("col should be [1,n]");
        else return true;
    }

}