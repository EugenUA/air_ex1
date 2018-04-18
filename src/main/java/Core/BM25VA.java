package Core;

import java.util.List;

public class BM25VA {

    private BM25 bm25;

    public BM25VA (){
        bm25 = new BM25();
    }
    public final double BM25VA_score(double tf,
                                     double numberOfDocuments,
                                     double documentLength,
                                     double queryFrequency,
                                     double documentFrequency,
                                     Document document,
                                     List<Document> documents
    ){
        /* Compute b */
        double averageDocumentLength = getAverageDocumentLength(documents);
        double average_term_frequency = documentLength/averageDocumentLength;
        double mavgtf = computeMeanAverageTermFrequency(documents);
        double b_va = (1 / Math.pow(mavgtf,2)) * (documentLength / document.getTerms().size()) +
                (1 - (1 / mavgtf))*average_term_frequency;
        bm25.setB(b_va);
        return bm25.score(tf,numberOfDocuments,documentLength,getAverageDocumentLength(documents),queryFrequency,documentFrequency);
    }

    private double computeMeanAverageTermFrequency(List<Document> documents){
        double averageDocumentLength = getAverageDocumentLength(documents);
        double sum_avgtf_d = 0;
        for(Document document : documents){
            sum_avgtf_d += document.getRawText().length()/averageDocumentLength;
        }
        return sum_avgtf_d / documents.size();
    }

    private double getAverageDocumentLength(List<Document> documents){
        double sumLength = 0;
        for(Document document : documents){
            sumLength += document.getRawText().length();
        }
            return sumLength / documents.size();
    }
}