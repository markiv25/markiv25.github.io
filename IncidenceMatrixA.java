import java.util.*;
/**
 * 612 LBE01 IncidenceMatrix
 * Prof. Kang
 */
 
public class IncidenceMatrixA {
   //attributes
   private String[] myDocs;               //input docs
   private ArrayList<String> termList;    //dictionary
   private ArrayList<int[]> docLists;
   
   //Constructor
   public IncidenceMatrixA(String[] docs) {
     myDocs = docs;
     termList = new ArrayList<String>();
     docLists = new ArrayList<int[]>();
     
     for(int i=0;i<myDocs.length;i++) {
      String[] words = myDocs[i].split(" ");
      for(String word:words) {
         if(!termList.contains(word)) {
            termList.add(word);
            int[] docList = new int[myDocs.length];
            docList[i] = 1;
            docLists.add(docList);
         }
         else {
            int index = termList.indexOf(word);
            int[] docList = docLists.remove(index);
            docList[i] = 1;
            docLists.add(index, docList);
         }
      }
      
     }
     
   }
   
   public ArrayList<Integer> search(String query) {
           //complete this method
           return null;
   }
   
   public String toString() {
      String outputString = new String();
      for(int i=0;i<termList.size();i++) {
         outputString += String.format("%-15s", termList.get(i));
         int[] docList = docLists.get(i);
         for(int j=0;j<docList.length;j++) {
            outputString += docList[j] + "\t";
         }
         outputString += "\n";
      }
      return outputString;
   }
   
   public static void main(String[] args) {
      //a document collection: corpus
      String[] docs = {"text data warehousing over big data",
                       "dimensional data warehousing over big data",
                       "nlp before text mining",
                       "nlp before text classification"};
                       
      IncidenceMatrixA matrix = new IncidenceMatrixA(docs);
      System.out.println(matrix);
      
      if(args[0] != null) {
         System.out.println("Query: " + args[0]);
         ArrayList<Integer> result = matrix.search(args[0]);
         for(Integer i:result) {
            System.out.println(docs[i]);
         }
      }                 
   }
}

/* Output
 text           1	0	1	1	
 data           1	1	0	0	
 warehousing    1	1	0	0	
 over           1	1	0	0	
 big            1	1	0	0	
 dimensional    0	1	0	0	
 nlp            0	0	1	1	
 before         0	0	1	1	
 mining         0	0	1	0	
 classification 0	0	0	1	
 
 Query: text
 text data warehousing over big data
 nlp before text mining
 nlp before text classification
 
 */