package Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PostingList {
    private String term;
    private List<Posting> postings;
    private HashSet<Document> documents;
    private int capacity = 27;

    public PostingList(String term) {
        this.term = term;
        postings = new ArrayList<>();
        documents = new HashSet<>();
    }

    public void addPosting(Term term, Document document) {
        postings.add(new Posting(term, document));
        documents.add(document);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int size() {
        return postings.size();
    }

    public String getTerm() {
        return term;
    }

    public HashSet<Document> getDocuments() {
        return documents;
    }

    public List<Posting> getPostings() {
        return postings;
    }

    public boolean isFull() {
        return size() == capacity;
    }
}