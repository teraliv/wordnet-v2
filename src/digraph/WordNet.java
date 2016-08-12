package digraph;

import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private Digraph                 digraph;    // Directed acyclic graph
    private SAP                     sap;        // SAP
    private Map<String, Integer>    nouns;      // Collection of nouns from synset.txt file.


    /**
     * Constructs new WordNet.
     * @param synset        - synsets file
     * @param hypernyms     - hypernyms file
     */
    public WordNet(String synset, String hypernyms) {

        if (synset == null || hypernyms == null)
            throw new NullPointerException("Can't open synsets or hypernyms file.");

        digraph     = new Digraph(synset, hypernyms);   // WordNet directed rooted graph.
        sap         = new SAP(digraph);
        nouns       = getNounsCollection();             // HashMap collection of all synset nouns.

        if (!isRooted())
            throw new IllegalArgumentException("The digraph is not rooted.");
    }


    /**
     * A method to return all WordNet nouns.
     * @return - WordNet nouns.
     */
    public Iterable<String> nouns() {
        return nouns.keySet();
    }


    /**
     * A method to check if the provided string is noun from synsets.txt
     * @param word  - string to check
     * @return      - returns true if word is valid noun, otherwise false
     */
    public boolean isNoun(String word) {
        return nouns.containsKey(word);
    }


    /**
     * A method to find distance between nounA and nounB.
     * @param nounA - noun A
     * @param nounB - noun B
     * @return      - returns distance between noun A and noun B
     */
    public int distance(String nounA, String nounB) {
        if ( isNoun(nounA) == false || isNoun(nounB) == false )
            throw new NullPointerException("One or both nouns does not exists!");

        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }


    /**
     * A synset (second field of synsets.txt) that is the common ancestorIdx of nounA and nounB
     * in a shortest ancestral path.
     * @param nounA - noun A
     * @param nounB - noun B
     * @return      - returns lowest common ancestorIdx of nounA and nounB
     */
    public String sap(String nounA, String nounB) {
        if ( isNoun(nounA) == false || isNoun(nounB) == false )
            throw new NullPointerException("One or both nouns does not exists!");

        int ancestor = sap.ancestorIdx(nouns.get(nounA), nouns.get(nounB));

        return digraph.getVertex(ancestor).synonym;
    }


    // HELPER METHODS
    /**
     * Helper method to get a Map Collection of all nouns from the directed graph.
     * @return  - Collection of all nouns.
     */
    private Map<String, Integer> getNounsCollection() {

        Map<String, Integer> nouns = new HashMap<>();

        for (Vertex v : digraph.getVertexMap().values()) {
            nouns.put(v.synonym, v.id);
        }

        return nouns;
    }


    /**
     * This method check is the directed graph is rooted graph. The root node is the node that does not
     * have any adjacent nodes. If such node exists therefore the graph is rooted graph.
     * @return - true if rooted digraph otherwise false.
     */
    public boolean isRooted() {

        boolean rooted = false;

        for (Vertex v : digraph.getVertexMap().values()) {
            if (v.adj.size() == 0)
                rooted = true;
        }

        return rooted;
    }


    public static void main(String[] args) {

        if (args.length < 2)
            throw new IndexOutOfBoundsException("Must be two nouns provided!");


        WordNet wordnet = new WordNet("../synsets.txt", "../hypernyms.txt");                            // advanced WordNet
        //WordNet wordnet = new WordNet("../sample-synsets.txt", "../sample-hypernyms.txt");      // simple WordNet

        String  a           = args[0];                  // noun A
        String  b           = args[1];                  // noun B
        String  ancestor    = wordnet.sap(a, b);        // least common ancestorIdx between A and B
        int     distance    = wordnet.distance(a, b);   // distance between A and B

        System.out.println("Ancestor: " + ancestor);
        System.out.println("Distance: " + distance);
    }

}
