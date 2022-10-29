package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File_Reader {

    public String Read_File(File file){
        try {
//            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            String content = Files.readString(Path.of(file.toURI()));
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
