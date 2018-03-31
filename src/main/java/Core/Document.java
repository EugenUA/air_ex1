package Core;

import java.util.List;

public class Document {
    private List<Term> terms;
    private int id;

    Document(int id, List<Term> terms) {
        this.id = id;
        this.terms = terms;
    }

    public int getId() {
        return id;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public boolean isEmpty() {
        return terms == null || terms.isEmpty();
    }
}