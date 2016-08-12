package digraph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

    public int          id;             // ID of current synset
    public int          cost;           // Traverse cost to destination Vertex
    public String       synonym;        // The synonym
    public String       definition;     // Dictionary definition
    public List<Edge>   adj;            // A list of adjacent vertices (hypernyms of this vertex)


    /**
     * Constructs a new Vertex for the digraph.
     * @param id - vertex id.
     * @param synonym - synonym.
     * @param definition - dictionary definition.
     */
    public Vertex(int id, String synonym, String definition) {
        this.id         = id;
        this.cost       = 0;
        this.synonym    = synonym;
        this.definition = definition;
        this.adj        = new LinkedList<>();
    }

    public void addEdge(Vertex hypernym) {
        adj.add(new Edge(hypernym, 1));
    }

}
