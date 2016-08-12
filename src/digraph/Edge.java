package digraph;

/**
 * This class represents an edge between two vertices. The destination vertex is
 * an hypernym vertex.
 */
public class Edge {

    public Vertex   destination;    // Edge to hypernym Vertex

    /**
     * Constructs a new Edge betwee two vertices.
     * @param destination - destination edge is a hypernym.
     * @param cost - traverse cost.
     */
    public Edge(Vertex destination, int cost) {
        this.destination = destination;
    }

}
