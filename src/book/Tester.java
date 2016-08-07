package book;

import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Tester {

    public static void main(String[] args) {

        Graph g = new Graph();

        try {
            FileReader fin = new FileReader("infile.txt");
            Scanner graphFile = new Scanner(fin);

            // Read the edges and insert
            String line;

            while (graphFile.hasNextLine()) {
                line = graphFile.nextLine();
                StringTokenizer st = new StringTokenizer(line);

                try {
                    if (st.countTokens() != 3) {
                        System.err.println("Skipping bad line " + line);
                        continue;
                    }

                    String  source  = st.nextToken();
                    String  dest    = st.nextToken();
                    int     cost    = Integer.parseInt(st.nextToken());

                    g.addEdge(source, dest, cost);
                }
                catch (NumberFormatException e) {
                    System.err.println("Skipping bad line " + line);
                }

            }
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("File read...");

        Scanner in = new Scanner(System.in);
        while (processRequest(in, g));
    }

    public static boolean processRequest(Scanner in, Graph g) {
        try {
            System.out.print("Enter start node:");
            String startName = in.nextLine();

            System.out.print("Enter destination node:");
            String destName = in.nextLine();

            g.unweighted(startName);
            g.printPath(destName);
        }
        catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

}
