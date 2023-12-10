/**
 * Graph is a nonlinear structure made 
 * up out of nodes connected by edges,
 * these are represented by an array of
 * adjacency lists
 * 
 * @author Ali Haidari (alih)
 * @version 12.5.2023
 * 
 */
public class Graph {
    private DLList<Integer>[] vertexArray;
    private DLList<Integer> freeVertices;
    private int vertexCount;
    /**
     * constructor for a Graph
     * 
     * @param initSize : initial capacity of vertices
     */
    @SuppressWarnings("unchecked") //allow for generic array allocation
    public Graph(int initSize) {
        if (initSize <= 0) {
            System.out.println("Error, cannot initialize graph.");
            throw new IllegalArgumentException();
        }
        //initialize vertex array to hold size DLLs
        vertexArray = (DLList<Integer>[]) new DLList[initSize];
        //create a list of free vertices
        freeVertices = new DLList<Integer>();

        for (int i = 0; i < vertexArray.length; i++) {
            //initialize free vertices of vertex array
            freeVertices.append(i);
        }
        vertexCount = 0;
    }

    /**
     * getter for the vertex array that
     * contains each adjacency list
     * 
     * @return array of DLList integers
     */
    public DLList<Integer>[] getVertexArray() {
        return vertexArray;
    }

    /**
     * getter for an adjacency list at a certain
     * vertex point
     * 
     * @param inVertex : vertex to get
     * @return DLList containing list of edges to vertex
     */
    public DLList<Integer> getAdjList(int inVertex) {
        return vertexArray[inVertex];
    }

    /**
     * getter for the DLList that keeps track
     * of the free vertices within vertex array
     * 
     * @return DLList containing indices of free slots
     */
    public DLList<Integer> getFreeVertices() {
        return freeVertices;
    }

    /**
     * getter for the number of vertices used
     * 
     * @return integer representing vertex count
     */
    public int getVertexCount() {
        return vertexCount;
    }



    /**
     * getter for the next free index within vertex array
     * 
     * @precondition curr is always at zero position
     * @return integer representing next free vertex index
     */
    public int nextFreeVertex() {
        return freeVertices.element();
    }

    /**
     * adds an edge between vertices 
     * 
     * @param firstVertex : index of first
     * @param secondVertex : index of second
     */
    public void addEdge(int firstVertex, int secondVertex) {
        //check if adj list is empty for first vertex
        if (vertexArray[firstVertex] == null) {
            vertexArray[firstVertex] = new DLList<Integer>();
            vertexCount++;
        }
        //check if adj list is empty for second vertex
        if (vertexArray[secondVertex] == null) {
            vertexArray[secondVertex] = new DLList<Integer>();
            vertexCount++;
        }
        vertexArray[firstVertex].append(secondVertex);
        vertexArray[secondVertex].append(firstVertex);
    }

    /**
     * checks for an edge between two vertices
     * 
     * @param firstVertex : index of first
     * @param secondVertex : index of second
     * @return true if edge exists, false otherwise
     */
    public boolean hasEdge(int firstVertex, int secondVertex) {       
        boolean output = false;
        //if either list is null then there is no edge
        if (vertexArray[firstVertex] == null ||
            vertexArray[secondVertex] == null) {
            return output;
        }

        //vertices that are contained by both lists have edge
        if (vertexArray[firstVertex].has(secondVertex) &&
            vertexArray[secondVertex].has(firstVertex)) {
            output = true;
        }                                                                                                         
        return output;
    }

    /**
     * removes an edge between two vertices
     * 
     * @param firstVertex : index of first
     * @param secondVertex : index of second
     */
    public void removeEdge(int firstVertex, int secondVertex) {
        //if there is an edge we remove it
        if (hasEdge(firstVertex, secondVertex)) {
            vertexArray[firstVertex].delete(secondVertex);
            vertexArray[secondVertex].delete(firstVertex);
        }
    }

    /**
     * updates a vertex index to be free
     * and returns index to free vertices list
     * 
     * @param inVertex : index to free up
     */
    public void freeVertex(int inVertex) {
        vertexArray[inVertex] = null;
        freeVertices.insert(inVertex);
        vertexCount--;
    }

    /**
     * expands the vertex array by doubling size
     */
    @SuppressWarnings("unchecked") //allow for generic array allocation
    public void expand() {       
        //double the size of the previous vertex array
        int expandSize = vertexArray.length * 2;
        //initialize expanded vertex array
        DLList<Integer>[] expandedArray = 
            (DLList<Integer>[]) new DLList[expandSize];
        //retain values from previous vertex array
        for (int i = 0; i < vertexArray.length; i++) {
            expandedArray[i] = vertexArray[i];
        }
        //update the available free vertices
        for (int j = vertexArray.length; j < expandedArray.length; j++) {
            freeVertices.append(j);
        }
        //set new vertex array
        vertexArray = expandedArray;
    }

