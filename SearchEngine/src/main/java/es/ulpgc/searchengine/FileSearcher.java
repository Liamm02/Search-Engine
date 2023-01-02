package es.ulpgc.searchengine;

import java.io.File;

public class FileSearcher {

    public static File searchFile(File dir, String name){
        File result = null;
        File[] dirlist  = dir.listFiles();

        assert dirlist != null;
        for (File file : dirlist) {
            if (file.isDirectory()) {
                result = searchFile(file, name);
                if (result != null) break;
            } else if (file.getName().matches(name)) {
                return file;
            }
        }
        return result;
    }
}
