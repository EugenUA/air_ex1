package Core;

import java.util.List;

public class Corpus {
    private List<Document> parsedDocuments;

    public Corpus(List<Document> documentList) {
        if (documentList != null && !documentList.isEmpty()) {
            this.parsedDocuments = documentList;
        }

    }

    public List<Document> getParsedDocuments() {
        return parsedDocuments;
    }

    public int size() {
        return parsedDocuments.size();
    }
}