package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "./src/main/resources/";
        Checker checker = new Checker(path);
        ArrayList<String> documents = checker.documents_checker();
        ArrayList<String> MetaData_documents = checker.MetaData_checker();
        checker.Inv_Index_checker();

        for (String name: documents){
            System.out.println("Indexing document: "+name);
            Document doc = new Document(name,path);
            doc.ReadMetada();
            Document_Normalizer dn = new Document_Normalizer(path);
            List<String> text = dn.Translator(name);

            HashMap<String, Set<Integer>> Doc_InvertedIndex = dn.Stopwords_Deleter(text,doc.Language);
            Document_indexer di = new Document_indexer();
            di.Indexer(Doc_InvertedIndex,name,path);

        }
    }
}