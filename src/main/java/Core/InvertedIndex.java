package Core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class InvertedIndex {
    private Corpus corpus;
    private Map<String, PostingList> postingListMap;

    public InvertedIndex(Corpus corpus) throws UnsupportedEncodingException, FileNotFoundException {
        this.corpus = corpus;
        Map<String, PostingList> dictionary = new HashMap<>();
        TreeSet sortedSet = new TreeSet();
        PostingList postingList;
        PrintWriter writer = new PrintWriter("index.dat", "ISO-8859-1");
        for (Document document : corpus.getParsedDocuments()) {
            for (Term term : document.getTerms()) {
                final String word = term.getWord();
                if (!dictionary.containsKey(word)) {
                    dictionary.put(word, new PostingList(word));
                }
                postingList = dictionary.get(word);
                postingList.addPosting(term, document);
                //dictionary.get(word).addPosting(term, document);

                // Step 1: Double capacity if Posting List is full
                if (postingList.isFull()) {
                    postingList.setCapacity(postingList.size() * 2);
                }

                // Step 2: Sort dictionary
                sortedSet.addAll(dictionary.values());


                // Step 3: Write to disk
                writer.println(postingList.toString());
            }
        }
        postingListMap = dictionary;
    }

    public int size() {
        return corpus.size();
    }

    public int numberOfTerms() {
        return postingListMap.keySet().size();
    }

    public Map<String, PostingList> getPostingListMap() {
        return postingListMap;
    }
}