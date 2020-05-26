/*************************************************************************
 *
 *  Pace University
 *  Spring 2020
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Czarina Manipon
 *  Collaborators: NONE
 *  References: Introduction to Algorithms 3rd Edition by Thomas H. Cormen
 *  https://www.w3schools.com/java/java_arraylist.asp
 * 
 *  Assignment: #2
 *  Problem: Evaluate experimentally the running time of two algorithms on
 *  inputs with many repeating values
 *  Description: Executes and tests the run time of Quick Sort and Bucket
 *  Sort
 *
 *  Input: Integer inputs of n and r
 *  Output: Runtime of Quicksort and Bucketsort
 *
 *  Visible data fields:
 *  None
 *
 *  Visible methods:
 *  public static void Main(String[] args);
 *  public static void quickSort(ArrayList<Double> A, int p, int r);
 *  public static int partition(ArrayList<Double> A, int p, int r);
 *  public static void bucketSort(ArrayList<Double> A);
 * 
 *   Remarks
 *   -------
 * 3. 
 * QuickSort
 *   n|   10      50      100      500      1000      10000
 * r
 * -    
 * 5    242339  1825495  222690   221754   9142448    2931460
 * 50   325614  144094   5338006  215204   22565593  49789895
 * 100  145965  422924   5868532  214268   26265238  22085593
 * 500  145964  261053   149707   206783   46740541  115888547
 * 1000 445379  315321   444444   216140   209590    125396842
 * 10000 262924  450059  216140   211462   364912    88412549
 *
 * BucketSort
 *   n|   10      50      100      500      1000      10000
 * r
 * -
 * 5    1277192 1825495  3956956  36201136 40333997  1912150726
 * 50   1264092 1404444  2188536  13832970 26584303  1391449458
 * 100  1227602 1647718  1991109  25391321 34766751  1153744885
 * 500  1284677 3295435  1659882  28668979 46740541  1249424796
 * 1000 4589470 1427835  4142218  24714830 30444884  1382616718
 * 10000 1248186 858947  3450757  25916233 32470613  1300553636
 *
 * 4.
 * Based on the above data, bucketsort (O(n) runs significantly slower than
 * quicksort (O(nlogn), especially when handling larger n number of
 * inputs. Both algorithms grow in size with larger inputs of n and with
 * more repetitions. Another similarity between the algorithms is that
 * runtime is decreased when n and r are the same or closer values compared
 * to when n and r are different values. Since the actual number of
 * repetitions ranges from 1 to twice the size of r, closer values of n and
 * r could result in arrays that nearly or completely made of only the same
 * value repeated throughout the array. In a lot of those cases, the
 * the resulting run time of both algorithms is the same regardless of
 * structure.
 *************************************************************************/
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;
import java.util.ArrayList;
public class repetition{
  public static void main(String[] args){
    int n; //stores size of input from user
    int r; //stores expected number of repetitions from user
    Scanner scan = new Scanner(System.in); //Scanner variable
    Random rand = new Random(); //Random generator variable
    
    System.out.println("Enter size of n:");
    n = scan.nextInt();
    System.out.println("Enter the expected number of repetitions:");
    r = scan.nextInt();
    
    //arrays
    ArrayList<Double> arr1 = new ArrayList<Double>(n);
    ArrayList<Double> arr2 = new ArrayList<Double>(n);
    double x; //saves random integer to be contained in both arrays
    int rr; //saves random number of repetitions for each number [1, 2r]
    int i = 0; //stores index of arrays to be filled
    while(i < n){ //fills arr1 and arr2 with random doubles
      x = rand.nextDouble(); //[0.0, 1.0)
      rr = rand.nextInt(2 * r) + 1; //[1, 2r]
      for(int j = 1; j <= rr; j++){ //adds x to both arrays rr times
          arr1.add(x);
          arr2.add(x);
          i++; //update i to count each repetition as part of n doubles in each array
          if(i == n){
          //if n doubles have already been alloted before rr repetitions
          //were finished, end the inner loop - no more doubles can be added
            break;
        }
      }
    }

    long startTime = System.nanoTime(); //saves start time of call to quickSort
    quickSort(arr1, 0, n - 1);
    System.out.println("Quick Sort Run Time: " + (System.nanoTime() - startTime));
    
    startTime = System.nanoTime(); //now save start time of call to bucketSort
    bucketSort(arr2);
    System.out.println("Bucket Sort Run Time: " + (System.nanoTime() - startTime));
  }
  
