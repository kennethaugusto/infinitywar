package avengers;

import java.util.*;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void readFile(int numGens, int[] genValues, double[] funcVals, int[][] eV) {
        for (int i = 0; i < numGens; i++) {
            genValues[i] = StdIn.readInt();
            funcVals[i] = StdIn.readDouble();
        }
        for (int i = 0; i < numGens; i++) {
            for (int j = 0; j < numGens; j++) {
                int val = (int) ( StdIn.readInt() / ( funcVals[i] * funcVals[j] ) );
                eV[i][j] = val;
            }
        }
    }

    private static int[] dijkstra(int[][] values) {
        int length = values.length;
        int[] distance = new int[length];
        boolean[] ifVisited = new boolean[length];

        for (int i = 1; i < distance.length; i++ ) {
            distance[i] = Integer.MAX_VALUE;
        }


        for (int i = 0; i < length; i++) {
            int current = findMinimumCostNode(distance, ifVisited);
            ifVisited[current] = true;
            for (int j = 0; j < length; j++) {
                int curDistance = distance[current] + values[current][j];
                if (!ifVisited[j] && values[current][j] != 0 && curDistance < distance[j]) {
                    distance[j] = curDistance;
                }
            }
        }
        return distance;
    }

    private static int findMinimumCostNode(int[] minimumCost, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int minimumNode = -1;
        int length = minimumCost.length;
        for (int i = 0; i < length; i++) {
            if (!visited[i] && minimumCost[i] < min) {
                min = minimumCost[i];
                minimumNode = i;
            }
        }
        return minimumNode;
    }


    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int numGenerators = StdIn.readInt();
        int[] generatorValues = new int[numGenerators];
        double[] functionalityValues = new double[numGenerators];
        int[][] edgeValues = new int[numGenerators][numGenerators];

        readFile(numGenerators, generatorValues, functionalityValues, edgeValues);

        int[] minimumCost = dijkstra(edgeValues);

        StdOut.print(minimumCost[numGenerators-1]);
    }
}
