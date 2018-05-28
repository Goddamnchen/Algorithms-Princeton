import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.Exception;

public class Percolation {
    private WeightedQuickUnionUF site;
    private int size;
    private int nOS;        //number of Open Sites
    private boolean[] siteStatus;

    public Percolation(int n) {      // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n can not < 0");
        site = new WeightedQuickUnionUF(n^2 + 2);       // implement index [0,n^2 +1]
        size = n;
        siteStatus = initSiteStatus(n^2 + 2);
    }
    /**
     * label a open site (row, col) if it is not open already
     * union site?
     */
    public void open(int row, int col){
        validate(row, col);
        if (!isOpen(row, col)){
            int index = xyTo1d(row, col);
            siteStatus[index] = true;
            nOS ++;
        }
    }
    public boolean isOpen(int row, int col){        // is site (row, col) open?
        validate(row, col);
        int index = xyTo1d(row, col);
        return siteStatus[index];
    }
    public boolean isFull(int row, int col){        // is site (row, col) full?
        validate(row, col);
        int index = xyTo1d(row, col);
        if (site.connected(index, 0) || site.connected(index, site.count() - 1)) return true;
        else return false;
    }
    public int numberOfOpenSites(){      // number of open sites
        return nOS;
    }
    public boolean percolates(){        // does the system percolate?
        if (site.find(0) == site.find(site.count()-1)) return true;
        else return false;
    }

    /**
     * initialize the status array of concrete and virtual sites
     */
    private boolean[] initSiteStatus(int n){
        boolean[] a = new boolean[n];
        a[0] = true;
        a[a.length - 1] = true;
        return a;
    }

    /**
     * convert 2-dimensional (row, col) to 1-dimensional index
     */
    private int xyTo1d(int x, int y){
        return (x - 1) * size + y;
    }

    /**
     * validating the range of row & col
     */
    private void validate(int x, int y){
        if (x < 1 || x > size) throw new IllegalArgumentException("row should be [1,n]");
        else if (y < 1 || y > size) throw new IllegalArgumentException("col should be [1,n]");
    }

}