import java.util.*;
/**
 * 612-2205 LBE07 Vector Space Model
 * Prof Kang
 */
 
public class VSMa {       
   //attributes
   private String[] myDocs;
   private ArrayList<String> termList;
   private ArrayList<ArrayList<Doc>> docLists;
   private double[] docLength;                     //for normalization
   
   // constructor
   public VSMa(String[] docs) {         
      //instanciate the attributes
      myDocs = docs;
      termList = new ArrayList<String>();
      
      docLists = new ArrayList<ArrayList<Doc>>();
      ArrayList<Doc> docList;
      for(int i=0;i<myDocs.length;i++) {
         String[] words = myDocs[i].split(" ");
         String word;
         
         for(int j=0;j<words.length;j++) {
            boolean match = false;
            word = words[j];
            if(!termList.contains(word)) {
               termList.add(word);
               docList = new ArrayList<Doc>();
               // #2
               Doc doc = new Doc(i, j); // raw term freq is one
               docList.add(doc);
               docLists.add(docList);
            }
            else {
               int index = termList.indexOf(word);
               docList = docLists.get(index);
               
               for(Doc did:docList) {
                  if(did.docId == i) {
                     //increment the raw tf
                     // #3
                     did.tw++;
                     match = true;
                     break;
                  }
               
               }
               if(!match) {
                  Doc doc = new Doc(i,1);
                  docList.add(doc);
               }
            }
           }
      
         }
         //compute tf.idf
         //To be completed
         // #4
         int N = myDocs.length;
         docLength = new double[N];
         
         for(int i= 0;i<termList.size();i++) {
            docList = docLists.get(i);
            int df = docList.size();
            Doc doc;
            for(int j=0;j<docList.size();j++) {
               doc = docList.get(j);
               double tfidf = (1 + Math.log10(doc.tw)) * (Math.log10(N * 1.0/df));
               docLength[doc.docId] += Math.pow(tfidf,2);
               doc.tw = tfidf;
               docList.set(j,doc);
                        
            }
         }         
         //update the doc length
         //To be completed
         // #5
         for(int i=0;i<N;i++) {
            docLength[i] = Math.sqrt(docLength[i]);
         }
   }
   

   public void rankSearch(String[] query) {
     //To be completed
     // #6
     
     HashMap<Integer, Double> docs = new HashMap<Integer, Double>();
     ArrayList<Doc> docList;
     for(String term: query) {
      int index = termList.indexOf(term);
      if(index < 0) continue;
      docList = docLists.get(index);
        double qtfidf = (1 + Math.log10(1)) * (Math.log10(myDocs.length * 1.0/docList.size()));
        
        Doc doc;
        for(int i=0;i<docList.size();i++) {
         doc = docList.get(i);
         double score = doc.tw * qtfidf;
         
         if(!docs.containsKey(doc.docId)) {
            docs.put(doc.docId, score);
         }
         else {
            score += docs.get(doc.docId);
            docs.put(doc.docId, score);
         }
        }
     }
     
      System.out.println(docs);
   }
   public String toString() {
      String outString = new String();
      ArrayList<Doc> docList;       
      for(int i=0;i<termList.size();i++) {
         outString += String.format("%-15s", termList.get(i));
         docList = docLists.get(i);
         for(int j=0;j<docList.size();j++) {
            outString += docList.get(j) + "\t";
         }
         outString += "\n";
      }
      return outString;
   }
   
   public static void main(String[] args) {
      String[] docs = {"text warehousing over big data",
                       "dimensional data warehouse over big data",
                       "nlp before text mining",
                       "nlp before text classification"};
      // Test VSM      
   }
}

class Doc {
   //To be completed
   int docId;
   double tw;
   
   public Doc(int did, double tw) {
      docId = did;
      this.tw = tw;
   }
   
   public String toString() {
      return docId + ": " + tw;
   }
}
