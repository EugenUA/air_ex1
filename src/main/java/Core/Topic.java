package Core;

public class Topic {
    private String terms;
    private String id;

    public Topic() {
        id = null;
        terms = null;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTerms() {
        return terms;
    }

    public boolean isEmpty() {
        return terms == null || terms.isEmpty();
    }

}