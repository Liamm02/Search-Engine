package es.ulpgc.searchengine.finders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.searchengine.Finder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static es.ulpgc.searchengine.DocumentsGetter.getDocuments;
import static es.ulpgc.searchengine.DocumentsGetter.getFolders;

public class MetadataFinder implements Finder {
    String path;
    Map<String, String> metadata;

    public MetadataFinder(Map<String, String> metadata) {
        this.path = "./Datamart/MetaData/";
        this.metadata = metadata;
    }

    public ArrayList<String> findCoincidences() {
        try{
            ArrayList <String> result = new ArrayList<>();
            String[] folders = getFolders(this.path);
            for (String folder:folders){
                Set<String> documents = getDocuments(this.path+"/"+folder);
                for (String doc:documents){
                    String docPath = this.path+"/"+folder+"/"+doc;
                    if (throwCheckers(docPath)) result.add(doc.split("\\.")[0]);
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private boolean throwCheckers(String docPath) throws IOException {
//        System.out.println(checkDate(docPath, metadata));
        return checkDate(docPath) && checkAuthor(docPath);
    }

    private boolean checkAuthor(String docPath) throws IOException {
        if (this.metadata.containsKey("author") &&
                docAuthorGetter(docPath).equals(this.metadata.get("author").toLowerCase())) return true;
        else return !(this.metadata.containsKey("author"));
    }

    private boolean checkDate(String docPath) throws IOException {
        int date = Integer.parseInt(docDateGetter(docPath));
        if (!(this.metadata.containsKey("from") || this.metadata.containsKey("to"))) return true;
        if (this.metadata.containsKey("from") && this.metadata.containsKey("to")){
            return date >= Integer.parseInt(this.metadata.get("from")) &&
                    date <= Integer.parseInt(this.metadata.get("to"));
        }
        else if (this.metadata.containsKey("from") && date >= Integer.parseInt(this.metadata.get("from"))) return true;
        else return this.metadata.containsKey("to") && date <= Integer.parseInt(this.metadata.get("to"));
    }
    private String docDateGetter(String docPath) throws IOException {
        File fileObj = new File(docPath);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> docMetadata = mapper.readValue(fileObj,
                new TypeReference<Map<String, Object>>() {});
        if (docMetadata.get("Release Date").toString().split(" ")[1].length() >= 4){
            return docMetadata.get("Release Date").toString().split(" ")[1];
        }
        else {
            return docMetadata.get("Release Date").toString().split(" ")[2];
        }
    }
    private String docAuthorGetter(String docPath) throws IOException {
        File fileObj = new File(docPath);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> docMetadata = mapper.readValue(fileObj,
                new TypeReference<Map<String, Object>>() {});
        if (docMetadata.containsKey("Author")) return docMetadata.get("Author").toString().toLowerCase();
        else return "";
    }
}
