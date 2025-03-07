public class UnionFind {
    private int[] parent;
    private int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        size = N;
        parent = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (v<0 || v>=size) {
            throw new IllegalArgumentException("Wrong index: " + v);
        }
        int temp=parent(v);
        while (temp>=0) {
            temp=parent(temp);
        }
        return -temp;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v<0 || v>=size) {
            throw new IllegalArgumentException("Wrong index: " + v);
        }
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (v1<0 || v1>=size) {
            throw new IllegalArgumentException("Wrong index: " + v1);
        }
        if (v2<0 || v2>=size) {
            throw new IllegalArgumentException("Wrong index: " + v2);
        }
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v<0 || v>=size) {
            throw new IllegalArgumentException("Wrong index: " + v);
        }
        int res=v;
        while (parent(res)>=0) {
            res=parent(res);
        }
        //path compression
        int temp=v;
        int old=v;
        while (temp!=res) {
            old=temp;
            parent[old]=res;
            temp=parent(temp);
        }
        return res;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (!connected(v1, v2)) {
            int smallroot,bigroot;
            if (sizeOf(v1)<=sizeOf(v2)) {
                smallroot=find(v1);
                bigroot=find(v2);
            }else {
                smallroot=find(v2);
                bigroot=find(v1);
            }
            parent[bigroot]=parent(bigroot)+parent(smallroot);
            parent[smallroot]=bigroot;
        }
    }

}
