package org.example;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class File_Writer {


    public void Json_writer(JSONObject json,String jsonPath){
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(jsonPath))) {
            json.write(writer);
            writer.write("\n");
        } catch (Exception ex) {
            System.err.println("Couldn't write json file\n"
                    + ex.getMessage());
        }
    }

}
