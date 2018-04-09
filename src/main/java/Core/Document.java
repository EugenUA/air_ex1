package Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    private List<Term> terms;
    private String rawText;
    private String id;
    private Map<String, Integer> termFrequencyMap;

    public Document(String id, String rawText) {
        this.id = id;
        this.rawText = rawText;
        this.termFrequencyMap = new HashMap<>();
    }

    public void calculateTermFrequency() {
        for (Term t : terms) {
            String word = t.getWord();
            if (!termFrequencyMap.containsKey(word)) {
                termFrequencyMap.put(word, 0);
            }
            int frequency = termFrequencyMap.get(word);
            termFrequencyMap.put(word, frequency + 1);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public boolean isEmpty() {
        return terms == null || terms.isEmpty();
    }

    public int getTermFrequency(String word) {
        if (!termFrequencyMap.containsKey(word)) {
            return 0;
        }
        return termFrequencyMap.get(word);
    }
}