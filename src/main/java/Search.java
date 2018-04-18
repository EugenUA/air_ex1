import Core.BM25;
import Core.BM25VA;
import Core.TfIdf;
import Core.Topic;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;


public class Search {
    public static void main(String[] args) throws Exception {
        List<Topic> topicList = new ArrayList<>();

        try {
            String filename = args[0];
            Iterator<Topic> topicIterator = new Iterator<Topic>() {
                BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
                boolean eof = false;

                @Override
                public boolean hasNext() {
                    return !eof;
                }

                @Override
                public Topic next() {
                    Topic topic = new Topic();
                    StringBuilder sb = new StringBuilder();
                    try {
                        String line;
                        boolean in_doc = false;
                        while (true) {
                            line = br.readLine();
                            if (line == null) {
                                eof = true;
                                break;
                            }
                            if (line.startsWith("<num>")) {
                                topic.setId(line.substring(14,17));
                            }

                            if (!in_doc) {
                                if (line.startsWith("<title>"))
                                    in_doc = true;
                                else
                                    continue;
                            }
                            if (line.startsWith("<desc>")) {
                                //sb.append(line);
                                break;
                            }

                            sb.append(line);
                        }
                        if (sb.length() > 0)
                            topic.setTerms(sb.toString().replaceAll("<title> " , ""));

                    } catch (IOException e) {
                        topic = null;
                    }
                    return topic;
                }
            };
            while (topicIterator.hasNext()) {
                Topic topic = topicIterator.next();
                if (topic != null && !topic.isEmpty()) {
                    topicList.add(topic);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        TfIdf tfIdf;
        BM25 bm25;
        BM25VA bm25va;

        String s;
        String s1;
        FileReader fr = new FileReader(new File("index.dat"));
        BufferedReader index = new BufferedReader(fr);

        FileReader docReader = new FileReader(new File("documentList.dat"));
        BufferedReader docList = new BufferedReader(docReader);
        int corpusSize = getCorpusSize("documentList.dat");
        Map<String, Integer> docIdToTfMap = new HashMap<>();
        List<Integer> lengthList = new ArrayList<>();
        // for (Topic topic : topicList) {
        Topic topic = topicList.get(0);
            String[] queryTerms = topic.getTerms().split("\\s*(=>|,|\\s)\\s*");
            while ((s = index.readLine()) != null) {
                for (String queryTerm : queryTerms) {
                    if (s.substring(0, s.indexOf(":")).equals(queryTerm)) {
                        String[] s2 = s.split(":");
                        String[] documentIds = StringUtils.split(s2[1],'|');
                        while ((s1 = docList.readLine()) != null) {
                            for (int i = 0; i < documentIds.length-1; i++) {
                                if ((s1.substring(s1.indexOf(":") +2, s1.lastIndexOf(":")).equals(documentIds[i].substring(1, documentIds[i].lastIndexOf(' ') -1))))  {
                                    String documentId = s1.substring(0, s1.indexOf(":"));
                                    int termFrequency = Integer.valueOf(documentIds[i].substring(documentIds[i].lastIndexOf(' ') + 1));
                                    int documentLength = Integer.valueOf((s1.substring(s1.lastIndexOf(":") +2, s1.length())));
                                    docIdToTfMap.put(documentId, termFrequency);
                                    lengthList.add(documentLength);
                                }
                            }
                        }
                    }
                }
            }
        // }

        switch (args[1]) {
            case "tfidf":
                tfIdf = new TfIdf();
                TreeMap<String, Double> tfIdfTreeSet = new TreeMap<>();
                for (String document : docIdToTfMap.keySet()) {
                    tfIdfTreeSet.put(document, tfIdf.score(docIdToTfMap.get(document), corpusSize, docIdToTfMap.size()));
                }

                SortedSet<Map.Entry<String, Double>> tfIdfSorted = rankSearchResults(tfIdfTreeSet);
                int i = 1;
                for (Map.Entry<String, Double> entry : tfIdfSorted) {
                    System.out.println(topic.getId() + " Q0 " + entry.getKey() + " " + i + " " + entry.getValue() + " experiment 626");
                    i++;
                    if (i == 1000) {
                        break;
                    }
                }

                break;
            case "bm25":
                bm25 = new BM25();
                int totalLength = 0;
                for (int length : lengthList) {
                    totalLength += length;
                }
                double averageDocumentLength = totalLength/corpusSize;
                TreeMap<String, Double> bm25TreeSet = new TreeMap<>();
                i = 0;
                for (String document : docIdToTfMap.keySet()) {
                    bm25TreeSet.put(document, bm25.score(docIdToTfMap.get(document), corpusSize, lengthList.get(i), averageDocumentLength, docIdToTfMap.get(document) +1, docIdToTfMap.size()));
                }

                SortedSet<Map.Entry<String, Double>> bm25Sorted = rankSearchResults(bm25TreeSet);
                i = 1;
                for (Map.Entry<String, Double> entry : bm25Sorted) {
                    System.out.println(topic.getId() + " Q0 " + entry.getKey() + " " + i + " " + entry.getValue() + " experiment 626");
                    i++;
                    if (i == 1000) {
                        break;
                    }
                }

                break;
            case "bm25va":
                bm25va = new BM25VA();
                totalLength = 0;
                for (int length : lengthList) {
                    totalLength += length;
                }
                averageDocumentLength = totalLength/corpusSize;
                TreeMap<String, Double> bm25VATreeSet = new TreeMap<>();
                i = 0;
                for (String document : docIdToTfMap.keySet()) {
                    bm25VATreeSet.put(document, bm25va.BM25VA_score(docIdToTfMap.get(document), corpusSize, lengthList.get(i), docIdToTfMap.get(document) +1, docIdToTfMap.size(), lengthList, averageDocumentLength));
                }

                SortedSet<Map.Entry<String, Double>> bm25VASorted = rankSearchResults(bm25VATreeSet);
                i = 1;
                for (Map.Entry<String, Double> entry : bm25VASorted) {
                    System.out.println(topic.getId() + " Q0 " + entry.getKey() + " " + i + " " + entry.getValue() + " experiment 626");
                    i++;
                    if (i == 1000) {
                        break;
                    }
                }

                break;
            default:
                throw new IllegalArgumentException("Incorrect function! Please try again");
        }
    }

    private static int getCorpusSize(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> rankSearchResults(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res= e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
