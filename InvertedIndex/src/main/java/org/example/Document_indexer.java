package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Document_indexer {

    public void Indexer(HashMap<String, Set<Integer>> Document_map, String doc, String path) throws JsonProcessingException {

        HashMap<String, Set<Integer>> mape = new HashMap<>();
        HashMap<String, Set<Integer>> mape2 = new HashMap<>();
        Set<Integer> list = new HashSet<>();
        list.add(1);
        list.add(1);

        mape.put("68978-0.txt", list);
        mape2.put("doc2", list);

//        JSONObject json = new JSONObject();
//        json.append("guarde", mape);
//        json.append("dog", mape2);


        JsonObject prueba = new JsonObject();


        String resourceName = path+"Inv_Index.json";

        File file = new File(resourceName);
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            if (content.isEmpty()){
                JSONObject json_empty = new JSONObject();
                for (String key : Document_map.keySet()) {
                    HashMap<String, List<Integer>> DOC_VALUE = new HashMap<>();
                    List<Integer> list2 = new ArrayList<>();
                    list2.addAll(Document_map.get(key));
                    Collections.sort(list2);
                    DOC_VALUE.put(doc, list2);
                    json_empty.append(key, DOC_VALUE);
                    //escribir
                }
            } else {
                JSONObject json = new JSONObject(content);
                for (String key : Document_map.keySet()) {
                    HashMap<String, List<Integer>> DOC_VALUE = new HashMap<>();
                    List<Integer> list2 = new ArrayList<>();
                    list2.addAll(Document_map.get(key));
                    Collections.sort(list2);
                    DOC_VALUE.put(doc, list2);

                    if (json.has(key)) {
                        if (json.get(key).toString().contains(doc)) {
                            for(Integer index = 0; index<json.getJSONArray(key).length(); index++){
                                if (json.getJSONArray(key).get(index).toString().contains(doc)){
                                    json.getJSONArray(key).remove(index);
                                }
                                json.append(key, DOC_VALUE);
                            }
                        } else {
                            json.append(key, DOC_VALUE);
                        }
                    }else {
                        json.append(key, DOC_VALUE);
                    }
                }
                System.out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }





//        System.out.println(json);
//        for (String key : Document_map.keySet()) {
//            HashMap<String, List<Integer>> DOC_VALUE = new HashMap<>();
//            List<Integer> list2 = new ArrayList<>();
//            list2.addAll(Document_map.get(key));
//            Collections.sort(list2);
//            DOC_VALUE.put(doc, list2);
//
//            if (json.has(key)) {
//                if (json.get(key).toString().contains(doc)) {
//                    for(Integer index = 0; index<json.getJSONArray(key).length(); index++){
//                        if (json.getJSONArray(key).get(index).toString().contains(doc)){
//                            json.getJSONArray(key).remove(index);
//                        }
//                        json.append(key, DOC_VALUE);
//                    }
//                } else {
//                    json.append(key, DOC_VALUE);
//                }
//            }else{
//                json.append(key, DOC_VALUE);
//            }
//            System.out.println(json);
//            break;
//        }

    }


}
