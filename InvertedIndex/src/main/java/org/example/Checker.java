package org.example;

import java.io.File;
import java.util.ArrayList;

public class Checker {

    public String path;
    public ArrayList<String> documents = new ArrayList<String>();

    public Checker(String path){
        this.path = path;
    }

    public ArrayList<String> documents_checker(){

        File dir = new File(this.path+"Documents/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                documents.add((child.getName()));
            }
        } else {
            System.out.println("There are no Documents or Path is not correct.");
        }
        return documents;
    }
    public void Indices_Folder_Checker(){
        File directory = new File(this.path+"Indices/");
        if (! directory.exists()){
            directory.mkdir();
        }
    }


}
