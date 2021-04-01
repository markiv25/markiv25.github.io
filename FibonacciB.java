import java.util.*;
/**
 * 612 LBE06 Dynamic Programming Example
 * Prof Kang
 * This program computes Fibonacci numbers using Iterative, Recursive & 
 * Dynamic Programming methods
 */

public class FibonacciB {
   //#3
   private ArrayList<Integer> fibCache = new ArrayList<Integer>();
   public FibonacciB() {
      fibCache.add(0);
      fibCache.add(1);
   }
   
/**
 * Compute a Fibonacci number using Iterative method //[0,1,1,2,3, 5, 8]
 */
   public int iterativeFib(int n) {
      //#1
      if(n <=1) return n;
      
      int previousValue =0;
      int currentValue = 1;
      int newValue = 1;
      
      for(int i=2;i<=n;i++) {
         newValue = previousValue + currentValue;
         previousValue =  currentValue;
         currentValue = newValue;
      }
           
      return newValue;
   }
   
/**
 * Compute a Fibonacci number using Recursive method
 */
   public int recursiveFib(int n) {
      //#2
      if(n <= 1) return n;
      else return recursiveFib(n-1) + recursiveFib(n-2);
      //return 0;
   }
   
/**
 * Compute a Fibonacci number using Dynamic Programming method [
 */
   public int dynamicFib(int n) {
      //#4
      if(n >= fibCache.size()) {
        fibCache.add(n,dynamicFib(n-1) + dynamicFib(n-2));  
      }
      return fibCache.get(n);
   }

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter n: ");
      int n = in.nextInt();
      
      FibonacciB f = new FibonacciB();
      for(int i=0;i<=n;i++) {
         //int fib = f.iterativeFib(i);
         //int fib = f.recursiveFib(i);
         int fib = f.dynamicFib(i);

         System.out.println("fib(" + i + ")= " + fib);
      }
   }
}