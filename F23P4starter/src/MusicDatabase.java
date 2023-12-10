
/**
 * music database handles all interactions
 * between commands and the graph and 
 * artist and song tables
 * 
 * @author Ali Haidari (alih)
 * @version 11.3.2023
 */
public class MusicDatabase {
    private HashTable<String, Integer> songTable;
    private HashTable<String, Integer> artistTable;
    private Graph graph;
    
    /**
     * constructor of a music database
     * 
     * @param initSize : initial hash table sizes
     */
    public MusicDatabase(int initSize) {
        songTable = new HashTable<String, Integer>(initSize);
        artistTable = new HashTable<String, Integer>(initSize);
        graph = new Graph(initSize);
    }
    
    /**
     * insert command for music database
     * that inserts to tables and adds
     * to graph
     * 
     * @param inArtist : artist to be inserted
     * @param inSong : song to be inserted
     * @throws Exception
     */
    public void insert(String inArtist, String inSong) throws Exception {
        Integer artistVertex;
        Integer songVertex;
        //check if we need to expand graph before inserting
        if (graph.getVertexCount() >= graph.getVertexArray().length / 2) {
            graph.expand();
        }
        
        //check if successful insertion into artist table
        if (artistTable.insert(inArtist, graph.nextFreeVertex())) {
            //print expansion message if necessary
            if (artistTable.isExpanded()) {
                System.out.println("Artist hash table size doubled.");
                artistTable.setExpanded(false);
            }
            
            //remove the artist vertex from free list
            artistVertex = graph.getFreeVertices().remove();
            //print out new artist insertion
            System.out.println("|" + inArtist + "| is added" 
                + " to the Artist database.");
        }
        else {
            //search in artist table for artist and vertex
            artistVertex = artistTable.search(inArtist);
        }
        
        //check if successful insertion into song table
        if (songTable.insert(inSong, graph.nextFreeVertex())) {
            //print expansion message if necessary
            if (songTable.isExpanded()) {
                System.out.println("Song hash table size doubled.");
                songTable.setExpanded(false);
            }
            //remove the song vertex from free list
            songVertex = graph.getFreeVertices().remove();
            //print out new song addition
            System.out.println("|" + inSong + "| is added" 
                + " to the Song database.");
        }
        else {
            //search in song table for song and vertex
            songVertex = songTable.search(inSong);
        }

        //we check if the edges exist between them
        if (graph.hasEdge(artistVertex, songVertex)) {
            System.out.println("|" + inArtist + "<SEP>" + inSong 
                + "|" + " duplicates a record already in the database.");
        }
        //otherwise we add edges between the vertices
        else {
            graph.addEdge(artistVertex, songVertex);
        }
    }
    
    /**
     * remove song command that removes from
     * song table and graph
     * 
     * @param inSong : song to be removed
     * @throws Exception
     */
    public void removeSong(String inSong) throws Exception {
        Integer removedSongVertex = songTable.remove(inSong);
        //check if removal exists in song table
        if (removedSongVertex == null) {
            System.out.println("|" + inSong + "| does not exist in"
                + " the Song database.");
        }
        //otherwise it exists and we remove from graph
        else {
            
            int numEdgesToRemove = graph.getAdjList(removedSongVertex).size();
            for (int i = 0; i < numEdgesToRemove; i++) {
                int edgeToRemove = graph.getAdjList(
                    removedSongVertex).element();
                graph.removeEdge(removedSongVertex, edgeToRemove);
            }
            graph.freeVertex(removedSongVertex);
            System.out.println("|" + inSong + "| is removed from the"
                + " Song database.");
        }
//        graph.print();
    }
    
    /**
     * remove artist command that removes
     * from artist table and graph
     * 
     * @param inArtist : artist to be removed
     * @throws Exception
     */
    public void removeArtist(String inArtist) throws Exception {
        Integer removedArtistVertex = artistTable.remove(inArtist);
        //check if removal exists in song table
        if (removedArtistVertex == null) {
            System.out.println("|" + inArtist + "| does not exist in"
                + " the Artist database.");
        }
        //otherwise it exists and we remove from graph
        else {
            int numEdgesToRemove = graph.getAdjList(removedArtistVertex).size();
            for (int i = 0; i < numEdgesToRemove; i++) {
                int edgeToRemove = graph.getAdjList(
                    removedArtistVertex).element();
                graph.removeEdge(removedArtistVertex, edgeToRemove);
            }
            graph.freeVertex(removedArtistVertex);
            System.out.println("|" + inArtist + "| is removed from the"
                + " Artist database.");
        }
//        graph.print();
    }
    
    
    /**
     * print song command that outputs the
     * song table
     */
    public void printSong() {
        for (int i = 0; i < songTable.currSize(); i++) {
            if (songTable.table()[i] != null) {
                if (songTable.table()[i].sameAs(songTable.tomb())) {
                    System.out.println(i + ": TOMBSTONE");
                }
                else {
                    String song = songTable.table()[i].key();
                    System.out.println(i + ": |" + song + "|");
                }
            }
        }
        System.out.println("total songs: " + songTable.currCount());
    }
    
    /**
     * print artist command that outputs
     * the artist table
     */
    public void printArtist() {
        for (int i = 0; i < artistTable.currSize(); i++) {
            if (artistTable.table()[i] != null) {
                if (artistTable.table()[i].sameAs(artistTable.tomb())) {
                    System.out.println(i + ": TOMBSTONE");
                }
                else {
                    String song = artistTable.table()[i].key();
                    System.out.println(i + ": |" + song + "|");
                }
            }
        }
        System.out.println("total artists: " + artistTable.currCount());
    }
    
    
    /**
     * print graph command that outputs the
     * graph information
     * 
     * @throws Exception 
     */
    public void printGraph() throws Exception {
        graph.output();
    }
}
