package Core;

public class TfIdf {
    public final double score(int termFrequency, double numberOfDocuments, double relevantDocuments) {
       double inverseDocumentFrequency = Math.log((numberOfDocuments / (1 + relevantDocuments)));
       if (termFrequency == 0) {
        return 0;
        }
        return inverseDocumentFrequency * termFrequency;
    }
}

//    private double getInverseDocumentFrequency(String word) {
//        double totalDocuments = corpus.size();
//        double relevantDocuments = getRelevantDocuments(word);
//        return Math.log((totalDocuments) / (1 + relevantDocuments));
//    }
//
//    private int getRelevantDocuments(String word) {
//        if (!postingListMap.containsKey(word)) {
//            return 0;
//        }
//        return postingListMap.get(word).getDocIdtoIntMap().size();
//    }