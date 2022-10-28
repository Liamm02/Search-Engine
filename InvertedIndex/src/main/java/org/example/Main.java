package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "./";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        checker.Indices_Folder_Checker();

        Document_Normalizer dn = new Document_Normalizer();
        List<String> text = dn.Translator(documents.get(0));
        System.out.println(text.get(0));

        dn.Stopwords_Deleter(text);
    }
}