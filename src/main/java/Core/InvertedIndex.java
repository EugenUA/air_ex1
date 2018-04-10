package Core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class InvertedIndex {
    private Corpus corpus;
    private Map<String, PostingList> postingListMap;
    private Map<String, Double> tfidfMap;

    public InvertedIndex(Corpus corpus) throws UnsupportedEncodingException, FileNotFoundException {
        this.corpus = corpus;
        Map<String, PostingList> dictionary = new HashMap<>();
        this.tfidfMap = new HashMap<>();
        PostingList postingList;
        PrintWriter writer = new PrintWriter("index.dat", "ISO-8859-1");
        for (Document document : corpus.getParsedDocuments()) {
            document.calculateTermFrequency();
            for (Term term : document.getTerms()) {
                final String word = term.getWord();
                if (!dictionary.containsKey(word)) {
                    dictionary.put(word, new PostingList(word));
                }
                postingList = dictionary.get(word);
                postingList.addPosting(term, document);


                // Step 1: Double capacity if Posting List is full
                if (postingList.isFull()) {
                    postingList.setCapacity(postingList.size() * 2);
                }

                postingListMap = dictionary;
                tfidfMap.put(document.getId(), calculateTfidf(document, term.getWord()));
            }
        }

        // Step 2: Write to disk
        for (String key : dictionary.keySet()) {
            writer.print(key + " ");
            for (Document document : dictionary.get(key).getDocuments()) {
                writer.print(document.getId() + " ");
                writer.print(" | " + tfidfMap.get(document.getId()) + " ");
            }
            writer.print('\n');
        }

        writer.close();
    }


    private double calculateTfidf(Document document, String term) {
        int termFrequency = document.getTermFrequency(term);
        if (termFrequency == 0) {
            return 0;
        }
        return getInverseDocumentFrequency(term) * document.getTermFrequency(term);
    }

    private double getInverseDocumentFrequency(String word) {
        double totalDocuments = corpus.size();
        double relevantDocuments = getRelevantDocuments(word);
        return Math.log((totalDocuments) / (1 + relevantDocuments));
    }

    private int getRelevantDocuments(String word) {
        if (!postingListMap.containsKey(word)) {
            return 0;
        }
        return postingListMap.get(word).getDocuments().size();
    }

    public double getTfidf(String docID) {
        Double tfidf = tfidfMap.get(docID);
        if (tfidf == null) {
            return 0;
        }
        return tfidf;
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