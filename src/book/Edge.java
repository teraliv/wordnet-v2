package book;

public class Edge {

    public Vertex dest;     // Second vertex in Edge
    public double cost;     // book.Edge cost

    public Edge(Vertex d, double c) {
        dest = d;
        cost = c;
    }

}
