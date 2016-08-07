package digraph;

/**
 * This class represents an edge between two vertices. The destination vettex is
 * an hypernym vertex.
 */
public class Edge {

    public Vertex   dest;   // Edge to hypernym Vertex
    public int      cost;   // Traverse cost to hypernym Vertex

    public Edge(Vertex dest, int cost) {
        this.dest = dest;
        this.cost = cost;
    }

}
