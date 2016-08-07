package book;

import java.util.LinkedList;
import java.util.List;

/**
 * Represent an edge in the graph.
 */
public class Vertex {

    public String       name;       // book.Vertex name
    public List<Edge>   adj;        // Adjacent vertices
    public double       dist;       // Cost
    public Vertex       prev;       // Previous vertex on shortest path
    public int          scratch;    // Extra variable used in algorithm

    public Vertex(String name) {
        this.name = name;
        adj = new LinkedList<Edge>();
        reset();
    }

    public void reset() {
        dist = Double.MAX_VALUE;
        prev = null;
        scratch = 0;
    }
}
