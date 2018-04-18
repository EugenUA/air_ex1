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
                                     List<Integer> lengthList,
                                     double averageDocumentLength
    ){

        /* Compute b */
        double average_term_frequency = documentLength/averageDocumentLength;
        double mavgtf = computeMeanAverageTermFrequency(averageDocumentLength, lengthList, numberOfDocuments);
        double b_va = (1 / Math.pow(mavgtf,2)) * (documentLength / documentLength) +    //TODO: must be number of terms
                (1 - (1 / mavgtf))*average_term_frequency;

        bm25.setB(b_va);
        return bm25.score(tf,numberOfDocuments,documentLength,averageDocumentLength,queryFrequency,documentFrequency);
    }

    private double computeMeanAverageTermFrequency(double averageDocumentLength, List<Integer> lengthList, double corpusSize){
        double sum_avgtf_d = 0;
        for(int length : lengthList) {
            sum_avgtf_d += length/averageDocumentLength;
        }
        return sum_avgtf_d / corpusSize;
    }
}