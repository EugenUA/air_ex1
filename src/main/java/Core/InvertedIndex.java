package Core;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    private List<Document> corpus;
    private Map<String, Map<Integer, Integer>> dictionary;
    public InvertedIndex(List<Document> corpus) throws Exception {
        this.corpus = corpus;
        dictionary = new HashMap<>();
        Map<Integer, Integer> postingList;
        PrintWriter writer = new PrintWriter("index.dat", "ISO-8859-1");
        int i = 0;
        for (Document document : corpus) {
            document.calculateTermFrequency();
            for (String term : document.getTerms()) {
                if (!dictionary.containsKey(term)) {
                    dictionary.put(term, new HashMap<>());
                }
                postingList = dictionary.get(term);
                postingList.put(i, document.getTermFrequency(term));
            }
            i++;
        }

        // Step 2: Write to disk
        for (String key : dictionary.keySet()) {
            writer.print(key + ": ");
            for (int docId : dictionary.get(key).keySet()) {
                writer.print(docId + " ");
                writer.print(" " + dictionary.get(key).get(docId) + "| ");
            }
            writer.print('\n');
        }
        writer.close();

        writeDocumentList();
    }

    public int size() {
        return corpus.size();
    }

    public int numberOfTerms() {
        return dictionary.keySet().size();
    }

    public Map<String, Map<Integer, Integer>> getDictionary() {
        return dictionary;
    }

    private void writeDocumentList() throws Exception {
        PrintWriter writer = new PrintWriter("documentList.dat");
        int i=0;
        for (Document document : corpus) {
            writer.print(document.getId() + ": " + i + ": " + document.getRawText().length());
            writer.print('\n');
            i++;
        }
        writer.close();
    }
}