/**
 * disjoint set creates unique sets of
 * nodes within a graph
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 */
public class DisjointSet {
    private int[] weights;
    private int[] nodeArray;

    /**
     * constructor for a disjoint set
     * 
     * @param size : number of nodes in entire set
     */
    DisjointSet(int size) {
        //initialize node and weight arrays
        nodeArray = new int[size];
        weights = new int[size];
        //fill in respective arrays
        for (int i = 0; i < size; i++) {
            nodeArray[i] = -1; // each node is its own root to start
            weights[i] = 1; // each node is weighted with one to start
        }
    }

    /**
     * getter for the node array in disjoint set
     * 
     * @return array of integers containing parents
     */
    public int[] nodeArray() {
        return nodeArray;
    }

    /**
     * merges two nodes if they are different
     * @param a : value of first node
     * @param b : value of second node
     */
    public void union(int a, int b) {
        int root1 = find(a); // find root of node a
        int root2 = find(b); // find root of node b
        if (root1 != root2) { // merge with weighted union
            if (weights[root2] > weights[root1]) {
                nodeArray[root1] = root2;
                weights[root2] += weights[root1];
            } 
            else {
                nodeArray[root2] = root1;
                weights[root1] += weights[root2];
            }
        }
    }

    /**
     * finds root of a node with path compression
     * @param curr : current node
     * @return integer that is the root
     */
    public int find(int curr) {
        if (nodeArray[curr] == -1) {
            return curr; // at root
        }
        nodeArray[curr] = find(nodeArray[curr]);
        return nodeArray[curr];
    }
}
