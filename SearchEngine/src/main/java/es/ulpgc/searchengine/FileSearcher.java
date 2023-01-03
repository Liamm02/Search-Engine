package es.ulpgc.searchengine;

import java.io.File;

public class FileSearcher {

    public static File searchFile(File dir, String name){
        File result = null;
        File[] dirList  = dir.listFiles();

        assert dirList != null;
        for (File file : dirList) {
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
