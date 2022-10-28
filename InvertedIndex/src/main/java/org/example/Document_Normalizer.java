package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Document_Normalizer {

    public List<String> Translator(String doc) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("./Documents/"+ doc)));


        String[] lines = text.replaceAll("\\p{Punct}|[×·”“—«»—ºªπ~:+\\[\\\\@^{%(-*|&<`}._=\\]!>;?¿¡#$)/]", " ").toLowerCase().split("\\r?\\n");
        List<String> LinesList = new ArrayList<String>(Arrays.asList(lines));
//        LinesList.removeIf(i -> i.length() == 1);
//
//        LinesList.removeAll(Arrays.asList("", null));
        return LinesList;
    }

    public void Stopwords_Deleter(List<String> LinesList) throws IOException {

        String StopwordsText = new String(Files.readAllBytes(Paths.get("./En_stopwords2.txt")));
        String[] stopwords = StopwordsText.split(",");
        Set<String> StopwordsSet = new HashSet<String>(Arrays.asList(stopwords));

        HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
        Integer line = 1;
        for (String cadena: LinesList) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            String[] words = cadena.split(" ");
            List<String> wordsList = new ArrayList<String>(Arrays.asList(words));
            for (String word: wordsList){
                if (word != ""){
                    if (StopwordsSet.contains(word)){
                        continue;
                    }else{
                        if (map.containsKey(word)){
                            ArrayList<Integer> temp3 = map.get(word);
                            temp3.add(line);
                            map.replace(word,temp3);
                        }else if (!map.containsKey(word)) {
                            temp.add(line);
                            map.put(word,temp);
                        }
                    }
                }
            }
            line += 1;
        }
        System.out.println(map.toString());
    }
}
