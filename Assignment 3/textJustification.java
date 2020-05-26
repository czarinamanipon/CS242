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
 *  https://www.w3schools.com/java/java_files_create.asp 
 *  https://www.dev2qa.com/how-to-write-console-output-to-text-file-in-java/
 *
 *  Assignment: #3
 *  Problem: Compare experimentally the result obtained in justifying text
 *  with a greedy algorithm against doing the same using LATEX rules and
 *  Dynamic Programming. 
 *  Description: Implements a method that arranges text in a "nicely"
 *  arranged dislay by justifying each line so that they are all the same
 *  length.
 *
 *  Input: int n, int w
 *  Output:
 *  Enter a number of words:
 *  Enter a width:
 *  File created: justtext.txt 
 *  File created: unjusttext.txt 
 *
 *  Visible data fields:
 *  NONE
 *
 *  Visible methods:
 *  public static void main(String[] args)
 *  public static int badness(String[] W, int i, int j, int w)
 *  public static int[] split(int w, String[] W)
 *  public static void justify(int w, String[] W, int[] L)
 *  public static int[] minBadness(String[] W, int w)
 *  public static int memoMinBadness(String[] W, int i, int[] memo, int[] linebreaks_memo, int w)
 *  
 *
 *
 *   Remarks
 *   -------
 *
 *   MS Word uses greedy, as it adds as many words as possible within the
 *   page width limit before starting a next line. This results in there
 *   sometimes being one word left hanging on the last line while the
 *   earlier lines have as at least more than one word fitted into the line.
 *
 *
 *************************************************************************/
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.util.Scanner;
import java.util.Random;
public class textJustification{
  public static void main(String[] args){
    PrintStream originalOut = System.out; //saves original print stream
    Scanner scan = new Scanner(System.in); //scanner object
    int n; //length of W[]
    int w; //page width
    System.out.println("Enter a number of words: ");
    n = scan.nextInt();
    System.out.println("Enter a width: ");
    w = scan.nextInt();
    
    String[] W = new String[n];
    for(int p = 0; p < n; p++){ //initializes W with empty strings
      W[p] = "";
    }
    int stringlen; //stores length of each string, random from [1, 15]
    Random rand = new Random(); //random generator to randomize
    for(int a = 0; a < n; a++){ //adds words to indices of W
      stringlen = rand.nextInt(16) + 1;
      for(int b = 0; b < stringlen; b++){
        if(b == stringlen - 1){
          W[a] += "b";
        } else {
          W[a] += "a";
        }
      }
    }
    
    int[] L = split(w, W); //calls split() and stores list of linebreaks in L
    justify(w, W, L);
    
    System.setOut(originalOut); //resets system.out
    try {
      File unjustFile = new File("unjusttext.txt"); //creates unjust text file
      PrintStream unjustStream = new PrintStream("./unjusttext.txt");
      if (unjustFile.exists()) {
        System.out.println("File created: " + unjustFile.getName());
        System.setOut(unjustStream); //directs system.out to justtext.txt
      } else {
        System.out.println("File already exists: " + unjustFile.getName());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    for(int u = 0; u < n; u++){ //prints W in a single line in unjust.txt
      System.out.print(W[u]);
    }
    
    System.setOut(originalOut); //resets System.out to console
  }
  
  public static int badness(String[] W, int i, int j, int w){
    //defines badness of a document according to LaTex system
    //returns number of space left on each line between width
    //index i = starting index of a line
    //index j = starting index of the next line
    //w = page width
    //returns index where line containing words from W should split
    int totalLength = 0;
    //initalizes total length of suffixes from i to j
    for(int k = i; k <= j; k++){
      //calculates total length of line from index i - j words
      totalLength = totalLength + W[k].length();
    }
    if(totalLength > w){
      //if the total length exceeds the page width, infinite badness
      return Integer.MAX_VALUE;
    } else {
      //else, return space difference of the width and the length of line i, j
      return w - totalLength;
    }
  }
  
  public static int[] split(int w, String[] W){
    int[] L = minBadness(W, w);
    return L;
  }
  
  public static void justify(int w, String[] W, int[] L){
    try {
      File justFile = new File("justtext.txt");
      PrintStream justStream = new PrintStream("./justtext.txt");
      if (justFile.exists()) {
        System.out.println("File created: " + justFile.getName());
        System.setOut(justStream); //directs system.out to justtext.txt
      } else {
        System.out.println("File already exists: " + justFile.getName());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
    // w = page width
    // W = strings
    // L = array of breakpoints
    int totalLength = 0;
    int spaceLength;
    for(int l = 0; l < L.length; l++){ //goes through each index of L
      for(int t = 0; t < L[l]; t++){ //calculates total length of each line
        totalLength += W[t].length();
      }
      spaceLength = w - totalLength;
      for(int i = l; i < L[l]; i++){ //from W[i] to W[l - 1]
        //j = each index of L
        //L[j] = breakpoint / starting suffix of W for next line
        //W[i] = word
        System.out.print(W[i]);
        if(i < L[i] - 1) //if not at the end of the line, add space
          for(int s = 0; s < spaceLength; s++){
            System.out.print(" ");
        }
        else if(i == L[i] - 1) //otherwise, start new line
          System.out.println();
      }
    }
  }
  public static int[] minBadness(String[] W, int w){
    //returns array of indices of W where each suffix of W should start
    //to maintain the minimum badness = the optimal amount of space left
    //between width with as many words on the line in order as possible
    int n = W.length;
    int[] memo = new int[n]; //each index = min badness of the array from
    //index i to n suffixes
    int[] linebreaks_memo = new int[n]; //contains on each position i the
    //index of W where the next line of suffix W[i..n-1] should start
    for(int i = 0; i < n; i++){
      //sets the min badness of each index in memo temporarily to -1
      memo[i] = -1;
    }
    memoMinBadness(W, 0, memo, linebreaks_memo, w);
    return linebreaks_memo;
  }
  
  public static int memoMinBadness(String[] W, int i, int[] memo, int[] linebreaks_memo, int w){
    //returns minimum badness of the words from index i to n - 1 of array W
    int n = W.length; //stores length of array W
    if(memo[i] >= 0)
      return memo[i]; //if the memo badness at i -> n is greater than 0, return ot
    if(i == n){ //if the index where the line starts is the last word in W[]
      memo[i] = n;
      linebreaks_memo[i] = n;
      //the min badness at index i = size of W and the breakpoint for suffix from i
      // is the index of the last Word in W[]
    }else{ //if the starting index of the line is not the last index in W
      int min = Integer.MAX_VALUE; //initializes minimum badness at infinity
      int indexOfmin = 0; //initializes index of line break at 0
      int temp; //temp variable meant to hold potential new minimum badness number
      for(int j = i + 1; j < n; j++){
        //for each suffix from after index i to end of the array
        temp = badness(W, i, j, w);
        temp = temp + memoMinBadness(W, j, memo, linebreaks_memo, w);
        //badness of line i,j plus the optimal split of the line
        if(temp < min){
          //if the new possible min badness is less than the current min
          //becomes the new min
          min = temp;
          indexOfmin = j; //stores index of min where the next line should start
        }
      }
      memo[i] = min; //stores min badness of the line starting at index i
      linebreaks_memo[i] = indexOfmin; //stores breakpoint where next line starts after
    }
    return memo[i];
  }
}
