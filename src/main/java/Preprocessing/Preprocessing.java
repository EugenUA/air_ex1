package Preprocessing;

import opennlp.tools.stemmer.PorterStemmer;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preprocessing {

    private final static Logger LOGGER = Logger.getLogger(Preprocessing.class);

    public Preprocessing(){}


    /**
     * @brief Makes preprocessing step for single string message
     * @details Preprocessing includes following steps: ->> removal of line delimiters;
     *                                                  ->> deleting of urls and emails;
     *                                                  ->> tokenization;
     *                                                  ->> case folding;
     *                                                  ->> stop words removal;
     *                                                  ->> stemming
     * @author Gruzdev Eugen
     * @date 29.03.2018
     * @param initialMessage message to be preprocessed
     * @return lsit of preprocessed tokens
     */
    public List<String> conductPreprocessing(String initialMessage){

        //List<String> finalWords = new ArrayList<String>();
        List<String> tokenizedWords = new ArrayList<String>();

        /* Deleting line breakers (inclusive new lines at the beginning and at the end of the string */
        initialMessage = initialMessage.trim().replace("\n", "").replace("\r","");

        /* Deleting URLs and EMAILS if they are present in the initial message */
        String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)";
        initialMessage = initialMessage.replaceAll(URL_PATTERN,"").replaceAll(EMAIL_PATTERN,"");

        /* TOKENIZATION and CASE FOLDING */
        tokenizedWords.addAll(Arrays.asList(initialMessage.replaceAll(
                                            "[^a-zA-Z ]", " ").toLowerCase().split("\\s+")));

        /* STOP WORDS REMOVAL */
        // Using stop words list available in resources folder
        try {
            List<String> stopWords = new ArrayList<String>();
            /* Reading stop words file*/
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/stopWords.txt"));
            String line = "";
            while((line = br.readLine()) != null){
                stopWords.add(line);
            }
            /* Removing stop words */
            tokenizedWords.removeAll(stopWords);
        } catch(IOException e) {
            LOGGER.info("stopWords.txt not found!");
        }

        /* STEMMING */
        // using Porter Stemming Algorithm and OpenNLP Library
        PorterStemmer porterStemmer = new PorterStemmer();
        List<String> stemmedWords = new ArrayList<String>();  //resulting set
        for(String word : tokenizedWords){
            stemmedWords.add(porterStemmer.stem(word));
        }

        return stemmedWords;
    }
}
