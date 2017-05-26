/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[] gridOpenStatus;
    private int gridSize;
    private int numberOfOpenSites;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    private WeightedQuickUnionUF weightedQuickUnionUFTop;
    private WeightedQuickUnionUF weightedQuickUnionUFTopBottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.gridOpenStatus = new boolean[n * n];
        this.gridSize = n;
        this.numberOfOpenSites = 0;

        int numberOfGridSites = (n * n) + 2;
        this.weightedQuickUnionUFTopBottom = new WeightedQuickUnionUF(numberOfGridSites);
        this.weightedQuickUnionUFTop = new WeightedQuickUnionUF(numberOfGridSites);

        this.virtualTopIndex = numberOfGridSites - 1;
        this.virtualBottomIndex = numberOfGridSites - 2;
    }

    private int coordinateToIndex(int row, int col) {
        if (this.isCoordinateOutOfBound(row, col)) {
            return -1;
        }

        int index = ((row - 1) * this.gridSize) + (col - 1);

        return index;
    }

    private int[] indexToCoordinate(int n) {
        int row = (n / this.gridSize) + 1;
        int col = n - ((row - 1) * this.gridSize) + 1;
        int[] coordinate = {row, col};

        return coordinate;
    }

    private int[] getNeighborsIndices(int n) {
        int[] coordinate = this.indexToCoordinate(n);
        int row = coordinate[0];
        int col = coordinate[1];

        int[] neighborsIndices = new int[4];

        neighborsIndices[0] = this.coordinateToIndex(row - 1, col);
        neighborsIndices[1] = this.coordinateToIndex(row, col - 1);
        neighborsIndices[2] = this.coordinateToIndex(row, col + 1);
        neighborsIndices[3] = this.coordinateToIndex(row + 1, col);

        return neighborsIndices;
    }

    private boolean isCoordinateOutOfBound(int row, int col) {
        boolean isRowSafe = (0 < row && row < this.gridSize + 1);
        boolean isColSafe = (0 < col && col < this.gridSize + 1); 
        
        if (!isRowSafe || !isColSafe) {
            return true;
        }

        return false;
    }

    private boolean isIndexOutOfBound(int index) {
        boolean isIndexSafe = index >= 0 && index < this.gridOpenStatus.length;
       
        if (!isIndexSafe) {
            return true;
        }

        return false;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.isCoordinateOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (row == 1) {
            this.weightedQuickUnionUFTopBottom.union(this.coordinateToIndex(row, col), this.virtualTopIndex);
            this.weightedQuickUnionUFTop.union(this.coordinateToIndex(row, col), this.virtualTopIndex);
        }

        if (row == this.gridSize) {
            this.weightedQuickUnionUFTopBottom.union(this.coordinateToIndex(row, col), this.virtualBottomIndex);
        }

        if (!this.isOpen(row, col)) {
            this.numberOfOpenSites++;
        }
        int index = this.coordinateToIndex(row, col);
        this.gridOpenStatus[index] = true;

        int[] neighborsIndices = this.getNeighborsIndices(index);

        for (int i = 0; i < neighborsIndices.length; i++) {
            int neighbourIndex = neighborsIndices[i];
            if (!this.isIndexOutOfBound(neighbourIndex) && this.gridOpenStatus[neighbourIndex]) {
                this.weightedQuickUnionUFTopBottom.union(index, neighbourIndex);
                this.weightedQuickUnionUFTop.union(index, neighbourIndex);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (this.isCoordinateOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int index = this.coordinateToIndex(row, col);

        return this.gridOpenStatus[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (this.isCoordinateOutOfBound(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int index = this.coordinateToIndex(row, col);

        return this.weightedQuickUnionUFTop.connected(index, this.virtualTopIndex);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.weightedQuickUnionUFTopBottom.connected(this.virtualBottomIndex, this.virtualTopIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
