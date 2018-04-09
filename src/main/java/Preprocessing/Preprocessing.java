package Preprocessing;

import Core.Document;
import Core.Term;
import opennlp.tools.stemmer.PorterStemmer;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Preprocessing {

    private final static Logger LOGGER = Logger.getLogger(Preprocessing.class);

    public Preprocessing(){}

    public List<String> tokenize(String content) {
        Scanner in = new Scanner(content);
        List<String> tokens = new ArrayList<>();
        while (in.hasNext()) {
            String s = in.next();
            tokens.add(s);
        }

        return tokens;
    }

    public List<Term> textToTerms(List<String> tokens) {
        if (tokens.isEmpty()) {
            return new ArrayList<>();
        }

        /* if (CASE FOLDING) */
        tokens.replaceAll(String::toLowerCase);

        /* if (STOP WORDS) */
        // Using stop words list available in resources folder
        try {
            List<String> stopWords = new ArrayList<>();
            /* Reading stop words file*/
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/stopWords.txt"));
            String line;
            while((line = br.readLine()) != null){
                stopWords.add(line);
            }
            /* Removing stop words */
            tokens.removeAll(stopWords);
        } catch(IOException e) {
            LOGGER.info("stopWords.txt not found!");
        }

        /* if (STEMMING) */
        // using Porter Stemming Algorithm and OpenNLP Library
        List<Term> terms = new ArrayList<>();
        PorterStemmer porterStemmer = new PorterStemmer();
        int pos = 0;
        for (String str : tokens) {
            String stemmedTerm = porterStemmer.stem(str);
            terms.add(new Term(stemmedTerm, pos));
            pos++;
        }
        return terms;
    }
}
