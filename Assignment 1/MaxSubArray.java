/*************************************************************************
 *
 *  Pace University
 *  Spring 2020
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Czarina Manipon
 *  Collaborators: None
 *  References: https://stackoverflow.com/questions/12952024/how-to-implement-infinity-in-java
 *              https://stackoverflow.com/questions/27976857/how-to-get-random-number-with-negative-number-in-range
 *              Introduction to Algorithms 3rd Edition
 *
 *  Assignment: #1
 *  Problem: Implement both a brute force array and a divide-and-conquer
 *  algorithm to find the max subarray sum in an array of numbers and analyze
 *  the run times of both.
 *  Description: Max SubArray Class that implements two different algorithims
 *  that each find the contigious subarray within an array of numbers that has
 *  the largest sum.
 *
 *  Input: 13 176 1405 13454 143255
 *  Output:
 * 
 *  Enter the size of the array: [13]
 *  Brute Force Max Subarray Sum: 245
 *  Algorithm 1 Run Time: 3626660 
 *  Divide and Conquer Max Subarry Sum: 245
 *  Algorithm 2 Run Time: 2272744
 *  Kadane: 245
 *  Kadane Run Time: 3033445 
 * 
 *  Enter the size of the array: [176]
 *  Brute Force Max Subarray Sum: 1051 
 *  Algorithm 1 Run Time: 8575422
 *  Divide and Conquer Max Subarry Sum: 1051
 *  Algorithm 2 Run Time: 2434616
 *  Kadane: 1051
 *  Kadane Run Time: 1874149 
 * 
 *  Enter the size of the array: [1405]
 *  Brute Force Max Subarray Sum: 2861 
 *  Algorithm 1 Run Time: 9349222
 *  Divide and Conquer Max Subarry Sum: 2861
 *  Algorithm 2 Run Time: 7097063 
 *  Kadane: 2861
 *  Kadane Run Time: 4145021 
 * 
 *  Enter the size of the array: [13454]
 *  Brute Force Max Subarray Sum: 9103 
 *  Algorithm 1 Run Time: 250401402
 *  Divide and Conquer Max Subarry Sum: 9103
 *  Algorithm 2 Run Time: 5896598 
 *  Kadane: 9103
 *  Kadane Run Time: 5135897 
 *  
 *  Enter the size of the array: [143255]
 * Brute Force Max Subarray Sum: 9093 
 * Algorithm 1 Run Time: 34860892892 
 * Divide and Conquer Max Subarry Sum: 9093 
 * Algorithm 2 Run Time: 73772025 
 * Kadane: 9093 
 * Kadane Run Time: 4961862 
 * 
 *
 *  Visible data fields:
 *  None
 *
 *  Visible methods:
 *  public static void main(String args[])
 *  public static int bruteForce(int[] A)
 *  public static int divideAndConquer(int[] A)
 *  public static int max(int A, int B)
 *  public static int max(int A, int B, int C)
 *  public static int maxSubarray(int[] A, int low, int high)
 *  public static int maxCrossingSubarray(int[] A, int low, int mid, int high)
 *
 *
 *   Remarks
 *   -------
 *
 *   PUT ALL NON-CODING ANSWERS HERE
 *
 *   2. 
 *                        n = 10   n = 10^2   n = 10^3   n = 10^4   n = 10^5
 *   brute force          3626660  9902204    9349222   250401402  34860892892
 *   divide and conquer   2272744  4039291    7097063    5896598    73772025
 *   kadane               3033445  1874149    4145021    5135897    4961862
 * 
 *  3. With every larger input n, brute force's run time increases
 *     exponentially. By n = 10^4, the run time grows by another 10th with
 *     every larger input n. By n = 10^5, the run time at about 34 million
 *     nano seconds has grown 11 times larger than the 3 million for n = 10.
 *     As for divide and conquer, the run time hardly grows beyond its
 *     initial run time with n = 10, as per O(nlogn). The run time of
 *     n = 10^5 at about 16 million nano sec is only 8 times bigger
 *     than the 2 million nano secs it took for divide and conquer to run
 *     input n = 10.
 *     
 *************************************************************************/
import java.util.Scanner;
import java.util.Random;
public class MaxSubArray{
  public static void main(String[] args){
    //creates array and calls both brute force and divide and conquer
    //algorithms to find max subarry sums
    Scanner scan = new Scanner(System.in); //scanner for user input n
    int n; //stores user inputed value that determines the size of the arraay
    
    System.out.print("Enter the size of the array:");
    n = scan.nextInt();
    
    int[] arr = new int[n]; //creates array of integers with size n
    Random rand = new Random(); //random generator to create both positive and negative numbers for array
    for(int i = 0; i < n; i++){ //fills array with random integers from (-100, 100)
      arr[i] = rand.nextInt(100 + 100) - 100;
    }
    System.out.println();
    
    long startTime = System.nanoTime(); //saves the start time of the method call
    System.out.println("Brute Force Max Subarray Sum: " + bruteForce(arr));
    System.out.println("Algorithm 1 Run Time: " + (System.nanoTime() - startTime));
    
    startTime = System.nanoTime();
    System.out.println("Divide and Conquer Max Subarry Sum: " + divideAndConquer(arr));
    System.out.println("Algorithm 2 Run Time: " + (System.nanoTime() - startTime));
    
    startTime = System.nanoTime();
    System.out.println("Kadane: " + kadane(arr));
    System.out.println("Kadane Run Time: " + (System.nanoTime() - startTime));
  }
  
