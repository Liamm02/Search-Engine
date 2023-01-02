package es.ulpgc.searchengine.finders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.searchengine.Finder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WordsFinder implements Finder {
    String path;
    List<String> words;
    Map<String, ArrayList<String>> docMetadata;

    public WordsFinder(List<String> words) {
        this.path = "./Datamart/Inv_Index.json";
        this.words = words;
    }

    public ArrayList<String> findCoincidences() {
        try {
            ArrayList<String> Result = new ArrayList<>();
            File fileObj = new File(this.path);
            ObjectMapper mapper = new ObjectMapper();
            this.docMetadata = mapper.readValue(fileObj, new TypeReference<Map<String, Object>>() {});
            if (generalCheck())  Result = obtain();
            return Result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<String> obtain(){
        ArrayList<String> Docs = this.docMetadata.get(this.words.get(0));
        Object[] FDocs = Docs.toArray();
        ArrayList<String> Files = new ArrayList<>();
        ArrayList<String> Copy = new ArrayList<>();

        for (Object e : FDocs){
            Files.add(e.toString().split("=")[0].substring(1).split("\\.")[0]);
            Copy.add(e.toString().split("=")[0].substring(1).split("\\.")[0]);
        }
        return filter(Files, Copy);
    }

    private ArrayList<String> filter(ArrayList<String> Files, ArrayList<String> Copy) {
        List<Object> FDocs;
        ArrayList<String> Docs;
        int contain = 0;
        for(String search: this.words){
            Docs = this.docMetadata.get(search);
            FDocs = Arrays.asList(Docs.toArray());
            for (String doc : Copy){
                for (Object fdoc : FDocs){
                    if ((fdoc.toString().contains(doc))) contain++;
                }
                if (contain == 0) Files.remove(doc);
                contain = 0;
            }
        }
        return Files;
    }

    public boolean generalCheck(){

        for(String word: this.words){
            if (this.docMetadata.get(word)==null) return false;
        }
        return true;
    }
}
