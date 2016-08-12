package digraph;

import java.util.*;

public class SAP {

    private Digraph digraph;    // Directed acyclic graph


    public SAP(Digraph digraph) {
        this.digraph = digraph;
    }

    /**
     * A method to find the length of ancestral path between v and w. If there is no
     * such path, the length is -1.
     * @param v     - vertex V
     * @param w     - vertex W
     * @return      - length between V and W, otherwise -1.
     */
    public int length(int v, int w) {

        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("vertex id must be positive!");
        }

        int lca = ancestorIdx(v, w);   // least common ancestorIdx

        if (lca == -1)
            return -1;
        else
            return getDistance(v, lca) + getDistance(w, lca);
    }


    /*
     *   NOTE
     *   Lowest Common Ancestor
     *
     *   In graph theory and computer science, the lowest common ancestorIdx (LCA) of two nodes v and w in a tree or
     *   directed acyclic graph (DAG) T is the lowest (i.e. deepest) node that has both v and w as descendants,
     *   where we define each node to be a descendant of itself (so if v has a direct connection from w,
     *   w is the lowest common ancestorIdx).
     *   --------------------------------------------------------------------------------------------------------
     */
    /**
     * A method to find common ancestor of v and w that participates in a shortest ancestral path.
     * @param v - Vertex V
     * @param w - Vertex W
     * @return  - returns least common ancestorIdx if such exists, otherwise -1.
     */
    public int ancestorIdx(int v, int w) {

        Vertex test = digraph.getVertex(w);

        if (digraph.getVertex(v) == null || digraph.getVertex(w) == null)
            return -1;

        List<Vertex> ancestorsV     = getAncestorsDFS(v);                           // all ancestors of vertex V
        List<Vertex> ancestorsW     = getAncestorsDFS(w);                           // all ancestors of vertex W
        List<Vertex> ancestors      = getCommonAncestors(ancestorsV, ancestorsW);   // common ancestors of V and W

        if (ancestors == null)      // -1, if no common ancestors
            return -1;

        int distV   = getDistance(v, ancestors.get(0).id);      // Distance between V and first ancestor in the list
        int distW   = getDistance(w, ancestors.get(0).id);      // Distance between W and first ancestor in the list
        int dist    = distV + distW;                            // Distance between V and W through the first ancestor
        int lca     = ancestors.get(0).id;                      // First common ancestor id


        // check other ancestors in the list if it LCA
        for (int i = 1; i < ancestors.size(); i++) {

            distV = getDistance(v, ancestors.get(i).id);
            distW = getDistance(w, ancestors.get(i).id);

            if ( (distV + distW) < dist ) {
                dist    = distV + distW;
                lca     = ancestors.get(i).id;
            }
        }

        return lca;
    }


    /**
     * This method returns the string definition of ancestor.
     * @param ancIdx    - ancestor ID.
     * @return          - returns ancestor definition.
     */
    public String ancestor(int ancIdx) {

        if (ancIdx == -1)
            throw new IllegalArgumentException("Ancestor does not exist: ancestor id is " + ancIdx);

        return digraph.getVertex(ancIdx).definition;
    }


    // HELPER METHODS
    /**
     * A method to find all traversed vertices from the given vertex to the root of digraph. This method
     * implements DFS (Depth First Search) algorithm to traverse through the graph.
     *
     * @param vertexID  - starting vertex.
     * @return          - returns a list of traversed vertices.
     */
    private List<Vertex> getAncestorsDFS(int vertexID) {

        List<Vertex>    traversed   = new ArrayList<>();    // List of traversed vertices from v to root
        Stack<Vertex>   stack       = new Stack<>();        // Stack to implement Depth First Search data structure
        Vertex          current;                            // Staring vertex

        stack.push(digraph.getVertex(vertexID));            // Put on stack first vertex in a path

        do {
            current = stack.pop();                          // Pop first vertex from stack
            traversed.add(current);                         // Add current to the list of visited vertices

            // add all vertices adjacent to current vertex to stack
            for (int i = 0; i < current.adj.size(); i++) {

                // check if adjacent vertex is already in the list (if it's been traversed already)
                if (!traversed.contains(current.adj.get(i).destination))
                    stack.push(current.adj.get(i).destination);
            }
        }
        // repeat the process until stack is not empty
        while (!stack.isEmpty());

        return traversed;
    }


    /**
     * A helper method to find all common ancestors from the list of two ancestors paths.
     * @param ancestorsV    - The list of all ancestors of vertex V.
     * @param ancestorsW    - The list of all ancestors of vertex W.
     * @return              - The list of common ancestors of V and W vertices.
     */
    private List<Vertex> getCommonAncestors(List<Vertex> ancestorsV, List<Vertex> ancestorsW) {

        if (ancestorsV == null || ancestorsW == null)
            throw new NullPointerException("One or both of the ancestors lists is empty!");

        List<Vertex> ancestors = new ArrayList<>();

        // loop through the ancestors lists and compare vertices
        for (Vertex vertexV : ancestorsV) {
            for (Vertex vertexW : ancestorsW) {
                if (vertexV.equals(vertexW))
                    ancestors.add(vertexV);
            }
        }

        return ancestors;
    }


    /**
     * This method calculates the distance cost of traverse between two vertices. The cost of
     * traverse between any two neighboring vertices is 1.
     * @param startID   - The starting vertex.
     * @param endID     - The ending vertex.
     * @return          - The distance between two vertices, of -1 if there is no path.
     */
    private int getDistance(int startID, int endID) {

        if (digraph.getVertex(startID) == null || digraph.getVertex(endID) == null)
            throw new NullPointerException("Start or End vertex does not exists!");


        Stack<Vertex>           stack = new Stack<>();      // Stack is used to implement DFS
        Map<Vertex, Integer>    costs = new HashMap<>();    // HashMap of vertices and costs associated with it

        int     distance = -1;  // Final distance between two vertices, -1 if no path between vertices
        int     currentCost;    // The cost of current vertex in digraph traverse
        Vertex  current;        // Current vertex in digraph traverse
        Vertex  adjacent;       // Adjacent vertex to current vertex

        stack.push(digraph.getVertex(startID));     // Push current vertex to the stack
        // put the starting vertex to map with the initial cost of zero
        costs.put(digraph.getVertex(startID), digraph.getVertex(startID).cost);

        do {
            current = stack.pop();      // Pop vertex from the stack

            // Here we check if current vertex is not the same as target vertex
            // We don't need to add target's adjacent vertices, since it is destination
            if (current.id != endID) {

                // loop through the list of adjacent vertices
                for (int i = 0; i < current.adj.size(); i++) {
                    currentCost     = costs.get(current) + 1;            // update current cost
                    adjacent        = current.adj.get(i).destination;    // update current adjacent vertex

                    // check if first time visiting this vertex
                    if (!costs.containsKey(adjacent)) {
                        costs.put(adjacent, currentCost);
                        stack.push(adjacent);
                    }
                    // check if the vertex has been visited (based on the vertex value update the cost)
                    else if (costs.get(adjacent) > currentCost) {
                        costs.replace(adjacent, currentCost);
                        stack.push(adjacent);
                    }
                }
            }

        }
        while (!stack.isEmpty());

        // will return the target cost value from the HashMap of all costs
        if (costs.get(digraph.getVertex(endID)) != null)
            distance = costs.get(digraph.getVertex(endID));

        return distance;
    }


    public static void main(String[] args) {

        if (args.length < 2)
            throw new IndexOutOfBoundsException("Provide two files: synsets and hypernyms");

        String  synsets     = args[0];                          // synsets file
        String  hypernyms   = args[1];                          // hypernyms file

        Digraph digraph     = new Digraph(synsets, hypernyms);  // directed graph
        SAP     sap         = new SAP(digraph);

        System.out.println("Enter two nouns numbers separated by space or CTRL + C to exit...");

        Scanner         input   = new Scanner(System.in);       // scanner to read input from console
        String          line    = input.nextLine();             // store input from console to string
        StringTokenizer st      = new StringTokenizer(line);    // tokenize input

        while (st.countTokens() == 2) {
            int v           = Integer.parseInt(st.nextToken()); // vertex V
            int w           = Integer.parseInt(st.nextToken()); // vertex W
            int length      = sap.length(v, w);                 // length between V and W
            int ancestor    = sap.ancestorIdx(v, w);            // least common ancestorIdx between V and W

            System.out.printf("sap = %d, ancestor = %d\n\n", length, ancestor);

            line            = input.nextLine();                 // read input again
            st              = new StringTokenizer(line);        // tokenize again
        }
    }
}
