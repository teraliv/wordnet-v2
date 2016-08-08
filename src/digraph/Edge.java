package digraph;

/**
 * This class represents an edge between two vertices. The destination vertex is
 * an hypernym vertex.
 */
public class Edge {

    public Vertex   dest;   // Edge to hypernym Vertex
    public int      cost;   // Traverse cost to hypernym Vertex

    /**
     * Constructs a new Edge betwee two vertices.
     * @param dest - destination edge is a hypernym.
     * @param cost - traverse cost.
     */
    public Edge(Vertex dest, int cost) {
        this.dest = dest;
        this.cost = cost;
    }

}
