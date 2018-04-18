package Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
    private List<String> terms;
    private String id;
    private String rawText;
    private Map<String, Integer> termFrequencyMap;

    public Document(String id) {
        this.id = id;
        this.termFrequencyMap = new HashMap<>();
    }

    public void calculateTermFrequency() {
        for (String term : terms) {
            if (!termFrequencyMap.containsKey(term)) {
                termFrequencyMap.put(term, 0);
            }
            int frequency = termFrequencyMap.get(term);
            termFrequencyMap.put(term, frequency + 1);
        }
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String getRawText() {
        return rawText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
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