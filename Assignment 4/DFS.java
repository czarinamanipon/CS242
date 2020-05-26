/*************************************************************************
 *
 *  Pace University
 *  Spring 2020
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Czarina Manipon
 *  Collaborators: None
 *  References: Introduction to Algorithms 3rd Edition Thomas H. Cormen
 *
 *  Assignment: #4
 *  Problem: Evaluate experimentally the performance of an efficient
 *  implementation of DFS
 *  Description: Builds an adjacency list for a directed graph, runs depth
 *  first search on an adjacency list and gives run time
 *
 *  Input: int nodes, int edges
 *  Output: Run time of an adjacency list made of given number of nodes and edges
 *
 *  Visible data fields:
 *  static int time
 *
 *  Visible methods:
 *  public static void main(String[] args)
 *  public static void depthFirstSearch(ArrayList<ArrayList<Node>> G)
 *  public static void DFSVisit(ArrayList<ArrayList<Node>> G, Node u)
 *  
 *
 *   Remarks
 *   -------
 *  2. Runtimes
 *             |E| = (|V|-1)   |E| = floor of[(|V|-1)^3/2]   |E| = (|V|-1)^2
 *  |V| = 10   322806          1852630                       2546898
 *  |V| = 100  1853566         14362559                      66471049
 *  |V| = 1000 14163262        114505624                     (OutOfMemoryError)
 * 
 *  3.
 *    approx. run time = 2V + 3E.
 *    Using an ArrayList to create the adj. list and access its contents,
 *    methods such as get() take constant time. As a result, the run time
 *    grows with growing sizes of V and even more so with larger sizes
 *    of E. DFS only needs to iterate through all V nodes twice and all E
 *    edges three times. With the constant time reading of the nodes and
 *    their data, the algorithm maintains a growth that reflects O(V+E)
 *************************************************************************/
import java.util.Scanner;
import java.util.ArrayList; 
import java.util.Random;
public class DFS{
  static int time; //global variable for timestamp
  public static void main(String[] args){
    Scanner scan = new Scanner(System.in); //scanner
    int nodes, edges; //stores user given number of nodes and edges
    System.out.println("Enter the number of nodes: ");
    nodes = scan.nextInt();
    System.out.println("Enter the number of edges: ");
    edges = scan.nextInt();
    
    ArrayList<ArrayList<Node>> A = new ArrayList<ArrayList<Node>>(nodes);
    //adjacency array
    //choose edges at random
    //stores list of edges in each node A[i]
      
    //initialize A with Node ArrayLists
    ArrayList<Node> tempList;
    for(int i = 0; i < nodes; i++){
        tempList = new ArrayList<Node>(edges);
        A.add(tempList);
    }
    
    Random rand = new Random(); //random generator to determine edges
    int tempu = 0, tempv = 0;
    Node e; //initializes new adjacent node
    for(int i = 0; i <= edges; i++){
      tempu = rand.nextInt(nodes - 1);
      //choose random node index in adjacency list
      tempv = rand.nextInt(nodes - 1);
      //choose another random node index
      while(tempv == tempu){
        tempv = rand.nextInt(nodes - 1);
        //loop until another node index besides the tempu node is chosen
        //prevents cycles in graph
      }
      e = new Node(tempv);
      A.get(tempu).add(e); //add node to adjacency list for u node
      if(A.get(tempu).size() > 1){
        //updates next data of previous Node in list
        A.get(tempu).get(A.get(tempu).size() - 2).setNext(e);
      }
    }
    
    /*for(int i = 0; i < nodes; i++){
      System.out.println("Nodes adjacent to " + i);
      for(int j = 0; j < A.get(i).size(); j++){
        System.out.print(A.get(i).get(j).printNode());
      }
      System.out.println();
    } */
   
    long startTime = System.nanoTime(); //stores start time of depthFirstSearch
    depthFirstSearch(A);
    System.out.println("Run time of " + nodes + " nodes and " + edges + " edges = " + (System.nanoTime() - startTime));
    
  }
  
  public static void depthFirstSearch(ArrayList<ArrayList<Node>> G){
    // Creates vertex info for vertex u with arrays
    int V = G.size();
    for(int i = 0; i < V; i++){
      for(int u = 0; u < G.get(i).size(); u++){
      //initialized all vertices u to the color white
        G.get(i).get(u).updateColor("White");
      }
    }
    
    time = 0; //start time set to 0
    for(int i = 0; i < V; i++){
      for(int u = 0; u < G.get(i).size(); u++){
      //finds each unvisisted node and visits them
         if(G.get(i).get(u).getColor().equals("White")){
        //if the node is white, it has yet to have been visitied
        DFSVisit(G, G.get(i).get(u));
         }
      }
    }
    
   /*for(int i = 0; i < G.size(); i++){
     for(int j = 0; j < G.get(i).size(); j++){
      System.out.println("Node " + G.get(i).get(j).getWeight() + ": Color: " + G.get(i).get(j).getColor() + " Discovery time: " + G.get(i).get(j).getDisc() + " Finishing Time: " + G.get(i).get(j).getFin());
    }
   } */
  }
  
  public static void DFSVisit(ArrayList<ArrayList<Node>> G, Node u){
    //visits a vertex that is initially white
    time = time + 1; //time the current vertex is discovered
    u.setDisc(time); //discovery time stored
    u.updateColor("GRAY"); //color of vertex is changed to grey, vertex was visited once
    for(int v = 0; v < G.get(u.getWeight()).size(); v++){ //explores the edges v of given vertex u
      if(G.get(u.getWeight()).get(v).getColor().equals("WHITE")){
        /* if the color of the vertex v at an edge from vertex u is white/undiscovered
         * save the previous node in the graph from v as u and visit vertex v next
         */
        DFSVisit(G, G.get(u.getWeight()).get(v));
      }
    }
    u.updateColor("BLACK"); //vertex u is finished visiting, change color to black
    time = time + 1; //update time
    u.setFin(time); //saves finishing time for vertex u
  }
}