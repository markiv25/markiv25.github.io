import java.io.*;
import java.util.*;
/**
 * ISTE-612 LBE03 Text processing
 * Prof Kang
 */

public class ParserB {
   private String[] myDocs;
      
   String[] stopWords = {"a","is","in","so","of", "at", "the","to","and","it","as",
         "be","are","on","into","if","it's"};
         
   public ParserB(String fileName) {
     
   }
   
   //#1
   public ParserB() {
     Arrays.sort(stopWords);
     
     String sw = new String();
     for(int i=0;i<stopWords.length;i++) {
      sw += stopWords[i] + " ";
     }
     System.out.println("Stop words: " + sw);
   }

   //#3
   //Binary search for a stop word
   public int searchStopWord(String key) {
      int lo = 0;
      int hi = stopWords.length -1;
      while(lo <= hi) {
         int mid = lo + (hi-lo)/2;
         int result = key.compareTo(stopWords[mid]);
         if (result < 0) hi = mid-1;
         else if(result > 0) lo = mid +1;
         else return mid;
      }
      return -1;
   }
   
   //Tokenization
   public ArrayList<String> parse(File fileName) throws IOException {
      //#5
      String[] tokens;
      ArrayList<String> pureTokens = new ArrayList<String>();
      ArrayList<String> stemms = new ArrayList<String>();
      
      Scanner scan = new Scanner(fileName);
      String allLines = new String();
      
      while(scan.hasNextLine()) {
         allLines += scan.nextLine().toLowerCase();    // case foldering
      }
      
      //Tokenization
      tokens = allLines.split("[ '.,?;:$%+()\\-\\*]+");
      //Remove stop words
      for(String token:tokens) {
         if(searchStopWord(token) == -1) {
            pureTokens.add(token);
         }
      }
      
      //Stemming
      Stemmer st = new Stemmer();
      for(String token:pureTokens) {
         st.add(token.toCharArray(), token.length());
         st.stem();
         stemms.add(st.toString());
         st = new Stemmer();
      }
      
      return stemms;
      //return null;
   }
   
   public static void main(String[] args) {
      //#2
      ParserB p = new ParserB();
      
      //#4
      System.out.println("Stop words: " + p.searchStopWord("are"));
      System.out.println("Stop words: " + p.searchStopWord("the"));
      System.out.println("Stop words: " + p.searchStopWord("aa"));
      
      //#6
      try {
         File file = new File("stemtest.txt");
         ArrayList<String> stemmed = p.parse(file);
         
         for(String st:stemmed) {
            System.out.println(st);
         }
      }
      catch(IOException ioe) {
         ioe.printStackTrace();
      }  
   }
}