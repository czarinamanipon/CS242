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
 *  Description: Node Class, contains variables and methods to construct a Node object
 *
 *  Input: none
 *  Output: none
 *
 *  Visible data fields:
 *  private String color
 *  private int dt
 *  private int ft
 *  private Node next
 *  private int weight;
 *
 *  Visible methods:
 *  public Node(int weight)
 *  public int getWeight()
 *  public void updateColor(String c)
 *  public String getColor()
 *  public void setDisc(int t)
 *  public void setFin(int t)
 *  public int getFin()
 *  public void setNext(Node n)
 *  public Node getNext()
 *  public String printNode()
 * 
 *************************************************************************/
public class Node{
  private String color; //stores color of node
  private int dt; //stores discovery time
  private int ft; //stores finishing time
  private Node next; //stores directed edge to next adj. node
  private int weight;
  
  public Node(int weight){
    color = "";
    dt = 0;
    ft = 0;
    next = null;
    this.weight = weight;
  }
  
  public int getWeight(){
    return weight;
  }
  
  public void updateColor(String c){
    //updates color of a node
    color = c;
  }
  
  public String getColor(){
    //gets color of node
    return color;
  }
  
  public void setDisc(int t){
    //sets discovery time of a node
    dt = t;
  }
  
  public int getDisc(){
    //returns discovery time of a node
    return dt;
  }
  
  public void setFin(int t){
    //sets finishing time of a node
    ft = t;
  }
  
  public int getFin(){
   //gets finishing time of a node
    return ft;
  }
  
  public void setNext(Node n){
    next = n;
  }
  
  public Node getNext(){
    return next;
  }
  
  public String printNode(){
    if(next != null)
      return "Node " + weight + " -> " + next.getWeight();
    else
      return "Node " + weight + " -> null";
  }
}