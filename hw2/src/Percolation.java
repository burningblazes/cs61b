import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private int N;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf_backup;
    private int numOpen;
    private boolean[][] isOpen;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N=N;
        uf = new WeightedQuickUnionUF(N*N+2);
        uf_backup = new WeightedQuickUnionUF(N*N+2); //for isFull

        for (int i = 0; i < N; i++) {
            uf.union(N*N,i); //N*N is the index of virtual top
            uf.union(N*N+1,N*N-1-i); //* N*N+1 is the index of virtual bottom
            uf_backup.union(N*N,i);
        }
        numOpen = 0;
        isOpen = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                isOpen[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        isValidIndex(row,col);
        if (! isOpen(row, col)) {
            isOpen[row][col] = true;
            numOpen++;
            int[][] neighbors={{row-1,col},{row,col-1},{row+1,col},{row,col+1}};
            for (int i = 0; i < neighbors.length; i++) {
                int nrow = neighbors[i][0];
                int ncol = neighbors[i][1];
                if (nrow<N && ncol<N && nrow>=0 && ncol>=0 &&isOpen(nrow,ncol)) {
                    uf.union(oneDIndex(nrow,ncol),oneDIndex(row,col));
                    uf_backup.union(oneDIndex(nrow,ncol),oneDIndex(row,col));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        isValidIndex(row,col);
        return isOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        isValidIndex(row,col);
        if (!isOpen(row, col)) {
            return false;
        } else {
            return uf_backup.connected(N*N,oneDIndex(row,col));
        }
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return uf.connected(N*N,N*N+1);
    }



    // helper methods

    private void isValidIndex(int row, int col) {
        if (row < 0 || row >= N || col <0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int oneDIndex(int row, int col) {
        return row*N+col;
    }

}
