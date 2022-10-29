package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Document_indexer {

    public void Indexer(HashMap<String, Set<Integer>> Document_map, String doc, String path) throws JsonProcessingException {

        String jsonPath = path+"Inv_Index.json";

        File_Reader fr = new File_Reader();
        File file = new File(jsonPath);
        String content = fr.Read_File(file);
        if (content.isEmpty()){
            JSONObject json = new JSONObject();
            for (String key : Document_map.keySet()) {
                HashMap<String, List<Integer>> DOC_LINES = DOC_LINES_relation_creator(Document_map,key,doc);
                json.append(key, DOC_LINES);

            }
            File_Writer fw = new File_Writer();
            fw.Json_writer(json,jsonPath);
        } else {
            JSONObject json = new JSONObject(content);
            for (String key : Document_map.keySet()) {
                HashMap<String, List<Integer>> DOC_LINES = DOC_LINES_relation_creator(Document_map,key,doc);
                 if (json.has(key)) {
                     if (json.get(key).toString().contains(doc)) {
                         for(Integer index = 0; index<json.getJSONArray(key).length(); index++){
                             if (json.getJSONArray(key).get(index).toString().contains(doc)){
                                 json.getJSONArray(key).remove(index);
                             }
                         }
                         json.append(key, DOC_LINES);
                     } else {
                         json.append(key, DOC_LINES);
                     }
                 }else {
                     json.append(key, DOC_LINES);
                 }
            }
            File_Writer fw = new File_Writer();
            fw.Json_writer(json,jsonPath);
        }
    }
    public HashMap<String,List<Integer>> DOC_LINES_relation_creator(HashMap<String, Set<Integer>> Document_map,String key,String doc){
        HashMap<String, List<Integer>> DOC_LINES = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        list2.addAll(Document_map.get(key));
        Collections.sort(list2);
        DOC_LINES.put(doc, list2);
        return DOC_LINES;
    }


}
