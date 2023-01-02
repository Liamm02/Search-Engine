package es.ulpgc.searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopwordsDelete {

    String path = "./Stopwords/English_stopwords.txt";

    public List<String> delete(List<String> words) throws IOException {
        List<String> output = new ArrayList<>(words);
        String stopwordsTxt = Files.readString(Path.of(this.path));
        List<String> stopWords = Arrays.asList(stopwordsTxt.split(","));
        for (String word : words){
            if (stopWords.contains(word)) output.remove(word);
        }

    return output;
    }

}
