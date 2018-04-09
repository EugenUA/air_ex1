package Core;

import java.util.List;

public class Document {
    private List<Term> terms;
    private String rawText;
    private String id;

    public Document() {

    }

    public Document(String id, List<Term> terms) {
        this.id = id;
        this.terms = terms;
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
}