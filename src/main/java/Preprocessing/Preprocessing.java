package Preprocessing;

import opennlp.tools.stemmer.PorterStemmer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Preprocessing {
    private boolean removeSpecial = false;
    private boolean caseFolding = false;
    private boolean stopWords = false;
    private boolean stemming = false;

    public Preprocessing(){}

    public List<String> tokenize(String content, String[] args) {
        for (String arg: args) {
            switch (arg) {
                case "sc":
                    removeSpecial = true;
                    break;
                case "cf":
                    caseFolding = true;
                case "sw":
                    stopWords = true;
                case "st":
                    stemming = true;
            }
        }

        Scanner in = new Scanner(content);
        List<String> tokens = new ArrayList<>();
        while (in.hasNext()) {
            String s = in.next();
            s = s.replaceAll("[^a-zA-Z ]", " ");
            if (!s.equals(" ")) {
                tokens.add(s);
            }
        }

        return textToTerms(tokens);
    }

    private List<String> textToTerms(List<String> tokens) {
        if (tokens.isEmpty()) {
            return new ArrayList<>();
        }

        if (removeSpecial) {
            String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            String EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)";
            for (String token : tokens) {
                token.replaceAll(URL_PATTERN,"").replaceAll(EMAIL_PATTERN,"");
            }
        }

        if (caseFolding) {
            tokens.replaceAll(String::toLowerCase);
        }

        if (stopWords) {
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
                e.printStackTrace();
            }
        }


        if (stemming) {
            // using Porter Stemming Algorithm and OpenNLP Library
            List<String> terms = new ArrayList<>();
            PorterStemmer porterStemmer = new PorterStemmer();
            for (String str : tokens) {
                terms.add(porterStemmer.stem(str));
            }
            return terms;
        }
        return tokens;

    }
}
