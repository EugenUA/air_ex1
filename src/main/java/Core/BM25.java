package Core;

 /**
  *
  * R(d; q) = BM25(d; q) =  ∑ ( Wt * (k1 + 1) * tf(t,d) 	  (k3 + 1) * tf(t,q) ) + k2 * |q| *  avgdl - dl
         * 							 	    ------------------- * ------------------				------------
         * 								   		K + tf(t,d)			 k3 + tf(t,d)					 avgdl + dl
         *
         *
         * 						K = k1((1 - b) + (b * dl) /avgdl)
  *
  * 	Wt ... term weight based on relevance feedback (RSJ - w(1))
  *     tf(t, d), tf(t, q) ... within term frequencies - document and query
  *     k1, k2, k3, b ... tuning parameters
  *             -> k1 ... governs the importance of within document frequency tf(t,q)
  *             -> k2 ... compensation factor for the high within document frequency values in large documents
  *             -> k3 ... governs the importance of within query frequency tf(t,q)
  *             -> b ... relative importance of within document frequency and document length
  *     dl, avdg = document length and average document length
  */


public class BM25 {

    private final double k_1 = 1.2d;
    private final double k_3 = 8d;

    /* default value */
    private double b = 0.75d;

    /* Return BM25 score */
    public final double score(double tf,
                              double numberOfDocuments,
                              double docLength,
                              double averageDocumentLength,
                              double queryFrequency,
                              double documentFrequency){

        double K = k_1 * ((1-b) + ((b * docLength) / averageDocumentLength));
        double w = (((k_1 + 1d)*tf) / (K + tf));
        w *= (((k_3 + 1)*queryFrequency) / (k_3 + queryFrequency));
        double result = w * Math.log((numberOfDocuments - documentFrequency + 0.5d)/(documentFrequency + 0.5d));
        return result;
    }

     public double getB() {
         return b;
     }

     public void setB(double b) {
         this.b = b;
     }
 }
