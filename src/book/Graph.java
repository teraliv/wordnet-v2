package book;

import java.util.*;

public class Graph {

    public static final double INFINITY = Double.MAX_VALUE;

    private Map<String, Vertex> vertexMap = new HashMap<>();

    private Vertex getVertex(String name) {

        Vertex vertex = vertexMap.get(name);

        if (vertex == null) {
            vertex = new Vertex(name);
            vertexMap.put(name, vertex);
        }

        return vertex;
    }

    public void addEdge(String source, String dest, double cost) {

        Vertex v = getVertex(source);
        Vertex w = getVertex(dest);
        v.adj.add(new Edge(w, cost));
    }

    private void clearAll() {
        for (Vertex v : vertexMap.values()) {
            v.reset();
        }
    }

    public void printPath(String dest) {

        Vertex w = vertexMap.get(dest);
        if( w == null)
            throw  new NoSuchElementException();
        else if (w.dist == INFINITY)
            System.out.println(dest + " is unreachable");
        else {
            System.out.println("(cost is: " + w.dist + ") ");
            printPath(w);
            System.out.println();
        }
    }

    private void printPath(Vertex dest) {

        if (dest.prev != null) {
            printPath(dest.prev);
            System.out.print(" to ");
        }
        System.out.print(dest.name);
    }

    public void unweighted(String startName) {
        clearAll();

        Vertex start = vertexMap.get(startName);

        if (start == null)
            throw new NoSuchElementException("Start vertex not found");

        Queue<Vertex> q = new LinkedList<>();
        q.add(start);
        start.dist = 0;

        while (!q.isEmpty()) {
            Vertex v = q.remove();

            for (Edge e : v.adj) {
                Vertex w = e.dest;

                if (w.dist == INFINITY) {
                    w.dist = v.dist + 1;
                    w.prev = v;
                    q.add(w);
                }
            }
        }
    }
}
