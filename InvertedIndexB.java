import java.util.*;
/**
 * 612 LBE02 InvertedIndexA
 * 612 LBE01 IncidenceMatrixA
 * Prof. Kang
 */
 
//public class IncidenceMatrixB {
public class InvertedIndexB {
   //attributes
   private String[] myDocs;               //input docs
   private ArrayList<String> termList;    //dictionary
   //private ArrayList<int[]> docLists;
   private ArrayList<ArrayList<Integer>> docLists;
   
   //Constructor
   //public IncidenceMatrixB(String[] docs) {
   public InvertedIndexB(String[] docs) {
     myDocs = docs;
     termList = new ArrayList<String>();
     ArrayList<Integer> docList;
     //docLists = new ArrayList<int[]>();
     docLists = new ArrayList<ArrayList<Integer>>();

     
     for(int i=0;i<myDocs.length;i++) {
      String[] words = myDocs[i].split(" ");
      for(String word:words) {
         if(!termList.contains(word)) {
            termList.add(word);
            //int[] docList = new int[myDocs.length];
            docList = new ArrayList<Integer>();
            
            //docList[i] = 1;
            docList.add(i);
            docLists.add(docList);
         }
         else {
            int index = termList.indexOf(word);
            //int[] docList = docLists.remove(index);
            docList = docLists.get(index);
            //docList[i] = 1;
            if(!docList.contains(i)) {
               docList.add(i);
               docLists.set(index, docList);
            }
            
         }
      }
      
     }
     
   }
   
   public ArrayList<Integer> search(String query) {
           //complete this method
           /*
           ArrayList<Integer> result = new ArrayList<Integer>();
           int index = termList.indexOf(query);
           if(index >= 0) {
               int[] docList = docLists.get(index);
               for(int i= 0;i<docList.length;i++)  {
                  if(docList[i] !=0) {
                     if(!result.contains(i))
                        result.add(i);
                  }
               }
           }
           return result; */
           //2/5/21
           int index = termList.indexOf(query);
           if(index >= 0) {
               return docLists.get(index);
            
           }
           else return null;
   }
   
   //2/5/21
   // search(t1, t2, t3)
   public ArrayList<Integer> search(String[] query) {
      ArrayList<Integer> result = search(query[0]);
      int termId = 1;
      while(termId < query.length) {
         ArrayList<Integer> result1 = search(query[termId]);
         result = merge(result, result1);
         termId++;
      } 
      return result;
   }
   
   //2/5/21
   public ArrayList<Integer> merge(ArrayList<Integer> a1, ArrayList<Integer> a2) {
      ArrayList<Integer> mergeList = new ArrayList<Integer>();
      int p1=0, p2=0;
      while(p1<a1.size() && p2<a2.size()) {
         if(a1.get(p1).intValue() == a2.get(p2).intValue()) {
            mergeList.add(a1.get(p1));
            p1++;
            p2++;
         }
         else if(a1.get(p1).intValue() < a2.get(p2).intValue()) p1++;        
         else p2++;
      }
      return mergeList;
   }
   
   public String toString() {
      String outputString = new String();
      for(int i=0;i<termList.size();i++) {
         outputString += String.format("%-15s", termList.get(i));
         //int[] docList = docLists.get(i);
         ArrayList<Integer> docList = docLists.get(i);
         //for(int j=0;j<docList.length;j++) {
         for(int j=0;j<docList.size();j++) {

            //outputString += docList[j] + "\t";
            outputString += docList.get(j) + "\t";
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
                       
      //IncidenceMatrixB matrix = new IncidenceMatrixB(docs);
      InvertedIndexB matrix = new InvertedIndexB(docs);

      System.out.println(matrix);
      
      //2/5/21
      //if(args[0] != null) {
      if(args.length == 1) {
         System.out.println("Query: " + args[0]);
         ArrayList<Integer> result = matrix.search(args[0]);
         for(Integer i:result) {
            System.out.println(docs[i]);
         }
      }
      else if(args.length >1) {
         String queryTerms = new String();
         for(String q:args) {
            queryTerms += " " + q;
         }
         System.out.println("Query for: " + queryTerms + "\n");
         ArrayList<Integer> result = matrix.search(args);
         
         if(result != null) {
            for(Integer i:result) {
               System.out.println(docs[i.intValue()]);
            }
         }
         else System.out.println("No Match");
      }
                      
   }
}

/* OutputIncidence
    text           0	2	3	
 data           0	1	
 warehousing    0	1	
 over           0	1	
 big            0	1	
 dimensional    1	
 nlp            2	3	
 before         2	3	
 mining         2	
 classification 3	
 
 */