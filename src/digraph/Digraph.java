package digraph;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Digraph {

    private Map<Integer, Vertex> vertexMap;
    private String synsets;
    private String hypernyms;


    public Digraph(String synsets, String hypernyms) {
        this.synsets    = synsets;
        this.hypernyms  = hypernyms;
        this.vertexMap  = new HashMap<>();
        readSynsets();
        readHypernyms();
    }


    // HELPER METHODS
    private void readSynsets() {

        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(new FileInputStream(synsets));
            String line;

            while (fileScanner.hasNextLine()) {

                line = fileScanner.nextLine();  // current line from synset.txt file
                addVertexToMap(line);
            }

        }
        catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        finally {
            if (fileScanner != null)
                fileScanner.close();
        }

    }


    private void readHypernyms() {

        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(new FileInputStream("hypernyms.txt"));
            String line = null;

            while (fileScanner.hasNextLine()) {

                line = fileScanner.nextLine();                                  // current line from hypernyms.txt
                StringTokenizer st = new StringTokenizer(line, ",");            // tokenize line

                int totalTokens = st.countTokens();                             // total number of tokens
                Vertex v = vertexMap.get(Integer.parseInt(st.nextToken()));     // vertex to hypernyms

                for (int i = 1; i < totalTokens; i++) {
                    Vertex w = vertexMap.get(Integer.parseInt(st.nextToken()));
                    v.adj.add(new Edge(w, 1));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        finally {
            if (fileScanner != null)
                fileScanner.close();
        }

    }


    /**
     * A helper method to split the given string into a three tokens: synset id, synonym, and definition. Create a
     * vertex based on the tokens and add this vertex to a vertex map.
     *
     * @param line - current line from synset.txt
     */
    private void addVertexToMap(String line) {

        // Splits the line from synset.txt by the first two occurrences of comma into a three string tokens
        String[] tokens = line.split(",", 3);

        int     synset_id   = Integer.parseInt(tokens[0]);
        String  synonym     = tokens[1];
        String  definition  = tokens[2];

        Vertex vertex = new Vertex(synset_id, synonym, definition);
        vertexMap.put(synset_id, vertex);
    }


    public void printVertexMap() {
        //for (Vertex v : vertexMap.values()) {
        //    System.out.println(v.synset_id);
        //    System.out.println(v.synonym);
        //    System.out.println(v.definition);
        //}

        Vertex v = vertexMap.get(592);

        System.out.println(v.adj.size());
        System.out.println(v.adj.get(5).dest.synset_id);
    }
}