    /**
     * finds the parents of all the utilized vertices
     * by finding and unioning a disjoint set 
     * 
     * @return KVPair holding vertices as key and parents as value
     */
    public KVPair<int[], int[]> findParents() {
        //initialize the components disjoint set
        DisjointSet components = new DisjointSet(getVertexCount());
        //initialize the map of vertices for components
        int [] vertexMap = new int[getVertexCount()];
        //initialize index of vertex map
        int k = 0;

        //iterate through each vertex 
        for (int i = 0; i < getVertexArray().length; i++) {
            //when a vertex exists we must get pairs
            if (getAdjList(i) != null) {
                //iterate through each pair within adj list
                for (int j = 0; j < getAdjList(i).size(); j++) {
                    //reposition the list 
                    getAdjList(i).repos(j);
                    //union the elements for connected components
                    components.union(i,  getAdjList(i).element());
                }
                //repos adj list to initial after iteration
                getAdjList(i).repos(0);
                //add the vertex to the map
                vertexMap[k] = i;
                //increment vertex map index
                k++;
            }
        }
        //use the vertex map to make the parents of self 
        for (int i = 0; i < components.nodeArray().length; i++) {
            if (components.nodeArray()[i] == -1) {
                components.nodeArray()[i] = vertexMap[i];
            }
        }
        //return the components array parent table
        return new KVPair<int[], int[]>(vertexMap, components.nodeArray());
    }


    /**
     * finds the frequencies of each parent within parent table
     * 
     * @param parentTable : the table of parents and vertices
     * @return HashTable holding the parents as key, and freqs as value
     * @throws Exception
     */
    public HashTable<String, Integer> findFrequencies(
        KVPair<int[], int[]> parentTable) throws Exception {

        //FIXME
//        System.out.println("parent table:");
//        for (int i = 0; i < parentTable.key().length; i++) {
//            int v = parentTable.key()[i];
//            int p = parentTable.value()[i];
//            System.out.println("{" + v + ", " + p + "}");
//        }

        //initialize a hash map that it used for freqs
        HashTable<String, Integer> freqHash =
            new HashTable<String, Integer>(parentTable.value().length);

        //fill in frequency hash map according to parents
        for (int i : parentTable.value()) {
            Integer freq = freqHash.search(String.valueOf(i));
            //if the value is already in hash map
            if (freq != null) {
                //remove the value
                freqHash.remove(String.valueOf(i));
                //increment the frequency
                freqHash.insert(String.valueOf(i), freq + 1);
            }
            //otherwise we insert it 
            else {
                freqHash.insert(String.valueOf(i), 1);
            }
            freqHash.insert(String.valueOf(i), null);
        }
        //FIXME
//        System.out.println("frequency table");
//        for (KVPair<String, Integer> kv : freqHash.table()) {
//            if (kv != null) {
//                System.out.println(kv.toString());
//            }
//        }
        return freqHash;
    }

    /**
     * finds the number of connected components and
     * the number of elements of largest component
     * 
     * @param inFreqs : HashTable of frequencies for each parent
     * @return KVPair of connected comps as key and highest freq as value
     */
    public KVPair<Integer, Integer> findComponentInfo(
        HashTable<String, Integer> inFreqs) {
        //connected will be unique entries of hash map
        int connectedComponents = inFreqs.currCount();
        //highest freq is zero to start
        int highestFreq = 0;
        //traverse through each table value
        for (int i = 0; i < inFreqs.table().length; i++) {
            if (inFreqs.table()[i] != null &&
                inFreqs.table()[i].value() > highestFreq) {
                //set highest freq when one is found
                highestFreq = inFreqs.table()[i].value();
            }
        }
        //FIXME
//        System.out.println("connected: " + connectedComponents 
//            + " | highest freq: " + highestFreq);
        //return a pair of integers with component info
        return new KVPair<Integer, Integer>(connectedComponents, highestFreq);
    }

    /**
     * selects all the parents with matching highest frequency
     * 
     * @param inHighestFreq : number of elements in largest component
     * @param inFreqs : HashTable holding parent frequencies
     * @return DLList of all parents matching highest frequency
     */
    public DLList<Integer> selectLargestParents(int inHighestFreq, 
        HashTable<String, Integer> inFreqs) {
        //initialize a linked list for parents with large components
        DLList<Integer> selectedParents = new DLList<Integer>();
        //traverse through each table value 
        for (int i = 0; i < inFreqs.table().length; i++) {
            if (inFreqs.table()[i] != null) {
                if (inFreqs.table()[i].value() == inHighestFreq) {
                    //append when the list finds a parent of highest freq
                    selectedParents.append(Integer.valueOf(
                        inFreqs.table()[i].key()));
                }
            }
        }
        //FIXME
//        System.out.println("PARENTS OF LARGEST");
//        System.out.println("__________________");
//        selectedParents.print();
        //return the selected parent
        return selectedParents;
    }

