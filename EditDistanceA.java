/**
 * 612 LBE06 Edit Distance
 * Prof Kang
 */

public class EditDistanceA {
   public int editDistance(String x, String y) {
      //To be completed
      if(x.length() == 0) return y.length();
      else if(y.length() == 0) return x.length();
      else {
         int insertDistance = editDistance(x, y.substring(0, y.length()-1)) +1;
         int deleteDistance = editDistance(x.substring(0, x.length()-1),y) +1;
         int replaceDistance = editDistance(x.substring(0, x.length()-1),
             y.substring(0, y.length()-1)) +
             ((x.charAt(x.length()-1) == y.charAt(y.length()-1))?0:1);//conditional operator
      
         return Math.min(Math.min(insertDistance, deleteDistance), replaceDistance);
      }
   }
   public static void main(String[] args) {
      EditDistanceA ed = new EditDistanceA();
      
      System.out.println(ed.editDistance("zeil","trial"));
      System.out.println(ed.editDistance("cats","fast"));
      System.out.println(ed.editDistance("cats","cats"));
      System.out.println(ed.editDistance("cats","cat"));

   }
}
