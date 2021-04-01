import java.util.*;
/**
 * 612 PBE06 Binary Search Tree
 * Prof Kang
 */
 
class Node {
   Node left;
   Node right;
   int value;
   public Node(int value) {
      this.value =value; 
   }
}

public class BSTa {
   private Node root;
   
   public BSTa() {}
   
   public BSTa(int[] keys) {
      //#5
      Arrays.sort(keys);
      int start = 0;
      int end = keys.length -1;
      int mid = (start + end) /2;
      
      Node r = new Node(keys[mid]);
      root = r;
      add(r, keys, start, mid-1);
      add(r, keys, mid+1, end);
   }
   
   //#4
   public void add(Node n, int[] A, int start, int end) {
      if(start <= end) {
         int mid = (start + end) /2;
         if(A[mid] < n.value) {
            n.left = new Node(A[mid]);
            add(n.left, A, start, mid-1);
            add(n.left, A, mid+1, end);
         }
         else {
            n.right = new Node(A[mid]);
            add(n.right, A, start, mid-1);
            add(n.right, A, mid+1, end);

         }
      }
   }
   
   public void insert(Node node, int value) {
      //To be Completed
      //#1
      if(value < node.value){
         if(node.left != null)insert(node.left, value);
         else {
            node.left = new Node(value);
            System.out.println("Inserted " + value + " to left node " + node.value);
         }
         
      }
      else if(value > node.value) {
         if(node.right != null)insert(node.right, value);
         else {
            node.right = new Node(value);
            System.out.println("Inserted " + value + " to right node " + node.value);
         }
      }
   }
   
   public void printInOrder(Node node) {
      if(node != null) {
         printInOrder(node.left);
         System.out.print(node.value + " ");
         printInOrder(node.right);
      }
   }
   public Node search(Node n, int key) {
      //To be completed
      //#2
      if(n == null) return null;
      if(n.value == key) return n;
      else if(key < n.value) return search(n.left, key);
      else return search(n.right, key);
      //return null;
   }
   
   public static void main(String[] args) {
   
      BSTa bst = new BSTa();
      Node rootnode = new Node(35);
      bst.insert(rootnode, 20);
      bst.insert(rootnode, 85);
      bst.insert(rootnode, 30);
      bst.insert(rootnode, 45);
      
      System.out.println("traversed");
      bst.printInOrder(rootnode);
      
      //#6
      int[] values = {35,20,85,30,45};
      bst = new BSTa(values);
      System.out.println("\n Traversed");
      bst.printInOrder(bst.root);
      
      Node n = bst.search(bst.root, 85);
      
      if(n != null) System.out.println("\n" + n.value);
      else System.out.println("\n no match");

      
      
      /*
      Node n = bst.search(rootnode, 45);
      //Node n = bst.search(rootnode, 100);
      if(n != null) System.out.println("\n" + n.value);
      else System.out.println("\n no match");
   */
   }
}
/*
Inserted 20 to left node 35
 Inserted 85 to right node 35
 Inserted 30 to right node 20
 Inserted 45 to left node 85
 traversed
 20 30 35 45 85 
  Traversed
 20 30 35 45 85 
 85
*/