  public static int bruteForce(int[] A){ //implements algorithm 1: brute force, O(n^2) runtime
    int sum; //stores the temporary max sum of integers from A[j] to A[n]
    int maxSum = Integer.MIN_VALUE; //stores the final max sum as the for loop iterates through the array
    for(int i = 0; i < A.length; i++){ //iterates over each integer in the array
      sum = 0;
      for(int j = i; j < A.length; j++){ //for each integer in the array, the sum of integers within the range A[j - n]
       sum = sum + A[j];
       maxSum = max(maxSum, sum);
      }
    }
    return maxSum;
  }
  
  public static int divideAndConquer(int[] A){ //implements algorithm 2: divide and conquer, O(nlogn) run time
    int low = 0; //stores the beginning index of the array
    int high = A.length - 1; //stores the ending index of the array
    return maxSubarray(A, low, high); 
  }
  
  public static int max(int a, int b){ //determines the max of two integers
    if(a >= b)
      return a;
    else
      return b;
  }
  
   public static int max(int a, int b, int c){ //returns the max of three integers
     if((a >= b) && (a >= c)){
       return a;
     }
     else if((b >= a) && (b >= c)){
      return b;
     }
     else {
       return c;
     }
  }
  
  public static int maxSubarray(int[] A, int low, int high){ //divides the array into two smaller subarrays, recursively finds the max sum of the subarrays, then compares them to find the final max sum
    if (A[high] == A[low]){ //base case
      return A[low];
    }
    else{
      int mid = (low + high) / 2; //stores middle value where the array will be split
      int leftSum = maxSubarray(A, low, mid); //stores the max sum of the left side of the array found by recursion
      int rightSum = maxSubarray(A, mid + 1, high); //stores the the max sum of the right side of the array found by recursion
      int crossSum = maxCrossingSubarray(A, low, mid, high); //stores the max sum of the array that crosses both the left and right subarrays in the center
      return max(leftSum, rightSum, crossSum);
    }
  }
  
  public static int maxCrossingSubarray(int[] A, int low, int mid, int high){ //finds the max sum of the subarray crossing the left and right subarrays in divide and conquer
    int leftSum = Integer.MIN_VALUE; //initializes the max sum of the left subarray to negative infinity
    int sum = 0; //stores the to be determined max sum of the left array
    for(int i = mid; i >= low; i--){ //from the middle value to the lowest value, determines a new sum and compares with the last sum to determine the final max sum of the left subarray
      sum = sum + A[i];
      if(sum > leftSum){ //if the sum from A[i] to the lowest value is greater than the current max sum, it becomes the new max sum of the left subarray
        leftSum = sum;
      }
    }
    int rightSum = Integer.MIN_VALUE; //initializes the max sum of the right subarray to negative infinity
    sum = 0;
    for(int j = (mid + 1); j <= high; j++){ //from the middle value to the highest value, determines a new sum and compares with the last sum to determine the final max sum of the right subarray
      sum = sum + A[j];
      if(sum > rightSum){ //if the sum from A[j] to the highest value is greater than the current max sum, it becomes the new max sum of the right subarray
        rightSum = sum;
      }
    }
    return leftSum + rightSum;
  }
  
  //extra credit
  //kadane's algorithm pseudocode from https://algorithmist.com/wiki/Kadane%27s_algorithm
  public static int kadane(int[] A){
    int maxSum = Integer.MIN_VALUE; //stores final max sum of a subarray in A[], init. at -infinity
    int maxStartIndex = 0; //stores the starting index of the max sub array
    int maxEndIndex = 0; //stores the ending index of the max sub array
    int currentMaxSum = 0; //stores a possible max sum value to compare with the last recorded max sum
    int currentStartIndex = 1; //marks the starting index of each subarray tested, constantly updates
    int n = A.length; //stores size of input n aka size of array A
    for(int currentEndIndex = 1; currentEndIndex < n; currentEndIndex++){
      //runs through entire length n of the array
      //creates a subarray that ends at each possible index
      //in A. Starting index of subarray changes according
      //to where the max sum if found on each iteration
      currentMaxSum = currentMaxSum + A[currentEndIndex];
      //updates Max sum to include the integer stored at the
      //current end index of the subarray being reviewed
      if(currentMaxSum > maxSum){
        //if the new found max sum is greater than the previous
        //found max sum, update accordingly
        maxSum = currentMaxSum;
        maxStartIndex = currentStartIndex;
        //starting index of the current max sub array is updated
        maxEndIndex = currentEndIndex;
        //ending index of the current max sub array is updated
      }
      if(currentMaxSum < 0){
        //if the current sum of integers in the current sub array
        //is negative, set current max sum to 0 instead. A negative
        //max sum means the subarray most likely contains negative
        //integers and most likely won't contain the max sum at all
        currentMaxSum = 0;
        currentStartIndex = currentEndIndex + 1;
        //update start index to restart a new subarray entirely
      }
    }
    return maxSum;
  }
}