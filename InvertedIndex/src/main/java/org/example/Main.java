package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "./src/main/resources/";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        checker.Indices_Folder_Checker();

        Document_Normalizer dn = new Document_Normalizer(path);
        List<String> text = dn.Translator(documents.get(5));

        HashMap<String, Set<Integer>> Doc_InvertedIndex = dn.Stopwords_Deleter(text);
        System.out.println(Doc_InvertedIndex);



    }
}