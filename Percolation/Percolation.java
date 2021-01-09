import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final int bottom;
    private int numberofOpenSites;
    private final boolean[][] opened; // stores opended
    private final WeightedQuickUnionUF qf;
    private final int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        size = n;
        bottom = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size + 1][size + 1];
        numberofOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index;
        checkValidity(row, col);
        index = getPosition(row, col);
        opened[row][col] = true;
        numberofOpenSites++;
        if (row == 1) {
            qf.union(index, TOP);
        }
        if (row == size) {
            qf.union(index, bottom);
        }
        if (row > 1 && isOpen(row - 1, col))
            qf.union(index, getPosition(row - 1, col));
        if (row < size && isOpen(row + 1, col))
            qf.union(index, getPosition(row + 1, col));
        if (col < size && isOpen(row, col + 1))
            qf.union(index, getPosition(row, col + 1));
        if (col > 1 && isOpen(row, col - 1))
            qf.union(index, getPosition(row, col - 1));

    }

    // return index
    private int getPosition(int row, int col) {
        return size * (row - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkValidity(row, col);
        return opened[row][col];
    }

    // check Valid row col index
    private void checkValidity(int row, int col) {
        if ((row <= 0 || row > size) || (col <= 0 || col > size)) {
            throw new IllegalArgumentException();
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
            return (qf.find(TOP) == qf.find(getPosition(row, col)));
        }
        else throw new IllegalArgumentException();
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberofOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(TOP) == qf.find(bottom);
    }

    /* test client (optional)
    public static void main(String[] args) {

    }*/
}
