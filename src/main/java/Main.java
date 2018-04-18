import Core.Document;

import Core.InvertedIndex;
import Preprocessing.Preprocessing;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader("/Users/admin/Development/TREC8all/untitled.txt"));     //create a list of the files in the collection
        String str;

        List<String> filenames = new ArrayList<>();
        while((str = in.readLine()) != null) {
            filenames.add(str);
        }

        List<Document> documentList = new ArrayList<>();            //create a list of documents to be indexed
        Preprocessing preprocessing = new Preprocessing();

        for (String filename : filenames) {     //Iterate through all the files in the collections and add them to the document list with a unique ID
            Iterator<Document> docIterator = new Iterator<Document>() {
                BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
                boolean eof = false;

                @Override
                public boolean hasNext() {
                    return !eof;
                }

                @Override
                public Document next() {
                    Document document = new Document(null);
                    StringBuilder sb = new StringBuilder();
                    try {
                        String line;
                        Pattern docno_tag = Pattern.compile("<DOCNO>\\s*(\\S+)\\s*<");
                        boolean in_doc = false;
                        while (true) {
                            line = br.readLine();
                            if (line == null) {
                                eof = true;
                                break;
                            }
                            if (!in_doc) {
                                if (line.startsWith("<DOC>"))
                                    in_doc = true;
                                else
                                    continue;
                            }
                            if (line.startsWith("</DOC>")) {
                                sb.append(line);
                                break;
                            }

                            Matcher m = docno_tag.matcher(line);
                            if (m.find()) {
                                document.setId(m.group(1));
                            }

                            sb.append(line);
                        }
                        if (sb.length() > 0)
                            document.setRawText(sb.toString());

                    } catch (IOException e) {
                        document = null;
                    }
                    return document;
                }
            };
            while (docIterator.hasNext()) {
                Document document = docIterator.next();
                if (document != null && document.getRawText() != null) {
                    document.setTerms(preprocessing.tokenize((document.getRawText()), args));
                    documentList.add(document);
                }
            }
        }

        InvertedIndex index = new InvertedIndex(documentList);
        System.out.println("Number of documents: " + index.size());
        System.out.println("Number of terms: " + index.numberOfTerms());
    }
}