    /**
     * maps the highest frequency parents to an array
     * of largest components and their vertices
     * 
     * @param selectedParents : list of parents that contain highest freq
     * @param parentTable : KVPair of parent array and vertex array
     * @param highestFreq : number of elements per component
     * @return integer matrix that contains all largest component vertices
     */
    public int[][] mapComponents(DLList<Integer> selectedParents,
        KVPair<int[], int[]> parentTable, int highestFreq) {
        int numComponents = selectedParents.size();
        int numVertices = highestFreq;

        //initialize the array of largest components
        int[][] componentArray = new int[numComponents][numVertices];
        
        //traverse through each parent that is selected 
        for (int i = 0; i < selectedParents.size(); i++) {
            selectedParents.repos(i);
            int insertedVertices = 0;
            for (int j = 0; j < parentTable.value().length 
                && insertedVertices < numVertices; j++) {
//                System.out.println(parentTable.key()[j] 
//                    + ", " + selectedParents.element() 
//                    + "[" + i + ", " + j + "]");
                //if the parent matches the selected parent
                if (parentTable.value()[j] == selectedParents.element()) {
                    //we add it to the component array
                    componentArray[i][insertedVertices] = parentTable.key()[j];
                    insertedVertices++;
                }
            }
        }
        //reset selected parents after looping
        selectedParents.repos(0);

//        for (int[] i : componentArray) {
//            for (int j: i) {
//                System.out.println(j);
//            }
//            System.out.println("");
//        }

        return componentArray;
    }

    /**
     * floyd warshall algorithm that computes
     * all pairs shortest paths for each vertex
     * 
     * @param componentVertices : array of vertices for one component
     * @return maximum diameter within distance array
     */
    public int floyd(int[] componentVertices) {
        int maxDiameter = 0;
        int n = componentVertices.length;
        int[][] d = new int[n][n];
        //initialize edges in distance matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //if it is a diagonal then we give zero weight
                if (i == j) {
                    d[i][j] = 0;
                }
                //if there exists an edge we give one weight
                else if (hasEdge(componentVertices[i], componentVertices[j])) {

                    d[i][j] = 1;
                }
                //otherwise no edge exists and we give infinite weight
                else {
                    d[i][j] = Integer.MAX_VALUE;
                }
                //FIXME
//                System.out.println(componentVertices[i] 
//                    + ", " + componentVertices[j] + "|" + d[i][j]);
            }
        }

        // compute all shortest k paths
        for (int k = 0; k < n; k++) { 
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if ((d[i][k] != Integer.MAX_VALUE) &&
                        (d[k][j] != Integer.MAX_VALUE) &&
                        (d[i][j] > (d[i][k] + d[k][j]))) {
                        d[i][j] = d[i][k] + d[k][j];
                        //FIXME? update the max diameter 
                        if (d[i][j] > maxDiameter) {
                            maxDiameter = d[i][j];
                        }
                    }
                }
            }
        }
        return maxDiameter;
    }


    /**
     * output all the relevant graph information
     * 
     * @throws Exception 
     */
    public void output() throws Exception {
        int largestDiameter = 0;
        //build the parent table
        KVPair<int[], int[]> parentTable = findParents();
        //find frequencies from parents
        HashTable<String, Integer> freqHash = findFrequencies(parentTable);
        //find the component info from freq hash map
        KVPair<Integer, Integer> compInfo = findComponentInfo(freqHash);
        //find the parents of component from freq hash map
        DLList<Integer> selectedParents = 
            selectLargestParents(compInfo.value(), freqHash);
        //when there are parents to map
        if (selectedParents.size() > 0) {
            //map the vertices for each component
            int[][] mapped = mapComponents(selectedParents, 
                parentTable, compInfo.value());
            //for each largest component calculate diameter
            for (int[] i : mapped) {
                int tempDiam = floyd(i);
                //update the largest
                if (tempDiam > largestDiameter) {
                    largestDiameter = tempDiam;
                }
            }
        }
        //print out the graph results
        System.out.println("There are " 
            + compInfo.key() + " connected components");
        System.out.println("The largest connected component has " 
            + compInfo.value() + " elements");
        System.out.println("The diameter of the largest component is " 
            + largestDiameter);
    }
    /**
     * prints out the array of adjacency list information
     */
    public void print() {
        for (int i = 0; i < vertexArray.length; i++) {
            if (vertexArray[i] == null) {
                System.out.println(i + ": null");
            }
            else {
                System.out.print(i + ": ");
                vertexArray[i].print();
            }
        }
        System.out.println("vertex count: " + getVertexCount());
        System.out.print("freelist:");
        freeVertices.print();
    }
}