  public static ArrayList<Double> quickSort(ArrayList<Double> A, int p, int r){
    /* partitions array in subarrays A[p, q - 1] and A[q + 1, r]
    int p = lowest value
    int r = highest value
    */
    int q; //stores partition index
    if(A.get(p) < A.get(r)){
      //if the lowest recieved value lower than the highest recieved value
      //continue to recursively partition in subarrays until the whole array is sorted
      q = partition(A, p, r);
      quickSort(A, p, q - 1);
      quickSort(A, q + 1, r);
    }
    return A;
  }
  
  public static int partition(ArrayList<Double> A, int p, int r){
    //rearranges subarray A[p...r] and returns a pivot index
    double x = A.get(r); //stores the last value of the array
    int i = p - 1; //starting pivot location at A[0]
    for(int j = p; j < r - 1; j++){
      //compares from A[1] until the end of the array
      if(A.get(j) <= x){
        /*if A[j] is less than or equal to the last value of the array,
         * the index of the current pivot is incremented
         * and the value at that index becomes the value at A[j]
         */
        i++;
        A.set(i, A.get(j));
      }
    }
    A.set(i + 1, A.get(r));
    return i + 1;
  }
  
  public static void bucketSort(ArrayList<Double> A){ //sorts array into "buckets"
    int n = A.size(); //stores size of original
    ArrayList<ArrayList<Double>> B = new ArrayList<ArrayList<Double>>(1);
    //bucket ArrayList - stores <Double> ArrayLists
    String c = ""; //string that will concat all buckets
    for(int i = 0; i < n; i++){ //init. B[] as an empty array
      ArrayList<Double> temp = new ArrayList<Double>(n);
      //temp list creates placeholder for buckets
      B.add(temp);
    }
    
    int t; //stores hash value
    for(int i = 0; i < n; i++){
      //takes doubles and places them in corresponding buckets
      t = (int)Math.floor(n * A.get(i));
      if(B.get(t).size() == 1) //if B is "empty" set the first index
        B.get(t).set(0, A.get(i));
      else //if B is not empty, add to bucket
        B.get(t).add(A.get(i));
    }
    
    for(int i = 0; i < n - 1; i++){
      //quicksorts buckets
      if(B.get(i).size() > 1) //skips empty bucket slots
        B.set(i, quickSort(B.get(i), 0, B.get(i).size() - 1));
    }
    
    for(int i = 0; i < n; i++){ //concats buckets
      for(int j = 0; j < B.get(i).size(); j++){ //inner bucket loop
        c += B.get(i).get(j) + " ";
      }
    }
    //System.out.println("Concat = " + c);
  }
  
  /*public static ArrayList<Double> countingSort(ArrayList<Double> A){
    //sorts bucketlist with counting sort
    // A[] = buckets
    int k = 0; //max value of A
    int temp = 0;
    for(int i = 0; i < A.size(); i++){
      //finds max value of A[]
      temp = A.get(i).intValue();
      if(temp >= k)
        k = temp;
    }
    ArrayList<Integer> C = new ArrayList<Integer>(); //stores counts of each num in A[]
    ArrayList<Double> ret = new ArrayList<Double>(A.size()); //return array
    for(int i = 0; i < k; i++){ //init. C with 0
      C.add(0);
    }
    for(int j = 1; j < k; j++){
      C.set(A.get(j).intValue(), (int)A.get(j).intValue() + 1);
      //C[i] stores number of elements equal to i
    }
    for(int i = 1; i < k; i++){
      C.set(i, C.get(i) + C.get(i - 1));
      //stores number of elements less than or equal to i
    }
    for(int j = A.size(); j > 1; j--){
      ret.set(C.get(A.get(j).intValue()), A.get(j));
      C.set((A.get(j).intValue()), C.get((int)A.get(j).intValue() - 1));
    }
    return ret;
  } */
}