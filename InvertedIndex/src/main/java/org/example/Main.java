package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "./src/main/resources/";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        checker.Indices_Folder_Checker();

        Document_Normalizer dn = new Document_Normalizer(path);
        List<String> text = dn.Translator(documents.get(2));

        HashMap<String, Set<Integer>> Doc_InvertedIndex = dn.Stopwords_Deleter(text);
        Document_indexer di = new Document_indexer();
        di.Indexer(Doc_InvertedIndex,documents.get(2),path);


    }
}