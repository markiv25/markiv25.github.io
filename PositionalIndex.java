import java.util.ArrayList;
/**
 * ISTE-612-2205 Lab #2
 * Tuheena
 * 11-03-2021
 */

public class PositionalIndex {
    String[] myDocs;
    ArrayList < String > termDictionary;
    ArrayList < ArrayList < Doc >> docLists;

    /**
     * Construct a positional index 
     * @param docs List of input strings or file names
     * 
     */
    public PositionalIndex(String[] docs) {
        //TASK1: TO BE COMPLETED

        myDocs = docs;
        termDictionary = new ArrayList < String > ();
        ArrayList < Doc > docList;
        docLists = new ArrayList < ArrayList < Doc >> ();

        for (int i = 0; i < myDocs.length; i++) {
            String words[] = myDocs[i].split(" ");
            //for(String i:words){
            //System.out.println(","+words);}
            String word;
            for (int j = 0; j < words.length; j++) {
                boolean match = false;
                word = words[j];
                if (!termDictionary.contains(word)) {
                    termDictionary.add(word);
                    docList = new ArrayList < Doc > ();
                    Doc doc = new Doc(i, j);
                    docList.add(doc); // System.out.println("Document List :" +docList);
                    docLists.add(docList); //System.out.println("Domument Lists :" + docLists);
                } else {
                    int index = termDictionary.indexOf(word);
                    docList = docLists.get(index);
                    int k = 0;
                    for (Doc did: docList) {
                        if (did.docId == i) {
                            did.insertPosition(j);
                            docList.set(k, did);
                            match = true;
                            break;
                        }
                        k++;
                    }
                    if (!match) {
                        Doc doc = new Doc(i, j);
                        docList.add(doc);
                    }
                }
            }
        }


    }

    /**
     * Return string representation of a positional index
     */
    public String toString() {
        String matrixString = new String();
        ArrayList < Doc > docList;
        for (int i = 0; i < termDictionary.size(); i++) {
            matrixString += String.format("%-15s", termDictionary.get(i));
            docList = docLists.get(i);
            for (int j = 0; j < docList.size(); j++) {
                matrixString += docList.get(j) + "\t";
            }
            matrixString += "\n";
        }
        return matrixString;
    }

    /**
     * 
     * @param post1 first postings
     * @param post2 second postings
     * @return merged result of two postings
     */
    public ArrayList < Doc > intersect(ArrayList < Doc > aL1, ArrayList < Doc > aL2, int xy) {
        //TASK2: TO BE COMPLETED
        ArrayList < Doc > intersecList = new ArrayList < Doc > ();
        int pL1 = 0, pL2 = 0;
        while (pL1 < aL1.size() && pL2 < aL2.size()) {
            if (aL1.get(pL1).docId == aL2.get(pL2).docId) {
                ArrayList < Integer > ppL1 = aL1.get(pL1).positionList;
                ArrayList < Integer > ppL2 = aL2.get(pL2).positionList;

                int posL1 = 0;
                while (posL1 < ppL1.size()) {
                    int posL2 = 0;
                    while (posL2 < ppL2.size()) {
                        if (ppL1.get(posL1) - ppL2.get(posL2) == xy) {
                            boolean search = false;
                            int x = 0;
                            for (Doc D: intersecList) {
                                if (D.docId == aL1.get(pL1).docId) {
                                    Doc ter = intersecList.get(x);
                                    ppL1.get(posL1);
                                    ter.insertPosition(ppL1.get(posL1));
                                    intersecList.set(x, ter);
                                    search = true;
                                    break;
                                }
                                x++;
                            }
                            if (!search) {
                                Doc doc = new Doc(aL1.get(pL1).docId, ppL1.get(posL1));
                                intersecList.add(doc);
                            }
                        }
                        posL2++;
                    }
                    posL1++;
                }
                pL1++;
                pL2++;
            } else if (aL1.get(pL1).docId < aL2.get(pL2).docId)
                pL1++;

            else
                pL2++;
        }
        return intersecList;
    }


    /**
     * 
     * @param query a phrase query that consists of any number of terms in the sequential order
     * @return docIds of documents that contain the phrase
     */
    public ArrayList < Doc > phraseQuery(String[] query) {
        //TASK3: TO BE COMPLETED

        String phrase = query[0];
        ArrayList < Doc > pika = new ArrayList < Doc > ();
        ArrayList < Doc > aL1, aL2;
        int pq = termDictionary.indexOf(phrase);
        if (pq != -1) {
            aL1 = docLists.get(pq);
            int xy = -1;
            //System.out.println("Next Word:" + query[1]);
            for (int rt = 1; rt < query.length; rt++) {
                int pq2 = termDictionary.indexOf(query[rt]);
                if (pq2 == -1) {
                    return pika;
                }
                aL2 = docLists.get(pq2);
                aL1 = intersect(aL1, aL2, xy);
                //System.out.println(" Terms :" + aL1);
                --xy;
            }
            return aL1;
        } else {
            return pika;
        }
    }


    public void hunt(String searchStr) {
        ArrayList < Doc > res = this.phraseQuery(searchStr.split(" "));
        //System.out.println("Find the String : " + looking);
        System.out.print(searchStr + "\t");
        if (res.size() == 0) {
            System.out.print(" Phrase Not found");
        }
        for (Doc doc: res) {
            System.out.print(doc + "\t");

        }
        System.out.println();

    }




    public static void main(String[] args) {
        String[] docs = {
            "text warehousing over big data",
            "dimensional data warehouse over big data",
            "nlp after text mining",
            "nlp after text classification"
        };

        PositionalIndex pi = new PositionalIndex(docs);
        System.out.print(pi);
        //TASK4: TO BE COMPLETED: design and test phrase queries with 2-5 terms


        System.out.println("\n Query Terms \n");
        pi.hunt("Kebab");
        pi.hunt("big data");


        pi.hunt("nlp after text classification");
        pi.hunt("data warehouse over big data");

    }

}

/**
 * 
 * Document class that contains the document id and the position list
 */
class Doc {
    int docId;
    ArrayList < Integer > positionList;
    public Doc(int did) {
        docId = did;
        positionList = new ArrayList < Integer > ();
    }
    public Doc(int did, int position) {
        docId = did;
        positionList = new ArrayList < Integer > ();
        positionList.add(new Integer(position));
    }

    public void insertPosition(int position) {
        positionList.add(new Integer(position));
    }

    public String toString() {
        String docIdString = "" + docId + ":<";
        for (Integer pos: positionList)
            docIdString += pos + ",";
        docIdString = docIdString.substring(0, docIdString.length() - 1) + ">";
        return docIdString;
    }
}