package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Checker {

    public String path;
    public ArrayList<String> documents = new ArrayList<String>();
    public ArrayList<String> MetaData_documents = new ArrayList<String>();

    public Checker(String path){
        this.path = path;
    }

    public ArrayList<String> documents_checker() throws Exception {

        File dir = new File(this.path+"Documents/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                documents.add((child.getName()));
            }
        } else {
            throw new Exception("There are no Documents or Path is not correct.");
        }
        return documents;
    }
    public void Inv_Index_checker() throws IOException {
        File file = new File(this.path+"Inv_Index.json");
        if (! file.exists()){
            Files.createFile(Path.of(this.path + "Inv_Index.json"));
        }
    }

    public ArrayList<String> MetaData_checker() throws Exception {


        File dir = new File(this.path+"MetaData/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing.length <  documents.size()) {
            System.out.println("There is at least 1 document without a Metadata file, we'll use default values.");
        }
        for (File child : directoryListing) {

            MetaData_documents.add((child.getName()));
        }
        return MetaData_documents;
    }


}
