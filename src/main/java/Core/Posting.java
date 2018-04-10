package Core;

public class Posting {
    private Term term;
    private Document document;

    public Posting(Term term, Document document) {
        this.term = term;
        this.document = document;
    }

    public Term getTerm() {
        return term;
    }

    public Document getDocument() {
        return document;
    }
}